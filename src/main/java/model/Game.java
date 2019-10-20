package model;

import java.util.List;

/**
 * This class starts a game and plays it.
 * 
 * It is singleton class since there can only be one instance of game class
 * running at a time. This will help manage the game so that the user would not be
 * able to run multiple game instances at once.
 * 
 * The Game class uses an instance of Parser to process user input, and an instance
 * of Board for the player to be able to play the game.
 * 
 * @author Mohamed Radwan
 * @version 1.0
 */
public class Game {
	/**
	 * The Parser object used by game to process user input.
	 */
	private Parser parser;
	
	/**
	 * The Board object used by game to 
	 */
	private Board board;
	
	/**
	 * The only instance of this class.
	 */
	private static final Game GAME = new Game();

	/**
	 * The constructor initializes the board and parser.
	 */
	private Game() {
		this.board = new Board();
		this.parser = new Parser();
	}

	/**
	 * This method will wait until the user types start to play
	 * the game, at which point it will call playGame().
	 */
	public void startGame() {
		System.out.println("Welcome to the game of JumpIN!");
		System.out.println("Type 'help' at any time if you need help.");
		System.out.println("Please type 'start' to start the game. Have fun!");
		boolean startGame = false;
		do {
			Command command = parser.readCommand();
			if (command.getCommandWord().equals(CommandWord.MOVE)) {
				System.out.println("You can't move before starting the game.");
			} else if (command.getCommandWord().equals(CommandWord.RESET)) {
				System.out.println("You can't reset the game before starting it.");
			} else {
				startGame = this.processCommandWord(command);
			}
		} while (!startGame);
		System.out.println("The game has begun, have fun!\n\n");
		this.playGame();
	}

	/**
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		System.out.println(this.board);
		do {
			if (processCommandWord(parser.readCommand())) {
				System.out.println("The game is already running, type 'reset' if you want to start over.\n");
			}
		} while (!this.board.isInWinningState());
		System.out.println("Congrats, you solved the puzzle!");
	}

	/**
	 * Process the command to see how the game should respond to the user's input.
	 * 
	 * @param command The command to process
	 * @return True, if the user has requested the game to start. False otherwise.
	 */
	private boolean processCommandWord(Command command) {
		CommandWord commandWord = command.getCommandWord();
		if (commandWord.equals(CommandWord.MOVE)) {
			if (board.move(new Move(command.getStartPos(), command.getEndPos()))) {
				System.out.println("\nMove has been made\n");
				System.out.println("\n" + board + "\n");
			} else {
				System.out.println("\nInvalid move try again\n");
			}
		} else if (commandWord.equals(CommandWord.QUIT)) {
			System.out.println("Thank you for playing. Good bye.");
			System.exit(0);
		} else if (commandWord.equals(CommandWord.INVALID)) {
			System.out.println("Invalid command, please try again.");
			System.out.println("Type 'help' if you need help.\n");
		} else if (commandWord.equals(CommandWord.RESET)) {
			this.board = new Board();
			System.out.println("The game has been reset, enjoy.\n\n" + board + "\n");
		} else if (commandWord.equals(CommandWord.START)) {
			return true;
		} else if (commandWord.equals(CommandWord.HELP)) {
			printHelp();
		}
		return false;
	}

	/**
	 * Prints the help menu for the Game.
	 */
	private void printHelp() {
		System.out.println("Help menu:");
		System.out.println();
		List<CommandWord> commandsWithInfo = parser.getAllCommands();
		for (CommandWord curr : commandsWithInfo) {
			System.out.println(curr.getHelpInfo() + "\n");
		}
	}

	/***
	 * @return The only instance of this class.
	 */
	public static Game getGame() {
		return GAME;
	}

	/**
	 * Main method used to run the game.
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		Game.getGame().startGame();
	}
}
