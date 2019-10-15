/**
 * 
 */
package app;

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
		boolean done = false;
		while (!this.isDone()) {
			command = parser.readCommand();
			done = this.processCommandWord(command);
		}
		System.out.println("\nThank you for playing. Good bye.");
	}

	/***
	 * Prints all the commands that can be used within the game.
	 */
	private void printHelp() {
		System.out.println("Here is the help menu:");
		parser.showCommands();
	}

	/***
	 * Process the command to see how the game should respond.
	 * 
	 * @param command
	 * @return
	 */
	private boolean processCommandWord(CommandWord command) {
		boolean endGame = false;
		CommandWord commandWord = command.readCommand();
		if (commandWord == CommandWord.HELP) {
			this.printHelp();
		} else if (commandWord == CommandWord.MOVE) {
			this.movePiece(commandWord);
		} else if (commandWord == CommandWord.INVALID) {
			System.out.println("Invalid command please try again.");
			System.out.println("Type 'help' if you need help.\n");
		} else if (commandWord == CommandWord.QUIT) {
			this.quitGame(commandWord);
		} else if (commandWord == CommandWord.START) {

		}
		return endGame;
	}

	/***
	 * Invokes a method in board to move the piece on specific coordinate
	 * 
	 * @param command
	 */
	private void movePiece(CommandWord command) {

	}

	/***
	 * checks if the user truly wants to quite the game.
	 * 
	 * @param commandWord
	 * @return true if the user wants to quite the game
	 */
	private boolean quitGame(CommandWord commandWord) {
		// TO DO
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
