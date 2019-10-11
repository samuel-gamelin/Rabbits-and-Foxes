package app;

/***
 * 
 * @author
 *
 */
public class Rabbit extends Piece {

	public Rabbit() {
		super(PieceType.RABBIT);
	}

	@Override
	public boolean canMove(int xStart, int yStart, int xEnd, int yEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "RB";
	}

}
