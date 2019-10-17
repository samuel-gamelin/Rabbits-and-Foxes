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
		boolean startGame = false;
		CommandWord commandWord;
		System.out.println("Welcome to JumpIN Game !");
		System.out.println("Type 'help' at any time if you need help.");
		System.out.println("Please type 'Start' to start the game. Have fun!");
		do {
			commandWord = parser.readCommand().getCommandWord();
			switch (commandWord) {
			case START:
				startGame = true;
				break;
			case HELP:
				printHelp();
				break;
			case QUIT:
				this.quit();
				break;
			default:
				break;
			}
		} while (!startGame);
		System.out.println("Foxes are moving to position...\nGame started, have fun.");
		this.playGame();
	}

	/***
	 * This method will print the help menu for the user
	 */
	private void printHelp() {
		System.out.println("Here is the help menu:");
		parser.showAllCommands();
		System.out.println();
	}

	private void quit() {
		System.out.println("\nThe foxes will now go back to sleep!");
		System.out.println("Thank you for playing. Good bye.");
		System.exit(0);
	}

	/**
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		do {
			processCommandWord(parser.readCommand());
			System.out.println(this.board.toString());
		} while (!this.board.isInWinningState());
		this.quit();
	}

	/**
	 * Process the command to see how the game should respond to the user's input.
	 * 
	 * @param command The command to process
	 * @return True, if the user has requested the game to end. False otherwise.
	 */
	private void processCommandWord(Command command) {
		CommandWord commandWord = command.getCommandWord();
		switch (commandWord) {
		case HELP:
			printHelp();
			break;
		case MOVE:
			board.move(new Move(command.getStartPos(), command.getEndPos()));
			break;
		case INVALID:
			System.out.println("Invalid command please try again.");
			System.out.println("Type 'help' if you need help.\n");
			break;
		case QUIT:
			this.quit();
			break;
		case RESET:
			System.out.println("The Game is being reset enjoy.");
			this.board = new Board();
			break;
		default:
			break;
		}
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
