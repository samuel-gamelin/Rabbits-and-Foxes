package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Rabbit;
import util.Move;

/**
 * Tests for the Board class.
 * 
 * @author Abdalla El Nakla
 * @author John Breton
 */

public class BoardTest {
	private Board board;
	private final String TESTBOARD = "RBG MU X X X FHU1 FTU1 X X X X X RBB X X X X X X X X X X X X";

	@Before
	public void setUp() {
		board = Board.createBoard(TESTBOARD);
	}

	@Test
	public void testBoardConstructor() {
		Board emptyBoard = new Board();
		assertNotNull(emptyBoard);
		assertEquals(emptyBoard, new Board(emptyBoard));
		assertNull(Board.createBoard("This is a test of malformed strings being passed to the facotry method."));
		assertNotNull(board);
	}

	@Test
	public void testBoardMove() {
		assertFalse(board.move(new Move(1, 2, 3, 4)));
		assertTrue(board.move(new Move(0, 0, 0, 2)));
		// Need to check to ensure a move is not null.
		assertFalse(board.move(null));
	}

	@Test
	public void testIsOccupied() {
		assert(board.isOccupied(1, 1));
		assertTrue(board.isOccupied(0, 0));
		assertFalse(board.isOccupied(6, 6));
		assertFalse(board.isOccupied(-1, -1));
	}

	@Test
	public void testGetPiece() {
		assertTrue(board.getPiece(1, 0) instanceof Fox);
		// Ensuring the piece hasn't been removed after an initial get.
		for (int i = 0; i < 2; i++) {
			assertTrue(board.getPiece(0, 1) instanceof Mushroom);
		}
		assertTrue(board.getPiece(2, 2) instanceof Rabbit);
		assertNull(board.getPiece(-1, -1));
		assertNull(board.getPiece(2, 3));
	}

	@Test
	public void testSetPiece() {
		Fox fox = new Fox(Fox.Direction.DOWN, true);
		assertTrue(board.setPiece(fox, 1, 1));
		assertTrue(board.setPiece(fox, 0, 0)); // overwrites the rabbit
		assertFalse(board.setPiece(fox, 6, 6));
		assertFalse(board.setPiece(null, 3, 3)); 
	}

	@Test
	public void testRemovePiece() {
		assertTrue(board.removePiece(0, 0) instanceof Rabbit);
		assertTrue(board.removePiece(0, 1) instanceof Mushroom);
		assertTrue(board.removePiece(1, 0) instanceof Fox);
		assertFalse(board.removePiece(0, 2) instanceof Fox);
		assertNull(board.removePiece(-1, -1));
	}
	
	@Test
	public void testGetPossibleMoves() {
		assertFalse(board.getPossibleMoves().isEmpty());
		Board emptyBoard = Board.createBoard("X X X X X X X X X X X X X X X X X X X X X X X X X");
		assertTrue(emptyBoard.getPossibleMoves().isEmpty());
	}
	
	@Test
	public void testToString() {
		Board emptyBoard = Board.createBoard("X X X X X X X X X X X X X X X X X X X X X X X X X");
		assertEquals(emptyBoard.toString(), "X X X X X X X X X X X X X X X X X X X X X X X X X");
		assertEquals(board.toString(), TESTBOARD);
	}

	@Test
	public void testIsWinningState() {
		assertTrue(board.isInWinningState()); // Game begins in winning state.
		board.removePiece(2, 2);
		assertTrue(board.isInWinningState()); // One rabbit remains, game is in winning state.
		board.removePiece(0, 0);
		assertFalse(board.isInWinningState()); // No more rabbits, the game is not in a winning state.
		board.setPiece(new Mushroom(), 0, 0);
		assertFalse(board.isInWinningState()); // Make sure it is actually checking for rabbits in brown holes.
	}
}