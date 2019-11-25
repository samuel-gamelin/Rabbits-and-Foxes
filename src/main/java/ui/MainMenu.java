package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import model.Board;
import resources.Resources;
import view.GameView;

/**
 * This class represents the main menu frame of the game.
 *
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 * @version 4.0
 */

public class MainMenu extends JFrame implements ActionListener {
	private JButton btnStart, btnSelectLevel, btnBuildLevel, btnHelp, btnLoadGameButton, btnQuitGame;
	private JFileChooser fc;

	public MainMenu() {
		GUIUtilities.applyDefaults();
		
		fc = new JFileChooser();

		this.setTitle("Main Menu");
		this.setContentPane(new JLabel(Resources.MAIN_MENU_BACKGROUND));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		this.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 8))), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 8))), BorderLayout.SOUTH);

		addMainMenuButton(this, btnStart = new JButton("Start"));
		addMainMenuButton(this, btnSelectLevel = new JButton("Select Level"));
		addMainMenuButton(this, btnLoadGameButton = new JButton("Open Saved Game"));
		addMainMenuButton(this, btnBuildLevel = new JButton("Level Builder"));
		addMainMenuButton(this, btnHelp = new JButton("Help"));
		addMainMenuButton(this, btnQuitGame = new JButton("Quit"));
		
		GUIUtilities.configureFrame(this);
	}

	/**
	 * Adds a button to the specified pane, registering this frame as an
	 * ActionListener.
	 *
	 * @param pane   The pane to which to add the specified button
	 * @param button The button to add and register this pane to as an
	 *               ActionListener
	 */
	private void addMainMenuButton(Container pane, JButton button) {
		button.setMaximumSize(new Dimension((int) (Resources.SIDE_LENGTH / 2.5), (int) (0.10 * Resources.SIDE_LENGTH)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		button.addActionListener(this);
		pane.add(button, BorderLayout.CENTER);
	}

	/**
	 * Handles button input for the menu options.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			this.dispose();
			SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(1), 1));
		} else if (e.getSource() == btnLoadGameButton) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				Board board = Board.loadBoard(fc.getSelectedFile().getAbsolutePath());

				if (board != null) {
					this.dispose();
					SwingUtilities.invokeLater(new GameView(board, -1));
				} else {
					GUIUtilities.displayMessageDialog(this, "Invalid file selection!", "Invalid File");
				}
			}
		} else if (e.getSource() == btnSelectLevel) {
			this.dispose();
			SwingUtilities.invokeLater(LevelSelector::new);
		} else if (e.getSource() == btnHelp) {
			GUIUtilities.displayMessageDialog(this,
					"Start: Starts the game\nSelect Level: Opens the level section menu\nOpen Saved Game: Continue from a previously saved game\nLevel Builder: Opens the level builder\nHelp: Displays the help menu\nQuit: Exits the application",
					"Help");
		} else if (e.getSource() == btnBuildLevel) {
			this.dispose();
			SwingUtilities.invokeLater(LevelBuilder::new);
		} else if (e.getSource() == btnQuitGame) {
		    if (GUIUtilities.displayOptionDialog(this, "Are you sure you want to exit?", "Exit Rabbits and Foxes!",
                    new String[] { "Yes", "No" }) == 0) {
                System.exit(0);
            }
		}
	}

	/**
	 * Starts the Rabbits and Foxes game.
	 *
	 * @param args The command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainMenu::new);
	}
}
