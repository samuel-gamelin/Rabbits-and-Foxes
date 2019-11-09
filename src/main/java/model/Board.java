package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import model.Piece.PieceType;
import model.Rabbit.RabbitColour;
import util.Move;

/**
 * This class represents a board which keeps track of tiles and pieces within
 * them. It also serves as the Model for the Rabbits and Foxes game.
 * 
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @author Dani Hashweh
 * @author John Breton
 * 
 * @version 3.0
 */
public class Board {
	/**
	 * The size of any side for the board.
	 */
	public static final int SIZE = 5;

	/**
	 * A 2D array of tiles used to manage all tiles on the board.
	 */
	private Tile[][] tiles;

	/**
	 * A list of listeners that are updated on the status of this board whenever
	 * appropriate.
	 */
	private List<BoardListener> boardListeners;

	/**
	 * Creates a board object and initializes it with the default game
	 * configuration.
	 */
	public Board() {
		tiles = new Tile[SIZE][SIZE];
		boardListeners = new ArrayList<>();
		// initializeEasy();
		// initializeTestBoard();
		// initializeUnsolvable();
		// initializeVeryHardBoard();
		initializeDefaultBoard();
	}

	/**
	 * A copy constructor for Board. Does not retain the list of listeners from the
	 * old board. That is, it its listener list is empty.
	 * 
	 * @param board The board to copy
	 */
	public Board(Board board) {
		this.tiles = new Tile[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				this.tiles[i][j] = new Tile(board.tiles[i][j]);
			}
		}
		this.boardListeners = new ArrayList<>();
	}

	private void initializeEasy() {
		initializeBaseBoard();

		tiles[1][0].placePiece(new Mushroom());
		tiles[1][4].placePiece(new Mushroom());
		tiles[2][0].placePiece(new Rabbit(RabbitColour.BROWN));
		tiles[2][4].placePiece(new Rabbit(RabbitColour.GRAY));
	}

	/**
	 * Configures the board for testing.
	 */
	private void initializeTestBoard() {
		initializeBaseBoard();

		tiles[0][1].placePiece(new Mushroom());
		tiles[2][0].placePiece(new Rabbit(RabbitColour.BROWN));
		tiles[2][4].placePiece(new Rabbit(RabbitColour.GRAY));

		Fox fox1 = new Fox(Fox.Direction.UP, true);
		tiles[1][3].placePiece(fox1);
		tiles[1][4].placePiece(fox1.getOtherHalf());
	}

	private void initializeVeryHardBoard() {
		initializeBaseBoard();
		Fox fox1 = new Fox(Fox.Direction.RIGHT, true);
		tiles[1][1].placePiece(fox1);
		tiles[0][1].placePiece(fox1.getOtherHalf());

		tiles[3][1].placePiece(new Rabbit(RabbitColour.WHITE));
		tiles[4][2].placePiece(new Rabbit(RabbitColour.BROWN));
		tiles[3][4].placePiece(new Rabbit(RabbitColour.GRAY));

		tiles[0][3].placePiece(new Mushroom());
		tiles[2][2].placePiece(new Mushroom());
		tiles[3][0].placePiece(new Mushroom());

	}

	private void initializeUnsolvable() {
		initializeBaseBoard();
		tiles[2][0].placePiece(new Rabbit(RabbitColour.BROWN));
	}

	/**
	 * Initializes the board with a default configuration.
	 */
	private void initializeDefaultBoard() {
		initializeBaseBoard();

		// Adding the mushrooms (there can be 0 to 3, here we have 2)
		tiles[3][1].placePiece(new Mushroom());
		tiles[2][4].placePiece(new Mushroom());

		// Adding the rabbits (there can be 1 to 3, here we have 3)
		tiles[1][4].placePiece(new Rabbit(RabbitColour.BROWN));
		tiles[3][0].placePiece(new Rabbit(RabbitColour.WHITE));
		tiles[4][2].placePiece(new Rabbit(RabbitColour.GRAY));

		// Adding the foxes (there can be 0 to 2, here we have 2)
		Fox fox1 = new Fox(Fox.Direction.LEFT, true);
		Fox fox2 = new Fox(Fox.Direction.UP, false);
		tiles[3][3].placePiece(fox1);
		tiles[4][3].placePiece(fox1.getOtherHalf());
		tiles[1][0].placePiece(fox2);
		tiles[1][1].placePiece(fox2.getOtherHalf());
	}

	private void initializeBaseBoard() {
		// Corner brown tiles
		tiles[0][0] = new Tile(Tile.Colour.BROWN);
		tiles[4][0] = new Tile(Tile.Colour.BROWN);
		tiles[0][4] = new Tile(Tile.Colour.BROWN);
		tiles[4][4] = new Tile(Tile.Colour.BROWN);

		// Center brown tile
		tiles[2][2] = new Tile(Tile.Colour.BROWN);

		// Regular green tiles
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (tiles[i][j] == null) {
					tiles[i][j] = new Tile(Tile.Colour.GREEN);
				}
			}
		}
	}

	/**
	 * Makes a move given the provided move object.
	 * 
	 * @param move The object representing the move
	 * @return true if the move was successful, false if parameters are invalid or
	 *         the move was unsuccessful
	 */
	public boolean move(Move move) {
		// Do a preliminary check on the move (i.e. making sure it is in bounds, and
		// that the starting tile actually has a piece)
		if (!validateBounds(move) || !tiles[move.xStart][move.yStart].isOccupied()) {
			return false;
		}

		if (tiles[move.xStart][move.yStart].retrievePiece().move(move, this)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	/**
	 * @return True if the board is in a winning state, false otherwise
	 */
	public boolean isInWinningState() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Piece piece = tiles[i][j].retrievePiece();
				if (piece != null && piece.getPieceType().equals(Piece.PieceType.RABBIT)
						&& !tiles[i][j].getColour().equals(Tile.Colour.BROWN)) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Determines if the specified position is occupied on the board.
	 * 
	 * @param x The x-coordinate of the position
	 * @param y The y-coordinate of the position
	 * @return True if the position is occupied, false otherwise
	 */
	public boolean isOccupied(int x, int y) {
		return validatePosition(x, y) && tiles[x][y].isOccupied();
	}

	/**
	 * Gets the piece at the specified location.
	 * 
	 * @param x The x position as a 0-based index
	 * @param y The y position as a 0-based index
	 * @return The piece at the specified position, null if there is no piece or the
	 *         position is invalid
	 */
	public Piece getPiece(int x, int y) {
		return validatePosition(x, y) ? this.tiles[x][y].retrievePiece() : null;
	}

	/**
	 * Sets the specified piece at the specified position.
	 * 
	 * @param piece The piece to set at the specified position
	 * @param x     The x-coordinate of the position
	 * @param y     The y-coordinate of the position
	 * @return True if the piece was successfully set, false otherwise
	 */
	public boolean setPiece(Piece piece, int x, int y) {
		if (validatePosition(x, y) && piece != null) {
			tiles[x][y].placePiece(piece);
			return true;
		}
		return false;
	}

	/**
	 * Removes the piece at the specified position.
	 * 
	 * @param x The x-coordinate of the position
	 * @param y The y-coordinate of the position
	 * @return True if the piece was successfully removed, false otherwise (i.e.
	 *         invalid position or there was no piece to remove)
	 */
	public Piece removePiece(int x, int y) {
		if (validatePosition(x, y) && tiles[x][y].isOccupied()) {
			return tiles[x][y].removePiece();
		}
		return null;
	}

	/**
	 * Adds a listener to this board.
	 * 
	 * @param boardListener The listener to add
	 * @return True if the listener was successfully added, false otherwise
	 */
	public boolean addListener(BoardListener boardListener) {
		return boardListeners.add(boardListener);
	}

	/**
	 * Notifies all listeners that the board has changed.
	 */
	private void notifyListeners() {
		boardListeners.stream().forEach(BoardListener::handleBoardChange);
	}

	/**
	 * Validates that a given position is within the board.
	 * 
	 * @param x The x-coordinate of the position
	 * @param y The y-coordinate of the position
	 * @return True if the specified position is within the board, false otherwise
	 */
	private boolean validatePosition(int x, int y) {
		return x >= 0 && y >= 0 && x < SIZE && y < SIZE;
	}

	/**
	 * Validates the bounds on the given move object.
	 * 
	 * @param move The move object's whose bounds are to be validated
	 * @return True if the move is within the board's bounds, false otherwise
	 */
	private boolean validateBounds(Move move) {
		return move.xStart >= 0 && move.xStart < SIZE && move.xEnd >= 0 && move.xEnd < SIZE && move.yStart >= 0
				&& move.yStart < SIZE && move.yEnd >= 0 && move.yEnd < SIZE;
	}

	/**
	 * @return A list containing all possible move objects for this board
	 */
	public Set<Move> getPossibleMoves() {
		Set<Move> moves = new LinkedHashSet<>();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				Piece piece = tiles[i][j].retrievePiece();
				if (piece != null && !piece.getPieceType().equals(PieceType.MUSHROOM)) {
					moves.addAll(piece.getPossibleMoves(this, i, j));
				}
			}
		}

		return moves;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(tiles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Board))
			return false;
		Board other = (Board) obj;
		if (!Arrays.deepEquals(tiles, other.tiles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();

		// Adding the top row of numbers
		representation.append("     ");
		for (int i = 0; i < SIZE; i++) {
			representation.append(i + 1);
			representation.append("        ");
		}

		for (int y = 0; y < SIZE; y++) {
			// First row
			representation.append("\n  ");
			for (int x = 0; x < SIZE; x++) {
				representation.append("|");
				if (tiles[x][y].getColour().equals(Tile.Colour.BROWN)) {
					representation.append("--BB--");
				} else {
					representation.append("------");
				}
				representation.append("| ");
			}

			// Second row
			representation.append("\n  ");
			for (int x = 0; x < SIZE; x++) {
				representation.append("|");
				representation.append("      ");
				representation.append("| ");
			}

			// Third row
			representation.append("\n" + (y + 1) + " ");
			for (int x = 0; x < SIZE; x++) {
				representation.append("|");
				representation.append("  ");
				representation.append(tiles[x][y].toString());
				representation.append("  ");
				representation.append("| ");
			}

			// Fourth row
			representation.append("\n  ");
			for (int x = 0; x < SIZE; x++) {
				representation.append("|");
				representation.append("      ");
				representation.append("| ");
			}

			// Fifth row
			representation.append("\n  ");
			for (int x = 0; x < SIZE; x++) {
				representation.append("|");
				if (tiles[x][y].getColour().equals(Tile.Colour.BROWN)) {
					representation.append("__BB__");
				} else {
					representation.append("______");
				}
				representation.append("| ");
			}
		}

		return representation.toString();
	}
}
