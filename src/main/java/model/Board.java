	package model;

/**
 * This class represents a board which keeps track of tiles and pieces within
 * them.
 * 
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @version 1.0
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
	 * Creates a board object and initializes it with the default game
	 * configuration.
	 */
	public Board() {
		tiles = new Tile[SIZE][SIZE];
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
		tiles[3][0].placePiece(new Rabbit());
		tiles[4][2].placePiece(new Rabbit());
		tiles[1][4].placePiece(new Rabbit());

		// Adding the foxes (there can be 0 to 2, here we have 2)
		tiles[1][1].placePiece(new Fox(Fox.FoxType.HEAD, Fox.Direction.HORIZONTAL));
		tiles[0][1].placePiece(new Fox(Fox.FoxType.TAIL, Fox.Direction.HORIZONTAL));
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

		if (xStart < 0 || xStart >= SIZE || xEnd < 0 || xEnd >= SIZE || yStart < 0 || yStart >= SIZE || yEnd < 0
				|| yEnd >= SIZE || !tiles[xStart][yStart].isOccupied() || tiles[xEnd][yEnd].isOccupied()) {
			return false;
		}
		if (tiles[xStart][yStart].retrievePiece().canMove(move)&& validatePath(move,tiles[xStart][yStart].retrievePiece().getPieceType())) {
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
				if (tiles[i][j].getColour().equals(Tile.Colour.BROWN) && (tiles[i][j].retrievePiece() == null
						|| !tiles[i][j].retrievePiece().getPieceType().equals(Piece.PieceType.RABBIT))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Validates the path given a Move object and the piece type.
	 * 
	 * @param move      The object representing the move
	 * @param piecetype The type of the piece
	 * @return True if the path for this move is valid for the specified piece given
	 *         the current state of the board, false otherwise.
	 */
	private boolean validatePath(Move move, Piece.PieceType piecetype) {
		int xStart = move.getXStart();
		int yStart = move.getYStart();
		int xEnd = move.getXEnd();
		int yEnd = move.getYEnd();
		int xDistance = Math.abs(xStart - xEnd);
		int yDistance = Math.abs(yStart - yEnd);		
		int direction = move.direction();
		
		if(piecetype == Piece.PieceType.RABBIT) {
			if(direction == 1){
				for(int i = 1; i < yDistance;i++ ) {
					if(!tiles[xStart][yStart+i].isOccupied()) {
						return false;
					}	
				}
				
			}
			
		}
		if(piecetype == Piece.PieceType.RABBIT) {
			if(direction == 0){
				for(int i = 1; i < yDistance;i++ ) {
					if(!tiles[xStart+i][yStart].isOccupied()) {
						return false;
					}	
				}
				
			}
			
		}
		if(piecetype == Piece.PieceType.FOX) {
			if(direction == 1){
				for(int i = 1; i < yDistance;i++ ) {
					if(tiles[xStart][yStart+i].isOccupied()) {
						return false;
					}	
				}
				
			}
			
		}
		if(piecetype == Piece.PieceType.FOX) {
			if(direction == 0){
				for(int i = 1; i < yDistance;i++ ) {
					if(tiles[xStart+i][yStart].isOccupied()) {
						return false;
					}	
				}
				
			}
			
		}
		return true;
		
	}

	/**
	 * @return A string representation of this board.
	 */
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
