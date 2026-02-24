package com.bookworm;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BookwormPanel extends PluginPanel
{
	private final JLabel statusLabel  = new JLabel("Not logged in");
	private final JLabel countLabel   = new JLabel("0 books collected");
	private final DefaultListModel<String> bookListModel = new DefaultListModel<>();
	private final JList<String> bookList = new JList<>(bookListModel);

	public BookwormPanel()
	{
		setLayout(new BorderLayout(0, 8));
		setBackground(ColorScheme.DARK_GRAY_COLOR);
		setBorder(new EmptyBorder(10, 10, 10, 10));

		// ── Header ────────────────────────────────────────────────────────────
		JPanel header = new JPanel(new BorderLayout(0, 4));
		header.setBackground(ColorScheme.DARK_GRAY_COLOR);

		JLabel title = new JLabel("Bookworm");
		title.setFont(FontManager.getRunescapeBoldFont());
		title.setForeground(Color.WHITE);

		statusLabel.setFont(FontManager.getRunescapeSmallFont());
		statusLabel.setForeground(Color.GRAY);

		countLabel.setFont(FontManager.getRunescapeSmallFont());
		countLabel.setForeground(new Color(0x90ee90));

		header.add(title,       BorderLayout.NORTH);
		header.add(statusLabel, BorderLayout.CENTER);
		header.add(countLabel,  BorderLayout.SOUTH);

		// ── Book list ─────────────────────────────────────────────────────────
		bookList.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		bookList.setForeground(Color.LIGHT_GRAY);
		bookList.setFont(FontManager.getRunescapeSmallFont());
		bookList.setSelectionBackground(ColorScheme.BRAND_ORANGE_TRANSPARENT);

		JScrollPane scroll = new JScrollPane(bookList);
		scroll.setBackground(ColorScheme.DARKER_GRAY_COLOR);
		scroll.setBorder(BorderFactory.createLineBorder(ColorScheme.MEDIUM_GRAY_COLOR));

		add(header, BorderLayout.NORTH);
		add(scroll,  BorderLayout.CENTER);
	}

	/** Called when the player logs in or the plugin connects. */
	public void setPlayerName(String name)
	{
		SwingUtilities.invokeLater(() ->
			statusLabel.setText(name != null ? "Player: " + name : "Not logged in"));
	}

	/** Replaces the displayed book list with the current collection. */
	public void updateBooks(List<String> bookNames, int totalBooks)
	{
		SwingUtilities.invokeLater(() ->
		{
			bookListModel.clear();
			for (String name : bookNames) bookListModel.addElement(name);
			countLabel.setText(bookNames.size() + " / " + totalBooks + " books collected");
		});
	}

	/** Adds a single book to the displayed list. */
	public void addBook(String bookName)
	{
		SwingUtilities.invokeLater(() ->
		{
			if (!bookListModel.contains(bookName)) bookListModel.addElement(bookName);
		});
	}
}
