package util;

import java.util.ArrayList;
import java.util.List;

import model.Board;
import model.Move;

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
	
	public Node(Board board) {
		this.board = new Board(board);
	}
	
	/**
	 * @return A list containing all children of this node
	 */
	public List<Node> getChildren() {
		List<Node> children = new ArrayList<Node>();
		
		for (Move move : board.getPossibleMoves()) {
			Board newBoard = new Board(board);
			newBoard.move(move);
			children.add(new Node(newBoard));
		}
		
		return children;
	}
	
	/**
	 * @return True if this node's board is in a winning state. False otherwise.
	 */
	public boolean isWinningNode() {
		return board.isInWinningState();
	}
}
