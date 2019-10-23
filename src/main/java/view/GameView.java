package view;

import javax.swing.JFrame;

import model.Board;
import model.BoardEvent;
import model.BoardListener;

/**
 * This class represents the view with which the user interacts in order to play the game.
 * 
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 */
public class GameView extends JFrame implements BoardListener {
	
	private Board board;
	
	public GameView() {
		board = new Board();
		board.addListener(this);
	}
	
	@Override
	public void handleBoardEvent(BoardEvent e) {
		return;
	}
}
