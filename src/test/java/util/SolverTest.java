package util;

import static org.junit.Assert.*;

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
	private Board rabbitBoard;
	private Board mushroomBoard;
	private Move noMove;
	private final String NORMAL = "X X X X X MU X X X MU RBB X X X RBG X X X X X X X X X X";
	private final String HARD = "X X RBG X MU RBB X X X X X X RBW X X X FHU1 FTU1 FHL0 X MU X MU FTL0 X";
	private final String MUSHROOM ="MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU MU";
	private final String RABBITHELL = "X RBG RBG RBG X RBG RBG RBG RBG RBG RBG RBG X RBG RBG RBG RBG RBG RBG RBG X RBG RBG RBG X";
	
	@Before
	public void setUp() {
		normalBoard = Board.createBoard(NORMAL);
		hardBoard = Board.createBoard(HARD);
		mushroomBoard = Board.createBoard(MUSHROOM);
		rabbitBoard = Board.createBoard(RABBITHELL);
		noMove = new Move(-1, -1, -1, -1);
	}

	@Test
	public void testGetNextBestMove() {
		assertNotSame(Solver.getNextBestMove(normalBoard).xStart, noMove.xStart);
		assertNotSame(Solver.getNextBestMove(hardBoard).xStart, noMove.xStart);
		assertSame(Solver.getNextBestMove(mushroomBoard).xStart, noMove.xStart);
		assertSame(Solver.getNextBestMove(rabbitBoard).xStart, noMove.xStart);
		assertNull(Solver.getNextBestMove(null));
	}
}
