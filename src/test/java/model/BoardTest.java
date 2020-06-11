package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Move;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Board class.
 *
 * @author Abdalla El Nakla
 * @author John Breton
 * @author Samuel Gamelin
 */
class BoardTest {

    private final String TEST_BOARD_1 = "RBG MU X X X FHU1 FTU1 X X X X X RBB X X X X X X X X X X X X";
    private Board board1;

    @BeforeEach
    void setUp() {
        board1 = Board.createBoard("Anyboard", TEST_BOARD_1);
    }

    @Test
    void testBoardConstructor() {
        Board emptyBoard = new Board("Anyboard");
        assertNotNull(emptyBoard);
        assertEquals(emptyBoard, new Board(emptyBoard));
        assertNull(Board.createBoard("Anyboard", "This is a test of malformed strings being passed to the facotry " +
                "method."));
        assertNotNull(board1);
    }

    @Test
    void testBoardMove() {
        assertFalse(board1.move(new Move(1, 2, 3, 4)));
        assertTrue(board1.move(new Move(0, 0, 0, 2)));
        // Need to check to ensure a move is not null.
        assertFalse(board1.move(null));
    }

    @Test
    void testIsOccupied() {
        assertTrue(board1.isOccupied(1, 1));
        assertTrue(board1.isOccupied(0, 0));
        assertFalse(board1.isOccupied(6, 6));
        assertFalse(board1.isOccupied(-1, -1));
    }

    @Test
    void testGetPiece() {
        assertTrue(board1.getPiece(1, 0) instanceof Fox);
        // Ensuring the piece hasn't been removed after an initial get.
        for (int i = 0; i < 2; i++) {
            assertTrue(board1.getPiece(0, 1) instanceof Mushroom);
        }
        assertTrue(board1.getPiece(2, 2) instanceof Rabbit);
        assertNull(board1.getPiece(-1, -1));
        assertNull(board1.getPiece(2, 3));
    }

    @Test
    void testSetPiece() {
        Fox fox = new Fox(Fox.Direction.DOWN, true);
        assertTrue(board1.setPiece(fox, 1, 1));
        assertTrue(board1.setPiece(fox, 0, 0)); // overwrites the rabbit
        assertFalse(board1.setPiece(fox, 6, 6));
        assertFalse(board1.setPiece(null, 3, 3));
    }

    @Test
    void testRemovePiece() {
        assertTrue(board1.removePiece(0, 0) instanceof Rabbit);
        assertTrue(board1.removePiece(0, 1) instanceof Mushroom);
        assertTrue(board1.removePiece(1, 0) instanceof Fox);
        assertFalse(board1.removePiece(0, 2) instanceof Fox);
        assertNull(board1.removePiece(-1, -1));
    }

    @Test
    void testGetPossibleMoves() {
        assertFalse(board1.getPossibleMoves().isEmpty());
        Board emptyBoard = Board.createBoard("Anyboard", "X X X X X X X X X X X X X X X X X X X X X X X X X");
        assertNotNull(emptyBoard);
        assertTrue(emptyBoard.getPossibleMoves().isEmpty());
    }

    @Test
    void testToString() {
        Board emptyBoard = Board.createBoard("Anyboard", "X X X X X X X X X X X X X X X X X X X X X X X X X");
        assertNotNull(emptyBoard);
        assertEquals("X X X X X X X X X X X X X X X X X X X X X X X X X", emptyBoard.toString());
        assertEquals(board1.toString(), TEST_BOARD_1);
    }

    @Test
    void testIsWinningState() {
        assertTrue(board1.isInWinningState()); // Game begins in winning state.
        board1.removePiece(2, 2);
        assertTrue(board1.isInWinningState()); // One rabbit remains, game is in winning state.
        board1.removePiece(0, 0);
        assertFalse(board1.isInWinningState()); // No more rabbits, the game is not in a winning state.
        board1.setPiece(new Mushroom(), 0, 0);
        assertFalse(board1.isInWinningState()); // Make sure it is actually checking for rabbits in brown holes.
    }
}
