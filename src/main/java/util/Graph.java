package util;

import java.util.ArrayDeque;
import java.util.Queue;

import model.Board;

public class Graph {
	/**
	 * Performs a bread-first search on the specified node.
	 */
	public void breadthFirstSearch(Node root) {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();
			
			if (currentNode.isWinningNode()) {
				System.out.println("Found solution");
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
