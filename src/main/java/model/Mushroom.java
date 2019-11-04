package model;

/***
 * A class representing a Mushroom piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * 
 * @version 2.0
 */
public class Mushroom extends Piece {

	/**
	 * Construct a new mushroom.
	 */
	public Mushroom() {
		super(PieceType.MUSHROOM);
	}

	/**
	 * Attempt to move a mushroom, which fails since they act as static obstacles in
	 * this game.
	 * 
	 * @param move  The Move that is being attempted
	 * @param board The Board on which this Move is taking place
	 * @return False. Mushrooms are not able to move in the game
	 */
	@Override
	public boolean move(Move move, Board board) {
		return false;
	}
}
