import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Rabbit;
import model.Board;
import model.Move;
public class RabbitTest {
	private Rabbit rabbitBrown;
	private Rabbit rabbitWhite;
	private Board board; 

	
	@Before
	public void setUp() {
		rabbitBrown = new Rabbit(true);
		rabbitWhite = new Rabbit(false);
		board = new Board();
		
	}

	@Test
	public void testIsColour() {
		assertFalse(rabbitWhite.isColour());
		assertTrue(rabbitBrown.isColour());

	}
	@Test
	public void testRabbitMovement() {
		board.setPiece(rabbitWhite, 0, 0);
		Move move = new Move(0,0,2,0);
		assertTrue(rabbitWhite.move(move, board));
		move = new Move(1,0,1,4);
		assertFalse(rabbitWhite.move(move, board));
	}

}
