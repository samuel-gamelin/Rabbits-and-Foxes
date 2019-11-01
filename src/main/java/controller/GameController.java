package controller;

import java.util.ArrayList;
import java.util.List;

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
	private List<Integer> moves;

	/**
	 * 
	 * @param board
	 */
	public GameController(Board board) {
		this.board = board;
		this.moves = new ArrayList<>();
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @return true if the move has been made otherwise false
	 */
	public boolean registerMove(int x, int y) {
		if (moves.size() == 0) {
			moves.add(x);
			moves.add(y);
		} else {
			boolean result = board.move(new Move(moves.get(0), moves.get(1), x, y));
			moves.clear();
			return result;
		}
		return false;
	}
	
}