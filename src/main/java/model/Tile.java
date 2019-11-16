package model;

/**
 * This class represents the tiles that the pieces will be placed on. These
 * tiles will also be placed on the board to track what is occupied and what
 * isn't.
 * 
 * @author Dani Hashweh
 * @author Mohamed Radwan
 * @version 3.0
 */
public class Tile {
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
	 * Gets the Piece currently occupying this Tile.
	 * Will return null if there is no Piece on this Tile.
	 * 
	 * @return The current Piece placed on the Tile.
	 */
	public Piece retrievePiece() {
		return piece;
	}

	/**
	 * Places piece and sets occupied to true
	 * 
	 * @param piece Of type Piece will now be placed on the tile.
	 */
	public void placePiece(Piece piece) {
		this.occupied = true;
		this.piece = piece;
	}

	/**
	 * @return The colour of this tile
	 */
	public TileColour getColour() {
		return tileColour;
	}
	
	/**
	 * @return A two to four character string representation of this tile. 
	 * If the tile has no piece, return the String used to represent empty on a board.
	 */
	@Override
	public String toString() {
		return piece != null ? piece.toString() : Board.EMPTY;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		return prime * result + ((piece == null) ? 0 : piece.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Tile))
			return false;
		Tile other = (Tile) obj;
		if (piece == null) {
			if (other.piece != null)
				return false;
		} else if (!piece.equals(other.piece)) {
			return false;
		}
		return true;
	}

}
