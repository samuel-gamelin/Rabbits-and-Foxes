package model;

import model.Piece.PieceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Mushroom class (for sake of completeness).
 *
 * @author Abdalla El Nakla
 * @author John Breton
 */

public class MushroomTest {
    private Mushroom mushroom;

    @BeforeEach
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
