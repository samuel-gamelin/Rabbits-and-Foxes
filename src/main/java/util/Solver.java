package util;

import java.util.List;
import java.util.ArrayList;

import model.Board;
import model.Fox;
import model.Piece;

/**
 * This class is used to solve a board representing a state in the game of
 * Rabbits and Foxes.
 * 
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * 
 * @version 3.0
 */
public class Solver {
	/**
	 * Keeps track of the most recently visited node.
	 */
	private static Node lastNonDuplicatedNode;

	/**
	 * Private constructor since solver cannot be instantiated
	 */
	private Solver() {
	}

	/**
	 * Determines the next best move, given a board object.
	 * 
	 * @param board The board whose next best move is to be determined
	 * @return The next best move. Should there be no next best move, a move object
	 *         with an invalid direction is returned.
	 */
	public static Move getNextBestMove(Board board) {
		Graph graph = new Graph();
		Node node = new Node(board);
		List<Node> winningNodePath = cleanNodeList(graph.depthFirstSearch(node));

		if (winningNodePath == null || winningNodePath.size() < 2) {
			return new Move(-1, -1, -1, -1);
		}

		if (lastNonDuplicatedNode != null && lastNonDuplicatedNode.equals(winningNodePath.get(1))
				&& winningNodePath.size() > 2) {
			return winningNodePath.get(1).getMoveTo(winningNodePath.get(2));
		} else {
			lastNonDuplicatedNode = node;
			return winningNodePath.get(0).getMoveTo(winningNodePath.get(1));
		}

	}

	/**
	 * Cleans the list from unwanted fox moves (repeated moves with the same fox).
	 * 
	 * @param nodeList
	 * @return
	 */
	public static List<Node> cleanNodeList(List<Node> nodeList) {
		if (nodeList == null || nodeList.size() < 3) {
			return nodeList;
		}
		List<Node> newList = new ArrayList<>();

		for (Node node : nodeList) {
			newList.add(node);
			if (newList.size() == 3) {
				Node node1 = newList.get(0);
				Node node2 = newList.get(1);
				Node node3 = newList.get(2);
				Move from1to2 = node1.getMoveTo(node2);
				Move from2to3 = node2.getMoveTo(node3);

				Piece piece1 = node1.getBoard().getPiece(from1to2.xStart, from1to2.yStart);
				Piece piece2 = node2.getBoard().getPiece(from2to3.xStart, from2to3.yStart);

				if (piece1 instanceof Fox && piece2 instanceof Fox && ((Fox) piece1).equals((Fox) piece2)) {
					newList.remove(node2);
				} else {
					return newList;
				}

			}
		}

		return newList;
	}
}
