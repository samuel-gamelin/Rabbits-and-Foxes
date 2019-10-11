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
	public boolean canMove(int xStart, int yStart, int xEnd, int yEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "MU";
	}

}
