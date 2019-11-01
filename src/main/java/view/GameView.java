package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;

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
 * @author John Breton
 * @version 2.0
 */
public class GameView extends JFrame implements BoardListener, ActionListener {
	private static final long serialVersionUID = 1L;
	private JFrame mainMenuFrame;
	private JFrame gameFrame;

	private JMenuBar menuBar;

	private JMenuItem menuReset;
	private JMenuItem menuQuit;
	private JMenuItem menuHelp;

	private JButton btnStart;
	private JButton btnHelp;
	private JButton btnQuit;

	private GridLayout boardLayout;

	private JButton[][] buttons;

	private Board board;

	private GameController gameController;
	
	private int[] xy;
	
	private int prevX, prevY;
	
	/**
	 * Create the application gui
	 */
	public GameView() {
		initialize();
	}
	
	/**
	 * 
	 */
	private void initialize() {
		
		board = new Board();
		board.addListener(this);

		gameController = new GameController(board);

		mainMenuFrame = new JFrame();

		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// absolute layout
		mainMenuFrame.getContentPane().setLayout(null);
		mainMenuFrame.setBounds(100, 100, 875, 925);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setLocationRelativeTo(null);

		btnStart = new JButton("Start");
		btnStart.setBounds(320, 249, 214, 83);
		mainMenuFrame.getContentPane().add(btnStart);

		btnHelp = new JButton("Help");
		btnHelp.setBounds(320, 375, 214, 83);
		mainMenuFrame.getContentPane().add(btnHelp);

		btnQuit = new JButton("Quit");
		btnQuit.setBounds(320, 517, 214, 83);
		mainMenuFrame.getContentPane().add(btnQuit);

		gameFrame = new JFrame();

		gameFrame.setContentPane(new JLabel(Resources.BOARD));
		gameFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		gameFrame.setSize(875, 925);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);

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
				// clear button default colours and make it transparent
				buttons[i][j].setOpaque(false);
				buttons[i][j].setContentAreaFilled(false);
				buttons[i][j].setBorderPainted(false);
				gameFrame.add(buttons[i][j]);
				buttons[i][j].addActionListener(this);
			}
		}

		updateView();
		
		btnHelp.addActionListener(this);
		menuHelp.addActionListener(this);
		btnQuit.addActionListener(this);
		menuQuit.addActionListener(this);

		btnStart.addActionListener(e -> {
			mainMenuFrame.dispose();
			gameFrame.setVisible(true);
		});
		
		xy = new int[2];
		
		prevX = -1;
		prevY = -1;

	}
	
	/**
	 * 
	 */
	private void updateView() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				Piece piece = board.getPiece(i, j);
				if ((piece != null)) {
					if ((piece.getPieceType()).equals(Piece.PieceType.MUSHROOM))
						(buttons[i][j]).setIcon(Resources.MUSHROOM);
					else if ((piece.getPieceType()).equals(Piece.PieceType.RABBIT))
						(buttons[i][j]).setIcon(Resources.RABBIT1); // change this later to know if its rabbit1 or rabbit2
					// gotta fix this, we dont know where the head is, left of tail? or right of tail? ontop of tail? under tail? etc.
					else if ((piece.getPieceType()).equals(Piece.PieceType.FOX)) {
						if (((Fox) (piece)).getDirection().equals(Fox.Direction.UP)
								&& ((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
							(buttons[i][j]).setIcon(Resources.FOX_HEAD_RIGHT);
						else if (((Fox) (piece)).getDirection().equals(Fox.Direction.DOWN)
								&& ((Fox) (piece)).getFoxType() == (Fox.FoxType.HEAD))
							(buttons[i][j]).setIcon(Resources.FOX_HEAD_UP);
					}
				}
			}
		}
		gameFrame.revalidate();
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnHelp || e.getSource() == menuHelp) {
			JOptionPane.showMessageDialog(null,
					"Start: Starts the game. \n"
							+ "Reset: Restarts the game. \n" + "Quit: Exits the application");
		} else if (e.getSource() == btnQuit || e.getSource() == menuQuit) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if (e.getSource().getClass().equals(JButton.class)) {
			locateButton(e.getSource());
			if (gameController.registerMove(xy[0], xy[1])) {
				buttons[prevX][prevY].setIcon(null);
				updateView();
			} else {
				prevX = xy[0];
				prevY = xy[1];
			}
		} 
	}

	@Override
	public void handleBoardChange() {
		// TODO Auto-generated method stub
		
	}
}