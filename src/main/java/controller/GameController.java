package controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import model.Board;
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
 * @author Abdalla El Nakla
 * @version 3.0
 */
public class GameController {
	private Board board;
	private int currentLevel;
	private List<Integer> moveList;
	private ArrayDeque<Move> undoMoveStack;
	private ArrayDeque<Move> redoMoveStack;

	/**
	 * An enumeration representing the validity of a click from the user.
	 */
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
		this.currentLevel = 1;
		this.moveList = new ArrayList<>();
		this.undoMoveStack = new ArrayDeque<>();
		this.redoMoveStack = new ArrayDeque<>();
	}

	/**
	 * This method is used to register a click based on the user input. It first
	 * checks if the button selected by the user contains an object that is not a
	 * mushroom. If that is satisfied it adds the move to a list. When the second
	 * move is made the method uses the original move as well as the new move to
	 * pass them to the model. The model will then update the board which will
	 * update the view for the user.
	 * 
	 * When a move is registered, it will clear the redo stack and push the move
	 * onto the the Undo Stack
	 * 
	 * 
	 * @param x Represents the start of the end x value of the user's move
	 * @param y Represents the start of the end y value of the user's move
	 * @return True if the selected location is valid, false if it is the first move
	 *         being made on the board or if the selected location is valid
	 */
	public ClickValidity registerClick(int x, int y) {
		if (moveList.isEmpty() && board.isOccupied(x, y) && !(board.getPiece(x, y) instanceof Mushroom)) {
			moveList.add(x);
			moveList.add(y);
			return ClickValidity.VALID;
		} else if (!moveList.isEmpty()) {
			Move movePiece = new Move(moveList.get(0), moveList.get(1), x, y);
			if (board.move(movePiece) && !moveList.isEmpty()) {
				moveList.clear();
				redoMoveStack.clear();
				undoMoveStack.push(movePiece);
				return ClickValidity.VALID_MOVEMADE;
			} else if (moveList.isEmpty()) {
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
	 * @param x represents the start of the x value of the piece selected
	 * @param y represents the start of the y value of the piece selected
	 * @return List of all possible moves for the selected piece.
	 */
	public List<Move> getPossibleMoves(int x, int y) {
		return board.getPiece(x, y) != null ? board.getPiece(x, y).getPossibleMoves(board, x, y) : new ArrayList<>();
	}

	/**
	 * Removes any history of a previously stored position.
	 */
	public void clearPendingPosition() {
		moveList.clear();
	}

	/**
	 * This method is used to reset the Game. In order to ensure that the new game
	 * will not have any old registered moves the method also clears the move array
	 * list.
	 * 
	 * @return The new board for the view to listen to it.
	 */
	public Board reset() {
		board = Resources.getLevel(currentLevel);
		moveList.clear();
		undoMoveStack.clear();
		redoMoveStack.clear();
		return board;
	}

	/**
	 * Returns the next best move based on this controller's current board.
	 * 
	 * @return The next best move
	 */
	public Move getNextBestMove() {
		return Solver.getNextBestMove(board);
	}

	/**
	 * 
	 * undoMove checkes if the UndoMoveStack is empty If it is empty, return false
	 * to show there where no moves made
	 * 
	 * If it is not empty, pop the stack and push it into the redo stack Then create
	 * a new move with the xEnd and yEnd in the starting position and vice versa to
	 * properly undo a move
	 * 
	 * @return True if there is a move to undo, false otherwise
	 */

	public boolean undoMove() {
		if (!undoMoveStack.isEmpty()) {
			Move undoMove = undoMoveStack.pop();
			redoMoveStack.push(undoMove);
			board.move(new Move(undoMove.xEnd, undoMove.yEnd, undoMove.xStart, undoMove.yStart));
			return true;
		}
		return false;
	}

	/**
	 * The redoMove checks if the redoMoveStack is empty If it is empty then return
	 * false, as there is no redoMoves
	 * 
	 * If it is not empty then the redoMoveStack is popped and then added into the
	 * undoMove stack. The redoMove is the set into the board.
	 * 
	 * @return True if there is a move to redo, false otherwise
	 */
	public boolean redoMove() {
		if (!redoMoveStack.isEmpty()) {
			Move redoMove = redoMoveStack.pop();
			undoMoveStack.push(redoMove);
			board.move(redoMove);
			return true;
		}
		return false;
	}

	/**
	 * Returns the current level of the game.
	 * 
	 * @return The current level of the game
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Increment the current level of the game by 1.
	 */
	public void incrementLevel() {
		currentLevel++;
	}

	/**
	 * Sets the level of the game
	 */
	public void setLevel(int level) {
		currentLevel = level;
	}
}
