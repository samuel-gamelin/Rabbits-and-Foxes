package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.GameController;
import model.Board;
import model.BoardListener;
import model.Fox;
import model.Fox.Direction;
import model.Mushroom;
import model.Piece;
import model.Rabbit;
import resources.Resources;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @version 2.0
 */
public class GameView implements BoardListener, ActionListener {
	private JFrame mainMenuFrame;
	private JFrame gameFrame;

	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuQuit;

	private JButton btnStart;
	private JButton btnHelp;
	private JButton btnQuit;

	private JButton[][] buttons;

	private Board board;

	private GameController gameController;

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

		/**
		 * 
		 * Main menu
		 * 
		 */
		mainMenuFrame = new JFrame("Rabbit and Foxes!");

		// Box Layout for main menu
		Container mainMenuPane = mainMenuFrame.getContentPane();
		mainMenuPane.setLayout(new BoxLayout(mainMenuPane, BoxLayout.Y_AXIS));

		btnStart = new JButton("Start");
		btnHelp = new JButton("Help");
		btnQuit = new JButton("Quit");
		addMenuButton(mainMenuPane, btnStart);
		addMenuButton(mainMenuPane, btnHelp);
		addMenuButton(mainMenuPane, btnQuit);

		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setSize((int) Resources.SIDE_LENGTH, (int) Resources.SIDE_LENGTH);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setLocationRelativeTo(null);
		mainMenuFrame.setVisible(true);

		/**
		 * 
		 * Game frame
		 * 
		 */
		gameFrame = new JFrame("Rabbit and Foxes!");
		// BorderLayout for game frame
		Container gamePane = gameFrame.getContentPane();
		gamePane.setLayout(new BorderLayout());

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuReset = createMenuBarButton("Reset"));
		menuBar.add(menuHelp = createMenuBarButton("Help"));
		menuBar.add(menuQuit = createMenuBarButton("Quit"));

		gamePane.add(menuBar, BorderLayout.NORTH);

		// GridLayout for board frame
		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));
		gamePane.add(boardLabel, BorderLayout.CENTER);

		// Organize the game frame
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);

		// Create the board and controller
		(board = new Board()).addListener(this);
		gameController = new GameController(board);

		// Create all buttons
		buttons = new JButton[5][5];

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[j][i] = new JButton();
				// Clear button default colours and make it transparent
				buttons[j][i].setOpaque(false);
				buttons[j][i].setContentAreaFilled(false);
				buttons[j][i].setFocusPainted(false);
				boardLabel.add(buttons[j][i]);

				// Register an anonymous listener on the button which notifies the controller
				// whenever a move is made (i.e. a button is clicked)
				final int x = j;
				final int y = i;
				buttons[j][i].addActionListener(e -> {
					gameController.registerMove(x, y);
				});
			}
		}

		updateView();

		menuReset.addActionListener(this);
		btnHelp.addActionListener(this);
		menuHelp.addActionListener(this);
		btnQuit.addActionListener(this);
		menuQuit.addActionListener(this);

		btnStart.addActionListener(e -> {
			mainMenuFrame.dispose();
			gameFrame.setVisible(true);
		});
	}

	/**
	 * Adds a button to the specified pane. Used in building the menu.
	 * 
	 * @param pane   The pane to which to add the specified button
	 * @param button The button to add
	 */
	private void addMenuButton(Container pane, JButton button) {
		pane.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 8))));
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
	private JButton createMenuBarButton(String text) {
		JButton button = new JButton("<html><p style='text-align:center;'>" + text + "</p></html>");
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(KeyStroke.getKeyStroke(Character.toLowerCase(text.charAt(0))), text);
		button.getActionMap().put(text, new AbstractAction() {
			private static final long serialVersionUID = -3061675265827831267L;

			@Override
			public void actionPerformed(ActionEvent e) {
				button.doClick();
			}
		});
		return button;
	}

	/**
	 * Updates the visual representation of the board.
	 */
	private void updateView() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				Piece piece = board.getPiece(i, j);
				if (piece != null) {
					if (piece instanceof Mushroom) {
						(buttons[i][j]).setIcon(Resources.MUSHROOM);
					} else if (piece instanceof Rabbit) {
						if (((Rabbit) (piece)).isColour()) {
							(buttons[i][j]).setIcon(Resources.RABBIT1);
						} else {
							(buttons[i][j]).setIcon(Resources.RABBIT2);
						}
					} else if (piece instanceof Fox) {
						Direction direction = ((Fox) (piece)).getDirection();
						boolean isHead = ((Fox) (piece)).getFoxType().equals(Fox.FoxType.HEAD);
						if (direction.equals(Fox.Direction.UP)) {
							if (isHead) {
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_UP);
							} else {
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_UP);
							}
						} else if (direction.equals(Fox.Direction.DOWN))
							if (isHead) {
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_DOWN);
							} else {
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_DOWN);
							}
						else if (direction.equals(Fox.Direction.LEFT))
							if (isHead) {
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_LEFT);
							} else {
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_LEFT);
							}
						else if (direction.equals(Fox.Direction.RIGHT))
							if (isHead) {
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_RIGHT);
							} else {
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_RIGHT);
							}
					}
				} else {
					buttons[i][j].setIcon(null);
					//buttons[i][j].revalidate();
				}
			}
		}
	}

	/**
	 * Enables or disables all buttons, depending on the specified state.
	 * 
	 * @param state The state in which all buttons should be set
	 */
	private void gameEndBoard(boolean state) {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setEnabled(state);
			}
		}
	}

	/**
	 * Resets the game.
	 */
	private void gameWinReset() {
		(this.board = gameController.reset()).addListener(this);
		updateView();
		gameEndBoard(true);
	}

	/**
	 * Handles button input for the menus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnHelp || e.getSource() == menuHelp) {
			JOptionPane.showMessageDialog(null, "Start (s): Starts the game. \n" + "Reset (r): Restarts the game. \n"
					+ "Quit (q): Exits the application", "Help", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == btnQuit || e.getSource() == menuQuit) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if (e.getSource() == menuReset) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameWinReset();
			}
		}
	}

	/**
	 * Updates whenever the Board changes. The View must be updated to reflect the
	 * new state of the Board.
	 */
	@Override
	public void handleBoardChange() {
		updateView();
		if (board.isInWinningState()) {
			gameEndBoard(false);
			JOptionPane.showMessageDialog(null, "Congrats! You win!");
			gameWinReset();
		}
	}

	/**
	 * Starts the Rabbits and Foxes game.
	 * 
	 * @param args The command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameView());
	}
}
