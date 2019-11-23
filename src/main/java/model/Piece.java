package model;

/**
 * This abstract class provides a high-level prototype for a piece.
 *
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * @version 4.0
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
	 * @return A short, two to four character string representing the piece.
	 */
	public abstract String toString();
}
