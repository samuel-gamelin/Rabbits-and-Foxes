package model;

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
	private static final Game game = new Game();

	/**
	 * The main constructor starts the game and initializes the board as well as the
	 * parser
	 */
	private Game() {
		this.parser = new Parser();
	}

	/**
	 * This method will start the game but wait for the user to type start to play
	 * the game. It then calls the method playGame().
	 */
	public void startGame() {
		System.out.println("Welcome to JumpIN Game !");
		System.out.println("Type 'help' at any time if you need help.");
		System.out.println("Please type 'Start' to start the game. Have fun!");
		this.board = new Board();
		boolean startGame = false;
		Command command;
		do {
			command = parser.readCommand();
			if (command.getCommandWord().equals(CommandWord.MOVE)) {
				System.out.println("Cant Move before starting the game");
			} else {
				startGame = this.processCommandWord(command);
			}
		} while (!startGame);
		System.out.println("Game started, have fun.");
		this.playGame();
	}

	/**
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		do {
			processCommandWord(parser.readCommand());
			System.out.println(this.board.toString());
		} while (!this.board.isInWinningState());
		System.out.println("Congrats your won !");
	}

	/**
	 * Process the command to see how the game should respond to the user's input.
	 * 
	 * @param command The command to process
	 * @return True, if the user has requested the game to end. False otherwise.
	 */
	private boolean processCommandWord(Command command) {
		CommandWord commandWord = command.getCommandWord();
		if (commandWord.equals(CommandWord.MOVE)) {
			board.move(new Move(command.getStartPos(), command.getEndPos()));
		} else if (commandWord.equals(CommandWord.QUIT)) {
			System.out.println("Thank you for playing. Good bye.");
			System.exit(0);
		} else if (commandWord.equals(CommandWord.INVALID)) {
			System.out.println("Invalid command please try again.");
			System.out.println("Type 'help' if you need help.\n");
		} else if (commandWord.equals(CommandWord.RESET)) {
			System.out.println("The Game is being reset enjoy.");
			this.board = new Board();
		} else if (commandWord.equals(CommandWord.START)) {
			return true;
		} else if (commandWord.equals(CommandWord.HELP)) {
			System.out.println("Here is the help menu:");
			parser.showAllCommands();
			System.out.println();
		}
		return false;
	}

	public static Game getGame() {
		return game;
	}

	/**
	 * Main method used to run the game.
	 * 
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		Game game = getGame();
		game.startGame();
	}

}
