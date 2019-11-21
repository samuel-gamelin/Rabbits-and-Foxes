package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import controller.GameController;
import resources.Resources;
import view.GameView;

/**
 * This class represents the main menu frame of the game.
 * 
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 */
public class MainMenu extends JFrame implements ActionListener {
	private JButton btnStart;
	private JButton btnSelectLevel;
	private JButton btnBuildLevel;
	private JButton btnHelp;
	private JButton btnLoadGameButton;
	private JFileChooser fc = new JFileChooser();

	/**
	 * Creates the main menu GUI.
	 */
	public MainMenu() {
		GUIUtilities.applyDefaults();

		this.setTitle("Rabbits and Foxes Main Menu");
		this.setContentPane(new JLabel(Resources.MAIN_MENU_BACKGROUND));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		btnStart = new JButton("Start");
		btnSelectLevel = new JButton("Select Level");
		btnBuildLevel = new JButton("Level Builder");
		btnLoadGameButton = new JButton("Open Saved Game");
		btnHelp = new JButton("Help");

		this.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 8))), BorderLayout.NORTH);
		this.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 8))), BorderLayout.SOUTH);

		addMainMenuButton(this, btnStart);
		addMainMenuButton(this, btnSelectLevel);
		addMainMenuButton(this, btnLoadGameButton);
		addMainMenuButton(this, btnBuildLevel);
		addMainMenuButton(this, btnHelp);

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
		button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) (0.10 * Resources.SIDE_LENGTH)));
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
			SwingUtilities.invokeLater(new GameView(0));
		} else if (e.getSource() == btnLoadGameButton) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				this.dispose();
				SwingUtilities.invokeLater(new GameView(GameController.openGame(file)));
			}
		} else if (e.getSource() == btnSelectLevel) {
			this.dispose();
			SwingUtilities.invokeLater(LevelSelector::new);
		} else if (e.getSource() == btnHelp) {
			GUIUtilities.displayMessageDialog(this,
					"Start: Starts the game\nLevel Select: Opens the level section menu\nHelp: Displays the help menu\nQuit: Exits the application",
					"Help");
		} else if (e.getSource() == btnBuildLevel) {
			this.dispose();
			SwingUtilities.invokeLater(LevelBuilder::new);
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
