/**
 * 
 */
package app;

/**
 * @author Mohamed
 *
 */
public class Game {
	private Parser parser;
	private Board board;
	private CommandUtil commandUtil;

	/**
	 * 
	 */
	public Game() {
		this.parser = new Parser();
		this.board = new Board();
	}

	/***
	 * 
	 */
	public void playGame() {

//		while (!this.isDone()) {
//			commandUtil = parser.readCommand();
//			finished = processCommand(command);
//		}

	}

	/***
	 * 
	 * @return true if the game is done
	 */
	private boolean isDone() {
		return this.board.isInWinningState();
	}

	/***
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		System.out.println("\n Welcome to JumpIN Game !");
		System.out.println("This is a Fun Game");
		System.out.println("Type 'help' if you need help. \n");
		game.playGame();
		System.out.println("Thank you for playing. Good bye.");

	}

}
