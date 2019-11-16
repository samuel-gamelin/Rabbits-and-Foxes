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
	 * A Colour that will represent the colour of the tile.
	 */
	private TileColour tileColour;

	/**
	 * A boolean that will represent if the tile is occupied or not.
	 */
	private boolean occupied;

	/**
	 * A piece of type Piece that will be placed on the tile.
	 */
	private Piece piece;

	/**
	 * An enumeration representing this tile's colour (either brown or green).
	 */
	public enum TileColour {
		BROWN, GREEN
	}

	/**
	 * Constructs the Tile based on the passed-in tileColour.
	 * 
	 * @param tileColour The colour of the tile as type Colour.
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
	 * @return True if the Tile is occupied, False if the Tile is not occupied.
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
	 * @return the current Piece placed on the Tile.
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		return result;
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
