package model;

/***
 * A class representing a Rabbit piece.
 * 
 * @author Abdalla El Nakla
 */
public class Rabbit extends Piece {

	public Rabbit() {
		super(PieceType.RABBIT);
	}

	@Override
	public boolean canMove(Move move) {
		return (move.direction() != -1);
	}

	@Override
	public String toShortString() {
		return "RB";
	}

}
