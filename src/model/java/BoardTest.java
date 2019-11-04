import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Move;
import model.Piece;
import model.Rabbit;

public class BoardTest {
	private Board board;
	
	@Before
	public void setUp() {
		board = new Board();
		
	}
	@Test
	public void testBoardNotNull() {
		assertNotNull(board);
	}
	
	@Test
	public void testBoardMove() {
		Move move = new Move(1,2,3,4);
		assertFalse(board.move(move));

	}
	@Test
	public void testIsOccupied() {
		assertTrue(board.isOccupied(1, 1));
		assertFalse(board.isOccupied(0, 0));
		assertFalse(board.isOccupied(6, 6));
		
	}
	public void testGetPiece() {
		assertEquals(Piece.PieceType.FOX,board.getPiece(1, 1));
		assertEquals(Piece.PieceType.MUSHROOM,board.getPiece(3, 1));
		assertEquals(Piece.PieceType.RABBIT,board.getPiece(4, 2));
	}
	
	@Test
	public void testSetPiece() {
		Piece piece = new Rabbit(Rabbit.RabbitColour.BROWN);
		assertTrue(board.setPiece(piece,0 , 0));
		assertTrue(board.setPiece(piece, 1, 1));//overwrites entry on board
	}
	@Test 
	public void testRemovePiece() {
		Rabbit rabbit1 = new Rabbit(Rabbit.RabbitColour.WHITE);
		assertEquals(rabbit1.getClass(),board.removePiece(4, 2).getClass());
	}

	@Test
	public void testIsWinningState() {
		assertFalse(board.isInWinningState());
		board.removePiece(3, 0);
		board.removePiece(4, 2);
		assertFalse(board.isInWinningState());
		board.removePiece(1, 4);
		assertTrue(board.isInWinningState()); // if there are no rabbits on the board the game is won

	}
	
	

}
