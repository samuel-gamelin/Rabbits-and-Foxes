package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import controller.GameController;
import controller.GameController.ClickValidity;
import model.Board;
import model.BoardListener;
import model.Fox;
import model.Mushroom;
import model.Piece;
import model.Rabbit;
import model.Rabbit.RabbitColour;
import resources.Resources;
import util.Move;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @author Samuel Gamelin
 * @version 3.0
 */
public class GameView extends MouseAdapter implements BoardListener, ActionListener {
	private JFrame mainMenuFrame, gameFrame;

	private JButton menuReset, menuHelp, menuQuit, menuHint, menuUndo, menuRedo;
	private JButton btnStart, btnHelp, btnQuit;

	private JCheckBox chkPath;
	private boolean pathSelection;

	private JButton[][] buttons;

	private Board board;

	private GameController gameController;

	private BevelBorder selectedBorder, hintBorderStart, hintBorderEnd, possiblePositionBorder;
	private EmptyBorder blankBorder;

	public static final String GAME_NAME = "Rabbit and Foxes!";

	/**
	 * Creates the application GUI.
	 */
	public GameView() {
		// Forces the look and feel of the application to remain consistent across
		// platforms
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// Removes focus border from all buttons
		UIManager.getLookAndFeelDefaults().put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

		// Setting up the borders used for JButtons
		selectedBorder = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
		hintBorderStart = new BevelBorder(BevelBorder.RAISED, Color.YELLOW, Color.YELLOW);
		hintBorderEnd = new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN);
		possiblePositionBorder = new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE);
		blankBorder = new EmptyBorder(0, 0, 0, 0);

		/**
		 * 
		 * Main menu frame
		 * 
		 */
		mainMenuFrame = new JFrame(GAME_NAME);
		mainMenuFrame.setContentPane(new JLabel(Resources.MAIN_MENU_BACKGROUND));
		mainMenuFrame.getContentPane().setLayout(new BoxLayout(mainMenuFrame.getContentPane(), BoxLayout.Y_AXIS));

		btnStart = new JButton("Start");
		btnHelp = new JButton("Help");
		btnQuit = new JButton("Quit");

		addMainMenuButton(mainMenuFrame, btnStart);
		addMainMenuButton(mainMenuFrame, btnHelp);
		addMainMenuButton(mainMenuFrame, btnQuit);

		mainMenuFrame.setIconImage(Resources.WINDOW_ICON.getImage());
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.pack();
		mainMenuFrame.setLocationRelativeTo(null);
		mainMenuFrame.setVisible(true);

		/**
		 * 
		 * Game frame
		 * 
		 */

		// Create the board and controller
		board = Resources.getLevel(1);
		board.addListener(this);
		gameController = new GameController(board);

		gameFrame = new JFrame(GAME_NAME + " Level: " + gameController.getCurrentLevel());

		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		menuHint = createMenuBarButton("Hint", true);
		menuUndo = createMenuBarButton("Undo", true);
		menuRedo = createMenuBarButton("Redo", true);
		menuReset = createMenuBarButton("Reset", false);
		menuHelp = createMenuBarButton("Help", false);
		menuQuit = createMenuBarButton("Quit", true);

		menuBar.add(menuHint);
		menuBar.add(menuUndo);
		menuBar.add(menuRedo);
		menuBar.add(menuReset);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);

		gameFrame.setJMenuBar(menuBar);
		gameFrame.setContentPane(new JLabel(Resources.BOARD));
		gameFrame.getContentPane().setLayout(new GridLayout(5, 5));

		// Organize the game frame
		gameFrame.setIconImage(Resources.WINDOW_ICON.getImage());
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);

		JLabel gameContentPane = (JLabel) gameFrame.getContentPane();

		// Create all buttons
		buttons = new JButton[5][5];

		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				buttons[x][y] = new JButton();
				// Clear button default colours and make it transparent
				buttons[x][y].setOpaque(false);
				buttons[x][y].setContentAreaFilled(false);
				buttons[x][y].setBorder(blankBorder);

				gameContentPane.add(buttons[x][y]);
				buttons[x][y].addMouseListener(this);

				final int xCopy = x;
				final int yCopy = y;

				// Register an anonymous listener on the button which notifies the controller
				// whenever a move is made (i.e. a button is clicked)
				buttons[x][y].addActionListener(e -> {
					ClickValidity clickResult = gameController.registerClick(xCopy, yCopy);

					// Highlights all possible moves for the selected piece.
					if (chkPath.isSelected()) {
						gameController.getPossibleMoves(xCopy, yCopy).parallelStream().forEach(move -> {
							buttons[move.xStart][move.yStart].setBorder(hintBorderStart);
							buttons[move.xEnd][move.yEnd].setBorder(possiblePositionBorder);
						});
					}

					if (clickResult == ClickValidity.VALID) {
						buttons[xCopy][yCopy].setBorder(selectedBorder);
					} else if (clickResult == ClickValidity.VALID_MOVEMADE) {
						clearButtonBorders();
					} else {
						clearMove();
						if (!Resources.INVALID_MOVE.isActive()) {
							Resources.INVALID_MOVE.start();
						}
					}
				});

			}
		}

		// Configure the escape key to cancel the pending move, setup the check box and
		bindKeyStroke(gameContentPane, "ESCAPE", "clear", this::clearMove);
		chkPath = new JCheckBox();
		chkPath.addItemListener(e -> {
			pathSelection = e.getStateChange() == ItemEvent.SELECTED;
			chkPath.setSelected(pathSelection);
		});
		updateView();

		// Attach action listeners to buttons
		btnStart.addActionListener(e -> {
			mainMenuFrame.setVisible(false);
			gameFrame.setVisible(true);
			displayHelpDialog();
		});
		btnHelp.addActionListener(this);
		btnQuit.addActionListener(this);

		menuReset.addActionListener(this);
		menuHelp.addActionListener(this);
		menuQuit.addActionListener(this);
		menuHint.addActionListener(this);
		menuUndo.addActionListener(this);
		menuRedo.addActionListener(this);
	}

	/**
	 * Adds a button to the specified pane. Used in building the main menu.
	 * 
	 * @param pane   The pane to which to add the specified button
	 * @param button The button to add
	 */
	private void addMainMenuButton(Container pane, JButton button) {
		pane.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 7))));
		button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 2, (int) (0.15 * Resources.SIDE_LENGTH)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(button);
	}

	/**
	 * Creates and returns a JButton suitable for the game's menu bar.
	 * 
	 * @param text The text inside the button
	 * @return The newly created JButton
	 */
	private JButton createMenuBarButton(String text, boolean enableShortcut) {
		JButton button = new JButton("<html><p style='text-align:center;'>" + text + "</p></html>");
		button.setBackground(Color.WHITE);
		button.setBorderPainted(false);

		if (enableShortcut) {
			bindKeyStroke(button, String.valueOf(Character.toLowerCase(text.charAt(0))), text, button::doClick);
		}
		return button;
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
	 * Clears button borders and the pending position.
	 */
	private void clearMove() {
		clearButtonBorders();
		gameController.clearPendingPosition();
	}

	/**
	 * Resets the game.
	 */
	private void resetGame() {
		board = gameController.reset();
		board.addListener(this);
		clearButtonBorders();
		updateView();
	}

	/**
	 * Updates the game frame's title with the new level name.
	 */
	private void updateFrameTitle() {
		gameFrame.setTitle(GAME_NAME + " Level: " + gameController.getCurrentLevel());
	}

	/**
	 * Clears the borders on all buttons.
	 */
	private void clearButtonBorders() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setBorder(blankBorder);
			}
		}
	}

	/**
	 * Updates the visual representation of the board.
	 */
	private void updateView() {
		for (int x = 0; x < Board.SIZE; x++) {
			for (int y = 0; y < Board.SIZE; y++) {
				Piece piece = board.getPiece(x, y);
				if (piece != null) {
					if (piece instanceof Mushroom) {
						buttons[x][y].setIcon(Resources.MUSHROOM);
					} else if (piece instanceof Rabbit) {
						if (((Rabbit) (piece)).getColour() == RabbitColour.BROWN) {
							buttons[x][y].setIcon(Resources.RABBIT1);
						} else if (((Rabbit) (piece)).getColour() == RabbitColour.WHITE) {
							buttons[x][y].setIcon(Resources.RABBIT2);
						} else {
							buttons[x][y].setIcon(Resources.RABBIT3);
						}
					} else {
						try {
							buttons[x][y].setIcon((ImageIcon) Resources.class.getDeclaredField(
									"FOX_" + ((Fox) (piece)).getFoxType() + "_" + ((Fox) (piece)).getDirection())
									.get(Resources.class));
						} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
							e.printStackTrace(System.out);
						}
					}
				} else {
					buttons[x][y].setIcon(null);
				}
			}
		}
	}

	@Override
	public void handleBoardChange() {
		updateView();
		if (board.isInWinningState()) {
			Resources.SOLVED.start();
			clearButtonBorders();

			if (gameController.getCurrentLevel() != Resources.NUMBER_OF_LEVELS) {
				int choice = displayOptionDialog(gameFrame,
						"Congrats, you solved it! Would you like to go to the next puzzle?", "Solved!",
						new String[] { "Next", "Reset", "Quit" });

				if (choice == 0) {
					gameController.incrementLevel();
					updateFrameTitle();
					resetGame();
				} else if (choice == 1) {
					resetGame();
				} else {
					System.exit(0);
				}
			} else {
				if (displayOptionDialog(gameFrame,
						"You have finished the game! Would you like to go to the main menu or exit?", "End Game",
						new String[] { "Main Menu", "Quit" }) == 0) {
					gameController.setToFirstLevel();
					updateFrameTitle();
					resetGame();
					gameFrame.setVisible(false);
					gameFrame.setLocationRelativeTo(null);
					mainMenuFrame.setLocationRelativeTo(null);
					mainMenuFrame.setVisible(true);
				} else {
					System.exit(0);
				}
			}
		}
	}

	/**
	 * Handles button input for the menus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnHelp) {
			displayMessageDialog(mainMenuFrame,
					"Start: Starts the game\nHelp: Displays the help menu\nQuit: Exits the application", "Help");
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
	 * Highlights a JButton when we enter the component with the mouse cursor.
	 * 
	 * @param e The mouse event that is triggered when the mouse enters the JButton
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (((JButton) e.getSource()).getBorder().equals(blankBorder)) {
			((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
		}
	}

	/**
	 * Stops highlighting a JButton when the mouse cursor leaves the component.
	 * 
	 * @param e The mouse event that is triggered when the mouse leaves the JButton
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		if (((JButton) e.getSource()).getBorder().equals(UIManager.getBorder("Button.border"))) {
			((JButton) e.getSource()).setBorder(blankBorder);
		}
	}

	/**
	 * Binds the specified keystroke to the specified JComponent.
	 * 
	 * @param component  The component on which the keystroke should be bound
	 * @param keystroke  The keystroke to bind
	 * @param actionName The name of keystroke action
	 * @param method     The method to execute when the keystroke is activated
	 */
	private void bindKeyStroke(JComponent component, String keystroke, String actionName, Runnable method) {
		if (keystroke.length() == 1) {
			component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keystroke.charAt(0)),
					actionName);
		} else {
			component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keystroke), actionName);
		}
		component.getActionMap().put(actionName, new AbstractAction() {
			/**
			 * Generated Serial ID
			 */
			private static final long serialVersionUID = 2208360823861350604L;

			@Override
			public void actionPerformed(ActionEvent e) {
				method.run();
			}

		});
	}

	/**
	 * Starts the Rabbits and Foxes game.
	 * 
	 * @param args The command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(GameView::new);
	}
}
