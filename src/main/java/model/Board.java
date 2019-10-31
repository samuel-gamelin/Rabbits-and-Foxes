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
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();

		// Do a preliminary check on the move (i.e. making sure it is in bounds, and
		// that the starting tile actually has a piece)
		if (xStart < 0 || xStart >= SIZE || xEnd < 0 || xEnd >= SIZE || yStart < 0 || yStart >= SIZE || yEnd < 0
				|| yEnd >= SIZE || !tiles[xStart][yStart].isOccupied()) {
			return false;
		}

		// Extract the piece to move
		Piece piece = tiles[xStart][yStart].retrievePiece();

		// Check to see if the piece is a fox, since they require more logic to move :')
		if (piece instanceof Fox) {
			boolean location = true; // True for immediate right or up, false otherwise
			// Find its head/tail
			if ((((Fox) piece).getDirection().equals(Fox.Direction.HORIZONTAL))) { // It's to the left or right
				if (xStart - 1 < 0) { // Must be to the right
					location = true;
				} else if (xStart + 1 > 4) { // Must be to the left
					location = false;
				} else { // Could be either, we need to check both
					if ((tiles[xStart - 1][yStart].retrievePiece() != null)
							&& (tiles[xStart - 1][yStart].retrievePiece() instanceof Fox)
							&& ((((Fox) tiles[xStart - 1][yStart].retrievePiece()).getId()) == ((Fox) piece).getId())) { // Check
																															// to
																															// the
																															// left
						location = false;
					} else if ((tiles[xStart + 1][yStart].retrievePiece() != null)
							&& (tiles[xStart + 1][yStart].retrievePiece() instanceof Fox)
							&& ((((Fox) tiles[xStart + 1][yStart].retrievePiece()).getId()) == ((Fox) piece).getId())) { // Must
																															// be
																															// to
																															// the
																															// right
						location = true;
					}
				}
				// We have both pieces of the fox, now we try to move them.
				if (piece.canMove(move) && validateFoxPath(move, piece, location)) { // Only need to check one, since it
																						// will be either true for both
																						// or false for both
					if (location && xDistance < 0) { // Check to see if the other part of fox is to the right and we are
														// moving left
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd + 1][yEnd].placePiece(tiles[xStart + 1][yStart].removePiece());
						return true;
					} else if (location && xDistance > 0) { // Check to see if the other part of fox is to the right and
															// we are moving right
						tiles[xEnd + 1][yEnd].placePiece(tiles[xStart + 1][yStart].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						return true;
					} else if (!location && xDistance > 0) { // Check to see if the other part of fox is to the left and
																// we are moving right
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd - 1][yEnd].placePiece(tiles[xStart - 1][yStart].removePiece());
						return true;
					} else { // We know the other part of the fox is to the left and we are moving left
						tiles[xEnd - 1][yEnd].placePiece(tiles[xStart - 1][yStart].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
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
					if ((tiles[xStart][yStart - 1].retrievePiece() != null)
							&& (tiles[xStart][yStart - 1].retrievePiece() instanceof Fox)
							&& ((((Fox) tiles[xStart][yStart - 1].retrievePiece()).getId()) == ((Fox) piece).getId())) { // Check
																															// above
						location = true;
					} else if ((tiles[xStart][yStart + 1].retrievePiece() != null)
							&& (tiles[xStart][yStart + 1].retrievePiece() instanceof Fox)
							&& ((((Fox) tiles[xStart][yStart + 1].retrievePiece()).getId()) == ((Fox) piece).getId())) { // Must
																															// be
																															// below
						location = false;
					}
				}
				// We have both pieces of the fox, now we try to move them.
				if (piece.canMove(move) && validateFoxPath(move, piece, location)) { // Only need to check one, since it
																						// will be either true for both
																						// or false for both.
					if (location && yDistance > 0) { // Check to see if the other part of the fox is up and we are
														// moving down
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd][yEnd - 1].placePiece(tiles[xStart][yStart - 1].removePiece());
						return true;
					} else if (location && yDistance < 0) { // Check to see if the other part of the fox is up and we
															// are moving up
						tiles[xEnd][yEnd - 1].placePiece(tiles[xStart][yStart - 1].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						return true;
					} else if (!location && yDistance > 0) { // Check to see if the other part of the fox is down and we
																// are moving down
						tiles[xEnd][yEnd + 1].placePiece(tiles[xStart][yStart + 1].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						return true;
					} else { // We know the other part of the fox is down and we are moving up
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd][yEnd + 1].placePiece(tiles[xStart][yStart + 1].removePiece());
						return true;
					}
				}
				return false; // It was an invalid move after all.
			}
		} else if (piece instanceof Rabbit) {
			// If the rabbit can move in the specified fashion and the path determined by
			// the board is acceptable for the rabbit, move the rabbit accordingly
			if (piece.canMove(move) && validateRabbitPath(move)) {
				tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
				return true;
			}
			return false; // It was an invalid move after all.
		}
		return false; // Mushrooms can't move
	}

	/**
	 * @return True if the board is in a winning state. False otherwise.
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
	 * Validate the path of a move object for a Fox.
	 * 
	 * @param move     The move we are trying to validate.
	 * @param fox      The fox piece we are trying to validate a move for.
	 * @param location True if the other piece of the fox is to the right or up,
	 *                 false otherwise.
	 * @return True if the path for this move is valid for foxes, false otherwise.
	 */
	private boolean validateFoxPath(Move move, Piece fox, boolean location) {
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
		int xDistance = move.xDistance();
		int yDistance = move.yDistance();
		int direction = move.direction();

		// Static or diagonal path
		if (direction == -1) {
			return false;
		}

		if ((((Fox) fox).getDirection().equals(Fox.Direction.HORIZONTAL)) && direction == 0) { // Check to see if the
																								// fox is horizontal and
																								// the move is
																								// horizontal
			if (location && xDistance > 0) { // The other part of the fox is to the right and we are moving right
				if (xEnd + 1 > 4) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = xStart + 2; i <= xEnd + 1; i++) { // Need to make sure there are no obstacles in the path
					if (tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			} else if (location && xDistance < 0) { // The other part of the fox is to the right and we are moving left
				for (int i = xStart - 1; i >= xEnd; i--) { // Need to make sure there are no obstacles in the path
					if (tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			} else if (!location && xDistance > 0) { // The other part of the fox is to the left and we are moving right
				for (int i = xStart + 1; i <= xEnd; i++) { // Need to make sure there are no obstacles in the path
					if (tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			} else { // We know that the other part of the fox is to the left and we are moving left
				if (xEnd - 1 < 0) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = xStart - 2; i >= xEnd - 1; i--) {
					if (tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			}
			return true; // The move is valid for the fox
		}

		if ((((Fox) fox).getDirection().equals(Fox.Direction.VERTICAL)) && direction == 1) { // Check to see if the fox
																								// is vertical and the
																								// move is vertical
			if (location && yDistance > 0) { // The other part of the fox is up and we are moving down
				for (int i = yStart + 1; i <= yEnd; i++) { // Need to make sure there are no obstacles in the path
					if (tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			} else if (location && yDistance < 0) { // The other part of the fox is up and we are moving up
				if (yEnd - 1 < 0) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = yStart - 2; i >= yEnd - 1; i--) { // Need to make sure there are no obstacles in the path
					if (tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			} else if (!location && yDistance > 0) { // The other part of the fox is down and we are moving down
				if (yEnd + 1 > 4) { // Check to see if the move will push the fox out of bounds
					return false;
				}
				for (int i = yStart + 2; i <= yEnd + 1; i++) { // Need to make sure there are no obstacles in the path
					if (tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			} else { // We know the other part of the fox is down and we are moving up
				for (int i = yStart - 1; i >= yEnd; i--) { // Need to make sure there are no obstacles in the path
					if (tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			}
			return true; // The move is valid for the fox
		}
		return false; // Direction and fox orientation did not match (invalid move)
	}

	/**
	 * Validate the path of a rabbit given a move object.
	 * 
	 * @param move The object representing the move
	 * @return True if the path for this move is valid for rabbits, false otherwise.
	 */
	private boolean validateRabbitPath(Move move) {
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
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
				for (int i = xStart - 1; i > xEnd; i--) {
					if (!tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			} else { // Moving right
				for (int i = xStart + 1; i < xEnd; i++) {
					if (!tiles[i][yStart].isOccupied()) {
						return false;
					}
				}
			}
		} else if (direction == 1) { // Vertical move
			if (yDistance < 0) { // Moving up
				for (int i = yStart - 1; i > yEnd; i--) {
					if (!tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			} else { // Moving down
				for (int i = yStart + 1; i < yEnd; i++) {
					if (!tiles[xStart][i].isOccupied()) {
						return false;
					}
				}
			}
		}
		if (tiles[xEnd][yEnd].isOccupied()) {
			return false;
		}
		return true;
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
	 * Gets the piece at the specified location.
	 * 
	 * @param x The x position as a 0-based index
	 * @param y The y position as a 0-based index
	 * @return The piece at the specified position, null if there is no piece
	 */
	public Piece getPiece(int x, int y) {
		return (x < 0 || y < 0 || x >= SIZE || y >= SIZE) ? null : this.tiles[x][y].retrievePiece();
	}
}
