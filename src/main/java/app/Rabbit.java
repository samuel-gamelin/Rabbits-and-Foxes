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
	public boolean canMove(Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "RB";
	}

}
