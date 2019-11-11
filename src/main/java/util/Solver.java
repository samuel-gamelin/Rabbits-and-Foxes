package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.Board;
import sun.misc.Cleaner;

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
	 * Determines the next best move, given a board object.
	 * 
	 * @param board The board whose next best move is to be determined
	 * @return The next best move. Should there be no next best move, a move object
	 *         with an invalid direction is returned.
	 */
	public static Move getNextBestMove(Board board) {
		Graph graph = new Graph();
		List<Node> winningNodePath = cleanListOfBoards(graph.breadthFirstSearch(new Node(board)));

		if (winningNodePath.size() < 2) {
			return new Move(-1, -1, -1, -1);
		}

		return winningNodePath.get(0).getMoveTo(winningNodePath.get(1));
	}

	/**
	 * Cleans the list from unwanted fox moves
	 * 
	 * @param list
	 * @return
	 */
	public static List<Node> cleanListOfBoards(List<Node> list) {
		List<Node> toBeRemovedNodes = new LinkedList<>();
		list.removeAll(toBeRemovedNodes);
		return list;
	}

}
