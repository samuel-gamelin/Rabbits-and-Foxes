package model;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * The CommandUtil class is responsible for keeping track of all valid CommandWords. 
 * It also processes and returns the CommandWord of a Command.
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
				validCommands.put(command.toString(), command); 
			}
		}
	}

	/**
	 * Return a List of all valid commands.
	 * 
	 * @return A List containing all of the valid CommandWords, as definied in the CommandWord enum.
	 */
	public List<CommandWord> getCommands() {
		List<CommandWord> commandList = new ArrayList<>();
		validCommands.entrySet().forEach(e -> commandList.add(e.getValue()));
		return commandList;
	}

	/**
	 * Return the corresponding CommandWord that matches the passed String.
	 * 
	 * @param commandWord The String representation of a valid CommandWord.
	 * @return The enumerated CommandWord, based on the passed String. If the String
	 *         is not recognized, CommandWord.INVALID is returned.
	 */
	public CommandWord getCommandWord(String commandWord) {
		CommandWord command = validCommands.get(commandWord);
		if (command == null) {
			return CommandWord.INVALID; 
		}
		return command;
	}
}