package model;

/**
 * 
 * @author Abdalla
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
		int foxDirection = move.direction();
		if (this.direction.equals(Direction.HORIZONTAL)) {
			if (foxDirection == 0) {
				return true;
			}
		}
		else if (this.direction.equals(Direction.VERTICAL)) {
			if (foxDirection == 1) {
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
