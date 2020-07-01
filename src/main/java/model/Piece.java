package model;

import lombok.Getter;

/**
 * This abstract class provides a high-level prototype for a piece.
 *
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 */
public abstract class Piece {

    @Getter
    private final PieceType pieceType;

    /**
     * Construct a new piece given the specified piece type
     *
     * @param pieceType The piece type of the piece, as a PieceType
     */
    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * @return A short, two to four character string representing the piece.
     */
    public abstract String toString();

    /**
     * An enumeration representing the piece's type.
     */
    public enum PieceType {
        FOX, MUSHROOM, RABBIT
    }
}
