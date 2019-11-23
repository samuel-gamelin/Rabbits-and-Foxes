package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Piece.PieceType;

/**
 * Tests for the Mushroom class (for sake of completeness).
 * 
 * @author Abdalla El Nakla
 * @author John Breton
 */

public class MushroomTest {
	private Mushroom mushroom;

	@Before
	public void setUp() {
		mushroom = new Mushroom();
	}

	@Test
	public void testConstructor() {
		assertTrue(mushroom instanceof Mushroom);
		assertTrue(!(mushroom instanceof MovablePiece));
	}

	@Test
	public void testPieceType() {
		assertEquals(PieceType.MUSHROOM, mushroom.getPieceType());
	}

	@Test
	public void testToString() {
		assertEquals("MU", mushroom.toString());
	}

}
