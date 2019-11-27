package model;

import model.Tile.TileColour;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * A suite of tests for the Tile class.
 * 
 * @author Samuel Gamelin
 */
public class TileTest {
	private Tile tile1;
	private Tile tile2;

	@Before
	public void setUp() {
		tile1 = new Tile(TileColour.BROWN);
		tile2 = new Tile(TileColour.GREEN);
	}

	@Test
	public void testIsOccupied() {
		tile1.placePiece(new Mushroom());
		tile2.placePiece(null);
		assertTrue(tile1.isOccupied());
		assertFalse(tile2.isOccupied());
	}

	@Test
	public void testRemovePiece() {
		Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.BROWN);
		tile1.placePiece(rabbit1);
		assertEquals(rabbit1, tile1.removePiece());
		assertNull(tile1.removePiece());
		assertNull(tile2.removePiece());
	}

	@Test
	public void testRetrievePiece() {
		Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.WHITE);
		tile1.placePiece(rabbit1);
		assertEquals(rabbit1, tile1.retrievePiece());
		assertNull(tile2.retrievePiece());
	}

	@Test
	public void testPlacePiece() {
		Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.WHITE);
		tile1.placePiece(rabbit1);
		assertEquals(rabbit1, tile1.retrievePiece());
		assertNull(tile2.retrievePiece());
	}

	@Test
	public void testGetColour() {
		assertEquals(Tile.TileColour.BROWN, tile1.getColour());
		assertEquals(Tile.TileColour.GREEN, tile2.getColour());
	}

	@Test
	public void testCopyConstructor() {
		assertNotEquals(tile1, tile2);
		tile1 = new Tile(tile2);
		assertEquals(tile1, tile2);
		tile2 = new Tile(tile1);
		assertEquals(tile2, tile1);
	}
}
