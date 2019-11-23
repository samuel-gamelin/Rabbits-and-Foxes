package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import controller.GameController;
import controller.GameController.ClickValidity;
import model.Board;
import model.BoardListener;
import resources.Resources;
import ui.GUIUtilities;
import ui.MainMenu;
import util.Move;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @author Mohamed Radwan
 * @version 4.0
 */
public class GameView extends JFrame implements ActionListener, BoardListener, MouseListener, Runnable {
	private static final BevelBorder SELECTBORDER = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
	private static final BevelBorder HINTBORDERSTART = new BevelBorder(BevelBorder.RAISED, Color.YELLOW, Color.YELLOW);
	private static final BevelBorder HINTBORDEREND = new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN);
	private static final BevelBorder POSSIBLEPOSITIONBORDER = new BevelBorder(BevelBorder.RAISED, Color.BLUE,
			Color.BLUE);

	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuHint;
	private JButton menuUndo;
	private JButton menuRedo;
	private JButton menuMainScreen;
	private JButton menuSaveButton;

	private JCheckBox showPossibleMovesBox;
	private JButton[][] buttons;

	private Board board;
	private GameController gameController;
	private JFileChooser fc = new JFileChooser();

	/**
	 * Creates the application GUI.
	 * 
	 * @param board          The board that this GameView should have
	 * @param level          The current level of the game. Only applicable to
	 *                       default levels. For user levels, a negative value must
	 *                       be provided.
	 * @param isDefaultLevel True if the level is a default level, false otherwise
	 */
	public GameView(Board board, int level) {
		this.board = board;
		this.board.addListener(this);

		gameController = new GameController(board, level);

		this.updateFrameTitle();
		JMenuBar menuBar = new JMenuBar();

		menuMainScreen = GUIUtilities.createMenuBarButton("Main Menu", true);
		menuHint = GUIUtilities.createMenuBarButton("Hint", true);
		menuUndo = GUIUtilities.createMenuBarButton("Undo", true);
		menuRedo = GUIUtilities.createMenuBarButton("Redo", true);
		menuReset = GUIUtilities.createMenuBarButton("Reset", false);
		menuSaveButton = GUIUtilities.createMenuBarButton("Save Game", true);
		menuHelp = GUIUtilities.createMenuBarButton("Help", false);

		menuBar.add(menuMainScreen);
		menuBar.add(menuHint);
		menuBar.add(menuUndo);
		menuBar.add(menuRedo);
		menuBar.add(menuReset);
		menuBar.add(menuSaveButton);
		menuBar.add(menuHelp);

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
				buttons[x][y].setBorder(GUIUtilities.BLANK_BORDER);

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
							buttons[move.xStart][move.yStart].setBorder(HINTBORDERSTART);
							buttons[move.xEnd][move.yEnd].setBorder(POSSIBLEPOSITIONBORDER);
						});
					}

					if (clickResult == ClickValidity.VALID) {
						buttons[xCopy][yCopy].setBorder(SELECTBORDER);
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
		GUIUtilities.bindKeyStroke(gameContentPane, "ESCAPE", "clear", this::clearMove);
		showPossibleMovesBox = new JCheckBox();
		showPossibleMovesBox.addItemListener(e -> {
			showPossibleMovesBox.setSelected(e.getStateChange() == ItemEvent.SELECTED);
		});
		GUIUtilities.updateView(buttons, board);

		menuMainScreen.addActionListener(this);
		menuReset.addActionListener(this);
		menuHelp.addActionListener(this);
		menuSaveButton.addActionListener(this);
		menuHint.addActionListener(this);
		menuUndo.addActionListener(this);
		menuRedo.addActionListener(this);

		GUIUtilities.configureFrame(this);
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
		GUIUtilities.updateView(buttons, board);
	}

	/**
	 * Updates the game frame's title with the new level name.
	 */
	private void updateFrameTitle() {
		this.setTitle("Rabbit and Foxes! Level: " + board.getName());
	}

	/**
	 * Clears the borders on all buttons.
	 */
	private void clearButtonBorders() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setBorder(GUIUtilities.BLANK_BORDER);
			}
		}
	}

	@Override
	public void handleBoardChange() {
		GUIUtilities.updateView(buttons, board);
		if (board.isInWinningState()) {
			Resources.SOLVED.start();
			clearButtonBorders();

			if (!gameController.isDefaultLevel()) {
				int choice = GUIUtilities.displayOptionDialog(this,
						"Congrats, you solved it! Would you like to go to reset or go to the main menu?", "Solved!",
						new String[] { "Reset", "Main Menu", "Quit" });
				if (choice == 0) {
					resetGame();
				} else if (choice == 1) {
					this.dispose();
					SwingUtilities.invokeLater(MainMenu::new);
				} else {
					System.exit(0);
				}
			} else {
				if (gameController.getCurrentLevel() != Resources.NUMBER_OF_LEVELS) {
					int choice = GUIUtilities.displayOptionDialog(this,
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
					if (GUIUtilities.displayOptionDialog(this,
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
	}

	/**
	 * Handles button input for the menus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuMainScreen
				&& GUIUtilities.displayOptionDialog(null, "Are you sure you want to return to main menu?",
						"Return to Main Menu", new String[] { "Yes", "No" }) == 0) {
			this.dispose();
			SwingUtilities.invokeLater(MainMenu::new);
		} else if (e.getSource() == menuHint) {
			Move bestMove = gameController.getNextBestMove();
			if (!buttons[bestMove.xStart][bestMove.yStart].getBorder().equals(SELECTBORDER)) {
				buttons[bestMove.xStart][bestMove.yStart].setBorder(HINTBORDERSTART);
			}
			buttons[bestMove.xEnd][bestMove.yEnd].setBorder(HINTBORDEREND);
		} else if (e.getSource() == menuSaveButton) {
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				board.saveBoard(fc.getSelectedFile().getAbsolutePath());
			}
		} else if (e.getSource() == menuHelp) {
			displayHelpDialog();
		} else if ((e.getSource() == menuReset) && (GUIUtilities.displayOptionDialog(this,
				"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbits and Foxes!",
				new String[] { "Yes", "No" }) == 0)) {
			resetGame();
		} else if (e.getSource() == menuUndo) {
			clearButtonBorders();
			if (!gameController.undoMove()) {
				GUIUtilities.displayMessageDialog(this, "No moves to undo", "Undo Move");
			}
		} else if (e.getSource() == menuRedo) {
			clearButtonBorders();
			if (!gameController.redoMove()) {
				GUIUtilities.displayMessageDialog(this, "No moves to redo", "Redo Move");
			}
		}
	}

	/**
	 * Highlights a JButton when we enter the component with the mouse cursor.
	 * 
	 * @param e The mouse event that is triggered when the mouse enters the JButton
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		if (((JButton) e.getSource()).getBorder().equals(GUIUtilities.BLANK_BORDER)) {
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
			((JButton) e.getSource()).setBorder(GUIUtilities.BLANK_BORDER);
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
