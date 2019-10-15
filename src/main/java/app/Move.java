/**
 * 
 */
package app;

/**
 * @author Mohamed Radwan
 *
 */
public class Move {

	private int xStartInt;
	private int yStartInt;

	private int xEndInt;
	private int yEndInt;

	/***
	 * 
	 * @param start
	 * @param end
	 */
	public Move(String start, String end) {
		if (start.length() == 2 && end.length() == 2 && start != null && end != null) {
			this.xStartInt = Character.getNumericValue(start.charAt(0)) - 10;
			this.yStartInt = Character.getNumericValue(start.charAt(1));
			this.xEndInt = Character.getNumericValue(end.charAt(0)) - 10;
			this.yEndInt = Character.getNumericValue(end.charAt(1));
		} else {
			this.xStartInt = -1;
			this.yStartInt = -1;
			this.xEndInt = -1;
			this.yEndInt = -1;
		}
	}

	/***
	 * 
	 * @param xStart
	 * @param ystart
	 * @param xEnd
	 * @param yEnd
	 */
	public Move(int xStart, int ystart, int xEnd, int yEnd) {
		this.xStartInt = xStart;
		this.yStartInt = ystart;
		this.xEndInt = xEnd;
		this.yEndInt = yEnd;
	}

	/***
	 *
	 * 
	 * @return the int value of x Start
	 */
	public int getxStart() {
		return this.xStartInt;
	}

	/***
	 * 
	 * 
	 * @return the int value of y Start
	 */
	public int getyStart() {
		return this.yStartInt;
	}

	/***
	 * 
	 * @return
	 */
	public int getxEnd() {
		return this.xEndInt;
	}

	/***
	 * 
	 * @return
	 */
	public int getyEnd() {
		return this.yEndInt;
	}

}
