package model;

/***
 * A class representing a Mushroom piece.
 * 
 * @author Abdalla El Nakla
 * @version 2.0
 */
public class Mushroom extends Piece {

	public Mushroom() {
		super(PieceType.MUSHROOM);
	}

	@Override
	public boolean canMove(Move move) {
		return false;
	}

	@Override
	public String toShortString() {
		return "MU";
	}

}
