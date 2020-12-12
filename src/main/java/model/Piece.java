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
