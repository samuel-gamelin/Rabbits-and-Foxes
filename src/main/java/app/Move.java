package app;

/**
 * @author Mohamed Radwan
 *	@author Samuel Gamelin
 */
public class Move {

	private int xStart;
	private int yStart;
	private int xEnd;
	private int yEnd;

	/**
	 * Constructs a move object given two strings, where each string must be of length 2 representing a position on the
	 * board. For example, 'A2' resolves to (0, 1) and 'C3' resolves to (2, 2).
	 * 
	 * @param start The starting position, specified as a two-character string
	 * @param end The ending position, specified as a two-character string
	 */
	public Move(String start, String end) {
		if (start != null && end != null && start.length() == 2 && end.length() == 2) {
			this.xStart = Character.getNumericValue(start.charAt(0)) - 10;
			this.yStart = Character.getNumericValue(start.charAt(1));
			this.xEnd = Character.getNumericValue(end.charAt(0)) - 10;
			this.yEnd = Character.getNumericValue(end.charAt(1));
		} else {
			this.xStart = -1;
			this.yStart = -1;
			this.xEnd = -1;
			this.yEnd = -1;
		}
	}

	/**
	 * Constructs a move object given the starting and ending x and y positions
	 * 
	 * @param xStart The starting x position
	 * @param yStart The starting y position
	 * @param xEnd The ending x position
	 * @param yEnd The starting y position
	 */
	public Move(int xStart, int yStart, int xEnd, int yEnd) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
	}

	/**
	 * @return The integer value of the starting x position
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

}
