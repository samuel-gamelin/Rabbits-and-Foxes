package model;

/**
 * This class represents a board which keeps track of tiles and pieces within
 * them.
 * 
 * @author Samuel Gamelin
 * @author Abdalla El Nakla
 * @author Dani Hashweh
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
		tiles[0][1].placePiece(new Fox(Fox.FoxType.HEAD, Fox.Direction.HORIZONTAL));
		tiles[1][1].placePiece(new Fox(Fox.FoxType.TAIL, Fox.Direction.HORIZONTAL));
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
		//int direction = move.direction();
		
		// Do a preliminary check on the move (i.e. making sure it is in bounds, and that the starting tile actually has a piece)
		if (xStart < 0 || xStart >= SIZE || xEnd < 0 || xEnd >= SIZE || yStart < 0 || yStart >= SIZE || yEnd < 0
				|| yEnd >= SIZE || !tiles[xStart][yStart].isOccupied()) {
			return false;
		}
		
		// Extract the piece to move
		Piece piece = tiles[xStart][yStart].retrievePiece();
		
		// If the piece can move in the specified fashion and the path determined by the board is acceptable for that specific piece, move the piece(s) accordingly
		if (piece.canMove(move) && validatePath(move, piece)) {
			//System.out.println(xEnd + 1);
			//System.out.println(tiles[xEnd + 1][yEnd].isOccupied());
			if (piece.getPieceType().equals(Piece.PieceType.FOX)) {								// Moving a Fox
				Fox UP = null, DOWN = null, LEFT = null, RIGHT = null;
				try {
					UP = (Fox) tiles[xStart][yStart - 1].retrievePiece();
				} catch (Exception e) {}
				try {
					DOWN = (Fox) tiles[xStart][yStart + 1].retrievePiece();
				} catch (Exception e) {}
				try {
					LEFT = (Fox) tiles[xStart - 1][yStart].retrievePiece();
				} catch (Exception e) {}
				try {
					RIGHT = (Fox) tiles[xStart + 1][yStart].retrievePiece();
				} catch (Exception e) {}
				
				if (((Fox) piece).getDirection().equals(Fox.Direction.HORIZONTAL)) {			// Moving horizontally
					if ((xStart + xDistance) < 0 || (xStart + xDistance) >= SIZE) {				// Ensure an adjacent tail is not pushed off the board
						return false;
					}
					//((Fox) tiles[xStart+1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)
					if (RIGHT != null && RIGHT.getDirection().equals(Fox.Direction.HORIZONTAL) && (xEnd + 1 < SIZE) && !tiles[xEnd][yEnd].isOccupied() || ((Fox) piece).getDirection().equals(Fox.Direction.HORIZONTAL)) {		// Other corresponding fox piece is on the right
						//move left and there is a right tail/head
						if((xStart > xEnd) && ((Fox) tiles[xStart-1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)){
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							tiles[xEnd+1][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
							return true; 
						}
						else if((xStart > xEnd) && ((Fox) tiles[xStart-1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)) {
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							tiles[xEnd+1][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
							return true; 
						}
						//move right and fox tail/head on the right (therefore move right object first)
						else if((xStart < xEnd) && ((Fox) tiles[xStart+1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)) {
							tiles[xEnd][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
							tiles[xEnd-1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
							return true; 
						}
						else if((xStart < xEnd) && ((Fox) tiles[xStart-1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)) {
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							tiles[xEnd-1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
							return true;
						}
						
						else if(((Fox) tiles[xStart+1][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)) {
							tiles[xEnd + 1][yEnd].placePiece(tiles[xStart + 1][yStart].removePiece());
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							return true; 
						}
					} else if (LEFT != null && LEFT.getDirection().equals(Fox.Direction.HORIZONTAL) && (xEnd - 1 > 0)  && !tiles[xEnd][yEnd].isOccupied() || ((Fox) piece).getDirection().equals(Fox.Direction.HORIZONTAL)) {	// Other corresponding fox piece is on the left
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd - 1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
						return true;
					}
				} else if (((Fox) piece).getDirection().equals(Fox.Direction.VERTICAL)) {		// Moving vertically
					if ((yStart + yDistance) <= 0 || (yStart + yDistance) >= SIZE) {			// Ensure an adjacent tail is not pushed off the board
						return false;
					}
					if (DOWN != null && DOWN.getDirection().equals(Fox.Direction.VERTICAL) && (yEnd + 1 < SIZE) && !tiles[xEnd][yEnd].isOccupied()) {			// Other corresponding fox piece is below
						tiles[xEnd][yEnd + 1].placePiece(tiles[xStart][yStart + 1].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						return true;
					} else if (UP != null && UP.getDirection().equals(Fox.Direction.VERTICAL) && (yEnd - 1 < 0) && !tiles[xEnd][yEnd].isOccupied()) {	// Other corresponding fox piece is above
						tiles[xEnd][yEnd - 1].placePiece(tiles[xStart][yStart - 1].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						return true;
					}
				}
				/*//IF TAIL IS ON RIGHT OF HEAD
				if((tiles[xStart][yStart]).toString().equals("FT") && (tiles[xStart-1][yStart]).toString().equals("FH")) {
					//If tail is going into head position
					if((tiles[xEnd][yEnd].isOccupied()) &&(tiles[xEnd][yEnd]).toString().equals("FT")) {
						tiles[xEnd-1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
					}
					//moving tail into empty position
					else if((tiles[xStart][yStart]).toString().equals("FT")){
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd-1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
					}
					//moving tail into empty position
					else if((tiles[xStart][yStart]).toString().equals("FT")){
						tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						tiles[xEnd+1][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
					}

					return true; 
				}
				
				//IF HEAD IS ON RIGHT OF TAIL
				else if((tiles[xStart][yStart]).toString().equals("FH") && (tiles[xStart+1][yStart]).toString().equals("FT")) {
					if (piece.canMove(move) && validatePath(move, piece)) {
						//If head is going into tail position
						if((tiles[xEnd][yEnd].isOccupied()) &&(tiles[xEnd][yEnd]).toString().equals("FT")) {
							tiles[xEnd-1][yEnd].placePiece(tiles[xStart-1][yStart].removePiece());
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
						}
						//moving head into empty position
						else if((tiles[xStart][yStart]).toString().equals("FH")){
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							tiles[xEnd+1][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
						}
						//moving tail into empty position
						else if((tiles[xStart][yStart]).toString().equals("FT")){
							tiles[xEnd][yEnd].placePiece(tiles[xStart][yStart].removePiece());
							tiles[xEnd+1][yEnd].placePiece(tiles[xStart+1][yStart].removePiece());
						}

						return true;
					}
				}*/
				return false;
			}
			
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
	 *
	 * @param xStart
	 * @param yStart
	 * @return 0 if FT is on right of FH. 1 if FH is on right of FT. 2 if FT is under FH. 3 if FH is under FT. 
	 */
	private int findAdjacentFoxPosition(int xStart, int yStart) {
		if(((Fox) tiles[xStart][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL) && (((tiles[xStart+1][yStart]).toString().equals("FT")))) 
			return 0; 
		else if(((Fox) tiles[xStart][yStart].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL) && (((tiles[xStart+1][yStart]).toString().equals("FH")))) 
			return 1; 
		else if(((Fox) tiles[xStart][yStart].retrievePiece()).getDirection().equals(Fox.Direction.VERTICAL) && (((tiles[xStart][yStart+1]).toString().equals("FT"))))
			return 2; 
		else if(((Fox) tiles[xStart][yStart].retrievePiece()).getDirection().equals(Fox.Direction.VERTICAL) && (((tiles[xStart][yStart+1]).toString().equals("FH"))))
			return 4; 
		return -1; 
	}

	/**
	 * Validates the path given a Move object and the piece type.
	 * 
	 * @param move      The object representing the move
	 * @param piece The piece whose move path is to be validate
	 * @return True if the path for this move is valid for the specified piece given
	 *         the current state of the board, false otherwise.
	 */
	private boolean validatePath(Move move, Piece piece) {
		Piece.PieceType piecetype = piece.getPieceType();
		
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
		
		// Rabbit
		if (piecetype.equals(Piece.PieceType.RABBIT)) {
			if (Math.abs(xDistance) == 1 || Math.abs(yDistance) == 1) {				// Rabbits must jump over at least one obstacle
				return false;
			} else if (direction == 0) {						// Horizontal move
				if (xDistance < 0) {							// Moving left
					for(int i = xStart - 1; i > xEnd; i--) {
						if(!tiles[i][yStart].isOccupied()) {
							return false;
						}
					}
				} else {										// Moving right
					for(int i = xStart + 1; i < xEnd; i++) {
						if(!tiles[i][yStart].isOccupied()) {
							return false;
						}
					}
				}
			} else if (direction == 1) {						// Vertical move
				if (yDistance < 0) {							// Moving up
					for(int i = yStart - 1; i > yEnd; i--) {
						if(!tiles[xStart][i].isOccupied()) {
							return false;
						}
					}
				} else {										// Moving down
					for(int i = yStart + 1; i < yEnd; i++) {
						if(!tiles[xStart][i].isOccupied()) {
							return false;
						}
					}
				}
			}
			if (tiles[xEnd][yEnd].isOccupied()) {
				return false;
			}
		} else if (piecetype.equals(Piece.PieceType.FOX)) {								// Fox
			if (direction == 0) {														// Horizontal move
				if (!(((Fox) piece).getDirection().equals(Fox.Direction.HORIZONTAL))) {
					return false;
				}
				if (xDistance < 0) {													// Moving left
					for(int i = xStart - 1; i >= xEnd; i--) {
						if(tiles[i][yStart].isOccupied() && (!(tiles[i][yStart].retrievePiece() instanceof Fox) || !((Fox) (tiles[i][yStart].retrievePiece())).getDirection().equals(Fox.Direction.HORIZONTAL))) {
							return false;
						}
					}
				} else {																// Moving right
					for(int i = xStart + 1; i <= xEnd; i++) {
						if(tiles[i][yStart].isOccupied() && (!(tiles[i][yStart].retrievePiece() instanceof Fox) || !((Fox) (tiles[i][yStart].retrievePiece())).getDirection().equals(Fox.Direction.HORIZONTAL))) {
							return false;
						}
					}
				}
				//if (!(tiles[xEnd][yEnd].retrievePiece() != null && tiles[xEnd][yEnd].retrievePiece().getPieceType().equals(Piece.PieceType.FOX) && (((Fox) tiles[xEnd][yEnd].retrievePiece()).getDirection().equals(Fox.Direction.HORIZONTAL)))) {
				//	return false;
				//}
			} else {
				if (!(((Fox) piece).getDirection().equals(Fox.Direction.VERTICAL))) {	// Vertical move
					return false;
				}
				if (yDistance < 0) {													// Moving up
					for(int i = yStart - 1; i >= yEnd; i--) {
						if (tiles[xStart][i].isOccupied() && (!(tiles[xStart][i].retrievePiece() instanceof Fox) || !((Fox) (tiles[xStart][i].retrievePiece())).getDirection().equals(Fox.Direction.VERTICAL))) {
							return false;
						}
					}
				} else {																// Moving down
					for(int i = yStart - 1; i <= yEnd; i++) {
						if(tiles[xStart][i].isOccupied() && (!(tiles[xStart][i].retrievePiece() instanceof Fox) || !((Fox) (tiles[xStart][i].retrievePiece())).getDirection().equals(Fox.Direction.VERTICAL))) {
							return false;
						}
					}
				}
				//if (!(tiles[xEnd][yEnd].retrievePiece() != null && tiles[xEnd][yEnd].retrievePiece().getPieceType().equals(Piece.PieceType.FOX) && (((Fox) tiles[xEnd][yEnd].retrievePiece()).getDirection().equals(Fox.Direction.VERTICAL)))) {
				//	return false;
				//}
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
