/**
 * 
 */
package app;

/**
 * @author Dani Hashweh
 *
 */
public class Tile {
	private Colour tileColour; 
	private boolean occupied; 
	private Piece piece; 
	
	public Tile() {
		this.tileColour = null; 
		this.occupied = false; 
		this.piece = null; 
	}
	
	public enum Colour{
		BROWN, GREEN
	}

	public Tile(Colour tileColour) {
		this.tileColour = tileColour;
	}

	public Tile(Colour tileColour, Piece piece) {
		this.tileColour = tileColour;
		this.piece = piece; 
	}
	
	public boolean isOccupied() {
		return occupied; 
	}
	
	private void setOccupied(boolean occupied) {
		occupied = !occupied; 
	}
	
	public Piece retreivePiece() {
		return piece; 
	}
	
	public Piece removePiece(Piece piece) {
		Piece oldPiece = piece; 
		setOccupied(false); 
		piece = null; 
		return oldPiece; 
	}
	
	public void placePiece(Piece piece) {
		setOccupied(true); 
		this.piece = piece; 
	}
	
	public Colour getColour() {
		return tileColour; 
	}
	
	@Override
	public String toString() {
		return ""; 
	}
	}
