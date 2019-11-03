package model;

/**
 * A class representing a Rabbit piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * 
 * @version 2.0
 */
public class Rabbit extends Piece {
	private RabbitColour colour;
	
	public enum RabbitColour{
		BROWN, WHITE
	}

	public Rabbit(RabbitColour colour) {
		super(PieceType.RABBIT);
		this.colour = colour;
	}

	/**
	 * Retrieve the color of the rabbit.
	 * 
	 * @return True if the Rabbit is brown, false if the Rabbit is white.
	 */
	public RabbitColour isColour() {
		return colour;
	}

	/**
	 * @param move
	 * @param board The board on which this move is taking place
	 * @return True if the move is successful, false otherwise.
	 */
	@Override
	public boolean move(Move move, Board board) {
		if (move.direction() == -1) {
			return false;
		}

		if (validatePath(move, board)) {
			board.removePiece(move.xStart, move.yStart);
			board.setPiece(this, move.xEnd, move.yEnd);
			return true;
		}
		return false;
	}

	/**
	 * Validate the path of a rabbit given a move object.
	 * 
	 * @param move  The object representing the move
	 * @param board The board on which the move is taking place.
	 * @return True if the path for this move is valid for rabbits, false otherwise.
	 */
	private boolean validatePath(Move move, Board board) {
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();
		int direction = move.direction();

		// Rabbits must jump over at least one obstacle
		if ((direction == -1 || Math.abs(xDistance) == 1 || Math.abs(yDistance) == 1)
				|| (direction == 0 && !horizontalMove(move, board, xDistance))
				|| (direction == 1 && !verticalMove(move, board, yDistance))) {
			return false;
		}

		return !board.isOccupied(move.xEnd, move.yEnd);
	}

	/**
	 * This method checks if the vertical path for the rabbit is valid.
	 * 
	 * @param move
	 * @param board
	 * @param xDistance
	 * @return True if the path is valid, otherwise false
	 */
	private boolean verticalMove(Move move, Board board, int yDistance) {
		if (yDistance < 0) { // Moving up
			for (int i = move.yStart - 1; i > move.yEnd; i--) {
				if (!board.isOccupied(move.xStart, i)) {
					return false;
				}
			}
		} else { // Moving down
			for (int i = move.yStart + 1; i < move.yEnd; i++) {
				if (!board.isOccupied(move.xStart, i)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * This method checks if the horizontal path for the rabbit is valid.
	 * 
	 * @param move
	 * @param board
	 * @param xDistance
	 * @return True if the path is valid, otherwise false
	 */
	private boolean horizontalMove(Move move, Board board, int xDistance) {
		if (xDistance < 0) { // Moving left
			for (int i = move.xStart - 1; i > move.xEnd; i--) {
				if (!board.isOccupied(i, move.yStart)) {
					return false;
				}
			}
		} else { // Moving right
			for (int i = move.xStart + 1; i < move.xEnd; i++) {
				if (!board.isOccupied(i, move.yStart)) {
					return false;
				}
			}
		}
		return true;
	}
}
