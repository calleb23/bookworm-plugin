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
	private static final int  WIDTH       = 270;
	private static final int  PAD_X       = 10;
	private static final int  PAD_Y       = 7;
	private static final int  LINE_GAP    = 3;
	private static final int  TOAST_GAP   = 5;

	// OSRS interface colour palette
	private static final Color COL_BG           = new Color(0x1a, 0x12, 0x08, 0xff); // deep brown-black
	private static final Color COL_BORDER_LIT   = new Color(0x9e, 0x7d, 0x44, 0xff); // top/left highlight
	private static final Color COL_BORDER_MID   = new Color(0x5c, 0x44, 0x20, 0xff); // outer frame
	private static final Color COL_BORDER_DARK  = new Color(0x0e, 0x0a, 0x04, 0xff); // bottom/right shadow
	private static final Color COL_DIVIDER      = new Color(0x4a, 0x38, 0x1c, 0xff); // inner divider line
	private static final Color COL_HEADER_TXT   = new Color(0xff, 0x98, 0x1f, 0xff); // OSRS orange
	private static final Color COL_NAME_TXT     = new Color(0xff, 0xff, 0xff, 0xff); // white
	private static final Color COL_COUNT_TXT    = new Color(0xb0, 0x9a, 0x78, 0xff); // muted tan

	private final Deque<ToastEntry> toasts = new ArrayDeque<>();

	@Inject
	public BookwormOverlay()
	{
		setPosition(OverlayPosition.TOP_CENTER);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
	}

	public void addToast(String bookName, int collected, int total)
	{
		toasts.addLast(new ToastEntry(bookName, collected, total, System.currentTimeMillis()));
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		if (toasts.isEmpty()) return null;

		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		final Font headerFont = FontManager.getRunescapeSmallFont();
		final Font nameFont   = FontManager.getRunescapeBoldFont();
		final Font countFont  = FontManager.getRunescapeSmallFont();

		final long now = System.currentTimeMillis();
		final Iterator<ToastEntry> it = toasts.iterator();
		int yOff = 0;

		while (it.hasNext())
		{
			final ToastEntry t = it.next();
			final long age = now - t.createdAt;
			if (age > DISPLAY_MS) { it.remove(); continue; }

			final float alpha = computeAlpha(age);

			// Pre-measure text
			g.setFont(headerFont);
			final FontMetrics hFm  = g.getFontMetrics();
			final int         hH   = hFm.getHeight();

			g.setFont(nameFont);
			final FontMetrics nFm  = g.getFontMetrics();
			final String      name = truncate(g, t.bookName, WIDTH - PAD_X * 2);
			final int         nH   = nFm.getHeight();

			g.setFont(countFont);
			final FontMetrics cFm  = g.getFontMetrics();
			final int         cH   = cFm.getHeight();

			final int innerH = PAD_Y + hH + LINE_GAP + 1 + LINE_GAP + nH + LINE_GAP + cH + PAD_Y;

			drawPanel(g, 0, yOff, WIDTH, innerH, alpha);

			// ── Text ──────────────────────────────────────────────────────────
			int ty = yOff + PAD_Y;

			// Header: "New Bookworm addition!"
			g.setFont(headerFont);
			g.setColor(applyAlpha(COL_HEADER_TXT, alpha));
			g.drawString("New Bookworm addition!", PAD_X, ty + hFm.getAscent());
			ty += hH + LINE_GAP;

			// Thin horizontal divider
			g.setColor(applyAlpha(COL_DIVIDER, alpha));
			g.fillRect(PAD_X, ty, WIDTH - PAD_X * 2, 1);
			ty += 1 + LINE_GAP;

			// Book name (bold, white)
			g.setFont(nameFont);
			g.setColor(applyAlpha(COL_NAME_TXT, alpha));
			g.drawString(name, PAD_X, ty + nFm.getAscent());
			ty += nH + LINE_GAP;

			// Count (small, muted)
			g.setFont(countFont);
			g.setColor(applyAlpha(COL_COUNT_TXT, alpha));
			g.drawString(t.collected + " / " + t.total + " books", PAD_X, ty + cFm.getAscent());

			yOff += innerH + TOAST_GAP;
		}

		return yOff > 0 ? new Dimension(WIDTH, yOff) : null;
	}

	// ── OSRS-style raised panel ───────────────────────────────────────────────
	// Outer frame (2px), inner highlight border (1px), dark fill
	private void drawPanel(Graphics2D g, int x, int y, int w, int h, float alpha)
	{
		// Outer 2-px frame — darker shade on all sides
		g.setColor(applyAlpha(COL_BORDER_MID, alpha));
		g.fillRect(x, y, w, h);

		// 1-px outer shadow on bottom and right
		g.setColor(applyAlpha(COL_BORDER_DARK, alpha));
		g.drawLine(x,         y + h - 1, x + w - 1, y + h - 1); // bottom
		g.drawLine(x + w - 1, y,         x + w - 1, y + h - 1); // right

		// 1-px highlight on top and left
		g.setColor(applyAlpha(COL_BORDER_LIT, alpha));
		g.drawLine(x,     y, x + w - 2, y    ); // top
		g.drawLine(x, y + 1, x,         y + h - 2); // left

		// Inner background (2px inset)
		g.setColor(applyAlpha(COL_BG, alpha));
		g.fillRect(x + 2, y + 2, w - 4, h - 4);

		// Narrow orange accent strip at very top of inner area
		g.setColor(applyAlpha(new Color(0xff, 0x98, 0x1f, 0x60), alpha));
		g.fillRect(x + 2, y + 2, w - 4, 2);
	}

	private float computeAlpha(long age)
	{
		if (age < FADE_IN_MS)  return (float) age / FADE_IN_MS;
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
		final int    collected;
		final int    total;
		final long   createdAt;

		ToastEntry(String bookName, int collected, int total, long createdAt)
		{
			this.bookName  = bookName;
			this.collected = collected;
			this.total     = total;
			this.createdAt = createdAt;
		}
	}
}
