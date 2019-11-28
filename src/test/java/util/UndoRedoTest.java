package util;

import controller.GameController;
import model.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the undo/redo feature.
 * 
 * @author John Breton
 */
public class UndoRedoTest {

	private GameController controller;

	@Before
	public void setUp() {
		String BOARD_DATA = "X X X X X FHU1 FTU1 X X RBB X X X X MU RBW MU X FHL0 X X X RBG FTL0 X";
		Board testBoard = Board.createBoard("Anyboard", BOARD_DATA);

		controller = new GameController(testBoard, -1);
		// Add a couple moves that can be undone/redone
		controller.registerClick(3, 0);
		controller.registerClick(3, 2);
		controller.registerClick(4, 2);
		controller.registerClick(2, 2);
	}

	@Test
	public void testUndoRedoMoves() {
		// We have not undone anything, so redoMove should return false
		assertFalse(controller.redoMove());

		for (int i = 0; i < 2; i++) {
			assertTrue(controller.undoMove());
		}

		// No more moves to undo
		assertFalse(controller.undoMove());

		for (int i = 0; i < 2; i++) {
			assertTrue(controller.redoMove());
		}

		// No more moves to redo
		assertFalse(controller.redoMove());

		// We should still have moves available to undo/redo after the testing
		assertTrue(controller.undoMove());
		assertTrue(controller.redoMove());

		// Make a new move
		controller.registerClick(1, 0);
		controller.registerClick(1, 2);

		// We should not be able to redo after a move
		assertFalse(controller.redoMove());
		// But we can undo
		assertTrue(controller.undoMove());
		// Which we can then redo
		assertTrue(controller.redoMove());
	}
}
