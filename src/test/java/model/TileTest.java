package model;

import model.Tile.TileColour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Tile class.
 *
 * @author Samuel Gamelin
 */
class TileTest {

    private Tile tile1;
    private Tile tile2;

    @BeforeEach
    void setUp() {
        tile1 = new Tile(TileColour.BROWN);
        tile2 = new Tile(TileColour.GREEN);
    }

    @Test
    void testIsOccupied() {
        tile1.placePiece(new Mushroom());
        tile2.placePiece(null);
        assertTrue(tile1.isOccupied());
        assertFalse(tile2.isOccupied());
    }

    @Test
    void testRemovePiece() {
        Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.BROWN);
        tile1.placePiece(rabbit1);
        assertEquals(rabbit1, tile1.removePiece());
        assertNull(tile1.removePiece());
        assertNull(tile2.removePiece());
    }

    @Test
    void testRetrievePiece() {
        Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.WHITE);
        tile1.placePiece(rabbit1);
        assertEquals(rabbit1, tile1.getPiece());
        assertNull(tile2.getPiece());
    }

    @Test
    void testPlacePiece() {
        Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.WHITE);
        tile1.placePiece(rabbit1);
        assertEquals(rabbit1, tile1.getPiece());
        assertNull(tile2.getPiece());
    }

    @Test
    void testGetColour() {
        assertEquals(Tile.TileColour.BROWN, tile1.getTileColour());
        assertEquals(Tile.TileColour.GREEN, tile2.getTileColour());
    }

    @Test
    void testCopyConstructor() {
        assertNotEquals(tile1, tile2);
        tile1 = new Tile(tile2);
        assertEquals(tile1, tile2);
        tile2 = new Tile(tile1);
        assertEquals(tile2, tile1);
    }
}
