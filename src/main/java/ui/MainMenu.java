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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import resources.Resources;
import util.Move;
import view.GameView;

public class MainMenu extends JFrame implements ActionListener {
	private JFrame mainMenuFrame;

	private JButton menuReset, menuHelp, menuQuit, menuHint, menuUndo, menuRedo, menuMainScreen;
	private JButton btnStart, btnHelp, btnQuit, btnSelectLevel, btnStartSelectLevel;

	private BevelBorder selectedBorder, hintBorderStart, hintBorderEnd, possiblePositionBorder;
	private EmptyBorder blankBorder;

	/**
	 * Creates the application GUI.
	 */
	public MainMenu() {
		// Forces the look and feel of the application to remain consistent across
		// platforms
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			Resources.LOGGER.error("Could not set the default look and feel", e);
		}
		// Removes focus border from all buttons
		UIManager.getLookAndFeelDefaults().put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

		/**
		 * 
		 * Main menu frame
		 * 
		 */
		this.setTitle(GameView.GAME_NAME);
		this.setContentPane(new JLabel(Resources.MAIN_MENU_BACKGROUND));
		this.getContentPane().setLayout(new BoxLayout(mainMenuFrame.getContentPane(), BoxLayout.Y_AXIS));

		btnStart = new JButton("Start");
		btnSelectLevel = new JButton("Select Level");
		btnHelp = new JButton("Help");
		btnQuit = new JButton("Quit");

		addMainMenuButton(this, btnStart);
		addMainMenuButton(this, btnSelectLevel);
		addMainMenuButton(this, btnHelp);
		addMainMenuButton(this, btnQuit);

		this.setIconImage(Resources.WINDOW_ICON.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);


		// Attach action listeners to buttons
		btnStart.addActionListener(this);
		btnHelp.addActionListener(this);
		btnQuit.addActionListener(this);
		btnSelectLevel.addActionListener(this);
	}

	/**
	 * Adds a button to the specified pane. Used in building the main menu.
	 * 
	 * @param pane   The pane to which to add the specified button
	 * @param button The button to add
	 */
	private void addMainMenuButton(Container pane, JButton button) {
		pane.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 7))));
		button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) (0.10 * Resources.SIDE_LENGTH)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setForeground(Color.BLACK);
		button.setBackground(Color.WHITE);
		button.setFont(new Font("Times New Roman", Font.PLAIN, 32));
		pane.add(button);
	}

	/**
	 * Displays an informational message dialog.
	 * 
	 * @param parent  The parent component of this option dialog
	 * @param message The message to display
	 * @param title   The title of the dialog box
	 */
	private void displayMessageDialog(Component parent, String message, String title) {
		JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Displays an option dialog, returning the choice selected by the user.
	 * 
	 * @param parent  The parent component of this option dialog
	 * @param message The message to display
	 * @param title   The title of the dialog box
	 * @param options The options to be provided in the dialog - the initial option
	 *                selected is the first element of the provided object array
	 * @return The choice made by the user
	 */
	private int displayOptionDialog(Component parent, String message, String title, Object[] options) {
		return JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
	}

	/**
	 * Pops up the in-game help dialog.
	 */
	private void displayHelpDialog() {
		JPanel panel = new JPanel(new BorderLayout(0, 15));

		panel.add(new JLabel("Show possible moves?"), BorderLayout.CENTER);
		panel.add(chkPath, BorderLayout.EAST);

		panel.add(new JLabel("<html><body><p style='width: 200px; text-align: justify'>"
				+ "Rabbits and Foxes is a game in which you must get all rabbits to safety by having them land in brown holes. "
				+ "To do this, rabbits can only jump over other pieces and must land in an empty hole. "
				+ "Foxes can slide along their initial direction as long as no other piece obstructs their way.<br><br>"
				+ "Hint (h): Outlines the next best move<br>" + "Help: Displays the help menu<br>"
				+ "Reset:   Restarts the game<br>" + "Quit   (q):   Exits the application<br>"
				+ "Escape (ESC): Clears the pending move" + "</p></body></html>"), BorderLayout.NORTH);

		JOptionPane.showMessageDialog(gameFrame, panel, "Help Dialog", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Handles button input for the menus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			setGameLevel(0);
		} else if (e.getSource() == menuMainScreen
				&& displayOptionDialog(null, "Are you sure you want to return to main menu?", "Return to Main Menu",
						new String[] { "Yes", "No" }) == 0) {
			gameFrame.setVisible(false);
			mainMenuFrame.setVisible(true);
		}

		else if (e.getSource() == btnStartSelectLevel) {
			if (listOfLevels.getSelectedIndex() == -1) {
				levelSelectorFrame.setVisible(false);
				setGameLevel(0);
			} else {
				levelSelectorFrame.setVisible(false);
				setGameLevel(listOfLevels.getSelectedIndex());
			}
		} else if (e.getSource() == btnSelectLevel) {
			levelSelectorFrame.setVisible(true);
			mainMenuFrame.setVisible(false);
		} else if (e.getSource() == btnHelp) {
			displayMessageDialog(mainMenuFrame,
					"Start: Starts the game\nLevel Select: Opens the level section menu\nHelp: Displays the help menu\nQuit: Exits the application",
					"Help");
		} else if (e.getSource() == menuHint) {
			Move bestMove = gameController.getNextBestMove();
			if (!buttons[bestMove.xStart][bestMove.yStart].getBorder().equals(selectedBorder)) {
				buttons[bestMove.xStart][bestMove.yStart].setBorder(hintBorderStart);
			}
			buttons[bestMove.xEnd][bestMove.yEnd].setBorder(hintBorderEnd);
		} else if (e.getSource() == menuHelp) {
			displayHelpDialog();
		} else if ((e.getSource() == menuQuit || e.getSource() == btnQuit) && displayOptionDialog(null,
				"Are you sure you want to exit?", "Exit Rabbits and Foxes!", new String[] { "Yes", "No" }) == 0) {
			System.exit(0);
		} else if ((e.getSource() == menuReset) && (displayOptionDialog(gameFrame,
				"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbits and Foxes!",
				new String[] { "Yes", "No" }) == 0)) {
			resetGame();
		} else if (e.getSource() == menuUndo && !gameController.undoMove()) {
			displayMessageDialog(gameFrame, "No moves to undo", "Undo Move");
		} else if (e.getSource() == menuRedo && !gameController.redoMove()) {
			displayMessageDialog(gameFrame, "No moves to redo", "Redo Move");
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
