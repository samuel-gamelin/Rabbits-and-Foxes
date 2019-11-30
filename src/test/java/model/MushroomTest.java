package model;

import model.Piece.PieceType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        assertNotNull(mushroom);
        assertFalse(mushroom instanceof MovablePiece);
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
