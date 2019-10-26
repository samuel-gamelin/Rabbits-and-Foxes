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

	private JFrame gamePane;
	private JMenuBar menuBar;

	private JMenuItem start;
	private JMenuItem reset;
	private JMenuItem quit;

	private GameController gameController;

	public GameView() {
		gameController = new GameController(new Board());
		JLabel container = new JLabel(Resources.BOARD);
		gamePane = new JFrame("Rabbits and Foxes");
		gamePane.setContentPane(container);
		gamePane.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		menuBar = new JMenuBar();
		
		start = new JMenu("Start");
		reset = new JMenu("Reset");
		quit = new JMenu("Quit");
		menuBar.add(start);
		menuBar.add(reset);
		menuBar.add(quit);
		
		gamePane.setJMenuBar(menuBar);

		
		gamePane.setVisible(true);


	}


	public static void main(String[] args) {
		GameView game = new GameView();
	}


	@Override
	public void handleBoardEvent(BoardEvent e) {
		// TODO Auto-generated method stub
		
	}
}
