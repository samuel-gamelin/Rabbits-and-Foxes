package util;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import model.Board;

/**
 * Tests for the Solver class.
 * 
 * @author John Breton
 */
public class SolverTest {
	private Board normalBoard;
	private Board hardBoard;
	private Board unsolvableBoard;
	private Move noMove;
	private final String NORMAL = "X X X X X MU X X X MU RBB X X X RBG X X X X X X X X X X";
	private final String HARD = "X X RBG X MU RBB X X X X X X RBW X X X FHU1 FTU1 FHL0 X MU X MU FTL0 X";
	private final String UNSOLVABLE = "X X RBG X X X X X X X X X X X X X X X X X X X X X X";

	@Before
	public void setUp() {
		normalBoard = Board.createBoard(NORMAL);
		hardBoard = Board.createBoard(HARD);
		unsolvableBoard = Board.createBoard(UNSOLVABLE);
		noMove = new Move(-1, -1, -1, -1);
	}

	@Test
	public void testGetNextBestMove() {
		// The Solver should find the next best move since this board is solvable.
		assertNotSame(Solver.getNextBestMove(normalBoard).xStart, noMove.xStart);
		// This board is solvable, and has an 82 move optimal solution (our most complex
		// puzzle).
		// The Solver should find a winning path.
		assertNotSame(Solver.getNextBestMove(hardBoard).xStart, noMove.xStart);
		// Passing in null should return the move (-1, -1, -1, -1), since an optimal
		// move can't be found.
		assertSame(Solver.getNextBestMove(null).xStart, noMove.xStart);
		// Likewise, passing in a Board with no solution should also return the move
		// (-1, -1, -1, -1).
		assertSame(Solver.getNextBestMove(unsolvableBoard).xStart, noMove.xStart);
	}
}
