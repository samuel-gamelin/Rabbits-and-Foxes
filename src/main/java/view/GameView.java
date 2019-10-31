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
	private JFrame mainMenuFrame; 
	private JFrame gameFrame; 

	private JMenuBar menuBar;

	private JMenuItem menuReset;
	private JMenuItem menuQuit;
	private JMenuItem menuHelp;

	private GridLayout boardLayout;

	private JButton[][] buttons;

	private Board board;
	private Piece piece;

	private GameController gameController;
	
	private void initialize() {
		mainMenuFrame = new JFrame(); 
		gameFrame = new JFrame(); 
		board = new Board();
		board.addListener(this);

		gameController = new GameController(board);

		gameFrame.setContentPane(new JLabel(Resources.BOARD));
		gameFrame.setSize(875, 925);
		setResizable(false);
		gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mainMenuFrame.setBounds(100, 100, 875, 925);
		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//absolute layout
		mainMenuFrame.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(320, 249, 214, 83);
		mainMenuFrame.getContentPane().add(btnStart);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.setBounds(320, 375, 214, 83);
		mainMenuFrame.getContentPane().add(btnHelp);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(320, 517, 214, 83);
		mainMenuFrame.getContentPane().add(btnQuit);
		
		menuBar = new JMenuBar();

		menuReset = new JMenuItem("Reset");
		menuHelp = new JMenuItem("Help");
		menuQuit = new JMenuItem("Quit");

		menuBar.add(menuReset);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);

		gameFrame.setJMenuBar(menuBar);

		boardLayout = new GridLayout(5, 5);
		gameFrame.setLayout(boardLayout);

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
				gameFrame.add(buttons[i][j]);
			}
		}
		
		btnStart.addActionListener(e -> {
			mainMenuFrame.dispose();
			gameFrame.setVisible(true);		
		});
		
		btnHelp.addActionListener(e -> {
			 helpText();
		});
		
		menuHelp.addActionListener(e -> {
			 helpText();
		});

		menuQuit.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		});

		
		
		menuHelp.addActionListener(e -> {
			
		});

	}

	/**
	 * Create the application gui
	 */
	public GameView() {
		initialize(); 
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameView gameView = new GameView();
				gameView.mainMenuFrame.setVisible(true);
			}
		});
	}
	
	private void helpText() {
		JOptionPane.showMessageDialog(null,
				"Start: Starts the game. \n"
						+ "Pause: Pauses the game, and clicking it again, will resume the game. \n"
						+ "Reset: Restarts the game. \n" + "Quit: Exits the application");
	}

	@Override
	public void handleBoardEvent(BoardEvent e) {

	}
}
