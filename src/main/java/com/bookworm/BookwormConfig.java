package com.bookworm;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("bookworm")
public interface BookwormConfig extends Config
{
	@ConfigItem(
		keyName = "serverUrl",
		name = "Server URL",
		description = "WebSocket relay address (e.g. ws://localhost:3000/plugin-relay)",
		position = 1
	)
	default String serverUrl()
	{
		return "wss://bookworm-production-30a2.up.railway.app/plugin-relay";
	}

	@ConfigItem(
		keyName = "enabled",
		name = "Enable Sync",
		description = "Send data to the Bookworm web app",
		position = 2
	)
	default boolean enabled()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showCollectionPopup",
		name = "Collection log pop-up",
		description = "Show an in-game overlay when a new book is collected",
		position = 3
	)
	default boolean showCollectionPopup()
	{
		return true;
	}

	@ConfigItem(
		keyName = "showChatMessage",
		name = "Chat notification",
		description = "Show a chat message when a new book is collected",
		position = 4
	)
	default boolean showChatMessage()
	{
		return true;
	}
}
