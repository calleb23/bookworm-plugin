package com.bookworm;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.Skill;
import net.runelite.api.VarPlayer;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.StatChanged;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.chat.ChatColorType;
import net.runelite.client.chat.ChatMessageBuilder;
import net.runelite.client.chat.ChatMessageManager;
import net.runelite.client.chat.QueuedMessage;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import okhttp3.OkHttpClient;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Bookworm",
	description = "Syncs collected books and skill levels to the Bookworm web app",
	tags = {"bookworm", "books", "tracker", "collection", "sync"}
)
public class BookwormPlugin extends Plugin
{
	@Inject private Client client;
	@Inject private ClientThread clientThread;
	@Inject private ClientToolbar clientToolbar;
	@Inject private ChatMessageManager chatMessageManager;
	@Inject private ConfigManager configManager;
	@Inject private BookwormConfig config;
	@Inject private OkHttpClient okHttpClient;
	@Inject private OverlayManager overlayManager;
	@Inject private BookwormOverlay overlay;

	private BookwormSocketClient socketClient;
	private BookwormPanel panel;
	private NavigationButton navButton;
	private final Gson gson = new Gson();
	private final Set<Integer> collectedBookIds = new HashSet<>();

	// API token stored per RS account — loaded from RSProfile config on startup
	private String apiToken = null;

	// pendingSync: true for one game tick after login — waits for all stats to load
	private boolean pendingSync = false;
	// initialSyncDone: prevents spamming skillUpdate before the full sync is sent
	private boolean initialSyncDone = false;

	@Provides
	BookwormConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BookwormConfig.class);
	}

	@Override
	protected void startUp()
	{
		loadCollectedBooks();
		loadApiToken();

		// ── Sidebar panel ─────────────────────────────────────────────────────
		panel = new BookwormPanel();
		BufferedImage icon;
		try
		{
			icon = ImageUtil.loadImageResource(getClass(), "/bookworm_icon.png");
		}
		catch (Exception e)
		{
			// Fallback: gold square if icon resource is missing
			icon = new BufferedImage(18, 18, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = icon.createGraphics();
			g.setColor(new Color(0xFFD700));
			g.fillRect(0, 0, 18, 18);
			g.dispose();
		}
		navButton = NavigationButton.builder()
			.tooltip("Bookworm")
			.icon(icon)
			.priority(7)
			.panel(panel)
			.build();
		clientToolbar.addNavigation(navButton);
		overlayManager.add(overlay);

		socketClient = new BookwormSocketClient(okHttpClient);
		socketClient.setMessageListener(this::onRelayMessage);
		socketClient.setConnectionListener(connected -> {
			if (panel != null) panel.setRelayStatus(connected);
		});
		if (config.enabled())
		{
			socketClient.connect(config.serverUrl());
		}
		// Plugin enabled mid-session while already logged in
		if (client.getGameState() == GameState.LOGGED_IN)
		{
			pendingSync = true;
		}
	}

	@Override
	protected void shutDown()
	{
		if (socketClient != null) socketClient.disconnect();
		socketClient = null;
		pendingSync = false;
		initialSyncDone = false;
		clientToolbar.removeNavigation(navButton);
		overlayManager.remove(overlay);
	}

	// ── Events ───────────────────────────────────────────────────────────────

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			pendingSync = true;
			initialSyncDone = false;
		}
		else if (event.getGameState() == GameState.LOGIN_SCREEN)
		{
			pendingSync = false;
			initialSyncDone = false;
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		if (!pendingSync) return;
		if (socketClient == null || !socketClient.isConnected()) return;

		pendingSync = false;
		sendFullSync();
		initialSyncDone = true;
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		// Don't send individual updates until the initial full sync is sent,
		// otherwise we'd spam 25 skillUpdate messages on login.
		if (!initialSyncDone || socketClient == null) return;

		String skillName = toWebAppSkillName(event.getSkill());
		int level = event.getLevel();

		Map<String, Object> data = new LinkedHashMap<>();
		data.put("skill", skillName);
		data.put("level", level);
		sendJson("skillUpdate", data);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (!"Read".equalsIgnoreCase(event.getMenuOption())) return;

		int itemId = event.getItemId();
		if (itemId < 0) return;

		Integer bookId = BookItemIds.ITEM_TO_BOOK.get(itemId);
		if (bookId != null)
		{
			bookRead(bookId);
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event)
	{
		if (event.getContainerId() != InventoryID.INVENTORY.getId()) return;
		if (socketClient == null || !initialSyncDone) return;

		for (Item item : event.getItemContainer().getItems())
		{
			if (item.getId() == -1) continue;
			Integer bookId = BookItemIds.ITEM_TO_BOOK.get(item.getId());
			if (bookId != null)
			{
				// Fire notification + sync for books that don't have a "Read" option
				// (e.g. received from chests, rewards, or NPCs).
				boolean isNew = trackBook(bookId);
				if (isNew)
				{
					String bookName = BookItemIds.BOOK_NAMES.getOrDefault(bookId, "Book #" + bookId);
					fireNotifications(bookName);
				}
			}
		}
	}


	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (!"bookworm".equals(event.getGroup()) || socketClient == null) return;

		if ("serverUrl".equals(event.getKey()))
		{
			socketClient.reconnect(config.serverUrl());
		}
		if ("enabled".equals(event.getKey()))
		{
			if (config.enabled()) socketClient.connect(config.serverUrl());
			else socketClient.disconnect();
		}
	}

	// ── Helpers ──────────────────────────────────────────────────────────────

	/** Called on OkHttp's background thread — dispatches incoming relay messages. */
	private void onRelayMessage(String json)
	{
		try
		{
			Map<?, ?> msg = gson.fromJson(json, Map.class);
			String type = (String) msg.get("type");

		if ("assign-token".equals(type))
		{
			// Server assigned us a new API token — save it for future connections
			Map<?, ?> data = (Map<?, ?>) msg.get("data");
			if (data != null && data.get("token") instanceof String)
			{
				apiToken = (String) data.get("token");
				saveApiToken();
				log.debug("Bookworm: received and saved API token");
			}
		}
		else if ("requestSync".equals(type))
		{
			clientThread.invokeLater(() ->
			{
				if (client.getGameState() == GameState.LOGGED_IN)
				{
					sendFullSync();
				}
			});
		}
		}
		catch (Exception e)
		{
			log.debug("Bookworm: failed to parse relay message: {}", e.getMessage());
		}
	}

	private void sendFullSync()
	{
		String playerName = client.getLocalPlayer() != null
			? client.getLocalPlayer().getName()
			: "Unknown";

		// 1. hello (include token so server can authenticate)
		Map<String, Object> helloData = new LinkedHashMap<>();
		helloData.put("playerName", playerName);
		if (apiToken != null) helloData.put("token", apiToken);
		sendJson("hello", helloData);

		// 2. scan current inventory for any book items not yet tracked
		net.runelite.api.ItemContainer inv = client.getItemContainer(InventoryID.INVENTORY);
		if (inv != null)
		{
			for (Item item : inv.getItems())
			{
				if (item.getId() == -1) continue;
				Integer bookId = BookItemIds.ITEM_TO_BOOK.get(item.getId());
				if (bookId != null)
				{
					collectedBookIds.add(bookId);
				}
			}
			saveCollectedBooks();
		}

		// 3. all collected book IDs
		Map<String, Object> booksData = new LinkedHashMap<>();
		booksData.put("bookIds", new ArrayList<>(collectedBookIds));
		sendJson("syncAllBooks", booksData);

		// 4. all skill levels + Quest Points
		Map<String, Integer> skills = new LinkedHashMap<>();
		for (Skill skill : Skill.values())
		{
			if (skill == Skill.OVERALL) continue;
			skills.put(toWebAppSkillName(skill), client.getRealSkillLevel(skill));
		}
		skills.put("Quest Points", client.getVarpValue(VarPlayer.QUEST_POINTS));
		Map<String, Object> skillsData = new LinkedHashMap<>();
		skillsData.put("skills", skills);
		sendJson("syncAllSkills", skillsData);

		// Update sidebar panel
		if (panel != null)
		{
			panel.setPlayerName(playerName);
			List<String> bookNames = new ArrayList<>();
			for (int id : collectedBookIds)
			{
				bookNames.add(BookItemIds.BOOK_NAMES.getOrDefault(id, "Book #" + id));
			}
			bookNames.sort(String::compareToIgnoreCase);
			panel.updateBooks(bookNames, BookItemIds.TOTAL_BOOKS);
		}

		log.debug("Bookworm: full sync sent for {}", playerName);
	}

	/**
	 * Called when the player actively reads a book (via menu click).
	 * Normal mode: notifies + saves only on first collection.
	 * Test mode: always notifies, never saves/syncs (for recording footage).
	 */
	private void bookRead(int bookId)
	{
		String bookName = BookItemIds.BOOK_NAMES.getOrDefault(bookId, "Book #" + bookId);

		if (config.testMode())
		{
			// Test mode: always notify, never persist or sync
			fireNotifications(bookName);
			return;
		}

		// Normal mode: notify and save only if this is a newly collected book
		boolean isNew = trackBook(bookId);
		if (isNew)
		{
			fireNotifications(bookName);
		}
	}

	/**
	 * Silently adds a book to the collected set without firing any notification.
	 * Used by passive inventory scanning and the initial full sync.
	 * Returns true if the book was newly added (first time seen).
	 */
	private boolean trackBook(int bookId)
	{
		boolean isNew = collectedBookIds.add(bookId);
		if (!isNew) return false;

		String bookName = BookItemIds.BOOK_NAMES.getOrDefault(bookId, "Book #" + bookId);
		saveCollectedBooks();

		Map<String, Object> data = new LinkedHashMap<>();
		data.put("bookId", bookId);
		sendJson("bookCollected", data);

		if (panel != null) panel.addBook(bookName);

		log.debug("Bookworm: book {} ({}) collected", bookId, bookName);
		return true;
	}

	private void fireNotifications(String bookName)
	{
		// In-game OSRS-style popup overlay
		if (config.showNotification() != net.runelite.client.config.Notification.OFF)
		{
			overlay.addToast(bookName);
		}

		// Optional chat message
		if (config.showChatMessage())
		{
			String msg = new ChatMessageBuilder()
				.append(ChatColorType.HIGHLIGHT)
				.append("Bookworm: ")
				.append(ChatColorType.NORMAL)
				.append(bookName)
				.build();
			chatMessageManager.queue(QueuedMessage.builder()
				.type(ChatMessageType.GAMEMESSAGE)
				.runeLiteFormattedMessage(msg)
				.build());
		}
	}

	private void sendJson(String type, Object data)
	{
		if (socketClient == null) return;
		Map<String, Object> msg = new LinkedHashMap<>();
		msg.put("type", type);
		msg.put("data", data);
		socketClient.send(gson.toJson(msg));
	}

	private String toWebAppSkillName(Skill skill)
	{
		return skill.getName();
	}

	private void loadCollectedBooks()
	{
		String json = configManager.getRSProfileConfiguration("bookworm", "collected");
		if (json != null && !json.isEmpty())
		{
			Type listType = new TypeToken<List<Integer>>()
			{
			}.getType();
			List<Integer> ids = gson.fromJson(json, listType);
			if (ids != null) collectedBookIds.addAll(ids);
		}
	}

	private void saveCollectedBooks()
	{
		configManager.setRSProfileConfiguration("bookworm", "collected",
			gson.toJson(new ArrayList<>(collectedBookIds)));
	}

	private void loadApiToken()
	{
		apiToken = configManager.getRSProfileConfiguration("bookworm", "apiToken");
	}

	private void saveApiToken()
	{
		if (apiToken != null)
		{
			configManager.setRSProfileConfiguration("bookworm", "apiToken", apiToken);
		}
	}
}
