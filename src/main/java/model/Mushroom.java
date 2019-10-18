package model;

/***
 * 
 * @author
 *
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
