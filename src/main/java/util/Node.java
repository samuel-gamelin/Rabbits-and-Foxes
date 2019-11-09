package util;

import java.util.LinkedHashSet;
import java.util.Set;

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
	public Set<Node> getChildren() {
		Set<Node> children = new LinkedHashSet<>();
		Set<Move> possibleMoves = board.getPossibleMoves();
		if (possibleMoves != null && !possibleMoves.isEmpty()) {
			for (Move move : possibleMoves) {
				Board newBoard = new Board(board);
				newBoard.move(move);
				children.add(new Node(newBoard));
			}
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Node))
			return false;
		Node other = (Node) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		return true;
	}

}
