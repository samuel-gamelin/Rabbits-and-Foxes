package model;

/**
 * The Command class holds information about any inputed commands.
 * 
 * A Command is made of 3 parts. The first part is a CommandWord.
 * 
 * The next 2 parts of a Command are Strings. These will typically be
 * represented by an "X" unless the CommandWord specified by the input was
 * CommandWord.MOVE. If that was the case, then these 2 Strings will be
 * initialized with Strings passed by the user for startPos and endPos.
 * 
 * @author John Breton
 * @version 1.0
 */
public class Command {
	// Final variable declaration.
	private static final String EMPTY = "X"; // Used to initialize an instance variable that has not been passed a value.

	// Instance variables declarations.
	private CommandWord commandWord;         // The CommandWord to be associated with this Command.
	private String startPos;                 // Specifies the start position of a move.
	private String endPos;                   // Specifies the end position of a move.

	/**
	 * Construct the Command. This is the default constructor for the Command class.
	 * This constructor is used for the valid commands: help, reset, start, and
	 * quit. For the move command, please use the secondary constructor.
	 * 
	 * @param commandWord The CommandWord to be registered to this Command.
	 */
	public Command(CommandWord commandWord) {
		this.commandWord = commandWord;
		this.startPos = EMPTY;
		this.endPos = EMPTY;
	}

	/**
	 * Construct the Command. This is the secondary constructor for the Command
	 * class. This constructor should only be called when the CommandWord is "move".
	 * 
	 * @param commandWord The CommandWord, for this constructor it should always be "move".
	 * @param startPos    The position where the move will originate from.
	 * @param endPos      The position where the move will conclude.
	 */
	public Command(CommandWord commandWord, String startPos, String endPos) {
		this.commandWord = commandWord;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	/**
	 * Return the CommandWord associated with this Command.
	 * 
	 * @return The CommandWord for this Command.
	 */
	public CommandWord getCommandWord() {
		return commandWord;
	}

	/**
	 * Return the start position associated with this Command.
	 * 
	 * @return The start position to use for accessing the Board, as a String. If no
	 *         start position was specified when this Command was created, this will
	 *         return "X".
	 */
	public String getStartPos() {
		return startPos;
	}

	/**
	 * Return the end position associated with this Command.
	 * 
	 * @return The end position to use for accessing the Board, as a String. If no
	 *         end position was specified when this Command was created, this will
	 *         return "X".
	 */
	public String getEndPos() {
		return endPos;
	}
}