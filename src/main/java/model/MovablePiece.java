package model;

import java.util.List;

import util.Move;

/**
 * This interface models the behaviour that a movable piece should have.
 * 
 * @author Samuel Gamelin
 *
 */
public interface MovablePiece {
	/**
	 * Moves the piece according to the specified move on the specified board.
	 * 
	 * @param move  The Move object representing the move that should be verified
	 *              for eligibility by this piece
	 * @param board The board on which the move should be made
	 * @return True if the piece was successfully moved from the specified start to
	 *         end positions, false otherwise
	 */
	boolean move(Move move, Board board);

	/**
	 * Determines and returns a list containing all possible moves for this piece on
	 * the specified board, at the specified position.
	 * 
	 * @param board The board on which possible moves should be determined
	 * @param x     The x position of the current piece from which possible moves
	 *              are to be determined
	 * @param y     The y position of the current piece from which possible moves
	 *              are to be determined
	 * @return The list of possible Move objects that this piece can make given the
	 *         specified board
	 */
	List<Move> getPossibleMoves(Board board, int x, int y);
}
