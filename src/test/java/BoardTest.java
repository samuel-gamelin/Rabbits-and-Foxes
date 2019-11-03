import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;

public class BoardTest {
	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
		
	}

	@Test
	public void testIsWinningState() {
		assertFalse(board.isInWinningState());
		board.removePiece(3, 0);
		board.removePiece(4, 2);
		assertFalse(board.isInWinningState());
		board.removePiece(1, 4);
		assertTrue(board.isInWinningState()); // if there are no rabbits on the board the game is won

	}

}
