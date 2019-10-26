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
public class GameController implements BoardListener{
	private Board board;
	
	public GameController(Board board) {
		this.board = board;
	}

	@Override
	public void handleBoardEvent(BoardEvent e) {
		// TODO Auto-generated method stub
		
	}

}
