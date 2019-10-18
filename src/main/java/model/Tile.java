package model;

/**
 * 
 * 
 * @author Dani Hashweh
 */
public class Tile {
	/**
	 * A Colour that will represent the colour of the tile.
	 */
	private Colour tileColour;

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
	public enum Colour {
		BROWN, GREEN
	}

	/**
	 * Constructs the Tile based on the passed-in tileColour.
	 * 
	 * @param tileColour The colour of the tile as type Colour.
	 */
	public Tile(Colour tileColour) {
		this.tileColour = tileColour;
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
		setOccupied(false);
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
		setOccupied(true);
		this.piece = piece;
	}

	/**
	 * @return The colour of this tile
	 */
	public Colour getColour() {
		return tileColour;
	}

	/**
	 * @return A 2-character string representation of this tile. If the tile has no piece, return the string "  " (two spaces).
	 */
	@Override
	public String toString() {
		return piece != null ? piece.toShortString(): "  "; 
	}

	/**
	 * Sets the tile to the stated occupied state.
	 * 
	 * @param occupied If True, Tile will become occupied, if False, the Tile will
	 *                 become available.
	 */
	private void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}
}
