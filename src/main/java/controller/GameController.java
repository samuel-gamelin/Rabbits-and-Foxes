package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import model.Board;
import model.Fox;
import model.Mushroom;
import resources.Resources;
import util.Move;
import util.Solver;

/**
 * The controller is used to register the user's moves so that it updates the
 * model. In this game, the user is expected to take two inputs (start position
 * followed by the end position). In the case of GUI, the user will input
 * through mouse clicks. After this class registers a full move it calls the
 * method move within Board to initiate the move.
 * 
 * @author Mohamed Radwan
 * @author Dani Hashweh
 * @author Samuel Gamelin
 * @version 3.0
 */
public class GameController {
	private Board board;
	private List<Integer> move;
	private static int currLevel = 4;

	// Stacks for undo and redo
	private static Stack<Move> undoMoveStack;
	private static Stack<Move> redoMoveStack;

	public enum ClickValidity {
		VALID, INVALID, VALID_MOVEMADE, INVALID_MOVEMADE
	}

	/**
	 * The constructor for this class uses a board that has been inputed by the view
	 * (the board listener) so that it can request the model to update it. This will
	 * ensure all three components of the MVC will be using the same board.
	 * 
	 * @param board The Board (model) that this controller should update
	 */
	public GameController(Board board) {
		this.board = board;
		this.move = new ArrayList<>();
		undoMoveStack = new Stack<Move>();
		redoMoveStack = new Stack<Move>();
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
	 * @return - True if the selected location is valid, false if it is the first
	 *         move being made on the board or if the selected location is valid
	 */
	public ClickValidity registerClick(int x, int y) {
		if (move.isEmpty() && board.isOccupied(x, y) && !(board.getPiece(x, y) instanceof Mushroom)) {
			move.add(x);
			move.add(y);
			return ClickValidity.VALID;
		} else if (!move.isEmpty() && (!board.isOccupied(x, y) || (board.getPiece(x, y) instanceof Fox
				&& board.getPiece(move.get(0), move.get(1)) instanceof Fox
				&& ((Fox) board.getPiece(x, y)).getID() == ((Fox) board.getPiece(move.get(0), move.get(1))).getID()))) {
			Move movePiece = new Move(move.get(0), move.get(1), x, y);
			boolean result = board.move(movePiece);
			if (result) {
				move.clear();
				redoMoveStack.clear();
				undoMoveStack.add(movePiece);
				return ClickValidity.VALID_MOVEMADE;
			}
			return ClickValidity.INVALID_MOVEMADE;
		}
		return ClickValidity.INVALID;
	}

	/**
	 * This method returns a list of all possible moves for the piece selected in
	 * the view.
	 * 
	 * @param x - represents the start of the x value of the piece selected
	 * @param y - represents the start of the y value of the piece selected
	 * @return List of all possible moves for the selected piece.
	 */
	public List<Move> getPossibleMoves(int x, int y) {
		return board.getPiece(x, y) != null ? board.getPiece(x, y).getPossibleMoves(board, x, y) : new ArrayList<>();
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
		board = Resources.getLevel(currLevel);
		return board;
	}

	/**
	 * @return The next best move for the specified board
	 */
	public Move getNextBestMove() {
		return Solver.getNextBestMove(board);
	}
	/**
	 * The undoMove is popped from the stack, and then added into the 
	 * reverseMove stack. the undoMove is then reversed and set into the board to move
	 * 
	 * @return True if there is a move to undo, false otherwise
	 */

	public boolean undoMove() {
		if(!undoMoveStack.isEmpty()) {
			Move undoMove = undoMoveStack.pop();
			redoMoveStack.add(undoMove);
			Move reverseMove = undoMove.reverseMove();
			board.move(reverseMove);
			return true;
		}
		return false;
	}
	/**
	 * The redoMove is popped from the stack, and then added into the 
	 * undoMove stack. the redoMove is the set into the board. 
	 * 
	 * @return True if there is a move to redo, false otherwise
	 */

	public boolean redoMove() {
		if (!redoMoveStack.isEmpty()) {
			Move redoMove = redoMoveStack.pop();
			undoMoveStack.add(redoMove);
			board.move(redoMove);
			return true;
		}
		return false;
	}

	/**
	 * Return the current level of the game.
	 * 
	 * @return The current level of the game, as an int.
	 */
	public static int getCurrentLevel() {
		return currLevel;
	}

	/**
	 * Increment the current level of the game by 1.
	 */
	public static void incrementLevel() {
		redoMoveStack.clear();
		undoMoveStack.clear();
		currLevel++;
	}

	/**
	 * Gets the the total levels available in the LevelData.json file
	 * 
	 * @return The total number of levels in the game as an int.
	 */
	public static int getTotalLevels() {
		return Resources.getNumberOfLevels();
	}

	/**
	 * Sets the current level to the first level.
	 */
	public static void setFirstLevel() {
		GameController.currLevel = 1;
	}

}