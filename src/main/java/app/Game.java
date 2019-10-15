/**
 * 
 */
package app;

import java.util.Scanner;

/**
 * This class is responsible for controlling the game. Using parser and board
 * class to play the game
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
	}

	/***
	 * This method will start the game but wait for the user to type start to play
	 * the game. It then calls the method playGame()
	 */
	public void startGame() {
		this.board = new Board();
		System.out.println("Welcome to JumpIN Game !");
		System.out.println("Type 'help' if you need help.");
		System.out.println("\nPlease type 'Start' to start the game. Have fun!");
		boolean startGame = false;
		do {
			if (parser.readCommand().getCommandWord() == CommandWord.START) {
				startGame = true;
			}

		} while (!startGame);
		System.out.println("Get Ready, Game is Starting.");
		System.out.println("Foxes are moving to position...");
		System.out.println("Game started, have fun.");
		this.playGame();
	}

	/***
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		boolean done = false;
		while (!(this.isDone() || done)) {
			Command command = parser.readCommand();
			CommandWord commandWord = command.getCommandWord();
			done = this.processCommandWord(commandWord);
		}
		System.out.println("\nThe foxes will now go back to sleep!");
		System.out.println("Thank you for playing. Good bye.");
	}

	/***
	 * Process the command to see how the game should respond to the user's input.
	 * 
	 * @param command
	 * @return
	 */
	private boolean processCommandWord(CommandWord commandWord) {
		boolean endGame = false;
		if (commandWord == CommandWord.HELP) {
			System.out.println("Here is the help menu:");
			parser.showAllCommands();
			System.out.println();
		} else if (commandWord == CommandWord.MOVE) {
			this.movePiece(commandWord);
		} else if (commandWord == CommandWord.INVALID) {
			System.out.println("Invalid command please try again.\nType 'help' if you need help.\n");
		} else if (commandWord == CommandWord.QUIT) {
			return true;
		} else if (commandWord == CommandWord.RESET) {
			this.startGame();
		}
		return endGame;
	}

	/***
	 * Invokes a method in board to move the piece on specific coordinate
	 * 
	 * @param command
	 */
	private void movePiece(CommandWord command) {
		System.out.println("Move Piece ... TO DO");
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
	 * Main method used to run the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.startGame();
	}

}
