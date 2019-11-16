package model;

import java.util.ArrayList;
import java.util.List;

import util.Move;
import util.Move.MoveDirection;

/**
 * A class representing a Fox piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * @author John Breton
 * @author Mohamed Radwan
 * 
 * @version 3.0
 */
public class Fox extends Piece {
	/**
	 * The direction in which the fox is facing
	 */
	private Direction direction;

	/**
	 * Stores the otherHalf of this Fox
	 */
	private Fox otherHalf;

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
	 * Construct a new Fox
	 * 
	 * @param direction The direction given for the Fox
	 * @param id        The id of the fox, used to differentiate foxes
	 */
	public Fox(Direction direction, boolean id) {
		super(PieceType.FOX);
		this.foxType = FoxType.HEAD;
		this.direction = direction;
		this.id = id;
		this.otherHalf = new Fox(direction, id, this);
	}

	/**
	 * Construct the tail of a Fox. Only called after a head has been constructed,
	 * as shown through this constructors private visibility.
	 * 
	 * @param direction The direction given for the Fox
	 * @param id        The id of the fox, used to differentiate foxes
	 * @param head      The head of this Fox's tail.
	 */
	private Fox(Direction direction, boolean id, Fox head) {
		super(PieceType.FOX);
		this.foxType = FoxType.TAIL;
		this.direction = direction;
		this.id = id;
		this.otherHalf = head;
	}

	/**
	 * A copy constructor for Fox.
	 * 
	 * @param piece The piece to copy
	 */
	public Fox(Piece piece) {
		this(((Fox) piece).direction, ((Fox) piece).id);
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	public static Fox createFox(String str) {
		if (str == null) 
			return null;
		
		Direction dir;
		switch (str.substring(2,3)) {
			case "L" :
				dir = Direction.LEFT;
				break;
			case "R" :
				dir = Direction.RIGHT;
				break;
			case "U" :
				dir = Direction.UP;
				break;
			default :
				dir = Direction.DOWN;
		}
		return new Fox(dir, str.substring(3,4).equals("1") ? true : false);
	}

	/**
	 * Returns the other half of a Fox.
	 * 
	 * @return The other half of this Fox.
	 */
	public Fox getOtherHalf() {
		return this.otherHalf;
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

	/**
	 * @return The ID of this Fox
	 */
	public boolean getID() {
		return id;
	}

	/**
	 * Attempt to move this Fox. Foxes can only slide in the direction that they are
	 * facing. They cannot slide through or onto other obstacles. The user will only
	 * select one piece of the Fox, so extra care must be taken to ensure both parts
	 * of the Fox are moved properly.
	 * 
	 * @param move  The move that is being attempted.
	 * @param board The board on which this move will take place.
	 * @return True if the move is successful, false otherwise.
	 */
	@Override
	public boolean move(Move move, Board board) {
		// Initial check to ensure the move direction matches the orientation of the
		// fox.
		// The use of ordinals saved two conditional checks. It's understood that if the
		// enum were to change order
		// this would fail. However, since that scenario has no reason to occur, this
		// implementation was kept.
		if ((direction.ordinal() < 2 && move.direction() != MoveDirection.HORIZONTAL)
				|| (direction.ordinal() > 1 && move.direction() != MoveDirection.VERTICAL)) {
			return false;
		}

		int xStart = move.xStart;
		int yStart = move.yStart;
		int xEnd = move.xEnd;
		int yEnd = move.yEnd;
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();
		boolean location = getRelativeLocation();

		// Only need to check one, since it will be either true for both or false for
		// both
		if (validatePath(move, board, location)) {
			// Moving left and the other piece is to the right or moving right and the other
			// piece is to the left
			// or moving up and the other piece is below or moving down and the other piece
			// is above.
			if ((location && xDistance < 0) || (!location && xDistance > 0) || (location && yDistance > 0)
					|| (!location && yDistance < 0)) {
				board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
				if (xDistance < 0) {
					board.setPiece(board.removePiece(xStart + 1, yStart), xEnd + 1, yEnd);
				} else if (xDistance > 0) {
					board.setPiece(board.removePiece(xStart - 1, yStart), xEnd - 1, yEnd);
				} else if (yDistance < 0) {
					board.setPiece(board.removePiece(xStart, yStart + 1), xEnd, yEnd + 1);
				} else {
					board.setPiece(board.removePiece(xStart, yStart - 1), xEnd, yEnd - 1);
				}
				// Moving left and the other piece is to the left or moving right and the other
				// piece is to the right
				// or moving up and the other piece is above or moving down and the other piece
				// is below.
			} else {
				if (xDistance < 0) {
					board.setPiece(board.removePiece(xStart - 1, yStart), xEnd - 1, yEnd);
				} else if (xDistance > 0) {
					board.setPiece(board.removePiece(xStart + 1, yStart), xEnd + 1, yEnd);
				} else if (yDistance < 0) {
					board.setPiece(board.removePiece(xStart, yStart - 1), xEnd, yEnd - 1);
				} else {
					board.setPiece(board.removePiece(xStart, yStart + 1), xEnd, yEnd + 1);
				}
				board.setPiece(board.removePiece(xStart, yStart), xEnd, yEnd);
			}
			return true;
		}
		return false; // It was an invalid move after all.
	}

	/**
	 * Validate the path of a move object for a Fox.
	 * 
	 * @param move     The move we are trying to validate.
	 * @param board    The board in which the move is taking place.
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
		MoveDirection moveDirection = move.direction();

		if (board.isOccupied(xEnd, yEnd) && moveDirection == MoveDirection.INVALID) {
			return false;
		}
		if (moveDirection == MoveDirection.HORIZONTAL) {
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
			// Trying to move vertically.
		} else {
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
				for (int i = yStart - 2; i >= yEnd - 1; i--) { // Need to make sure there are no obstacles in the
																// path
					if (board.isOccupied(xStart, i)) {
						return false;
					}
				}
			} else if (!location && yDistance > 0) { // The other part of the fox is down and we are moving down
				if (yEnd + 1 > 4) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = yStart + 2; i <= yEnd + 1; i++) { // Need to make sure there are no obstacles in the
																// path
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
	}

	@Override
	public List<Move> getPossibleMoves(Board board, int x, int y) {
		List<Move> moves = new ArrayList<>();

		boolean location = getRelativeLocation();

		// Horizontal sliding
		if ((foxType == FoxType.TAIL && direction == Direction.LEFT)
				|| (foxType == FoxType.HEAD && direction == Direction.RIGHT)) {
			for (int i = x + 1; i < Board.SIZE; i++) {
				Move moveX = new Move(x, y, i, y);
				if (validatePath(moveX, board, location)) {
					moves.add(moveX);
				}
			}
		} else if ((foxType == FoxType.TAIL && direction == Direction.RIGHT)
				|| (foxType == FoxType.HEAD && direction == Direction.LEFT)) {
			for (int i = x - 1; i >= 0; i--) {
				Move moveX = new Move(x, y, i, y);
				if (validatePath(moveX, board, location)) {
					moves.add(moveX);
				}
			}
			// vertical
		} else if ((foxType == FoxType.TAIL && direction == Direction.UP)
				|| (foxType == FoxType.HEAD && direction == Direction.DOWN)) {
			for (int i = y + 1; i < Board.SIZE; i++) {
				Move moveY = new Move(x, y, x, i);
				if (validatePath(moveY, board, location)) {
					moves.add(moveY);
				}
			}
		} else if ((foxType == FoxType.TAIL && direction == Direction.DOWN)
				|| (foxType == FoxType.HEAD && direction == Direction.UP)) {
			for (int i = y - 1; i >= 0; i--) {
				Move moveY = new Move(x, y, x, i);
				if (validatePath(moveY, board, location)) {
					moves.add(moveY);
				}
			}
		}
		return moves;
	}

	/**
	 * Determine the location of the other half of the Fox relative to this half.
	 * 
	 * @return the location of the other half of the Fox relative to this half.
	 */
	private boolean getRelativeLocation() {
		return !((foxType == FoxType.TAIL && (direction == Direction.LEFT || direction == Direction.DOWN))
				|| (foxType == FoxType.HEAD && (direction == Direction.RIGHT || direction == Direction.UP)));
	}

	/**
	 * Returns a String representation of this Fox, along with all 
	 * associated information contained within.
	 * 
	 * @return A short four character representation encoding the information of this Fox.
	 */
	@Override
	public String toString() {
		if (foxType == FoxType.HEAD) {
			return "FH" + this.getDirection().toString().substring(0,1) + (this.getID() ? 1 : 0);
		}
		return "FT" + this.getDirection().toString().substring(0,1) + (this.getID() ? 1 : 0);
	}
}
