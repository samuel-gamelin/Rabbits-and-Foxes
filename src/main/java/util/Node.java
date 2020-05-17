package util;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import model.Board;

import java.util.HashSet;
import java.util.Set;

/**
 * This class represents a node in the game tree of Rabbits and Foxes.
 *
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * @version 4.0
 */
@EqualsAndHashCode
@ToString
public class Node {

    /**
     * The board associated with this node.
     */
    @Getter
    private final Board board;

    public Node(Board board) {
        this.board = new Board(board);
    }

    /**
     * Returns a set containing all child nodes of this node.
     *
     * @return A set containing all children of this node
     */
    public Set<Node> getChildren() {
        Set<Node> children = new HashSet<>();
        for (Move move : board.getPossibleMoves()) {
            Board newBoard = new Board(board);
            newBoard.move(move);
            children.add(new Node(newBoard));
        }
        return children;
    }

    /**
     * Determines the move object required to go from this node to a specified node.
     *
     * @param node The node to attempt to go to
     * @return The move object from this node to the specified one. Should there be
     * no such move, a move object with an invalid direction is returned.
     */
    public Move getMoveTo(Node node) {
        for (Move move : board.getPossibleMoves()) {
            Board newBoard = new Board(board);
            if (newBoard.move(move) && node.equals(new Node(newBoard))) {
                return move;
            }
        }
        return new Move(-1, -1, -1, -1);
    }

    /**
     * @return True if this node's board is in a winning state. False otherwise.
     */
    public boolean isWinningNode() {
        return board.isInWinningState();
    }
}
