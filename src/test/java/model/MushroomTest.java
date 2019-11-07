package model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Mushroom;
import model.Piece.PieceType;
import util.Move;

public class MushroomTest {
	private Mushroom mushroom;

	@Before
	public void setUp() {
		mushroom = new Mushroom();
	}

	@Test
	public void getPieceType() {
		assertEquals(PieceType.MUSHROOM, mushroom.getPieceType());
	}

	@Test
	public void testMove() {
		Move move = new Move(1, 2, 3, 4);
		Board board = new Board();
		assertFalse(mushroom.move(move, board));
	}

}
