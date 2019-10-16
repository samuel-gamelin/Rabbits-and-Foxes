package app;

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "MU";
	}

}
