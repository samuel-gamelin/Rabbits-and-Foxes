package util;

import java.util.Set;

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

	private static Move extractMove(Set<Node> list) {
		return null;
	}

	public static Move getNextBestMove(Board board) {
		Graph graph = new Graph();
		return extractMove(graph.depthFirstSearch(new Node(board)));
	}

}
