package com.bookworm;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Notification;

@ConfigGroup("bookworm")
public interface BookwormConfig extends Config
{
	@ConfigItem(
		keyName = "serverUrl",
		name = "Server URL",
		description = "WebSocket relay address",
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
		keyName = "showNotification",
		name = "Collection log popup",
		description = "Show an in-game collection log style popup when a new book is collected",
		position = 3
	)
	default Notification showNotification()
	{
		return Notification.ON;
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

	@ConfigItem(
		keyName = "testMode",
		name = "Test mode",
		description = "Fire notifications on every book read without saving — turn off when done testing",
		position = 5
	)
	default boolean testMode()
	{
		return false;
	}
}
