package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Move;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Fox class.
 *
 * @author Abdalla El Nakla
 * @author John Breton
 */
class FoxTest {

    private Board board;

    private Fox fox1;

    private Fox fox2;

    @BeforeEach
    void setUp() {
        String TEST_BOARD = "RBG MU X X X FHU1 FTU1 X X X FHL0 X RBB X X FTL0 X X X X X X X X X";
        board = Board.createBoard("Anyboard", TEST_BOARD);
        fox1 = new Fox(Fox.Direction.UP, true);
        fox2 = new Fox(Fox.Direction.LEFT, false);
        board.setPiece(fox1, 0, 1);
    }

    @Test
    void testFoxConstructors() {
        assertNotNull(fox1);
        assertNotNull(fox2);
        Fox fox3 = Fox.createFox("FHD1");
        assertNotNull(fox3);
        Fox fakeFox = Fox.createFox("AAAAAAAAAAAAA");
        assertNull(fakeFox);
    }

    @Test
    void testGetOtherHalf() {
        assertNotNull(fox1.getOtherHalf());
        assertEquals(Fox.FoxType.TAIL, fox1.getOtherHalf().getFoxType());
    }

    @Test
    void testGetDirection() {
        assertEquals(Fox.Direction.LEFT, fox2.getDirection());
        assertEquals(Fox.Direction.UP, fox1.getDirection());
    }

    @Test
    void testGetFoxType() {
        assertEquals(Fox.FoxType.HEAD, fox1.getFoxType());
        assertEquals(Fox.FoxType.TAIL, fox2.getOtherHalf().getFoxType());
    }

    @Test
    void testId() {
        assertTrue(fox1.id());
        assertFalse(fox2.id());
    }

    @Test
    void testGetPossibleMoves() {
        // By design, the head will only generate possible moves in front of itself.
        assertTrue(fox1.getPossibleMoves(board, 0, 1).isEmpty());
        // By design, the tail will only generate possible moves behind itself.
        assertFalse(fox1.getOtherHalf().getPossibleMoves(board, 1, 1).isEmpty());
    }

    /**
     * This will also check for validate path if it works
     */
    @Test
    void testFoxMove() {
        assertFalse(fox1.move(new Move(1, 2, 3, 4), board));
        Move move = new Move(1, 0, 1, 2);
        assertFalse(fox2.move(move, board));
        assertTrue(fox1.move(move, board));
        assertFalse(fox1.move(null, null));
        assertFalse(fox1.move(new Move(1, 2, 1, 0), null));
    }

    @Test
    void testToString() {
        assertEquals(fox1.toString(), "FHU1");
        assertEquals(fox2.toString(), "FHL0");
        assertEquals(fox2.getOtherHalf().toString(), "FTL0");
    }
}
