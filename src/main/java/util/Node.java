package util;

import model.Board;

/**
 * This class represents a node in the game tree of Rabbits and Foxes.
 * 
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 *
 * @version 3.0
 */
public class Node {
	private boolean visited;
	private Board board;
	
	public Node(Board board) {
		this.board = board;
	}
	
	public void visit() {
		visited = true;
	}
}
