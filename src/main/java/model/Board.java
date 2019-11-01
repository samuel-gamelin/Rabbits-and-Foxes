package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a board which keeps track of tiles and pieces within
 * them. It also serves as the Model for the Rabbits and Foxes game.
 * 
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @author Dani Hashweh
 * @author John Breton
 * 
 * @version 2.0
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
		initializeDefaultBoard();
	}

	/**
	 * Initializes the board with a default configuration.
	 */
	private void initializeDefaultBoard() {
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

		// Adding the mushrooms (there can be 0 to 3, here we have 2)
		tiles[3][1].placePiece(new Mushroom());
		tiles[2][4].placePiece(new Mushroom());

		// Adding the rabbits (there can be 1 to 3, here we have 3)
		tiles[1][4].placePiece(new Rabbit());
		tiles[3][0].placePiece(new Rabbit());
		tiles[4][2].placePiece(new Rabbit());

		// Adding the foxes (there can be 0 to 2, here we have 2)
		tiles[3][3].placePiece(new Fox(Fox.FoxType.HEAD, Fox.Direction.HORIZONTAL, false));
		tiles[4][3].placePiece(new Fox(Fox.FoxType.TAIL, Fox.Direction.HORIZONTAL, false));
		tiles[1][0].placePiece(new Fox(Fox.FoxType.HEAD, Fox.Direction.VERTICAL, true));
		tiles[1][1].placePiece(new Fox(Fox.FoxType.TAIL, Fox.Direction.VERTICAL, true));
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
				if (tiles[i][j].retrievePiece() != null
						&& tiles[i][j].retrievePiece().getPieceType().equals(Piece.PieceType.RABBIT)
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
	 * @param x The x-coordinate of the position
	 * @param y The y-coordinate of the position
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
		boardListeners.forEach(l -> l.handleBoardChange());
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
}
