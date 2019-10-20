package model;

/***
 * 
 * @author Abdalla
 *
 */
public class Rabbit extends Piece {

	public Rabbit() {
		super(PieceType.RABBIT);
	}

	@Override
	public boolean canMove(Move move) {
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
		if((xStart==xEnd)&&(yStart !=yEnd)) {
			return true;
		}
		if((xStart!=xEnd)&&(yStart==yEnd)) {
			return true;
		}
		return false;
	}

	@Override
	public String toShortString() {
		return "RB";
	}

}
