package app;

/**
 * This class represents a board which keeps track of tiles and pieces within them.
 * 
 * @author Samuel Gamelin
 * @version 1.0
 */
public class Board {
	/**
	 * The size of any side for the board
	 */
	public static final int SIZE = 5;
	
	/**
	 * A 2D array of tiles used to manager all tiles on the board
	 */
	private Tile[][] tiles;
	
	/**
	 * Creates a board object, initializing it with the default game configuration.
	 */
	public Board() {
		tiles = new Tile[SIZE][SIZE];
		initializeDefaultBoard();
	}
	
	/**
	 * Initializes the board with a default tile and piece configuration
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
		
		// Adding the mushrooms (there can be 0 to 3)
		tiles[3][1].placePiece(new Mushroom());
		tiles[2][4].placePiece(new Mushroom());
		
		// Adding the rabbits (there can be 1 to 3)
		tiles[3][0].placePiece(new Rabbit());
		tiles[4][2].placePiece(new Rabbit());
		tiles[1][4].placePiece(new Rabbit());
		
		// Adding the foxes (there can be 0 to 2)
		tiles[1][1].placePiece(new Fox());
		tiles[4][3].placePiece(new Fox());
	}
	
	/**
	 * Makes a move.
	 * 
	 * @param xStart The starting x position
	 * @param xEnd The ending x position
	 * @param yStart The starting y position
	 * @param yEnd The ending y position
	 * @return true if the move was successful, false if parameters are invalid or move was unsuccessful
	 */
	public boolean move(int xStart, int xEnd, int yStart, int yEnd) {
		if (xStart < 0 || xStart > SIZE || xEnd < 0 || xEnd > SIZE
				|| yStart < 0 || yStart > SIZE || yEnd < 0 || yEnd
				> SIZE || !tiles[xStart][yStart].isOccupied()
				|| tiles[xEnd][yEnd].isOccupied()) {
			return false;
		}
		if (tiles[xStart][yStart].retrievePiece().canMove(xStart, xEnd, yStart, yEnd)) {
			tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
			return true;
		}
		return false;
	}
	
	/**
	 * @return True if the board is in a winning state. False otherwise.
	 */
	public boolean isInWinningState() {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (!(tiles[i][j] != null && tiles[i][j].getColour().equals(Tile.Colour.BROWN) && tiles[i][j].retrievePiece().getType().equas(Piece.RABBIT))) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns a String representation of the Board.
	 * 
	 * @return A string representation
	 */
	@Override
	public String toString() {
		StringBuilder representation = new StringBuilder();
		
		// Adding the top row of letters
		for (int i = 0; i < SIZE; i++) {
			representation.append(" ");
			representation.append((char) (i + 'A'));
		}
		for (int x = 0; x < SIZE; x++) {
			representation.append(x + 1);
			representation.append(" ");
			for (int y = 0; y < SIZE; y++) {
				representation.append(tiles[x][y].retrievePiece().toShortString());
			}
		}
		return representation.toString();
	}	
}
