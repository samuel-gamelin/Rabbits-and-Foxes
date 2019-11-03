import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Fox;
import model.Move;

public class FoxTest {
	
	private Board board;
	
	private Fox fox1;
	private Fox fox2;
	private Fox fox3;
	private Fox fox4;
	private Fox fox5;
	private Fox fox6;
	private Fox fox7;
	private Fox fox8;
	
	@Before
	public void setUp() {
		board = new Board();
		
		fox1 = new Fox(Fox.FoxType.HEAD, Fox.Direction.LEFT, false, false);
		fox2 = new Fox(Fox.FoxType.TAIL, Fox.Direction.LEFT, true, true);
		fox3 = new Fox(Fox.FoxType.HEAD, Fox.Direction.UP, false, false);
		fox4 = new Fox(Fox.FoxType.TAIL, Fox.Direction.UP, true, true);
		
		fox5 = new Fox(Fox.FoxType.HEAD, Fox.Direction.RIGHT, false, true);
		fox6 = new Fox(Fox.FoxType.TAIL, Fox.Direction.RIGHT, true, false);
		fox7 = new Fox(Fox.FoxType.HEAD, Fox.Direction.DOWN, false, false);
		fox8 = new Fox(Fox.FoxType.TAIL, Fox.Direction.DOWN, true, true);
		
	}
	

	@Test
	public void testDirection() {
		assertEquals(Fox.Direction.LEFT,fox1.getDirection());
		assertEquals(Fox.Direction.UP,fox3.getDirection());
		assertEquals(Fox.Direction.RIGHT,fox5.getDirection());
		assertEquals(Fox.Direction.DOWN,fox7.getDirection());
	}
	
	@Test
	public void getFoxType() {
		assertEquals(Fox.FoxType.HEAD,fox1.getFoxType());
		assertEquals(Fox.FoxType.TAIL,fox2.getFoxType());

	}
	/**
	 * This will also check for validate path if it works
	 */
	@Test
	public void testFoxMove()
	{
		Move move = new Move(1,2,3,4);
		assertFalse(fox1.move(move, board));
		move = new Move(1,0,1,2);
		assertTrue(fox3.move(move, board));
		assertFalse(fox1.move(move, board));
		
		
	}


}
