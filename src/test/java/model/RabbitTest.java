package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Move;

import static org.junit.jupiter.api.Assertions.*;

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

    @BeforeEach
    public void setUp() {
        rabbitBrown = new Rabbit(Rabbit.RabbitColour.BROWN);
        rabbitWhite = new Rabbit(Rabbit.RabbitColour.WHITE);
        String TESTBOARD = "X MU X X X FHU1 FTU1 X X X X X X X X X X X X X X X X X X";
        board = Board.createBoard("Anyboard", TESTBOARD);
        assertNotNull(board);
        board.setPiece(rabbitWhite, 0, 0);
        board.setPiece(rabbitBrown, 2, 2);
    }

    @Test
    public void testRabbitConstructors() {
        assertNotNull(rabbitBrown);
        Rabbit rabbitGray = Rabbit.createRabbit("RBG");
        assertNotNull(rabbitGray);
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
        assertEquals("RBW", rabbitWhite.toString());
    }
}
