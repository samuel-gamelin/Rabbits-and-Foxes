package app;

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

	/**
	 * This method will start the game but wait for the user to type start to play
	 * the game. It then calls the method playGame().
	 */
	public void startGame() {
		this.board = new Board();
		System.out.println("Welcome to JumpIN Game !");
		System.out.println("Type 'help' if you need help.");
		System.out.println("\nPlease type 'Start' to start the game. Have fun!");
		boolean startGame = false;
		do {
			if (parser.readCommand().getCommandWord().equals(CommandWord.START)) {
				startGame = true;
			}

		} while (!startGame);
		System.out.println("Get Ready, Game is Starting.");
		System.out.println("Foxes are moving to position...");
		System.out.println("Game started, have fun.");
		this.playGame();
	}

	/**
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		boolean done;
		do {
			done = processCommandWord(parser.readCommand().getCommandWord());
			System.out.println(board);
		} while (!this.board.isInWinningState() || !done);
		
		System.out.println("\nThe foxes will now go back to sleep!");
		System.out.println("Thank you for playing. Good bye.");
	}

	/**
	 * Process the command to see how the game should respond to the user's input.
	 * 
	 * @param command The command to process
	 * @return True, if the user has requested the game to end. False otherwise.
	 */
	private boolean processCommandWord(CommandWord commandWord) {
		boolean endGame = false;
		if (commandWord.equals(CommandWord.HELP)) {
			System.out.println("Here is the help menu:");
			parser.showAllCommands();
			System.out.println();
		} else if (commandWord.equals(CommandWord.MOVE)) {
			// Logic needed to extract move object from command
			board.move(new Move(0, 0, 0, 0));
		} else if (commandWord.equals(CommandWord.INVALID)) {
			System.out.println("Invalid command please try again.\nType 'help' if you need help.\n");
		} else if (commandWord.equals(CommandWord.QUIT)) {
			endGame = true;
		} else if (commandWord.equals(CommandWord.RESET)) {
			this.startGame();
		}
		return endGame;
	}

	/**
	 * Main method used to run the game.
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.startGame();
	}

}
