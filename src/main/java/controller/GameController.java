package controller;

import model.Board;
import model.Move;

/**
 * 
 * 
 * @author Mohamed Radwan
 * @version 2.0
 */
public class GameController {
	private Board board;
	private Move move;
	private boolean makeMove;

	/**
	 * 
	 * @param board
	 */
	public GameController(Board board) {
		this.board = board;
		this.makeMove = false;
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return true if the move has been made otherwise false
	 */
	public boolean registerMove(int x, int y) {
		if (!makeMove) {
			move.setxStart(x);
			move.setyStart(y);
			makeMove = true;
		} else {
			move.setxEnd(x);
			move.setyEnd(y);
			makeMove = false;
			return board.move(move);
		}
		return false;
	}
	
}