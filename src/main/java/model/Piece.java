package model;

import java.util.List;

/**
 * This abstract class provides a high-level prototype for a piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * 
 * @version 3.0
 */
public abstract class Piece {

	/**
	 * An enumeration representing the piece's type.
	 */
	public enum PieceType {
		FOX, MUSHROOM, RABBIT
	}

	private PieceType pieceType;

	/**
	 * Construct a new piece given the specified piece type
	 * 
	 * @param pieceType The piece type of the piece, as a PieceType
	 */
	public Piece(PieceType pieceType) {
		this.pieceType = pieceType;
	}

	/**
	 * @return This piece's type, as a PieceType
	 */
	public PieceType getPieceType() {
		return pieceType;
	}

	/**
	 * Moves the piece according to the specified move on the specified board.
	 * 
	 * @param move  The Move object representing the move that should be verified
	 *              for eligibility by this piece
	 * @param board The board on which the move should be made
	 * @return True if the piece was successfully moved from the specified start to
	 *         end positions, false otherwise
	 */
	abstract boolean move(Move move, Board board);

	/**
	 * @return The list of possible Move objects that this piece can make given the specified board.
	 */
	abstract List<Move> getPossibleMoves(Board board);
	
	/**
	 * @return A short, two-character string representing the piece.
	 */
	abstract String toShortString();

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (pieceType != other.pieceType)
			return false;
		return true;
	}
}
