package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import util.Move;

/**
 * Tests for the Fox class.
 * 
 * @author Abdalla El Nakla
 * @author John Breton
 */
public class FoxTest {

	private Board board;

	private final String TESTBOARD = "RBG MU X X X FHU1 FTU1 X X X FHL0 X RBB X X FTL0 X X X X X X X X X";

	private Fox fox1;
	private Fox fox2;

	@Before
	public void setUp() {
		board = Board.createBoard(TESTBOARD);
		fox1 = new Fox(Fox.Direction.UP, true);
		fox2 = new Fox(Fox.Direction.LEFT, false);
		board.setPiece(fox1, 0, 1);
	}

	@Test
	public void testFoxConstructors() {
		assertNotNull(fox1);
		assertTrue(fox2 instanceof Fox);
		Fox fox3 = Fox.createFox("FHD1");
		assertTrue(fox3 instanceof Fox);
		Fox fakeFox = Fox.createFox("AAAAAAAAAAAAA");
		assertNull(fakeFox);
	}

	@Test
	public void testGetOtherHalf() {
		assertNotNull(fox1.getOtherHalf());
		assertEquals(Fox.FoxType.TAIL, fox1.getOtherHalf().getFoxType());
	}

	@Test
	public void testGetDirection() {
		assertEquals(Fox.Direction.LEFT, fox2.getDirection());
		assertEquals(Fox.Direction.UP, fox1.getDirection());
	}

	@Test
	public void testGetFoxType() {
		assertEquals(Fox.FoxType.HEAD, fox1.getFoxType());
		assertEquals(Fox.FoxType.TAIL, fox2.getOtherHalf().getFoxType());
	}

	@Test
	public void testGetID() {
		assertTrue(fox1.getID());
		assertFalse(fox2.getID());
	}

	@Test
	public void testGetPossibleMoves() {
		// By design, the head will only generate possible moves in front of itself.
		assertTrue(fox1.getPossibleMoves(board, 0, 1).isEmpty());
		// By design, the tail will only generate possible moves behind itself.
		assertFalse(fox1.getOtherHalf().getPossibleMoves(board, 1, 1).isEmpty());
	}

	/**
	 * This will also check for validate path if it works
	 */
	@Test
	public void testFoxMove() {
		assertFalse(fox1.move(new Move(1, 2, 3, 4), board));
		Move move = new Move(1, 0, 1, 2);
		assertFalse(fox2.move(move, board));
		assertTrue(fox1.move(move, board));
		assertFalse(fox1.move(null, null));
		assertFalse(fox1.move(new Move(1, 2, 1, 0), null));
	}

	@Test
	public void testToString() {
		assertEquals(fox1.toString(), "FHU1");
		assertEquals(fox2.toString(), "FHL0");
		assertEquals(fox2.getOtherHalf().toString(), "FTL0");
	}

}
