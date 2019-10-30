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

	public GameController(Board board) {
		this.board = board;
	}

	public boolean move(Move move) {
		return board.move(move);
	}

}
