/**
 * 
 */
package app;

import java.util.HashMap;

/**
 * The CommandUtil class is responsible for processing user
 * commands. It also keeps track of all possible commands
 * 
 * @author John Breton
 * @version 1.0
 */
public class CommandUtil {
	
	// Instance variable decelerations.
	private CommandWord command; 
	private HashMap<String, CommandWord> validCommands; //Maps a specific String to a valid CommandWord
	
	/**
	 * Constructor for the CommandUtil class.
	 */
	public CommandUtil() {
		validCommands = new HashMap<String, CommandWord>();
	}
	
	/**
	 * 
	 */
	public void showCommands() {
		
	}
	
	/**
	 * Checks to see if the passed String is a valid command for the game.
	 * The valid commands for the game are 
	 * 
	 * @param command The string to check to determine if it's a valid command.
	 * @return True if the passed string is a command, false otherwise. 
	 */
	public boolean isCommand(String command) {
		return true;
	}
	
	/**
	 * 
	 * @param command
	 * @return
	 */
	public CommandWord getCommandWord(String command) {
		return QUIT;
	}
}
