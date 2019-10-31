package model;

/**
 * This abstract class provides a high-level prototype for a piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * @version 2.0
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
	 * @return True if the piece can move the specified start to end positions.
	 *         False otherwise.
	 * @param move The Move object representing the move that should be verified for eligibility by this piece.
	 */
	abstract boolean canMove(Move move);

	/**
	 * @return A short, two-character string representing the piece.
	 */
	abstract String toShortString();
}
