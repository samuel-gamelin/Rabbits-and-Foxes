package util;

import java.util.List;

import model.Board;

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
	 * @return The next best move. Should there be no next best move, a move object with an invalid direction is returned.
	 */
	public static Move getNextBestMove(Board board) {
		Graph graph = new Graph();
		List<Node> winningNodePath = graph.breadthFirstSearch(new Node(board));
		
		if (winningNodePath.size() < 2) {
			return new Move(-1, -1, -1, -1);
		}
		
		return winningNodePath.get(0).getMoveTo(winningNodePath.get(1));
	}
}
