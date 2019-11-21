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
 */
public class LevelSelector extends JFrame implements ActionListener {
	private JList<String> listOfLevels;
	private JButton btnStartSelectLevel;

	public LevelSelector() {
		this.setContentPane(new JLabel(Resources.LEVEL_SELECTOR_BACKGROUND));
		this.getContentPane().setLayout(new BorderLayout());

		btnStartSelectLevel = new JButton("Start");

		this.add(btnStartSelectLevel, BorderLayout.SOUTH);
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

		btnStartSelectLevel = new JButton("Start");
		btnStartSelectLevel
				.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) (0.20 * Resources.SIDE_LENGTH)));
		btnStartSelectLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnStartSelectLevel.setForeground(Color.BLACK);
		btnStartSelectLevel.setBackground(Color.WHITE);
		btnStartSelectLevel.setFont(new Font("Times New Roman", Font.PLAIN, 32));

		JPanel padding = new JPanel(new GridBagLayout());
		padding.setBorder(new EmptyBorder(0, 0, 0, 0));
		padding.setOpaque(false);
		padding.add(listOfLevels, new GridBagConstraints());

		this.add(btnStartSelectLevel, BorderLayout.SOUTH);
		this.add(padding, BorderLayout.CENTER);

		btnStartSelectLevel.addActionListener(this);

		this.setTitle("Level Selector");
		GUIUtilities.configureFrame(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStartSelectLevel) {
			this.dispose();
			SwingUtilities.invokeLater(
					new GameView(listOfLevels.getSelectedIndex() == -1 ? 0 : listOfLevels.getSelectedIndex()));
		}

	}
}
