package view;

import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import controller.GameController;
import model.Board;
import model.BoardEvent;
import model.BoardListener;
import model.Fox;
import model.Piece;
import model.Piece.PieceType;
import resources.Resources;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 * @version 2.0
 */
public class GameView extends JFrame implements BoardListener {
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;

	private JMenuItem menuReset;
	private JMenuItem menuQuit;
	private JMenuItem menuHelp;

	private GridLayout boardLayout;

	private JButton[][] buttons;

	private Board board;
	private Piece piece;

	private GameController gameController;

	public GameView() {
		board = new Board();
		board.addListener(this);

		gameController = new GameController(board);

		this.setContentPane(new JLabel(Resources.BOARD));
		this.setSize(875, 925);
		setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		menuBar = new JMenuBar();

		menuReset = new JMenuItem("Reset");
		menuHelp = new JMenuItem("Help");
		menuQuit = new JMenuItem("Quit");

		menuBar.add(menuReset);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);

		this.setJMenuBar(menuBar);

		boardLayout = new GridLayout(5, 5);
		this.setLayout(boardLayout);

		buttons = new JButton[5][5];

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j] = new JButton();
				piece = board.getPiece(i, j);
				if ((piece != null)) {
					if ((piece.getPieceType()).equals(Piece.PieceType.MUSHROOM))
						(buttons[i][j]).setIcon(Resources.MUSHROOM);
					else if ((piece.getPieceType()).equals(Piece.PieceType.RABBIT))
						(buttons[i][j]).setIcon(Resources.RABBIT1); // change this later to know if its rabbit1 or
																	// rabbit2
					// gotta fix this, we dont know where the head is, left of tail? or right of
					// tail? ontop of tail? under tail? etc.
					else if ((piece.getPieceType()).equals(Piece.PieceType.FOX)) {
						if (((Fox) (piece)).getDirection().equals(Fox.Direction.HORIZONTAL)
								&& ((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
							(buttons[i][j]).setIcon(Resources.FOX_HEAD_RIGHT);
						else if (((Fox) (piece)).getDirection().equals(Fox.Direction.VERTICAL)
								&& ((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
							(buttons[i][j]).setIcon(Resources.FOX_HEAD_UP);
					}
				}
				// clear button default colours and make it transparent
				buttons[i][j].setOpaque(false);
				buttons[i][j].setContentAreaFilled(false);
				buttons[i][j].setBorderPainted(false);
				this.add(buttons[i][j]);
			}
		}

		menuQuit.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		});

		menuHelp.addActionListener(e -> {
			JOptionPane.showMessageDialog(this,
					"Start: Starts the game. \n"
							+ "Pause: Pauses the game, and clicking it again, will resume the game. \n"
							+ "Reset: Restarts the game. \n" + "Quit: Exits the application");
		});

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameView();
			}
		});
	}

	@Override
	public void handleBoardEvent(BoardEvent e) {

	}
}
