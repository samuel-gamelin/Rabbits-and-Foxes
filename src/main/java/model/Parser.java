/**
 * 
 */
package model;

import java.util.Scanner;

/**
 * The Parser class is responsible for parsing user input. This input is used to
 * determine how to progress through the Game, based on the CommandWord the user
 * has inputed.
 * 
 * @author John Breton
 * @version 1.0
 */
public class Parser {
	// Instance variables declarations.
	Scanner parser;
	CommandUtil commandUtil;

	/**
	 * Construct the Parser. The Parser is a Scanner that scans for user input. Any
	 * Parser will also have a CommandUtil that deals with command utilities.
	 */
	public Parser() {
		commandUtil = new CommandUtil();
		parser = new Scanner(System.in);
	}

	/**
	 * Read the current line of input from the parser and stores it in a temporary
	 * String array.
	 * 
	 * The first thing that is read should be a valid CommandWord. If this is the
	 * only input, the default Command constructor is called and returned.
	 * 
	 * If there are exactly three Strings in the temporary String array, it should
	 * indicate that a move CommandWord was issued. In this instance, the second
	 * Command constructor is called and returned.
	 * 
	 * @return A Command, constructed based on the passed CommandWord and, if
	 *         applicable, subsequent input. If the input is malformed a Command
	 *         with value CommandWord.INVALID is returned.
	 */
	public Command readCommand() {
		String[] input = parser.nextLine().split(" ");
		if (input.length == 1) {
			return new Command(commandUtil.getCommandWord(input[0].toLowerCase()));
		} else if (input.length == 3 && (commandUtil.getCommandWord(input[0]) == CommandWord.MOVE)) {
			return new Command(commandUtil.getCommandWord(input[0].toLowerCase()), input[1], input[2]);
		}
		return new Command(CommandWord.INVALID);
	}

	/**
	 * Print a list of all the valid commands for the game. This list includes the
	 * valid commands: help, move, reset, start, and quit.
	 */
	public void showAllCommands() {
		commandUtil.showCommands();
	}
}