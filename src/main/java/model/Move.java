package model;

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
	 * @return direction Horizontal = 0, Vertical = 1, Invalid = -1
	 *
	 */
	public int direction() {
		int direction = -1;
		if ((xStart == xEnd) && (yStart != yEnd)) {
			direction = 1;
		} else if ((xStart != xEnd) && (yStart == yEnd)) {
			direction = 0;
		}
		return direction;
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
}
