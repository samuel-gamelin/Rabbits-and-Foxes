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
 * Tests for the Rabbit class.
 * 
 * @author Abdalla El Nakla
 * @author John Breton
 */
public class RabbitTest {
	private Rabbit rabbitBrown;
	private Rabbit rabbitWhite;
	private Board board;
	private final String TESTBOARD = "X MU X X X FHU1 FTU1 X X X X X X X X X X X X X X X X X X";

	@Before
	public void setUp() {
		rabbitBrown = new Rabbit(Rabbit.RabbitColour.BROWN);
		rabbitWhite = new Rabbit(Rabbit.RabbitColour.WHITE);
		board = Board.createBoard(TESTBOARD);
		board.setPiece(rabbitWhite, 0, 0);
		board.setPiece(rabbitBrown, 2, 2);
	}

	@Test
	public void testRabbitConstructors() {
		assertNotNull(rabbitBrown);
		Rabbit rabbitGray = Rabbit.createRabbit("RBG");
		assertTrue(rabbitGray instanceof Rabbit);
		Rabbit fakeRabbit = Rabbit.createRabbit("RABBIT");
		assertNull(fakeRabbit);
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
		assertFalse(rabbitWhite.move(null, null));
	}

	@Test
	public void testGetPossibleMoves() {
		assertFalse(rabbitWhite.getPossibleMoves(board, 0, 0).isEmpty());
		assertTrue(rabbitBrown.getPossibleMoves(board, 2, 2).isEmpty());
	}

	@Test
	public void testToString() {
		assertEquals(rabbitWhite.toString(), "RBW");
	}

}
