package model;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resources.Resources;
import util.Move;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Board class.
 *
 * @author Abdalla El Nakla
 * @author John Breton
 * @author Samuel Gamelin
 */
public class BoardTest {
    private Board board1;
    private Board board2;
    private final String TESTBOARD1 = "RBG MU X X X FHU1 FTU1 X X X X X RBB X X X X X X X X X X X X";

    @BeforeEach
    public void setUp() {
        board1 = Board.createBoard("Anyboard", TESTBOARD1);
        String TESTBOARD2 = "RBW MU X X X FHU1 FTU1 X X X X X RBW X X X X X X X X X X MU X";
        board2 = Board.createBoard("Anyboard", TESTBOARD2);
    }

    @Test
    public void testBoardConstructor() {
        Board emptyBoard = new Board("Anyboard");
        assertNotNull(emptyBoard);
        assertEquals(emptyBoard, new Board(emptyBoard));
        assertNull(Board.createBoard("Anyboard",
                "This is a test of malformed strings being passed to the facotry method."));
        assertNotNull(board1);
    }

    @Test
    public void testBoardMove() {
        assertFalse(board1.move(new Move(1, 2, 3, 4)));
        assertTrue(board1.move(new Move(0, 0, 0, 2)));
        // Need to check to ensure a move is not null.
        assertFalse(board1.move(null));
    }

    @Test
    public void testIsOccupied() {
        assert (board1.isOccupied(1, 1));
        assertTrue(board1.isOccupied(0, 0));
        assertFalse(board1.isOccupied(6, 6));
        assertFalse(board1.isOccupied(-1, -1));
    }

    @Test
    public void testGetPiece() {
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
    public void testSetPiece() {
        Fox fox = new Fox(Fox.Direction.DOWN, true);
        assertTrue(board1.setPiece(fox, 1, 1));
        assertTrue(board1.setPiece(fox, 0, 0)); // overwrites the rabbit
        assertFalse(board1.setPiece(fox, 6, 6));
        assertFalse(board1.setPiece(null, 3, 3));
    }

    @Test
    public void testRemovePiece() {
        assertTrue(board1.removePiece(0, 0) instanceof Rabbit);
        assertTrue(board1.removePiece(0, 1) instanceof Mushroom);
        assertTrue(board1.removePiece(1, 0) instanceof Fox);
        assertFalse(board1.removePiece(0, 2) instanceof Fox);
        assertNull(board1.removePiece(-1, -1));
    }

    @Test
    public void testGetPossibleMoves() {
        assertFalse(board1.getPossibleMoves().isEmpty());
        Board emptyBoard = Board.createBoard("Anyboard", "X X X X X X X X X X X X X X X X X X X X X X X X X");
        assertNotNull(emptyBoard);
        assertTrue(emptyBoard.getPossibleMoves().isEmpty());
    }

    @Test
    public void testToString() {
        Board emptyBoard = Board.createBoard("Anyboard", "X X X X X X X X X X X X X X X X X X X X X X X X X");
        assertNotNull(emptyBoard);
        assertEquals("X X X X X X X X X X X X X X X X X X X X X X X X X", emptyBoard.toString());
        assertEquals(board1.toString(), TESTBOARD1);
    }

    @Test
    public void testIsWinningState() {
        assertTrue(board1.isInWinningState()); // Game begins in winning state.
        board1.removePiece(2, 2);
        assertTrue(board1.isInWinningState()); // One rabbit remains, game is in winning state.
        board1.removePiece(0, 0);
        assertFalse(board1.isInWinningState()); // No more rabbits, the game is not in a winning state.
        board1.setPiece(new Mushroom(), 0, 0);
        assertFalse(board1.isInWinningState()); // Make sure it is actually checking for rabbits in brown holes.
    }

    @Test
    public void testSaveAndLoadBoard() {
        assertTrue(board1.saveBoard("testBoard.json")); // Save the first board

        JsonObject savedBoardObject = Resources.loadJsonObjectFromPath(new File("testBoard.json").getAbsolutePath(),
                true); // Reload the board as a JSON object

        JsonObject jsonObject = new JsonObject(); // Create a mock JSON object that should be equal to the one that was just loaded
        jsonObject.addProperty("name", board1.getName());
        jsonObject.addProperty("board", board1.toString());

        assertEquals(jsonObject, savedBoardObject); // Check that these two JSON objects are the same

        assertTrue(board2.saveBoard("testBoard.json")); // Replicate for the second board (ensuring the file is properly overwritten)

        savedBoardObject = Resources.loadJsonObjectFromPath(new File("testBoard.json").getAbsolutePath(), true);

        jsonObject = new JsonObject();
        jsonObject.addProperty("name", board2.getName());
        jsonObject.addProperty("board", board2.toString());

        assertEquals(jsonObject, savedBoardObject);

        assertTrue(new File("testBoard.json").delete()); // Ensure the file is properly deleted
    }
}
