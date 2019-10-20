/**
 * 
 */
package model;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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
	Scanner scanner;
	CommandUtil commandUtil;

	/**
	 * Construct the Parser. The Parser is a Scanner that scans for user input. Any
	 * Parser will also have a CommandUtil that deals with command utilities.
	 */
	public Parser() {
		commandUtil = new CommandUtil();
		scanner = new Scanner(System.in);
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
		String[] input = scanner.nextLine().split(" ");
		if (input.length == 1) {
			return new Command(commandUtil.getCommandWord(input[0].toLowerCase()));
		} else if (input.length == 3 && (commandUtil.getCommandWord(input[0]) == CommandWord.MOVE)) {
			return new Command(commandUtil.getCommandWord(input[0].toLowerCase()), input[1], input[2]);
		}
		return new Command(CommandWord.INVALID);
	}

	/**
	 * This method is used to return to Game class all available commands with
	 * information on what each command does and how to use it.
	 * 
	 * @return commandListWithInfo - a list of all the valid commands for the game.
	 *         This list includes the valid commands: help, move, reset, start, and
	 *         quit.
	 */
	public List<String> getAllCommands() {
		List<CommandWord> commandList = commandUtil.getCommands();
		List<String> commandListWithInfo = new ArrayList<>();
		for (CommandWord curr : commandList) {
			if (curr.equals(CommandWord.HELP)) {
				commandListWithInfo.add("  help: Displays this menu.");
			} else if (curr.equals(CommandWord.START)) {
				commandListWithInfo.add("  start: Starts the game. Can only be used before the game has started.");
			} else if (curr.equals(CommandWord.MOVE)) {
				StringBuilder temp = new StringBuilder();
				temp.append("  move x1y1 x2y2: Moves a piece from x1y1 to x2y2, if the move is valid.\n\n");
				temp.append("                  Rabbits (Denoted by \"RB\") can move by jumping over\n");
				temp.append("                  occupied tiles. Rabbits can only move in a straight line.\n\n");
				temp.append("                  Foxes (Denoted by \"FH\" and \"FT\") can move by sliding\n");
				temp.append("                  to the left and right if they are horizontal, or up and\n");
				temp.append("                  down if they are vertical. They cannot slide through or\n");
				temp.append("                  past occupied tiles. They can be jumped over by rabbits.\n\n");
				temp.append("                  Mushrooms (Denoted by \"MU\") cannot move. However, they\n");
				temp.append("                  can be jumped over by rabbits.\n\n");
				temp.append("                  An example move: \"move 12 14\", which moves a piece from\n");
				temp.append("                  column 1 row 2, to column 1 row 4, if the move is valid.");
				commandListWithInfo.add(temp.toString());
			} else if (curr.equals(CommandWord.RESET)) {
				commandListWithInfo.add("  reset: Resets the game. Can only be used after the game has started.");
			} else if (curr.equals(CommandWord.QUIT)) {
				commandListWithInfo.add("  quit: Quits the game.");
			}
		}
		return commandListWithInfo;
	}
}