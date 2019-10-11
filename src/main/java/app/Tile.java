/**
 * 
 */
package app;

/**
 * @author
 *
 */
public class Tile {
	private Colour tileColour;

	public enum Colour {
		BROWN, GREEN
	}

	public Tile(Colour tileColour) {
		this.tileColour = tileColour;
	}

}
