package model;

/**
 * 
 * @author
 *
 */
public class Fox extends Piece {

	public Fox() {
		super(PieceType.FOX);
	}

	@Override
	public boolean canMove(Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "FH";
	}

}
