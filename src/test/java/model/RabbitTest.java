package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Rabbit;
import util.Move;
import model.createBoard;

public class RabbitTest {
	private Rabbit rabbitBrown;
	private Rabbit rabbitWhite;
	private createBoard board;

	@Before
	public void setUp() {
		rabbitBrown = new Rabbit(Rabbit.RabbitColour.BROWN);
		rabbitWhite = new Rabbit(Rabbit.RabbitColour.WHITE);
		board = new createBoard();

	}

	@Test
	public void testIsColour() {
		assertEquals(Rabbit.RabbitColour.WHITE, rabbitWhite.getColour());
		assertEquals(Rabbit.RabbitColour.BROWN, rabbitBrown.getColour());

	}

	@Test
	public void testRabbitMovement() {
		board.setPiece(rabbitWhite, 0, 0);
		Move move = new Move(0, 0, 2, 0);
		assertTrue(rabbitWhite.move(move, board));
		move = new Move(1, 0, 1, 4);
		assertFalse(rabbitWhite.move(move, board));
	}

}
