/**
 * 
 */
package app;

import java.util.HashMap;

/**
 * The CommandUtil class is responsible for processing inputed commands. It also
 * keeps track of all possible valid commands.
 * 
 * @author John Breton
 * @version 1.0
 */
public class CommandUtil {
	// Instance variable declaration.
	private HashMap<String, CommandWord> validCommands; // Maps a corresponding String to a valid CommandWord

	/**
	 * Construct the HashMap that contains the valid commands for the Game.
	 */
	public CommandUtil() {
		validCommands = new HashMap<>();
		for (CommandWord command : CommandWord.values()) {
			if (command != CommandWord.INVALID) {
				validCommands.put(command.toString(), command); // Map the string representation of the CommandWord to
																// itself.
			}
		}
	}

	/**
	 * Display a String of all valid commands separated by a whitespace. Once all
	 * the valid commands have been displayed, a newline is printed.
	 */
	public void showCommands() {
		validCommands.entrySet().forEach(e -> {
			System.out.print(e.getValue() + " ");
		});
		System.out.println();
	}

	/**
	 * Return the corresponding CommandWord that matches the passed String. If no
	 * CommandWord matches the passed String, this method will return
	 * CommandWord.INVALID
	 * 
	 * @param command The String representation of a valid CommandWord.
	 * @return The enumerated CommandWord, based on the passed String. If the String
	 *         is not recognized, CommandWord.INVALID is returned.
	 */
	public CommandWord getCommandWord(String commandWord) {
		CommandWord command = validCommands.get(commandWord);
		if (command == null) {
			return CommandWord.INVALID; // The commandWord was not a valid string representation of any valid
										// CommandWord.
		}
		return command;
	}
}