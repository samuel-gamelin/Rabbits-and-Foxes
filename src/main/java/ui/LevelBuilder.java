package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import model.Board;
import resources.Resources;

/**
 * This class represents a level builder which allows the user to create and
 * save their own levels.
 * 
 * @author Abdalla El Nakla
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 */
public class LevelBuilder extends JFrame {
	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuQuit;
	private JButton menuHint;
	private JButton menuUndo;
	private JButton menuRedo;
	private JButton menuMainScreen;
	private JButton menuSaveButton;

	private Board board;
	private JButton[][] buttons;

	public LevelBuilder() {
		this.board = new Board();

		this.setTitle("Level Builder");
		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));

		this.add(boardLabel, BorderLayout.CENTER);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		menuMainScreen =  ui.GUIUtilities.createMenuBarButton("Main Menu", true);
		menuHint =  ui.GUIUtilities.createMenuBarButton("Hint", true);
		menuUndo =  ui.GUIUtilities.createMenuBarButton("Undo", true);
		menuRedo =  ui.GUIUtilities.createMenuBarButton("Redo", true);
		menuReset = ui.GUIUtilities.createMenuBarButton("Reset", false);
		menuSaveButton =  ui.GUIUtilities.createMenuBarButton("Save Game", true);
		menuHelp =  ui.GUIUtilities.createMenuBarButton("Help", false);
		menuQuit =  ui.GUIUtilities.createMenuBarButton("Quit", true);

		menuBar.add(menuMainScreen);
		menuBar.add(menuHint);
		menuBar.add(menuUndo);
		menuBar.add(menuRedo);
		menuBar.add(menuReset);
		menuBar.add(menuSaveButton);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);

		this.setJMenuBar(menuBar);
		this.setContentPane(new JLabel(Resources.BOARD));
		this.getContentPane().setLayout(new GridLayout(5, 5));

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

		GUIUtilities.configureFrame(this);
	}

	// Added for testing frame
	public static void main(String[] args) {
		SwingUtilities.invokeLater(LevelBuilder::new);
	}
}
