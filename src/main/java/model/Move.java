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
	 * (0, 1) and '33' resolves to (2, 2). This will allow
	 * 
	 * @param start - The starting position, specified as a two-character string.
	 * @param end   - The ending position, specified as a two-character string.
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
	 * @param xStart The integer value of the starting x position
	 * @param yStart The integer value of the starting y position
	 * @param xEnd   The integer value of the ending x position
	 * @param yEnd   The integer value of the starting y position
	 */
	public Move(int xStart, int yStart, int xEnd, int yEnd) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}

	/***
	 * This will return the private variable representing the starting x string
	 * position.
	 * 
	 * @return xStart The integer value of the starting x position
	 */
	public int getXStart() {
		return this.xStart;
	}

	/**
	 * @return The integer value of the starting y position
	 */
	public int getYStart() {
		return this.yStart;
	}

	/**
	 * @return The integer value of the ending x position
	 */
	public int getXEnd() {
		return this.xEnd;
	}

	/**
	 * @return The integer value of the ending y position
	 */
	public int getYEnd() {
		return this.yEnd;
	}


	/***
	 * This will return the direction of the Movement
	 * 
	 * @return 1 if vertical, 0 if Horizontal, and -1 if invalid
	 */
	public int direction() {
		if ((xStart == xEnd) && (yStart != yEnd)) {
			return 1;
		}
		if ((xStart != xEnd) && (yStart == yEnd)) {
			return 0;
		}
		return -1;
	}

}
