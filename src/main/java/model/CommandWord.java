package model;

/**
 * The CommandWord enumeration is used to store all of the defined commands for
 * the Game. It also stores their corresponding String representation, along with
 * information about their use in Game.
 * 
 * Of note, all String representations are strictly lowercase.
 * 
 * @author John Breton
 * @version 1.0
 */
public enum CommandWord {
	HELP("help", "  help: Displays this menu."), 
	MOVE("move", "  move x1y1 x2y2: Moves a piece from x1y1 to x2y2, if the move is valid.\n\n"
			   + "                  Rabbits (Denoted by \"RB\") can move by jumping over\n"
			   + "                  occupied tiles. Rabbits can only move in a straight line.\n\n"
			   + "                  Foxes (Denoted by \"FH\" and \"FT\") can move by sliding\n"
			   + "                  to the left and right if they are horizontal, or up and\n"
			   + "                  down if they are vertical. They cannot slide through or\n"
			   + "                  past occupied tiles. They can be jumped over by rabbits.\n\n"
			   + "                  Mushrooms (Denoted by \"MU\") cannot move. However, they\n"
			   + "                  can be jumped over by rabbits.\n\n"
			   + "                  An example move: \"move 12 14\", which moves a piece from\n"
			   + "                  column 1 row 2, to column 1 row 4, if the move is valid."),
	RESET("reset", "  reset: Resets the game. Can only be used after the game has started."), 
	START("start", "  start: Starts the game. Can only be used before the game has started."),
	QUIT("quit", "  quit: Quits the game."), 
	INVALID("invalid", "invalid");

	// Instance variable declaration.
	private String command;  // The string representation of the CommandWord.
	private String helpInfo; // The string representation of what this CommandWord does in Game.

	/**
	 * Construct the CommandWord based on the passed Strings.
	 * 
	 * @param command The CommandWord as a String.
	 * @param helpInfo The help information for a CommandWord, as a String.
	 */
	CommandWord(String command, String helpInfo) {
		this.command = command;
		this.helpInfo = helpInfo;
	}
	
	/**
	 * Return the helpful information about how to use the CommandWord.
	 * 
	 * @return The String representation of the help information for the CommandWord.
	 */
	public String getHelpInfo() {
		return helpInfo;
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