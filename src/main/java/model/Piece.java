package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This abstract class provides a high-level prototype for a piece.
 *
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 */
@Getter
@RequiredArgsConstructor
public abstract class Piece {

    private final PieceType pieceType;

    /**
     * Generates a short string representing this piece.
     *
     * @return A short, two- to four-character string representing this piece.
     */
    public abstract String toString();

    /**
     * An enumeration representing this piece's type.
     */
    public enum PieceType {
        FOX, MUSHROOM, RABBIT
    }
}
