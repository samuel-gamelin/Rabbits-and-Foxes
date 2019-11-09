package util;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
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
		Set<Node> vistied = new HashSet<>();
		Set<Node> winningPath = new HashSet<>();
		int i = 0;
		while (!stack.isEmpty()) {
			Node currentNode = stack.pop();
			winningPath.add(currentNode);
			if (!vistied.contains(currentNode)) {
				vistied.add(currentNode);
				winningPath.remove(currentNode);
				Set<Node> children = currentNode.getChildren();
				children.removeAll(vistied);
				for (Node child : children) {
					if (child.isWinningNode()) {
						System.out.println("Found solution " + i + "\n" + child);
						return;
					} else {
						stack.add(child);
						i++;
					}
				}
			}
		}
		System.out.println("No solution exists: " + i);
	}

	/**
	 * Performs a breadth-first search on the specified node.
	 * 
	 * @param root The node from which to stem the search
	 */
	public void breadthFirstSearch(Node root) {
		Queue<Node> queue = new ArrayDeque<>();
		queue.add(root);
		int i = 0;
		Set<Node> vistied = new HashSet<>();
		while (!queue.isEmpty()) {
			Node currentNode = queue.remove();
			if (!vistied.contains(currentNode)) {
				vistied.add(currentNode);
				Set<Node> children = currentNode.getChildren();
				if (!children.isEmpty()) {
					for (Node child : children) {
						if (child.isWinningNode()) {
							System.out.println("Found solution " + i + "\n" + child);
							return;
						} else {
							queue.add(child);
							i++;
						}
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

		graph.depthFirstSearch(new Node(new Board()));
//		graph.breadthFirstSearch(new Node(new Board()));

		System.out.println(
				"\nExecution time (in seconds): " + TimeUnit.NANOSECONDS.toNanos((System.nanoTime() - startTime)));
	}
}
