package ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import model.Board;
import resources.Resources;

public class LevelBuilder extends JFrame {
	private JButton[][] buttons;
	
	public LevelBuilder() {
		this.setContentPane(new JLabel(Resources.BOARD));
		this.getContentPane().setLayout(new GridLayout(5, 5));

		JLabel gameContentPane = (JLabel) this.getContentPane();

		// Create all buttons
		buttons = new JButton[5][5];

		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				JButton button = new JButton();
				button.setOpaque(false);
				button.setContentAreaFilled(false);
				buttons[x][y] = button;
				gameContentPane.add(buttons[x][y]);
			}
		}

		Utilities.configureFrame(this);
	}
}
