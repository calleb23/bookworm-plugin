package com.bookworm;

import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;

import javax.inject.Inject;
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
	private static final long DISPLAY_MS  = 5000;
	private static final long FADE_IN_MS  = 150;
	private static final long FADE_OUT_MS = 600;
	private static final int  WIDTH       = 260;
	private static final int  TOAST_GAP   = 6;

	// Matched from the OSRS collection log popup screenshot
	private static final Color COL_HEADER_BG    = new Color(0x5a, 0x42, 0x28); // header bar
	private static final Color COL_BODY_BG      = new Color(0x3a, 0x2c, 0x18); // body
	private static final Color COL_BORDER_OUT   = new Color(0x26, 0x1c, 0x0c); // outer edge
	private static final Color COL_BORDER_LIT   = new Color(0xb0, 0x8c, 0x50); // top/left bevel
	private static final Color COL_BORDER_MID   = new Color(0x78, 0x5c, 0x2c); // frame fill
	private static final Color COL_CORNER       = new Color(0xc8, 0xa4, 0x60); // corner ornament
	private static final Color COL_DIVIDER      = new Color(0x78, 0x5c, 0x2c); // header/body line
	private static final Color COL_TITLE        = new Color(0xff, 0xcc, 0x44); // "Bookworm" gold
	private static final Color COL_LABEL        = new Color(0xe8, 0xa8, 0x30); // "New item:" gold
	private static final Color COL_ITEM         = new Color(0xff, 0xf4, 0xe4); // bright warm white

	private final Deque<ToastEntry> toasts = new ArrayDeque<>();

	@Inject
	public BookwormOverlay()
	{
		setPosition(OverlayPosition.TOP_CENTER);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	public void addToast(String bookName)
	{
		toasts.addLast(new ToastEntry(bookName, System.currentTimeMillis()));
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		if (toasts.isEmpty()) return null;

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		final Font titleFont = FontManager.getRunescapeBoldFont();
		final Font labelFont = FontManager.getRunescapeSmallFont();
		final Font itemFont  = FontManager.getRunescapeBoldFont();

		final long now = System.currentTimeMillis();
		final Iterator<ToastEntry> it = toasts.iterator();
		int yOff = 0;

		while (it.hasNext())
		{
			final ToastEntry t = it.next();
			final long age = now - t.createdAt;
			if (age > DISPLAY_MS) { it.remove(); continue; }

			final float alpha = computeAlpha(age);

			g.setFont(titleFont);
			final FontMetrics titleFm = g.getFontMetrics();

			g.setFont(labelFont);
			final FontMetrics labelFm = g.getFontMetrics();

			g.setFont(itemFont);
			final FontMetrics itemFm  = g.getFontMetrics();
			final String      itemStr = truncate(g, t.bookName, WIDTH - 20);

			final int headerH = 6 + titleFm.getHeight() + 6;
			final int bodyH   = 8 + labelFm.getHeight() + 4 + itemFm.getHeight() + 8;
			final int totalH  = headerH + bodyH + 4; // 4 = 2px border top + 2px border bottom

			drawPanel(g, 0, yOff, WIDTH, totalH, headerH, alpha);

			// ── Header: "Bookworm" centred ────────────────────────────────────
			g.setFont(titleFont);
			g.setColor(applyAlpha(COL_TITLE, alpha));
			final String title  = "Bookworm";
			final int    titleW = titleFm.stringWidth(title);
			g.drawString(title, (WIDTH - titleW) / 2, yOff + 2 + 6 + titleFm.getAscent());

			// ── Body text ─────────────────────────────────────────────────────
			int ty = yOff + 2 + headerH + 8;

			g.setFont(labelFont);
			g.setColor(applyAlpha(COL_LABEL, alpha));
			g.drawString("New item:", (WIDTH - labelFm.stringWidth("New item:")) / 2, ty + labelFm.getAscent());
			ty += labelFm.getHeight() + 4;

			g.setFont(itemFont);
			g.setColor(applyAlpha(COL_ITEM, alpha));
			g.drawString(itemStr, (WIDTH - itemFm.stringWidth(itemStr)) / 2, ty + itemFm.getAscent());

			yOff += totalH + TOAST_GAP;
		}

		return yOff > 0 ? new Dimension(WIDTH, yOff) : null;
	}

	/**
	 * Draws a two-section OSRS-style panel matching the collection log popup:
	 * outer dark edge → bevel frame → header bg → divider → body bg.
	 * Corner ornaments drawn at all four corners.
	 */
	private void drawPanel(Graphics2D g, int x, int y, int w, int h, int headerH, float alpha)
	{
		// Outermost 1-px dark edge
		g.setColor(applyAlpha(COL_BORDER_OUT, alpha));
		g.drawRect(x, y, w - 1, h - 1);

		// 2-px bevel frame (inset 1px)
		g.setColor(applyAlpha(COL_BORDER_MID, alpha));
		g.fillRect(x + 1, y + 1, w - 2, h - 2);

		// Bevel highlights — top & left lighter
		g.setColor(applyAlpha(COL_BORDER_LIT, alpha));
		g.drawLine(x + 1, y + 1, x + w - 3, y + 1);   // top
		g.drawLine(x + 1, y + 1, x + 1,     y + h - 3); // left

		// Header background
		g.setColor(applyAlpha(COL_HEADER_BG, alpha));
		g.fillRect(x + 2, y + 2, w - 4, headerH);

		// Divider between header and body
		g.setColor(applyAlpha(COL_DIVIDER, alpha));
		g.fillRect(x + 2, y + 2 + headerH, w - 4, 1);

		// Body background
		g.setColor(applyAlpha(COL_BODY_BG, alpha));
		g.fillRect(x + 2, y + 2 + headerH + 1, w - 4, h - headerH - 5);

		// Corner ornaments (4×4 lighter squares)
		g.setColor(applyAlpha(COL_CORNER, alpha));
		g.fillRect(x + 1,     y + 1,     4, 4); // top-left
		g.fillRect(x + w - 5, y + 1,     4, 4); // top-right
		g.fillRect(x + 1,     y + h - 5, 4, 4); // bottom-left
		g.fillRect(x + w - 5, y + h - 5, 4, 4); // bottom-right
	}

	private float computeAlpha(long age)
	{
		if (age < FADE_IN_MS)
			return (float) age / FADE_IN_MS;
		if (age > DISPLAY_MS - FADE_OUT_MS)
			return 1f - (float)(age - (DISPLAY_MS - FADE_OUT_MS)) / FADE_OUT_MS;
		return 1f;
	}

	private String truncate(Graphics2D g, String text, int maxWidth)
	{
		final FontMetrics fm = g.getFontMetrics();
		if (fm.stringWidth(text) <= maxWidth) return text;
		while (text.length() > 3 && fm.stringWidth(text + "...") > maxWidth)
			text = text.substring(0, text.length() - 1);
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
		final long   createdAt;

		ToastEntry(String bookName, long createdAt)
		{
			this.bookName  = bookName;
			this.createdAt = createdAt;
		}
	}
}
