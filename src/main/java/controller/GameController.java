package controller;

import model.Board;
import model.BoardEvent;
import model.BoardListener;

/**
 * 
 * 
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 */
public class GameController {
	private Board board;

	public GameController(Board board) {
		this.board = board;
	}
}
