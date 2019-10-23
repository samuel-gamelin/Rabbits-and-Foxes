package model;

/***
 * A class representing a Mushroom piece.
 * 
 * @author Abdalla El Nakla
 */
public class Mushroom extends PieceTest {

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
