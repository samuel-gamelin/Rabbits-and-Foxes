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
 */

public class Move {

	private int xStart;
	private int yStart;
	private int xEnd;
	private int yEnd;

	/**
	 * Constructs a move object given two strings, where each string must be of
	 * length 2 representing a position on the board. For example, '12' resolves to
	 * (0, 1) and '33' resolves to (2, 2). Should the inputs be invalid, all
	 * positions will be set to -1.
	 * 
	 * @param start - The start position, specified as a two-character string.
	 * @param end   - The end position, specified as a two-character string.
	 */
	public Move(String start, String end) {
		if (start != null && end != null && start.length() == 2 && end.length() == 2) {
			this.xStart = Character.getNumericValue(start.charAt(0)) - 1;
			this.yStart = Character.getNumericValue(start.charAt(1)) - 1;
			this.xEnd = Character.getNumericValue(end.charAt(0)) - 1;
			this.yEnd = Character.getNumericValue(end.charAt(1)) - 1;
		} else {
			this.xStart = -1;
			this.yStart = -1;
			this.xEnd = -1;
			this.yEnd = -1;
		}
	}

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
