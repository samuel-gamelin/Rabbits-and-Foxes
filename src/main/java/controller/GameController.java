package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Move;
import model.Mushroom;

/**
 * The controller is used to register the user's moves so that it updates the
 * model. In this game, the user is expected to take two inputs (start position
 * followed by the end position). In the case of GUI, the user will input
 * through mouse clicks. After this class registers a full move it calls the
 * method move within Board to initiate the move.
 * 
 * @author Mohamed Radwan
 * @version 2.0
 */
public class GameController {
	private Board board;
	private List<Integer> move;

	/**
	 * The constructor for this classes uses a board that has been inputed by the
	 * view(the board listener) so that it can request the model to update it. This
	 * will ensure all three components of the MVC will be using the same board.
	 * 
	 * @param board
	 */
	public GameController(Board board) {
		this.board = board;
		this.move = new ArrayList<>();
	}

	/**
	 * This method is used to register the move based on the user input. It first
	 * checks if the button selected by the user contains an object that is not a
	 * mushroom. If that is satisfied it adds the move to a list. When the second
	 * move is made the method uses the original move as well as the new move to
	 * pass them to the model. The model will then update the board which will
	 * update the view for the user.
	 * 
	 * @param x - Represents the start of the end x value of the user's move
	 * @param y - Represents the start of the end y value of the user's move
	 * @return - Returns True if the move has been successful otherwise will return
	 *         false
	 */
	public boolean registerMove(int x, int y) {
		if (move.isEmpty() && board.isOccupied(x, y) && !(board.getPiece(x, y) instanceof Mushroom)) {
			move.add(x);
			move.add(y);
		} else if (!move.isEmpty()) {
			boolean result = board.move(new Move(move.get(0), move.get(1), x, y));
			move.clear();
			return result;
		}
		return false;
	}


	/**
	 * This method is used to reset the Game. In order to ensure that the new game
	 * will not have any old registered moves the method also clears the move array
	 * list.
	 * 
	 * @return - The new board for the view to listen to it.
	 */
	public Board reset() {
		move.clear();
		board = new Board();
		return board;
	}

}