package controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.MovablePiece;
import model.Mushroom;
import model.Piece;
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
 * @version 4.0
 */
public class GameController {
	private Board board;
	private boolean isDefaultLevel;
	private int currentLevel;
	private List<Integer> moveList;
	private ArrayDeque<Move> undoMoveStack;
	private ArrayDeque<Move> redoMoveStack;

	public static Board customLoadBoard;

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
	 * @param board          The Board (model) that this controller should update
	 * @param isDefaultLevel True if the passed in board is part of the default
	 *                       levels, false otherwise
	 */
	public GameController(Board board, int level) {
		this.board = board;
		this.currentLevel = level;
		this.moveList = new ArrayList<>();
		this.undoMoveStack = new ArrayDeque<>();
		this.redoMoveStack = new ArrayDeque<>();

		if (currentLevel > 0) {
			this.isDefaultLevel = true;
		}
	}

	/**
	 * Set the custom saved loaded board
	 * 
	 * @param board, which will be used to set the static board in game controller. 
	 */
	public static void setLoadedBoard(Board board) {
		customLoadBoard = board;
	}

	/**
	 * This method is used to register a click based on the user input. It first
	 * checks if the button selected by the user contains an object that is not a
	 * mushroom. If that is satisfied it adds the move to a list. When the second
	 * move is made the method uses the original move as well as the new move to
	 * pass them to the model. The model will then update the board which will
	 * update the view for the user.
	 * <p>
	 * When a move is registered, it will clear the redo stack and push the move
	 * onto the the Undo Stack
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
		Piece piece = board.getPiece(x, y);
		if (piece instanceof MovablePiece) {
			return ((MovablePiece) piece).getPossibleMoves(board, x, y);
		}
		return new ArrayList<>();
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
		if (isDefaultLevel) {
			board = Resources.getDefaultBoardByLevel(currentLevel);
		} else {
			board = Resources.getAllUserBoards().stream().filter(b -> b.getName().equals(board.getName())).findFirst()
					.orElse(null);

			if (board == null) {
				board = Resources.getDefaultBoardByLevel(1);
				isDefaultLevel = true;
			}
		}

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
	 * Undoes a move.
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
	 * Redoes a move.
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
	 * @return The current level of the game. -1 if this controller maintains a
	 *         board that is not part of the default levels.
	 */
	public int getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Increment the current level of the game by 1.
	 */
	public void incrementLevel() {
		if (isDefaultLevel) {
			currentLevel++;
		}
	}

	/**
	 * Sets the level of the game. Has no effect if the controller maintains a board
	 * that is not part of the default levels.
	 */
	public void setLevel(int level) {
		if (isDefaultLevel) {
			currentLevel = level;
		}
	}

	/**
	 * Returns whether or not this controller is associated with a default level.
	 *
	 * @return True if the level is a default level, false if it is a user level
	 */
	public boolean isDefaultLevel() {
		return isDefaultLevel;
	}
}
