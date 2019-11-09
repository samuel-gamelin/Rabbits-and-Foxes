package model;

import java.util.ArrayList;
import java.util.List;

import util.Move;
import util.Move.MoveDirection;

/**
 * A class representing a Rabbit piece.
 * 
 * @author Abdalla El Nakla
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * 
 * @version 3.0
 */
public class Rabbit extends Piece {
	/**
	 * The colour of the rabbit
	 */
	private RabbitColour colour;

	/**
	 * An enumeration of the colour for the rabbit
	 */
	public enum RabbitColour {
		BROWN, WHITE, GRAY
	}

	/**
	 * Constructs a new Rabbit.
	 * 
	 * @param colour the colour of the rabbit
	 * 
	 */
	public Rabbit(RabbitColour colour) {
		super(PieceType.RABBIT);
		this.colour = colour;
	}

	/**
	 * A copy constructor for Rabbit.
	 * 
	 * @param piece The piece to copy
	 */
	public Rabbit(Piece piece) {
		this(((Rabbit) piece).colour);
	}

	/**
	 * Retrieve the color of the rabbit.
	 * 
	 * @return the colour of the Rabbit brown or white
	 */
	public RabbitColour getColour() {
		return colour;
	}

	/**
	 * @param move  The object representing the move
	 * @param board The board on which this move is taking place
	 * @return True if the move is successful, false otherwise.
	 */
	@Override
	public boolean move(Move move, Board board) {
		if (move.direction().equals(MoveDirection.INVALID)) {
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
		// Rabbits must jump over at least one obstacle
		if ((move.direction().equals(MoveDirection.INVALID) || Math.abs(move.xDistance()) == 1
				|| Math.abs(move.yDistance()) == 1)
				|| (move.direction().equals(MoveDirection.HORIZONTAL) && !horizontalMove(move, board))
				|| (move.direction().equals(MoveDirection.VERTICAL) && !verticalMove(move, board))) {
			return false;
		}

		return !board.isOccupied(move.xEnd, move.yEnd);
	}

	/**
	 * This method checks if the vertical path for the rabbit is valid.
	 * 
	 * @param move  The object representing the move
	 * @param board The board on which the move is taking place.
	 * @return True if the path is valid, otherwise false
	 */
	private boolean verticalMove(Move move, Board board) {
		if (move.yDistance() < 0) { // Moving up
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
	 * @param move  The object representing the move
	 * @param board The board on which the move is taking place.
	 * @return True if the path is valid, otherwise false
	 */
	private boolean horizontalMove(Move move, Board board) {
		if (move.xDistance() < 0) { // Moving left
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

	@Override
	public List<Move> getPossibleMoves(Board board, int x, int y) {
		List<Move> moves = new ArrayList<>();

		for (int i = 0; i < Board.SIZE; i++) {
			Move moveX = new Move(x, y, i, y);
			Move moveY = new Move(x, y, x, i);
			if (i != x && !board.isOccupied(moveX.xEnd, moveX.yEnd) && validatePath(moveX, board)) {
				moves.add(moveX);
			}
			if (i != y && !board.isOccupied(moveY.xEnd, moveY.yEnd) && validatePath(moveY, board)) {
				moves.add(moveY);
			}
		}

		return moves;
	}

	@Override
	public String toShortString() {
		return "RB";
	}
}
