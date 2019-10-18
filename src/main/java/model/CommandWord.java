package model;

/**
 * The CommandWord enumeration is used to store all of the defined commands for
 * the Game. It also stores their corresponding String representation.
 * 
 * Of note, all String representations are strictly lowercase.
 * 
 * @author John Breton
 * @version 1.0
 */
public enum CommandWord {
	HELP("help"), MOVE("move"), RESET("reset"), START("start"), QUIT("quit"), INVALID("invalid");

	// Instance variable declaration.
	private String command; // The string representation of the CommandWord.

	/**
	 * Construct the passed command String.
	 * 
	 * @param command The command as a String.
	 */
	CommandWord(String command) {
		this.command = command;
	}

	@Override
	/**
	 * Override the toString method inherited from Object. This method will return
	 * the string representation of a defined CommandWord.
	 * 
	 * @return The String representation of the CommandWord.
	 */
	public String toString() {
		return command;
	}
}