package util;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import model.Board;

/**
 * This class models a graph in the Rabbits and Foxes game.
 * 
 * @author Samuel Gamelin
 * @author Mohamed Radwan
 * 
 * @version 3.0
 */
public class Graph {
	/**
	 * Performs a depth-first search on the specified node. Exits when a winning
	 * state is found.
	 * 
	 * @param root The node from which to stem the search
	 */
	public void depthFirstSearch(Node root) {
		ArrayDeque<Node> stack = new ArrayDeque<>();
		stack.add(root);
		while (!stack.isEmpty()) {
			Node currentNode = stack.pop();
			List<Node> children = currentNode.getChildren();
			if (!children.isEmpty() && !currentNode.isVisited()) {
				currentNode.setVisited(true);
				for (Node child : children) {
					if (child.isWinningNode()) {
						System.out.println("Found solution \n" + child);
						return;
					} else {
						stack.add(child);
					}
				}
			}

		}
		System.out.println("No solution exists.");
	}

	/**
	 * Performs a breadth-first search on the specified node.
	 * 
	 * @param root The node from which to stem the search
	 */
	public void breadthFirstSearch(Node root) {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();

			if (currentNode.isWinningNode()) {
				System.out.println("Found solution\n" + currentNode);
				return;
			} else {
				for (Node child : currentNode.getChildren()) {
					if (child.isWinningNode()) {
						System.out.println("Found solution\n" + child);
						return;
					} else if (!child.isVisited() && !child.getChildren().isEmpty()) {
						queue.add(child);
					}
				}
			}
		}

		System.out.println("No solution exists.");
	}

	/*
	 * Testing.
	 */
	public static void main(String[] args) {
		Graph graph = new Graph();
		final long startTime = System.nanoTime();

		// graph.depthFirstSearch(new Node(new Board()));
		graph.depthFirstSearch(new Node(new Board()));

		System.out.println(
				"\nExecution time (in seconds): " + TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime));
	}
}
