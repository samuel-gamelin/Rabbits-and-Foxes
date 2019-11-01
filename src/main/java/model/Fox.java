package model;

/**
 * A class representing a Fox piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * 
 * @version 2.0
 */
public class Fox extends Piece {
	/**
	 * The direction in which the fox is facing.
	 */
	private Direction direction;

	/**
	 * The type of fox
	 */
	private FoxType foxType;

	/**
	 * A boolean used to identify which fox parts belong together and to distinguish
	 * one fox from another.
	 */
	private boolean id;

	/**
	 * An enumeration representing the head and tail of the fox
	 */
	public enum FoxType {
		HEAD, TAIL
	}

	/**
	 * An enumeration representing the direction the fox is going
	 */
	public enum Direction {
		LEFT, RIGHT, UP, DOWN
	}

	/**
	 * Construct a new fox given the head or tail and the direction
	 * 
	 * @param foxType   The fox type of the fox, as a FoxType
	 * @param direction The direction given for the Fox
	 * @param id        The id of the fox, used to differentiate different foxes
	 */
	public Fox(FoxType foxType, Direction direction, boolean id) {
		super(PieceType.FOX);
		this.foxType = foxType;
		this.direction = direction;
		this.id = id;
	}

	/**
	 * @return the Direction
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * @return the FoxType, Whether it is heads or tails
	 */
	public FoxType getFoxType() {
		return foxType;
	}

	@Override
	public boolean move(Move move, Board board) {
		if ((direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) && move.direction() != 0
				|| (direction.equals(Direction.UP) || direction.equals(Direction.DOWN)) && move.direction() != 1) {
			return false;
		}

		int xStart = move.xStart;
		int yStart = move.yStart;
		int xEnd = move.xEnd;
		int yEnd = move.yEnd;
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();

		boolean location = true; // True for immediate right or up, false otherwise

		// Find its head/tail
		if (direction.equals(Direction.LEFT) || direction.equals(Direction.RIGHT)) { // It's to the left or right
			if (xStart - 1 < 0) { // Must be to the right
				location = true;
			} else if (xStart + 1 > 4) { // Must be to the left
				location = false;
			} else { // Could be either, we need to check both
				if (board.isOccupied(xStart - 1, yStart) && board.getPiece(xStart - 1, yStart) instanceof Fox
						&& (((Fox) board.getPiece(xStart - 1, yStart)).id) == this.id) { // Check to the left
					location = false;
				} else if (board.isOccupied(xStart + 1, yStart) && board.getPiece(xStart + 1, yStart) instanceof Fox
						&& ((Fox) board.getPiece(xStart + 1, yStart)).id == this.id) { // Must be to the right
					location = true;
				}
			}
			// We have both pieces of the fox, now we try to move them.
			if (validatePath(move, board, location)) { // Only need to check one, since it will be either true for
														// both or false for both
				if (location && xDistance < 0) { // Check to see if the other part of fox is to the right and we are
													// moving left
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					board.setPiece(board.removePiece(xStart + 1, yStart), xEnd + 1, yEnd);
					return true;
				} else if (location && xDistance > 0) { // Check to see if the other part of fox is to the right and we
														// are moving right
					board.setPiece(board.removePiece(xStart + 1, yStart), xEnd + 1, yEnd);
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					return true;
				} else if (!location && xDistance > 0) { // Check to see if the other part of fox is to the left and we
															// are moving right
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					board.setPiece(board.removePiece(xStart - 1, yStart), xEnd - 1, yEnd);
					return true;
				} else { // We know the other part of the fox is to the left and we are moving left
					board.setPiece(board.removePiece(xStart - 1, yStart), xEnd - 1, yEnd);
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					return true;
				}
			}
			return false; // It was an invalid move after all.
		} else { // It's above or below it.
			if (yStart - 1 < 0) { // Must be below
				location = false;
			} else if (yStart + 1 > 4) { // Must be above
				location = true;
			} else { // Could be either, we need to check both.
				if (board.isOccupied(xStart, yStart - 1) && board.getPiece(xStart, yStart - 1) instanceof Fox
						&& ((Fox) board.getPiece(xStart, yStart - 1)).id == this.id) { // Check above
					location = true;
				} else if (board.isOccupied(xStart, yStart + 1) && board.getPiece(xStart, yStart + 1) instanceof Fox
						&& ((Fox) board.getPiece(xStart, yStart + 1)).id == this.id) { // Must be below
					location = false;
				}
			}
			// We have both pieces of the fox, now we try to move them.
			if (validatePath(move, board, location)) { // Only need to check one, since it will be either true for
														// both or false for both.
				if (location && yDistance > 0) { // Check to see if the other part of the fox is up and we are moving
													// down
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					board.setPiece(board.removePiece(xStart, yStart - 1), xEnd, yEnd - 1);
					return true;
				} else if (location && yDistance < 0) { // Check to see if the other part of the fox is up and we are
														// moving up
					board.setPiece(board.removePiece(xStart, yStart - 1), xEnd, yEnd - 1);
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					return true;
				} else if (!location && yDistance > 0) { // Check to see if the other part of the fox is down and we are
															// moving down
					board.setPiece(board.removePiece(xStart, yStart + 1), xEnd, yEnd + 1);
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					return true;
				} else { // We know the other part of the fox is down and we are moving up
					board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
					board.setPiece(board.removePiece(xStart, yStart + 1), xEnd, yEnd + 1);
					return true;
				}
			}
			return false; // It was an invalid move after all.
		}
	}

	/**
	 * Validate the path of a move object for a Fox.
	 * 
	 * @param move     The move we are trying to validate.
	 * @param fox      The fox piece we are trying to validate a move for.
	 * @param location True if the other piece of the fox is to the right or up,
	 *                 false otherwise.
	 * @return True if the path for this move is valid for foxes, false otherwise.
	 */
	private boolean validatePath(Move move, Board board, boolean location) {
		int xStart = move.xStart;
		int yStart = move.yStart;
		int xEnd = move.xEnd;
		int yEnd = move.yEnd;
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();
		int direction = move.direction();

		// Static or diagonal path
		if (direction == -1) {
			return false;
		}

		if ((this.direction.equals(Direction.LEFT) || this.direction.equals(Direction.RIGHT)) && direction == 0) { // Check to see if the fox is horizontal
																				// and the move is horizontal
			if (location && xDistance > 0) { // The other part of the fox is to the right and we are moving right
				if (xEnd + 1 > 4) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = xStart + 2; i <= xEnd + 1; i++) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(i, yStart)) {
						return false;
					}
				}
			} else if (location && xDistance < 0) { // The other part of the fox is to the right and we are moving left
				for (int i = xStart - 1; i >= xEnd; i--) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(i, yStart)) {
						return false;
					}
				}
			} else if (!location && xDistance > 0) { // The other part of the fox is to the left and we are moving right
				for (int i = xStart + 1; i <= xEnd; i++) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(i, yStart)) {
						return false;
					}
				}
			} else { // We know that the other part of the fox is to the left and we are moving left
				if (xEnd - 1 < 0) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = xStart - 2; i >= xEnd - 1; i--) {
					if (board.isOccupied(i, yStart)) {
						return false;
					}
				}
			}
			return true; // The move is valid for the fox
		} else if ((this.direction.equals(Direction.UP) || this.direction.equals(Direction.DOWN)) && direction == 1) { // Check to see if the fox is vertical
																					// and the move is vertical
			if (location && yDistance > 0) { // The other part of the fox is up and we are moving down
				for (int i = yStart + 1; i <= yEnd; i++) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(xStart, i)) {
						return false;
					}
				}
			} else if (location && yDistance < 0) { // The other part of the fox is up and we are moving up
				if (yEnd - 1 < 0) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = yStart - 2; i >= yEnd - 1; i--) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(xStart, i)) {
						return false;
					}
				}
			} else if (!location && yDistance > 0) { // The other part of the fox is down and we are moving down
				if (yEnd + 1 > 4) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = yStart + 2; i <= yEnd + 1; i++) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(xStart, i)) {
						return false;
					}
				}
			} else { // We know the other part of the fox is down and we are moving up
				for (int i = yStart - 1; i >= yEnd; i--) { // Need to make sure there are no obstacles in the path
					if (board.isOccupied(xStart, i)) {
						return false;
					}
				}
			}
			return true; // The move is valid for the fox
		}
		return false; // Direction and fox orientation did not match (invalid move)
	}
}
