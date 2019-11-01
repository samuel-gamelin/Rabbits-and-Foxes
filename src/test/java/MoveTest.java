import static org.junit.Assert.*;

import org.junit.Test;

import model.Move;

/**
 * 
 * @author Mohamed
 *
 */
public class MoveTest {

	/**
	 * 
	 */
	@Test
	public void testIntInput() {
		Move move = new Move(1, 2, 5, 6);
		assertEquals(1, move.xStart);
		assertEquals(2, move.yStart);
		assertEquals(5, move.xEnd);
		assertEquals(6, move.yEnd);
	}

	/**
	 * 
	 */
	@Test
	public void testXDistance() {
		Move move = new Move(1, 2, 5, 6);
		assertEquals(4, move.xDistance());
	}

	/**
	 * 
	 */
	@Test
	public void testYDistance() {
		Move move = new Move(1, 2, 5, 6);
		assertEquals(4, move.yDistance());
	}

	/**
	 * 
	 */
	@Test
	public void testDirection() {
		Move move = new Move(1, 2, 5, 6);
		assertEquals(-1, move.direction());
		move = new Move(1, 2, 1, 6);
		assertEquals(1, move.direction());
		move = new Move(1, 1, 1, 1);
		assertEquals(-1, move.direction());
		move = new Move(4, 1, 6, 1);
		assertEquals(0, move.direction());

	}

}
