import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Mushroom;
import model.Rabbit;
import model.Tile;

public class TileTest {

	public static final int SIZE = 5;

	private Tile[][] tiles;
	private Board board;

	@Before
	public void setUp() {
		tiles = new Tile[SIZE][SIZE];
		board = new Board();
		// Corner brown tiles
		tiles[0][0] = new Tile(Tile.Colour.BROWN);
		tiles[4][0] = new Tile(Tile.Colour.BROWN);
		tiles[0][4] = new Tile(Tile.Colour.BROWN);
		tiles[4][4] = new Tile(Tile.Colour.BROWN);

		// Center brown tile
		tiles[2][2] = new Tile(Tile.Colour.BROWN);

		// Regular green tiles
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (tiles[i][j] == null) {
					tiles[i][j] = new Tile(Tile.Colour.GREEN);
				}
			}
		}

	}

	@Test
	public void testIsOccupied() {
		tiles[0][0].placePiece(new Mushroom());
		assertTrue(tiles[0][0].isOccupied());
		assertFalse(tiles[0][1].isOccupied());

	}

	@Test
	public void testRemovePiece() {
		Rabbit rabbit1 = new Rabbit(true);
		tiles[1][1].placePiece(rabbit1);
		assertEquals(rabbit1, tiles[1][1].removePiece());

	}

	@Test
	public void testRetrievePiece() {
		Rabbit rabbit1 = new Rabbit(true);
		tiles[1][1].placePiece(rabbit1);
		assertEquals(rabbit1, tiles[1][1].retrievePiece());
	}

	@Test
	public void testColor() {
		assertEquals(Tile.Colour.BROWN, tiles[0][0].getColour());
		assertEquals(Tile.Colour.BROWN, tiles[2][2].getColour());
		assertEquals(Tile.Colour.BROWN, tiles[4][0].getColour());
		assertEquals(Tile.Colour.BROWN, tiles[0][4].getColour());
		assertEquals(Tile.Colour.BROWN, tiles[4][4].getColour());
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (tiles[i][j] == null) {
					assertEquals(Tile.Colour.GREEN, tiles[i][j].getColour());
				}

			}
		}
	}
}
