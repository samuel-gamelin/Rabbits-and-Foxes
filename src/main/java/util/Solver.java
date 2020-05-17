package util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import model.Board;
import model.Fox;
import model.Piece;

import java.util.*;

/**
 * This class is used to solve a board representing a state in the game of
 * Rabbits and Foxes.
 *
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * @version 4.0
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Solver {

    private static List<Node> lastHint;

    /**
     * Determines the next best move, given a board object.
     *
     * @param board The board whose next best move is to be determined
     * @return The next best move. Should there be no next best move, a move object
     * with an invalid direction is returned.
     */
    public static Move getNextBestMove(Board board) {
        if (board == null) {
            return new Move(-1, -1, -1, -1);
        }

        Node node = new Node(board);

        if (lastHint == null || !lastHint.contains(node)) {
            lastHint = cleanNodeList(breadthFirstSearch(node));
        }

        if (!lastHint.isEmpty()) {
            int boardOne = lastHint.indexOf(node);
            if (lastHint.size() > boardOne) {
                return lastHint.get(boardOne).getMoveTo(lastHint.get(boardOne + 1));
            }
        }
        return new Move(-1, -1, -1, -1);
    }

    /**
     * Cleans the list from unwanted fox moves (repeated, consecutive moves with the
     * same fox).
     *
     * @param nodeList The list of nodes to clean
     * @return A list of nodes where unwanted nodes have been removed
     */
    private static List<Node> cleanNodeList(List<Node> nodeList) {
        if (nodeList == null || nodeList.size() < 3) {
            return nodeList;
        }
        List<Node> removeFromList = new ArrayList<>();

        for (int i = 0; i < nodeList.size() - 3; i++) {
            Node node1 = nodeList.get(i);
            Node node2 = nodeList.get(i + 1);
            Node node3 = nodeList.get(i + 2);

            Move from1to2 = node1.getMoveTo(node2);
            Move from2to3 = node2.getMoveTo(node3);

            Piece piece1 = node1.getBoard().getPiece(from1to2.xStart, from1to2.yStart);
            Piece piece2 = node2.getBoard().getPiece(from2to3.xStart, from2to3.yStart);

            if (piece1 instanceof Fox && piece2 instanceof Fox && ((Fox) piece1).getID() == ((Fox) piece2).getID()) {
                removeFromList.add(node2);
            }
        }

        nodeList.removeAll(removeFromList);
        return nodeList;
    }

    /**
     * Performs a breadth-first search on the specified node.
     *
     * @param root The node from which to stem the search
     * @return The list of nodes that form the winning path. Should there be no
     * winning path, this list will be empty.
     */
    private static List<Node> breadthFirstSearch(Node root) {
        if (root.isWinningNode()) {
            return new LinkedList<>();
        }

        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);

        Set<Node> visited = new HashSet<>();
        Map<Node, Node> parentMap = new HashMap<>();
        List<Node> winningPathList = new LinkedList<>();

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();
            if (!visited.contains(currentNode)) {
                visited.add(currentNode);
                Set<Node> children = currentNode.getChildren();
                children.removeAll(visited);
                for (Node child : children) {
                    parentMap.put(child, currentNode);
                    if (child.isWinningNode()) {
                        Node node = child;
                        while (node != null) {
                            winningPathList.add(0, node);
                            node = parentMap.get(node);
                        }
                        return winningPathList;
                    } else {
                        queue.add(child);
                    }
                }
            }
        }
        return winningPathList;
    }
}
