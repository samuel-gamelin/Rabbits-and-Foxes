package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * This class represents the tiles that the pieces will be placed on. These
 * tiles will also be placed on the board to track what is occupied and what
 * isn't.
 *
 * @author Dani Hashweh
 * @author Mohamed Radwan
 */
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public class Tile {

    /**
     * Represents the colour of the Tile.
     */
    private final TileColour tileColour;

    /**
     * Represents if the Tile is occupied.
     */
    private boolean occupied;

    /**
     * A Piece that occupies the Tile.
     */
    private Piece piece;

    /**
     * A copy constructor for Tile.
     *
     * @param tile The tile to copy
     */
    public Tile(Tile tile) {
        this.tileColour = tile.tileColour;
        this.occupied = tile.occupied;
        this.piece = tile.piece;
    }

    /**
     * Removes piece from the Tile.
     *
     * @return The piece that was removed.
     */
    public Piece removePiece() {
        Piece oldPiece = this.piece;
        this.occupied = false;
        this.piece = null;
        return oldPiece;
    }

    /**
     * Places piece and sets occupied to true.
     *
     * @param piece The piece to place on the tile
     */
    public void placePiece(Piece piece) {
        if (piece != null) {
            this.occupied = true;
            this.piece = piece;
        }
    }

    /**
     * Returns a two to four character string representation of this tile. If the
     * tile has no piece, return the String used to represent an empty tile on a
     * board
     *
     * @return A two to four character string representation of this tile. If the
     * tile has no piece, return the String used to represent an empty tile on a
     * board.
     */
    @Override
    public String toString() {
        return piece != null ? piece.toString() : Board.EMPTY;
    }

    /**
     * An enumeration representing this Tile's colour (either brown or green).
     */
    public enum TileColour {
        BROWN, GREEN
    }
}
