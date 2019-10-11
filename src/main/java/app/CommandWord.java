package app;
/**
 * Stores all the possible command words for Rabbits and Foxes. 
 * It also stores the String representation.
 * 
 * @author John Breton
 * @version 1.0
 */
public enum CommandWord {
	HELP("help"), MOVE("move"), START("start"), QUIT("quit"), INVALID("invalid");
	
	// The string representation of command.
	private String command;
	
	/**
	 * Constructors the command as a String.
	 * 
	 * @param command The command as a String. 
	 */
	CommandWord(String command) {
		this.command = command;
	}
	
	@Override
	/**
	 * @return The String representation of the command.
	 */
	public String toString() {
		return command;
	}
}
