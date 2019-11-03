package model;

/**
 * A class representing a Rabbit piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * 
 * @version 2.0
 */
public class Rabbit extends Piece {
	private boolean colour;
	
	public Rabbit(boolean colour) {
		super(PieceType.RABBIT);
		this.colour = colour;
	}
	
	/**
	 * Retrieve the colour of the rabbit.
	 * @return True if the Rabbit is brown, false if the Rabbit is white.
	 */
	public boolean isColour() {
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
	 * @param move The object representing the move
	 * @param board The board on which the move is taking place.
	 * @return True if the path for this move is valid for rabbits, false otherwise.
	 */
	private boolean validatePath(Move move, Board board) {
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();
		int direction = move.direction();

		// Static or diagonal path
		if (direction == -1) {
			return false;
		}

		if (Math.abs(xDistance) == 1 || Math.abs(yDistance) == 1) { // Rabbits must jump over at least one obstacle
			return false;
		} else if (direction == 0) { // Horizontal move
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
		} else if (direction == 1) { // Vertical move
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
		}
		
		if (board.isOccupied(move.xEnd, move.yEnd)) { // Ensure the landing spot is clear
			return false;
		}
		return true;
	}
}
