import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Mushroom;
import model.Piece.PieceType;

public class MushroomTest {
	private Mushroom mushroom;
	
	@Before
	public void setUp(){
		mushroom = new Mushroom();
	}
	@Test
	public void getPieceType() {
		assertEquals(PieceType.MUSHROOM,mushroom.getPieceType());
	}
	
	@Test
	public void testMove() {
		assertFalse(mushroom.move(null, null));
	}

}
