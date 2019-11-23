package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import resources.Resources;
import view.GameView;

/**
 * This class represents the level selector for the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @author Samuel Gamelin
 */
public class LevelSelector extends JFrame implements ActionListener {
	private JList<String> listOfLevels;
	private JButton btnStartSelectLevel, btnMainMenu, btnCustomLevels, btnNextPage, btnLastPage;

	public LevelSelector() {
		this.setContentPane(new JLabel(Resources.LEVEL_SELECTOR_BACKGROUND));
		this.getContentPane().setLayout(new BorderLayout());

		btnStartSelectLevel = new JButton("Start");
		formatButton(btnStartSelectLevel);
		btnMainMenu = new JButton("Back to Main Menu");
		formatButton(btnMainMenu);
		btnCustomLevels = new JButton("Go to Custom Levels");
		formatButton(btnCustomLevels);
		btnNextPage = new JButton("Next Page");
		formatButton(btnNextPage);
		btnLastPage = new JButton("Last Page");
		formatButton(btnLastPage);

		btnStartSelectLevel.addActionListener(this);
		btnMainMenu.addActionListener(this);
		btnCustomLevels.addActionListener(this);

		JPanel pageButtons = new JPanel();
		pageButtons.setOpaque(false);
		pageButtons.setLayout(new BoxLayout(pageButtons, BoxLayout.LINE_AXIS));
		pageButtons.add(Box.createHorizontalGlue());
		pageButtons.add(btnLastPage);
		pageButtons.add(Box.createHorizontalGlue());
		pageButtons.add(btnNextPage);
		pageButtons.add(Box.createHorizontalGlue());

		JPanel actionButtons = new JPanel();
		actionButtons.setOpaque(false);
		actionButtons.setLayout(new BoxLayout(actionButtons, BoxLayout.LINE_AXIS));
		actionButtons.add(Box.createHorizontalGlue());
		actionButtons.add(btnMainMenu);
		actionButtons.add(Box.createHorizontalGlue());
		actionButtons.add(btnStartSelectLevel);
		actionButtons.add(Box.createHorizontalGlue());
		actionButtons.add(btnCustomLevels);
		actionButtons.add(Box.createHorizontalGlue());

		JPanel allButtons = new JPanel();
		allButtons.setOpaque(false);
		allButtons.setLayout(new BoxLayout(allButtons, BoxLayout.PAGE_AXIS));
		allButtons.add(pageButtons);
		allButtons.add(Box.createRigidArea(new Dimension(0, (int) Resources.SIDE_LENGTH / 25)));
		allButtons.add(actionButtons);
		allButtons.add(Box.createRigidArea(new Dimension(0, (int) Resources.SIDE_LENGTH / 50)));

		this.add(allButtons, BorderLayout.SOUTH);
		// this.add(padding, BorderLayout.CENTER);

		List<String> allLevels = new ArrayList<>();
		for (int i = 1; i <= Resources.NUMBER_OF_LEVELS; i++) {
			allLevels.add("Level " + i);
		}

		// convert the arraylist to a string array
		String[] arrAllLevels = allLevels.toArray(new String[0]);
		listOfLevels = new JList<>(arrAllLevels);

		listOfLevels.setSelectedIndex(0);
		listOfLevels.setFont(new Font("Times New Roman", Font.PLAIN, 28));

		DefaultListCellRenderer renderer = (DefaultListCellRenderer) listOfLevels.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		renderer.setOpaque(false);

		listOfLevels.setOpaque(false);
		listOfLevels.setForeground(Color.WHITE);

		JPanel padding = new JPanel(new GridBagLayout());
		padding.setBorder(new EmptyBorder(0, 0, 0, 0));
		padding.setOpaque(false);
		padding.add(listOfLevels, new GridBagConstraints());

		this.add(padding, BorderLayout.CENTER);

		this.setTitle("Level Selector");
		GUIUtilities.configureFrame(this);
	}

	/**
	 * Used to
	 * 
	 * @param button
	 */
	private void formatButton(JButton button) {
		button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) (0.20 * Resources.SIDE_LENGTH)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Times New Roman", Font.PLAIN, 32));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStartSelectLevel) {
			this.dispose();
			int level = listOfLevels.getSelectedIndex() == -1 ? 0 : listOfLevels.getSelectedIndex();
			SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level, true));
		} else if (e.getSource() == btnMainMenu) {
			this.dispose();
			SwingUtilities.invokeLater(MainMenu::new)
		}
	}
