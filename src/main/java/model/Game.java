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
	private static final Game GAME = new Game();

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
		System.out.println("Welcome to the game of JumpIN!");
		System.out.println("Type 'help' at any time if you need help.");
		System.out.println("Please type 'start' to start the game. Have fun!");
		this.board = new Board();
		boolean startGame = false;
		Command command;
		do {
			command = parser.readCommand();
			if (command.getCommandWord().equals(CommandWord.MOVE)) {
				System.out.println("You can't move before starting the game.");
			} else if (command.getCommandWord().equals(CommandWord.RESET)) {
				System.out.println("You can't reset the game before starting it.");
			} else {
				startGame = this.processCommandWord(command);
			}
		} while (!startGame);
		System.out.println("The game has begun, have fun!");
		this.playGame();
	}

	/**
	 * This method will run the game for the user to interact with.
	 */
	private void playGame() {
		System.out.println(this.board.toString());
		do {
			if (processCommandWord(parser.readCommand())) {
				System.out.println("The Game is already running type 'rest' if you want to start over");
			}
		} while (!this.board.isInWinningState());
		System.out.println("Congrats, you solved the puzzle!");
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
			System.out.println(this.board.toString());
		} else if (commandWord.equals(CommandWord.QUIT)) {
			System.out.println("Thank you for playing. Good bye.");
			System.exit(0);
		} else if (commandWord.equals(CommandWord.INVALID)) {
			System.out.println("Invalid command, please try again.");
			System.out.println("Type 'help' if you need help.\n");
		} else if (commandWord.equals(CommandWord.RESET)) {
			System.out.println("The game has been reset, enjoy.");
			this.board = new Board();
			this.playGame();
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
		return GAME;
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
