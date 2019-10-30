package model;

/**
 * This class represents the move coordinates for the game. Since move will be
 * used in multiple classes its easier to represent it under one object.
 * 
 * Move is used to represent the start and end point for the movement. This
 * class is called in Game when a new move is being made. It is constructed one
 * the player decides to make a move with the command word.
 * 
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 * @version 2.0
 */

public class Move {

	private int xStart;
	private int yStart;
	private int xEnd;
	private int yEnd;

	/**
	 * Given the position of the object being moved and where it needs to be moved
	 * this will construct the object for the move class.
	 * 
	 * @param xStart - The integer value of the start x position.
	 * @param yStart - The integer value of the start y position.
	 * @param xEnd   - The integer value of the ending x position.
	 * @param yEnd   - The integer value of the start y position.
	 */
	public Move(int xStart, int yStart, int xEnd, int yEnd) {
		// Subtract by 1 to get the postion in the array
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}

	/**
	 * This will return the private variable representing the start x position.
	 * 
	 * @return xStart - The value of the start x position as an integer.
	 */
	public int getXStart() {
		return xStart;
	}

	/**
	 * This will return the private variable representing the start y position.
	 * 
	 * @return yStart - The value of the start y position as an integer.
	 */
	public int getYStart() {
		return yStart;
	}

	/**
	 * This will return the private variable representing the end x position.
	 * 
	 * @return xEnd - The value of the end x position as an integer.
	 */
	public int getXEnd() {
		return xEnd;
	}

	/**
	 * This will return the private variable representing the end y position.
	 * 
	 * @return yEnd - The value of the end y position as an integer.
	 */
	public int getYEnd() {
		return yEnd;
	}

	/**
	 * 
	 * @param xStart
	 */
	public void setxStart(int xStart) {
		this.xStart = xStart;
	}

	/**
	 * 
	 * @param yStart
	 */
	public void setyStart(int yStart) {
		this.yStart = yStart;
	}

	/**
	 * 
	 * @param xEnd
	 */
	public void setxEnd(int xEnd) {
		this.xEnd = xEnd;
	}

	/**
	 * 
	 * @param yEnd
	 */
	public void setyEnd(int yEnd) {
		this.yEnd = yEnd;
	}

	/**
	 * This method is used to compute the direction of movement. Since each piece
	 * has move restrictions this method will be used to initially determine if a
	 * move is valid.
	 * 
	 * @return direction - Horizontal = 0, Vertical = 1, Invalid = -1
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
