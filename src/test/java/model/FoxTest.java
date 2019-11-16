package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.createBoard;
import model.Fox;
import util.Move;

public class FoxTest {

	private createBoard board;
	private Fox fox1;
	private Fox fox2;

	@Before
	public void setUp() {
		board = new createBoard();
		fox1 = new Fox(Fox.Direction.UP, true);
		fox2 = new Fox(Fox.Direction.LEFT, false);
	}

	@Test
	public void testGetOtherHalf() {
		assertEquals(Fox.FoxType.TAIL, fox1.getOtherHalf().getFoxType());
	}

	@Test
	public void testDirection() {
		assertEquals(Fox.Direction.LEFT, fox2.getDirection());
		assertEquals(Fox.Direction.UP, fox1.getDirection());
	}

	@Test
	public void getFoxType() {
		assertEquals(Fox.FoxType.HEAD, fox1.getFoxType());
		assertEquals(Fox.FoxType.TAIL, fox2.getOtherHalf().getFoxType());

	}

	public void getFoxId() {
		assertEquals(true, fox1.getID());
		assertEquals(false, fox2.getID());
	}

	/**
	 * This will also check for validate path if it works
	 */
	@Test
	public void testFoxMove() {
		Move move = new Move(1, 2, 3, 4);
		assertFalse(fox1.move(move, board));
		move = new Move(1, 0, 1, 2);
		assertFalse(fox2.move(move, board));
		assertTrue(fox1.move(move, board));

	}

}
