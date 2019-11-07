package util;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import model.Board;

/**
 * This class models a graph in the Rabbits and Foxes game.
 * 
 * @author Samuel Gamelin
 */
public class Graph {
	/**
	 * Performs a depth-first search on the specified node. Exits when a winning state is found.
	 * 
	 * @param root The node from which to stem the search
	 */
	@Deprecated
	public void depthFirstSearch(Node root) {
		Stack<Node> stack = new Stack<>();
		stack.add(root);
		
		while (!stack.isEmpty()) {
			Node node = stack.pop();
			
			if (node.isWinningNode()) {
				System.out.println("Found solution\n" + node);
				return;
			}
			
			if (!node.isVisited()) {
				node.setVisited(true);
			}
			
			List<Node> children = node.getChildren();
			for (Node child : children) {
				if (!child.isVisited()) {
					stack.add(child);
				}
			}
		}
	}
	
	/**
	 * Performs a bread-first search on the specified node.
	 * 
	 * @param root The node from which to stem the search
	 */
	public void breadthFirstSearch(Node root) {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();
			
			if (currentNode.isWinningNode()) {
				System.out.println("Found solution");
				System.out.println(currentNode);
				return;
			} else {
				queue.addAll(currentNode.getChildren());
			}
		}
	}
	
	/*
	 * Testing.
	 */
	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.breadthFirstSearch(new Node(new Board()));
	}
}
