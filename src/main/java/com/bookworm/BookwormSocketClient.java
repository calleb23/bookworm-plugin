package com.bookworm;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

@Slf4j
public class BookwormSocketClient
{
	// Derived client with a 25-second ping interval to keep the connection alive
	// through Railway's proxy (which drops idle WebSocket connections at ~60s).
	private final OkHttpClient httpClient;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

	private WebSocket socket;
	private String url;
	private final AtomicBoolean connected = new AtomicBoolean(false);
	private final AtomicBoolean running = new AtomicBoolean(false);
	private ScheduledFuture<?> reconnectFuture;
	private int reconnectDelaySec = 5;
	private Consumer<String> messageListener;
	private Consumer<Boolean> connectionListener;

	public BookwormSocketClient(OkHttpClient httpClient)
	{
		this.httpClient = httpClient.newBuilder()
			.pingInterval(25, TimeUnit.SECONDS)
			.build();
	}

	public void connect(String url)
	{
		this.url = url;
		running.set(true);
		doConnect();
	}

	private void doConnect()
	{
		if (!running.get()) return;

		Request request = new Request.Builder()
			.url(url)
			.header("x-client-type", "runelite")
			.build();

		socket = httpClient.newWebSocket(request, new WebSocketListener()
		{
			@Override
			public void onOpen(WebSocket ws, Response response)
			{
				log.info("Bookworm: connected to relay at {}", url);
				connected.set(true);
				reconnectDelaySec = 5;
				if (connectionListener != null) connectionListener.accept(true);
			}

			@Override
			public void onMessage(WebSocket ws, String text)
			{
				if (messageListener != null)
				{
					messageListener.accept(text);
				}
			}

			@Override
			public void onFailure(WebSocket ws, Throwable t, Response response)
			{
				connected.set(false);
				log.warn("Bookworm: connection failed — {}", t.getMessage());
				if (connectionListener != null) connectionListener.accept(false);
				scheduleReconnect();
			}

			@Override
			public void onClosed(WebSocket ws, int code, String reason)
			{
				connected.set(false);
				log.info("Bookworm: disconnected ({})", reason);
				if (connectionListener != null) connectionListener.accept(false);
				scheduleReconnect();
			}
		});
	}

	private void scheduleReconnect()
	{
		if (!running.get()) return;
		if (reconnectFuture != null && !reconnectFuture.isDone()) return;

		log.debug("Bookworm: retrying in {}s", reconnectDelaySec);
		reconnectFuture = scheduler.schedule(this::doConnect, reconnectDelaySec, TimeUnit.SECONDS);
		reconnectDelaySec = Math.min(reconnectDelaySec * 2, 30);
	}

	/** Set a listener for messages received from the relay (e.g. requestSync). */
	public void setMessageListener(Consumer<String> listener)
	{
		this.messageListener = listener;
	}

	/** Set a listener for connection state changes (true = connected, false = disconnected). */
	public void setConnectionListener(Consumer<Boolean> listener)
	{
		this.connectionListener = listener;
	}

	/** Send a JSON string to the relay. No-op if not connected. */
	public void send(String json)
	{
		if (connected.get() && socket != null)
		{
			socket.send(json);
		}
	}

	public boolean isConnected()
	{
		return connected.get();
	}

	/** Switch to a new URL — closes current socket and reconnects immediately. */
	public void reconnect(String newUrl)
	{
		this.url = newUrl;
		if (socket != null) socket.close(1000, "URL changed");
		doConnect();
	}

	public void disconnect()
	{
		running.set(false);
		connected.set(false);
		if (reconnectFuture != null) reconnectFuture.cancel(false);
		if (socket != null) socket.close(1000, "Plugin shut down");
		scheduler.shutdownNow();
	}
}
