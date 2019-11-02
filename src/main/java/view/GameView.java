package view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.*;
import controller.GameController;
import model.Board;
import model.BoardListener;
import model.Fox;
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
	private double sideLength = 0.75 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	private JFrame mainMenuFrame;
	private JFrame gameFrame;

	private JMenuBar menuBar;

	private JMenuItem menuReset;
	private JMenuItem menuQuit;
	private JMenuItem menuHelp;

	private JButton btnStart;
	private JButton btnHelp;
	private JButton btnQuit;

	private JButton[][] buttons;

	private Board board;

	private GameController gameController;

	private int[] xy;

	/**
	 * Create the application gui
	 */
	public GameView() {
		// Forces the look and feel of the application to remain consistent across platforms
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
		mainMenuFrame.setSize((int) sideLength, (int) sideLength);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setLocationRelativeTo(null);
		mainMenuFrame.setVisible(true);

		/**
		 * 
		 * Game frame
		 * 
		 */
		gameFrame = new JFrame("Rabbit and Foxes!");

		menuBar = new JMenuBar();
		menuReset = new JMenuItem("Reset");
		menuHelp = new JMenuItem("Help");
		menuQuit = new JMenuItem("Quit");
		menuBar.add(menuReset);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);
		
		// BorderLayout for game frame
		Container gamePane = gameFrame.getContentPane();
		gamePane.setLayout(new BorderLayout());
		gamePane.add(menuBar, BorderLayout.NORTH);

		// GridLayout for board frame
		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));
		gamePane.add(boardLabel, BorderLayout.CENTER);

		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);

		/**
		 * 
		 */
		(board = new Board()).addListener(this);
		gameController = new GameController(board);

		buttons = new JButton[5][5];

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[j][i] = new JButton();
				// Clear button default colours and make it transparent
				buttons[j][i].setOpaque(false);
				buttons[j][i].setContentAreaFilled(false);
				buttons[j][i].setFocusPainted(false);
				boardLabel.add(buttons[j][i]);
				buttons[j][i].addActionListener(this);
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

		xy = new int[2];
	}

	/**
	 * Adds a button to the specified pane. Used in building the menu.
	 * 
	 * @param pane The pane to which to add the specified button
	 * @param button The button to add
	 */
	private void addMenuButton(Container pane, JButton button) {
		pane.add(Box.createRigidArea(new Dimension(0, (int) (0.5 * sideLength / 4))));
		button.setMaximumSize(new Dimension((int) sideLength / 2, (int) (0.15 * sideLength)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(button);
	}

	/**
	 * Updates the visual representation of the board.
	 */
	private void updateView() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				Piece piece = board.getPiece(i, j);
				if ((piece != null)) {
					if ((piece.getPieceType()).equals(Piece.PieceType.MUSHROOM))
						(buttons[i][j]).setIcon(Resources.MUSHROOM);
					else if ((piece.getPieceType()).equals(Piece.PieceType.RABBIT)) {
						if (((Rabbit) (piece)).isColour())
							(buttons[i][j]).setIcon(Resources.RABBIT1);
						else
							(buttons[i][j]).setIcon(Resources.RABBIT2);
					} else if ((piece.getPieceType()).equals(Piece.PieceType.FOX)) {
						if (((Fox) (piece)).getDirection().equals(Fox.Direction.UP)) {
							if (((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_UP);
							else
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_UP);
						} else if (((Fox) (piece)).getDirection().equals(Fox.Direction.DOWN))
							if (((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_UP);
							else
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_UP);
						else if (((Fox) (piece)).getDirection().equals(Fox.Direction.LEFT))
							if (((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_LEFT);
							else
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_LEFT);
						else if (((Fox) (piece)).getDirection().equals(Fox.Direction.RIGHT))
							if (((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
								(buttons[i][j]).setIcon(Resources.FOX_HEAD_RIGHT);
							else
								(buttons[i][j]).setIcon(Resources.FOX_TAIL_RIGHT);
					}
				} else {
					buttons[i][j].setIcon(null);
					buttons[i][j].revalidate();
				}
			}
		}
	}

	/**
	 * 
	 * @param c
	 */
	private void locateButton(Object b) {
		for (int x = 0; x < buttons.length; x++) {
			for (int y = 0; y < buttons.length; y++) {
				if (b.equals(buttons[x][y])) {
					xy[0] = x;
					xy[1] = y;
				}
			}
		}
	}

	//i will add javadoc, false if u want all buttons disabled, true if enabled 
	private void gameEndBoard(boolean state) {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setEnabled(state);
			}
		}
	}
	
	//will add javadoc, this basically resets the game once the game is won
	//eventually when we have levels, they will be notified here. 
	private void gameWinReset(Board board) {
		(this.board = gameController.reset()).addListener(this);
		updateView();
		gameEndBoard(true);
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnHelp || e.getSource() == menuHelp) {
			JOptionPane.showMessageDialog(null,
					"Start: Starts the game. \n" + "Reset: Restarts the game. \n" + "Quit: Exits the application");
		} else if (e.getSource() == btnQuit || e.getSource() == menuQuit) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if (e.getSource() == menuReset) {
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				gameWinReset(this.board); 
			}
		} else if (e.getSource().getClass().equals(JButton.class)) {
			locateButton(e.getSource());
			gameController.registerMove(xy[0], xy[1]);
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
			JOptionPane.showMessageDialog(null, "Congrats! You win!");
			gameWinReset(board); 
		}
	}

	/**
	 * Starts the Rabbits and Foxes game
	 * 
	 * @param args The command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameView());
	}
}
