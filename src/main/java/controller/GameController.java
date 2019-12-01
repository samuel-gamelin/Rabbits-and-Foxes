package controller;

import model.Board;
import model.MovablePiece;
import model.Mushroom;
import model.Piece;
import resources.Resources;
import util.Move;
import util.Solver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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
    /**
     * The board that this controller is currently maintaining.
     */
    private Board board;

    /**
     * Represents whether or not this controller represents a default level.
     */
    private boolean isDefaultLevel;

    /**
     * Represents this controller's current level.
     */
    private int currentLevel;

    /**
     * A list used to hold information about the most recent click.
     */
    private List<Integer> moveList;

    /**
     * A stack that maintains moves that can be undone.
     */
    private ArrayDeque<Move> undoMoveStack;

    /**
     * A stack that maintains moves that can be redone.
     */
    private ArrayDeque<Move> redoMoveStack;

    /**
     * An enumeration representing the validity of a click from the user.
     */
    public enum ClickValidity {
        VALID, INVALID, VALID_MOVEMADE, INVALID_MOVEMADE
    }

    /**
     * Constructs a controller based on the supplied board and level number. Should the level number be non-positive,
     * this controller will not represent a default level.
     *
     * @param board The Board (model) that this controller should update
     * @param level The level for this controller
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
     * This method registers a click based on the given coordinates, and performs a move if appropriate.
     *
     * @param x Represents the start of the end x value of the user's move
     * @param y Represents the start of the end y value of the user's move
     * @return An instance of the enumerated ClickValidity type, indicating the registered click's validity
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
     * Resets this controller, returning the newly-reset model (board).
     *
     * @return The newly-reset board
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
     * board that is not part of the default levels.
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
     * Returns whether or not this controller is associated with a default level.
     *
     * @return True if the level is a default level, false if it is a user level
     */
    public boolean isDefaultLevel() {
        return isDefaultLevel;
    }
}
