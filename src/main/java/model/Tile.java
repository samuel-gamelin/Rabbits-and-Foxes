package model;

import java.io.Serializable;

/**
 * This class represents the tiles that the pieces will be placed on. These
 * tiles will also be placed on the board to track what is occupied and what
 * isn't.
 * 
 * @author Dani Hashweh
 * @author Mohamed Radwan
 * @version 3.0
 */
public class Tile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8740166907112638422L;

	/**
	 * Represents the colour of the Tile.
	 */
	private TileColour tileColour;

	/**
	 * Represents if the Tile is occupied.
	 */
	private boolean occupied;

	/**
	 * A Piece that occupies the Tile.
	 */
	private Piece piece;

	/**
	 * An enumeration representing this Tile's colour (either brown or green).
	 */
	public enum TileColour {
		BROWN, GREEN
	}

	/**
	 * Constructs the Tile based on the passed-in tileColour.
	 * 
	 * @param tileColour The TileColour of the tile.
	 */
	public Tile(TileColour tileColour) {
		this.tileColour = tileColour;
	}

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
	 * Determine if there is a Piece on this Tile.
	 * 
	 * @return True if the Tile is occupied, false otherwise.
	 */
	public boolean isOccupied() {
		return occupied;
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
	 * Gets the Piece currently occupying this Tile. Will return null if there is no
	 * Piece on this Tile.
	 * 
	 * @return The current Piece placed on the Tile.
	 */
	public Piece retrievePiece() {
		return piece;
	}

	/**
	 * Places piece and sets occupied to true
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
	 * @return The colour of this tile
	 */
	public TileColour getColour() {
		return tileColour;
	}

	/**
	 * @return A two to four character string representation of this tile. If the
	 *         tile has no piece, return the String used to represent empty on a
	 *         board.
	 */
	@Override
	public String toString() {
		return piece != null ? piece.toString() : Board.EMPTY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (occupied ? 1231 : 1237);
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		result = prime * result + ((tileColour == null) ? 0 : tileColour.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tile other = (Tile) obj;
		if (occupied != other.occupied)
			return false;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece))
			return false;
		if (tileColour != other.tileColour)
			return false;
		return true;
	}
}
