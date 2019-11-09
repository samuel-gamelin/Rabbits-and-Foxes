package util;

/**
 * This class represents the move coordinates for the game. Since move will be
 * used in multiple classes its easier to represent the start and end points of
 * a move using one object.
 * 
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 * 
 * @version 3.0
 */

public final class Move {
	public final int xStart;
	public final int yStart;
	public final int xEnd;
	public final int yEnd;

	public enum MoveDirection {
		HORIZONTAL, VERTICAL, INVALID;
	}

	/**
	 * Given the position of the object being moved and where it needs to be moved
	 * this will construct the object for the move class.
	 * 
	 * @param xStart The integer value of the start x position.
	 * @param yStart The integer value of the start y position.
	 * @param xEnd   The integer value of the ending x position.
	 * @param yEnd   The integer value of the start y position.
	 */
	public Move(int xStart, int yStart, int xEnd, int yEnd) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}

	/**
	 * This method is used to compute the direction of movement. Since each piece
	 * has move restrictions this method will be used to initially determine if a
	 * move is valid.
	 * 
	 * @return Direction enum HORIZONTAL, VERTICAL, INVALID
	 *
	 */
	public MoveDirection direction() {
		if ((xStart == xEnd) && (yStart != yEnd)) {
			return MoveDirection.VERTICAL;
		} else if ((xStart != xEnd) && (yStart == yEnd)) {
			return MoveDirection.HORIZONTAL;
		}
		return MoveDirection.INVALID;
	}

	/**
	 * This method calculates the distance the object needs to move in the x
	 * direction.
	 * 
	 * @return the distance in the Horizontal direction.
	 */
	public int xDistance() {
		return xEnd - xStart;
	}

	/**
	 * This method calculates the distance the object needs to move in the x
	 * direction.
	 * 
	 * @return the distance in the Vertical direction.
	 */
	public int yDistance() {
		return yEnd - yStart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xEnd;
		result = prime * result + xStart;
		result = prime * result + yEnd;
		result = prime * result + yStart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Move))
			return false;
		Move other = (Move) obj;
		if (xEnd != other.xEnd)
			return false;
		if (xStart != other.xStart)
			return false;
		if (yEnd != other.yEnd)
			return false;
		if (yStart != other.yStart)
			return false;
		return true;
	}
	
	
}
