package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Board;

/**
 * Tests for the Solver class.
 * 
 * @author John Breton
 */
public class SolverTest {
	private Board easyBoard, normalBoard, hardBoard, unsolvableBoard;
	private Move noMove;
	private final String EASY = "X X X X X MU X X X MU RBB X X X RBG X X X X X X X X X X";
	private final String NORMAL = "X X X X X FHU1 FTU1 X X RBB X X X X MU RBW MU X FHL0 X X X RBG FTL0 X";
	private final String HARD = "X FTR1 X MU X X FHR1 X X X X X MU X X MU RBW X X RBG X X RBB X X";
	private final String UNSOLVABLE = "X X RBG X X X X X X X X X X X X X X X X X X X X X X";

	@Before
	public void setUp() {
		easyBoard = Board.createBoard(EASY);
		normalBoard = Board.createBoard(NORMAL);
		hardBoard = Board.createBoard(HARD);
		unsolvableBoard = Board.createBoard(UNSOLVABLE);
		noMove = new Move(-1, -1, -1, -1);
	}

	@Test
	public void testGetNextBestMove() {
		// The Solver should find the next best move since this board is solvable
		assertNotSame(Solver.getNextBestMove(normalBoard).xStart, noMove.xStart);
		// This board is solvable, and has an 84 move optimal solution (our most complex
		// puzzle solver-wise)
		// The Solver should find a winning path.
		assertNotSame(Solver.getNextBestMove(hardBoard).xStart, noMove.xStart);
		// Passing in null should return the move (-1, -1, -1, -1), since an optimal
		// move can't be found.
		assertSame(Solver.getNextBestMove(null).xStart, noMove.xStart);
		// Likewise, passing in a Board with no solution should also return the move
		// (-1, -1, -1, -1).
		assertSame(Solver.getNextBestMove(unsolvableBoard).xStart, noMove.xStart);
	}

	@Test
	public void testSolveEasyBoard() {
		/*
		 * This easy board has an optimal two move solution. The solver will apply these
		 * two moves, at which point the board will be checked to ensure it has been
		 * solved.
		 */

		// Apply the next best moves to this board, ensuring that it is
		// not in a solved state after each move.
		for (int i = 0; i < 2; i++) {
			assertFalse(normalBoard.isInWinningState());
			easyBoard.move(Solver.getNextBestMove(easyBoard));
		}

		// The board should now be solved
		assertTrue(easyBoard.isInWinningState());
	}

	@Test
	public void testSolveNormalBoard() {
		/*
		 * This normal board has an optimal six move solution. The solver will apply
		 * these six moves, at which point the board will be checked to ensure it has
		 * been solved.
		 */

		// Apply the next best moves to this board, ensuring that it is
		// not in a solved state after each move.
		for (int i = 0; i < 6; i++) {
			assertFalse(normalBoard.isInWinningState());
			normalBoard.move(Solver.getNextBestMove(normalBoard));
		}

		// The board should now be solved.
		assertTrue(normalBoard.isInWinningState());
	}
}
