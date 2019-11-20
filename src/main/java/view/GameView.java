package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

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
import ui.MainMenu;
import ui.Utilities;
import util.Move;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @version 4.0
 */
public class GameView extends JFrame implements ActionListener, BoardListener, MouseListener, Runnable {
	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuQuit;
	private JButton menuHint;
	private JButton menuUndo;
	private JButton menuRedo;
	private JButton menuMainScreen;
	private JButton menuSaveButton; 

	private BevelBorder selectedBorder;
	private BevelBorder hintBorderStart;
	private BevelBorder hintBorderEnd;
	private BevelBorder possiblePositionBorder;

	private EmptyBorder blankBorder;
	private JCheckBox showPossibleMovesBox;
	private JButton[][] buttons;

	private Board board;
	private GameController gameController;
	private JFileChooser fc = new JFileChooser();

	/**
	 * Creates the application GUI.
	 */
	public GameView(int level) {
		// Setting up the borders used for JButtons
		selectedBorder = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
		hintBorderStart = new BevelBorder(BevelBorder.RAISED, Color.YELLOW, Color.YELLOW);
		hintBorderEnd = new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN);
		possiblePositionBorder = new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE);
		blankBorder = new EmptyBorder(0, 0, 0, 0);

		// Create the board and controller
		board = Resources.getLevel(1);
		board.addListener(this);
		gameController = new GameController(board);

		this.updateFrameTitle();

		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		menuMainScreen = createMenuBarButton("Main Menu", true);
		menuHint = createMenuBarButton("Hint", true);
		menuUndo = createMenuBarButton("Undo", true);
		menuRedo = createMenuBarButton("Redo", true);
		menuReset = createMenuBarButton("Reset", false);
		menuSaveButton = createMenuBarButton("Save Game", true);
		menuHelp = createMenuBarButton("Help", false);
		menuQuit = createMenuBarButton("Quit", true);

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

		JLabel gameContentPane = (JLabel) this.getContentPane();

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
					if (showPossibleMovesBox.isSelected()) {
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
		Utilities.bindKeyStroke(gameContentPane, "ESCAPE", "clear", this::clearMove);
		showPossibleMovesBox = new JCheckBox();
		showPossibleMovesBox.addItemListener(e -> {
			showPossibleMovesBox.setSelected(e.getStateChange() == ItemEvent.SELECTED);
		});
		updateView();

		menuMainScreen.addActionListener(this);
		menuReset.addActionListener(this);
		menuHelp.addActionListener(this);
		menuSaveButton.addActionListener(this);
		menuQuit.addActionListener(this);
		menuHint.addActionListener(this);
		menuUndo.addActionListener(this);
		menuRedo.addActionListener(this);

		// Organize the frame
		this.setIconImage(Resources.WINDOW_ICON.getImage());
		Utilities.configureFrame(this);
		this.setGameLevel(level);
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
			Utilities.bindKeyStroke(button, String.valueOf(Character.toLowerCase(text.charAt(0))), text,
					button::doClick);
		}
		return button;
	}

	/**
	 * Pops up the in-game help dialog.
	 */
	private void displayHelpDialog() {
		JPanel panel = new JPanel(new BorderLayout(0, 15));

		panel.add(new JLabel("Show possible moves?"), BorderLayout.CENTER);
		panel.add(showPossibleMovesBox, BorderLayout.EAST);

		panel.add(new JLabel("<html><body><p style='width: 200px; text-align: justify'>"
				+ "Rabbits and Foxes is a game in which you must get all rabbits to safety by having them land in brown holes. "
				+ "To do this, rabbits can only jump over other pieces and must land in an empty hole. "
				+ "Foxes can slide along their initial direction as long as no other piece obstructs their way.<br><br>"
				+ "Hint (h): Outlines the next best move<br>" + "Help: Displays the help menu<br>"
				+ "Reset:   Restarts the game<br>" + "Quit   (q):   Exits the application<br>"
				+ "Escape (ESC): Clears the pending move" + "</p></body></html>"), BorderLayout.NORTH);

		JOptionPane.showMessageDialog(this, panel, "Help Dialog", JOptionPane.INFORMATION_MESSAGE);
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
		this.setTitle("Rabbit and Foxes! Level: " + gameController.getCurrentLevel());
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
							Resources.LOGGER.error("Could not obtain the required field from the Resources class", e);
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
				int choice = Utilities.displayOptionDialog(this,
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
				if (Utilities.displayOptionDialog(this,
						"You have finished the game! Would you like to go to the main menu or exit?", "End Game",
						new String[] { "Main Menu", "Quit" }) == 0) {
					this.dispose();
					SwingUtilities.invokeLater(MainMenu::new);
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
		if (e.getSource() == menuMainScreen
				&& Utilities.displayOptionDialog(null, "Are you sure you want to return to main menu?",
						"Return to Main Menu", new String[] { "Yes", "No" }) == 0) {
			this.dispose();
			SwingUtilities.invokeLater(MainMenu::new);
		} else if (e.getSource() == menuHint) {
			Move bestMove = gameController.getNextBestMove();
			if (!buttons[bestMove.xStart][bestMove.yStart].getBorder().equals(selectedBorder)) {
				buttons[bestMove.xStart][bestMove.yStart].setBorder(hintBorderStart);
			}
			buttons[bestMove.xEnd][bestMove.yEnd].setBorder(hintBorderEnd);
			}
		else if(e.getSource() == menuSaveButton) {
			int returnVal = fc.showSaveDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				gameController.saveGame(file, board);
			}
		}
		else if (e.getSource() == menuHelp) {
			displayHelpDialog();
		} else if ((e.getSource() == menuQuit) && Utilities.displayOptionDialog(this, "Are you sure you want to exit?",
				"Exit Rabbits and Foxes!", new String[] { "Yes", "No" }) == 0) {
			System.exit(0);
		} else if ((e.getSource() == menuReset) && (Utilities.displayOptionDialog(this,
				"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbits and Foxes!",
				new String[] { "Yes", "No" }) == 0)) {
			resetGame();
		} else if (e.getSource() == menuUndo) {
			clearButtonBorders();
			if (!gameController.undoMove()) {
				Utilities.displayMessageDialog(this, "No moves to undo", "Undo Move");
			}
		} else if (e.getSource() == menuRedo) {
			clearButtonBorders();
			if (!gameController.redoMove()) {
				Utilities.displayMessageDialog(this, "No moves to redo", "Redo Move");
			}
		}
	}

	/**
	 * Sets the game level whenever the start button is pressed.
	 */
	private void setGameLevel(int level) {
		gameController.setLevel(level + 1);
		resetGame();
		updateFrameTitle();
		displayHelpDialog();
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

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void run() {
	}
}
