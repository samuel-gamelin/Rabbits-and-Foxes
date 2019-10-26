package view;

import javax.swing.*;

import controller.GameController;
import model.Board;
import model.BoardEvent;
import model.BoardListener;
import resources.Resources;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 */
public class GameView extends JFrame implements BoardListener {
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;

	private JMenuItem start;
	private JMenuItem pause;
	private JMenuItem reset;
	private JMenuItem quit;

	private Board board;
	private GameController gameController;

	public GameView() {
		board = new Board();
		board.addListener(this);
		gameController = new GameController(board);
		this.setContentPane(new JLabel(Resources.BOARD));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		menuBar = new JMenuBar();

		start = new JMenu("Start");
		pause = new JMenu("Pause");
		reset = new JMenu("Reset");
		quit = new JMenu("Quit");
		menuBar.add(start);
		menuBar.add(pause);
		menuBar.add(reset);
		menuBar.add(quit);

		this.setJMenuBar(menuBar);

		this.setVisible(true);

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
		// TODO Auto-generated method stub
	}
}
