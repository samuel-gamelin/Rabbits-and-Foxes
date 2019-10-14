/**
 * 
 */
package app;

/**
 * @author Dani Hashweh
 *
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
	 * Constructs the Tile based on the passed tileColour. 
	 * @param tileColour The colour of the tile as type Colour.  
	 */
	public Tile(Colour tileColour) {
		this.tileColour = tileColour;
	}

	/**
	 * Constructs the Tile based on the tileColour and the piece which will be placed on the tile. 
	 * @param tileColour The colour of the Tile as type Colour. 
	 * @param piece The piece type Piece that will be placed on the Tile. 
	 */
	public Tile(Colour tileColour, Piece piece) {
		this.tileColour = tileColour;
		this.piece = piece; 
	}
	
	public enum Colour{
		BROWN, GREEN
	}
	
	/**
	 * @return True if the Tile is occupied, False if the Tile is not occupied. 
	 */
	public boolean isOccupied() {
		return occupied; 
	}
	
	/**
	 * Sets the tile to the stated occupied state. 
	 * @param occupied If True, Tile will become occupied, if False, the Tile will become available.
	 */
	private void setOccupied(boolean occupied) {
		this.occupied = !occupied; 
	}
	
	/**
	 * @return the current Piece placed on the Tile. 
	 */
	public Piece retrievePiece() {
		return piece; 
	}
	
	/**
	 * Removes piece from the Tile. 
	 * @param piece The piece of type Piece that will removed from the tile. 
	 * @return The piece that was removed. 
	 */
	public Piece removePiece() {
		Piece oldPiece = this.piece; 
		setOccupied(false); 
		this.piece = null; 
		return oldPiece; 
	}
	
	/**
	 * Places piece and sets occupied to true
	 * @param piece Of type Piece will now be placed on the tile. 
	 */
	public void placePiece(Piece piece) {
		setOccupied(true); 
		this.piece = piece; 
	}
	
	/**
	 * @return The colour of the tile
	 */
	public Colour getColour() {
		return tileColour; 
	}
	
	/**
	 * @return A string representation
	 */
	@Override
	public String toString() {
		return piece.toShortString(); 
	}
	}
