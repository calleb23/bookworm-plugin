package com.bookworm;

import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class BookwormOverlay extends Overlay
{
	private static final long DISPLAY_MS = 4500;
	private static final long FADE_MS    = 700;
	private static final int  WIDTH      = 230;
	private static final int  BOX_H      = 62;
	private static final int  GAP        = 5;
	private static final int  PAD        = 9;

	private static final Color COL_BG     = new Color(30, 22, 8, 215);
	private static final Color COL_BORDER = new Color(255, 215, 0, 255);
	private static final Color COL_GOLD   = new Color(255, 215, 0, 255);
	private static final Color COL_SUB    = new Color(195, 170, 110, 255);

	private static final Font FONT_HEADER = new Font("RuneScape Small", Font.PLAIN, 13);
	private static final Font FONT_NAME   = new Font("RuneScape Small", Font.BOLD,  15);
	private static final Font FONT_COUNT  = new Font("RuneScape Small", Font.PLAIN, 12);

	private final Deque<ToastEntry> toasts = new ArrayDeque<>();

	@Inject
	public BookwormOverlay()
	{
		setPosition(OverlayPosition.TOP_CENTER);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	/** Called from the plugin (on the client thread) when a new book is collected. */
	public void addToast(String bookName, int collected, int total)
	{
		toasts.addLast(new ToastEntry(bookName, collected, total, System.currentTimeMillis()));
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		if (toasts.isEmpty()) return null;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		long now = System.currentTimeMillis();
		Iterator<ToastEntry> it = toasts.iterator();
		int yOff = 0;

		while (it.hasNext())
		{
			ToastEntry t = it.next();
			long age = now - t.createdAt;
			if (age > DISPLAY_MS) { it.remove(); continue; }

			float alpha = age > (DISPLAY_MS - FADE_MS)
				? 1f - (float)(age - (DISPLAY_MS - FADE_MS)) / (float) FADE_MS
				: 1f;
			alpha = Math.max(0f, Math.min(1f, alpha));

			// Background
			g.setColor(applyAlpha(COL_BG, alpha));
			g.fillRoundRect(0, yOff, WIDTH, BOX_H, 8, 8);

			// Gold border
			g.setColor(applyAlpha(COL_BORDER, alpha));
			g.setStroke(new BasicStroke(1.5f));
			g.drawRoundRect(0, yOff, WIDTH, BOX_H, 8, 8);

			// Header line
			g.setFont(FONT_HEADER);
			g.setColor(applyAlpha(COL_SUB, alpha));
			g.drawString("New book added to collection!", PAD, yOff + PAD + 13);

			// Book name (truncate if too long)
			g.setFont(FONT_NAME);
			g.setColor(applyAlpha(COL_GOLD, alpha));
			String name = truncate(g, t.bookName, WIDTH - PAD * 2);
			g.drawString(name, PAD, yOff + PAD + 31);

			// Count
			g.setFont(FONT_COUNT);
			g.setColor(applyAlpha(COL_SUB, alpha));
			g.drawString(t.collected + " / " + t.total + " books", PAD, yOff + PAD + 48);

			yOff += BOX_H + GAP;
		}

		return yOff > 0 ? new Dimension(WIDTH, yOff) : null;
	}

	private String truncate(Graphics2D g, String text, int maxWidth)
	{
		FontMetrics fm = g.getFontMetrics();
		if (fm.stringWidth(text) <= maxWidth) return text;
		while (text.length() > 3 && fm.stringWidth(text + "...") > maxWidth)
		{
			text = text.substring(0, text.length() - 1);
		}
		return text + "...";
	}

	private Color applyAlpha(Color c, float alpha)
	{
		return new Color(c.getRed(), c.getGreen(), c.getBlue(),
			Math.max(0, Math.min(255, Math.round(c.getAlpha() * alpha))));
	}

	private static class ToastEntry
	{
		final String bookName;
		final int collected;
		final int total;
		final long createdAt;

		ToastEntry(String bookName, int collected, int total, long createdAt)
		{
			this.bookName  = bookName;
			this.collected = collected;
			this.total     = total;
			this.createdAt = createdAt;
		}
	}
}
