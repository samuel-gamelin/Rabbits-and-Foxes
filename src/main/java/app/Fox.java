/**
 * 
 */
package app;

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
	public boolean canMove(int xStart, int yStart, int xEnd, int yEnd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toShortString() {
		return "FH";
	}

}
