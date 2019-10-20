package model;

/**
 * 
 * @author Abdalla El Nakla
 *
 */
public class Fox extends Piece {
	private Direction direction;
	private FoxType foxType;

	/*
	 * An enumeration represeting
	 */
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

	public FoxType getFoxType() {
		return foxType;
	}

	@Override
	public boolean canMove(Move move) {
		return ((this.direction.equals(Direction.HORIZONTAL) && (move.direction() == 0))
				|| (this.direction.equals(Direction.VERTICAL) && (move.direction() == 1)));
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
