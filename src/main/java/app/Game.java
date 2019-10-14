/**
 * 
 */
package app;

import java.util.ArrayList;

/**
 * This class is responsible for controlling the game. Using parser and board
 * class to play the game/
 * 
 * @author Mohamed Radwan
 * @version 1.0
 */
public class Game {
	private Parser parser;
	private Board board;

	/**
	 * The main constructor starts the game and initializes the board as well as the
	 * parser
	 */
	public Game() {
		this.parser = new Parser();
		this.board = new Board();
	}

	/***
	 * This method is used to call a method within board to check the status of the
	 * game
	 * 
	 * @return true if the game is done
	 */
	private boolean isDone() {
		return this.board.isInWinningState();
	}

	/***
	 * This method is used to play the game.
	 */
	public void playGame() {
		System.out.println("Welcome to JumpIN Game !");
		System.out.println("Type 'help' if you need help.\n");
		ArrayList<String> command;
		boolean done = false;
		while (!this.isDone()) {
			command = parser.readCommand();
			done = this.processCommandWord(command);
		}
		System.out.println("\nThank you for playing. Good bye.");
	}

	private void printHelp() {
		// TODO
		System.out.println("Help menu goes here");
		// parser.showCommands();
	}

	private boolean processCommandWord(ArrayList<String> command) {
		return false;
	}

	private boolean quiteGame(CommandWord commandWord) {
		return true;
	}

	/***
	 * Main method used to run the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create a new game
		Game game = new Game();
		// run the game
		game.playGame();
	}

}
