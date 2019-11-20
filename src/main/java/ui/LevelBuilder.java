package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Board;
import resources.Resources;

public class LevelBuilder extends JFrame {
	private JButton[][] buttons;
	
	public LevelBuilder() {
		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));
		
		JButton yee = new JButton("Yee Yee Yee");
		
		this.add(boardLabel, BorderLayout.CENTER);
		this.add(yee, BorderLayout.EAST);

		// Create all buttons
		buttons = new JButton[5][5];

		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				JButton button = new JButton();
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				buttons[x][y] = button;
				boardLabel.add(buttons[x][y]);
			}
		}

		Utilities.configureFrame(this);
	}
}
