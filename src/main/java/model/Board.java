package model;

import lombok.Getter;
import lombok.Setter;
import util.Move;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * This class represents a board which keeps track of tiles and pieces within
 * them. It also serves as the Model for the Rabbits and Foxes game.
 *
 * @author Samuel Gamelin
 * @author John Breton
 * @author Mohamed Radwan
 * @author Abdalla El Nakla
 * @author Dani Hashweh
 * @version 4.0
 */
public class Board {

    /**
     * The size of any side for the board.
     */
    public static final int SIZE = 5;

    /**
     * A String used to represent an empty tile on the board.
     */
    static final String EMPTY = "X";
    /**
     * A 2D array of tiles used to manage all tiles on the board.
     */
    private final Tile[][] tiles;
    /**
     * A list of listeners that are updated on the status of this board whenever
     * appropriate.
     */
    private final List<BoardListener> boardListeners;
    /**
     * The Board's name.
     */
    @Getter
    @Setter
    private String name;

    /**
     * Construct an empty board.
     *
     * @param name The name of the board
     */
    public Board(String name) {
        this.name = Objects.requireNonNull(name);
        this.tiles = new Tile[SIZE][SIZE];
        this.boardListeners = new ArrayList<>();
        initializeBaseBoard();
    }

    /**
     * A copy constructor for Board. Does not retain the list of listeners from the
     * old board. That is, it empties its listener list.
     *
     * @param board The board to copy
     */
    public Board(Board board) {
        this.name = board.name;
        this.tiles = new Tile[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.tiles[i][j] = new Tile(board.tiles[i][j]);
            }
        }
        this.boardListeners = new ArrayList<>();
    }

    /**
     * Create a board object and initializes the pieces specified by the passed
     * String. This is a factory method.
     *
     * @param name           The name of the Board
     * @param representation The String representation of the Board that is being
     *                       created. Must be of length 25.
     * @return The newly constructed Board based on the passed String.
     */
    public static Board createBoard(String name, String representation) {
        String[] currBoard = representation.split("\\s+");
        if (currBoard.length != 25) {
            return null;
        }
        Board board = new Board(name);
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!currBoard[5 * i + j].equals(EMPTY)) {
                    if (currBoard[5 * i + j].length() == 2) {
                        board.tiles[i][j].placePiece(new Mushroom());
                    } else if (currBoard[5 * i + j].length() == 3) {
                        board.tiles[i][j].placePiece(Rabbit.createRabbit(currBoard[5 * i + j]));
                    } else if (currBoard[5 * i +
                            j].substring(1, 2).equals(Fox.FoxType.HEAD.toString().substring(0, 1))) {
                        Fox f = Fox.createFox(currBoard[5 * i + j]);
                        board.tiles[i][j].placePiece(f);
                        switch (f.getDirection()) {
                            case DOWN -> board.tiles[i][j - 1].placePiece(f.getOtherHalf());
                            case LEFT -> board.tiles[i + 1][j].placePiece(f.getOtherHalf());
                            case RIGHT -> board.tiles[i - 1][j].placePiece(f.getOtherHalf());
                            default -> board.tiles[i][j + 1].placePiece(f.getOtherHalf());
                        }
                    }
                }
            }
        }
        return board;
    }

    /**
     * Initializes the base configuration for any board (green and brown tiles).
     */
    private void initializeBaseBoard() {
        // Corner brown tiles
        tiles[0][0] = new Tile(Tile.TileColour.BROWN);
        tiles[4][0] = new Tile(Tile.TileColour.BROWN);
        tiles[0][4] = new Tile(Tile.TileColour.BROWN);
        tiles[4][4] = new Tile(Tile.TileColour.BROWN);

        // Center brown tile
        tiles[2][2] = new Tile(Tile.TileColour.BROWN);

        // Regular green tiles
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] == null) {
                    tiles[i][j] = new Tile(Tile.TileColour.GREEN);
                }
            }
        }
    }

    /**
     * Makes a move given the provided move object.
     *
     * @param move The object representing the move
     * @return true if the move was successful, false if parameters are invalid or
     * the move was unsuccessful
     */
    public boolean move(Move move) {
        if (move == null) return false;
        Piece piece = tiles[move.xStart][move.yStart].getPiece();
        if (piece instanceof MovablePiece && ((MovablePiece) piece).move(move, this)) {
            notifyListeners();
            return true;
        }
        return false;
    }

    /**
     * Given a position on the board return boolean based on colour. This is used to
     * Determine the the type of tile on a board.
     *
     * @param x The x-coordinate of the position
     * @param y The y-coordinate of the position
     * @return False is returned if the colour of the tile is brown. True is
     * returned if the colour of the tile is green.
     */
    public boolean tileType(int x, int y) {
        return tiles[x][y].getTileColour() != Tile.TileColour.BROWN;
    }

    /**
     * Checks to see if the Board is in a winning state. If no rabbits are present
     * on the Board, the game can't be played, so the Board is not in a winning
     * state.
     *
     * @return True if the board is in a winning state, false otherwise
     */
    public boolean isInWinningState() {
        int rabbitCount = 0;
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                Piece piece = tiles[x][y].getPiece();
                if (piece != null && piece.getPieceType() == Piece.PieceType.RABBIT) {
                    rabbitCount++;
                    if (tiles[x][y].getTileColour() != Tile.TileColour.BROWN) {
                        return false;
                    }
                }
            }
        }
        return rabbitCount != 0;
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
     * position is invalid
     */
    public Piece getPiece(int x, int y) {
        return validatePosition(x, y) ? this.tiles[x][y].getPiece() : null;
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
        if (piece != null && validatePosition(x, y)) {
            tiles[x][y].placePiece(piece);
            notifyListeners();
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
     * invalid position or there was no piece to remove)
     */
    public Piece removePiece(int x, int y) {
        if (validatePosition(x, y) && tiles[x][y].isOccupied()) {
            notifyListeners();
            return tiles[x][y].removePiece();
        }
        return null;
    }

    /**
     * Adds a listener to this board.
     *
     * @param boardListener The listener to add
     */
    public void addListener(BoardListener boardListener) {
        if (!boardListeners.contains(boardListener)) {
            boardListeners.add(boardListener);
        }
    }

    /**
     * Notifies all listeners that the board has changed.
     */
    private void notifyListeners() {
        boardListeners.forEach(BoardListener::handleBoardChange);
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
     * @return A list containing all possible move objects for this board
     */
    public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Piece piece = tiles[i][j].getPiece();
                if (piece instanceof MovablePiece) {
                    moves.addAll(((MovablePiece) piece).getPossibleMoves(this, i, j));
                }
            }
        }
        return moves;
    }

    /**
     * @return The hash code for this Board, as an integer.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return prime * result + Arrays.deepHashCode(tiles);
    }

    /**
     * Determines equality between a board and another object. Equality between two
     * Board objects is satisfied if the two-dimensional array of tiles maintained
     * by both boards are "deeply" equal. No regard is given to the list of
     * listeners or the board's name.
     *
     * @param obj The object to compare this board to
     * @return True if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Board)) {
            return false;
        }
        return Arrays.deepEquals(tiles, ((Board) obj).tiles);
    }

    /**
     * Create a String of the board to be stored in a JSON Object.
     *
     * @return A String representation of this board.
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                str.append(tiles[i][j].toString()).append(" ");
            }
        }
        return str.toString().trim();
    }
}
