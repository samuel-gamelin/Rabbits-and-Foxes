package model;

/**
 * 
 * @author Abdalla
 *
 */
public class Fox extends Piece {
	private Direction direction;
	private FoxType foxType;

	public enum FoxType {
		HEAD, TAIL
	}

	public enum Direction {
		VERTICAL, HORIZONTAL
	}

	public Fox(FoxType foxType, Direction direction) {
		super(PieceType.FOX);
		this.foxType = foxType;
		this.direction = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public FoxType getFoxType() {
		return foxType;
	}

	public void setFoxType(FoxType foxType) {
		this.foxType = foxType;
	}

	@Override
	public boolean canMove(Move move) {
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
		if (this.direction.equals(Direction.HORIZONTAL)) {
			if ((xStart == xEnd) && (yStart != yEnd)) {
				return true;
			}
		}
		if (this.direction.equals(Direction.VERTICAL)) {
			if ((xStart != xEnd) && (yStart == yEnd)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public String toShortString() {
		if (this.foxType.equals(FoxType.HEAD)) {
			return "FH";
		} else {
			return "FT";
		}

	}

}
