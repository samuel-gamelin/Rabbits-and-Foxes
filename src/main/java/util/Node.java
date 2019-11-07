package util;

import java.util.ArrayList;
import java.util.List;

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
	private Board board;
	private boolean visited;

	public Node(Board board) {
		this.board = new Board(board);
	}

	/**
	 * @return A list containing all children of this node
	 */
	public List<Node> getChildren() {
		List<Node> children = new ArrayList<>();

		for (Move move : board.getPossibleMoves()) {
			Board newBoard = new Board(board);
			newBoard.move(move);
			children.add(new Node(newBoard));
		}

		return children;
	}

	/**
	 * @return True if this node has been visited, false otherwise
	 */
	public boolean isVisited() {
		return visited;
	}

	/**
	 * Sets the visited state of this node.
	 * 
	 * @param visited The visited value to set
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	/**
	 * @return True if this node's board is in a winning state. False otherwise.
	 */
	public boolean isWinningNode() {
		return board.isInWinningState();
	}

	@Override
	public String toString() {
		return board.toString();
	}
}
