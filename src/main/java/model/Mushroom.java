package model;

/***
 * A class representing a Mushroom piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * 
 * @version 2.0
 */
public class Mushroom extends Piece {
	public Mushroom() {
		super(PieceType.MUSHROOM);
	}

	@Override
	public boolean move(Move move, Board board) {
		return false;
	}
}
