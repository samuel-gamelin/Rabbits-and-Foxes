package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Mushroom;
import model.Piece.PieceType;
import util.Move;

/**
 * Tests for the Mushroom class (for sake of completeness).
 * 
 * @author Abdalla El Nakla
 * @author John Breton
 */

public class MushroomTest {
	private Mushroom mushroom;
	private Board board;

	@Before
	public void setUp() {
		mushroom = new Mushroom();
		board = Board.createBoard("MU X X X X MU X X X X MU X X X X MU X X X X MU X X X X");
	}
	
	@Test
	public void testConstructor() {
		assertTrue(new Mushroom() instanceof Mushroom);
	}

	@Test
	public void getPieceType() {
		assertEquals(PieceType.MUSHROOM, mushroom.getPieceType());
	}
	
	@Test
	public void testGetPossibleMoves() {
		assertTrue(mushroom.getPossibleMoves(board, 0, 0).isEmpty());
		assertNull(mushroom.getPossibleMoves(null, 0, 0));
	}

	@Test
	public void testMove() {
		assertFalse(mushroom.move(new Move(0, 0, 0, 1), board));
		assertFalse(mushroom.move(null, null));
	}
	
	@Test
	public void testToString() {
		assertEquals(mushroom.toString(), "MU");
	}

}
