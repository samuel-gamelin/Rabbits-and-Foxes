package util;

import org.junit.Before;
import org.junit.Test;
import util.Move.MoveDirection;

import static org.junit.Assert.assertEquals;

/**
 * A suite of tests for the Move class.
 *
 * @author Mohamed Radwan
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 */
public class MoveTest {
    private Move move1;
    private Move move2;

    @Before
    public void setUp() {
        move1 = new Move(1, 2, 5, 6);
        move2 = new Move(-1, -1, -1, -1);
    }

    @Test
    public void testIntInput() {
        assertEquals(1, move1.xStart);
        assertEquals(2, move1.yStart);
        assertEquals(5, move1.xEnd);
        assertEquals(6, move1.yEnd);
        assertEquals(-1, move2.xStart);
        assertEquals(-1, move2.yStart);
        assertEquals(-1, move2.xEnd);
        assertEquals(-1, move2.yEnd);
    }

    @Test
    public void testDirection() {
        assertEquals(MoveDirection.INVALID, move1.direction());
        assertEquals(MoveDirection.INVALID, move2.direction());

        move1 = new Move(1, 3, 1, 4);
        move2 = new Move(-5, 3, 0, 3);

        assertEquals(MoveDirection.VERTICAL, move1.direction());
        assertEquals(MoveDirection.HORIZONTAL, move2.direction());
    }

    @Test
    public void testXDistance() {
        assertEquals(4, move1.xDistance());
        assertEquals(0, move2.xDistance());
    }

    @Test
    public void testYDistance() {
        assertEquals(4, move1.yDistance());
        assertEquals(0, move2.yDistance());
    }
}
