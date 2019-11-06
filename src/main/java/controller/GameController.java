package controller;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Fox;
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
 * @version 3.0
 */
public class GameController {
	private Board board;
	private List<Integer> move;

	public enum ClickValidity {
		VALID, INVALID, VALID_MOVEMADE, INVALID_MOVEMADE
	}

	/**
	 * The constructor for this class uses a board that has been inputted by the
	 * view (the board listener) so that it can request the model to update it. This
	 * will ensure all three components of the MVC will be using the same board.
	 * 
	 * @param board The Board (model) that this controller should update
	 */
	public GameController(Board board) {
		this.board = board;
		this.move = new ArrayList<>();
	}

	/**
	 * This method is used to register a click based on the user input. It first
	 * checks if the button selected by the user contains an object that is not a
	 * mushroom. If that is satisfied it adds the move to a list. When the second
	 * move is made the method uses the original move as well as the new move to
	 * pass them to the model. The model will then update the board which will
	 * update the view for the user.
	 * 
	 * @param x - Represents the start of the end x value of the user's move
	 * @param y - Represents the start of the end y value of the user's move
	 * @return - True if the selected location is valid, false if it is the first move being made on the board or if the selected location is valid
	 */
	public ClickValidity registerClick(int x, int y) {
		if (move.isEmpty() && board.isOccupied(x, y) && !(board.getPiece(x, y) instanceof Mushroom)) {
			move.add(x);
			move.add(y);
			return ClickValidity.VALID;
		} else if (!move.isEmpty() && (!board.isOccupied(x, y) || (board.getPiece(x, y) instanceof Fox && board.getPiece(move.get(0), move.get(1)) instanceof Fox && ((Fox) board.getPiece(x, y)).getID() == ((Fox) board.getPiece(move.get(0), move.get(1))).getID()))) {
			boolean result = board.move(new Move(move.get(0), move.get(1), x, y));
			if (result) {
				move.clear();
				return ClickValidity.VALID_MOVEMADE;
			}
			return ClickValidity.INVALID_MOVEMADE;
		}
		return ClickValidity.INVALID;
	}

	/**
	 * Removes any history of a previously stored position.
	 */
	public void clearPendingPosition() {
		move.clear();
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
