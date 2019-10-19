package model;

/**
 * The Command class holds information about any inputed commands.
 * 
 * A Command is made of 3 parts. The first part is a CommandWord. This is done
 * to keep track of whether a valid CommandWord was specified by the input.
 * 
 * The next 2 parts of a Command are Strings. These will typically be
 * represented by an "XX" unless the CommandWord specified by the input was
 * CommandWord.MOVE. If that was the case, then these 2 Strings will store
 * information that helps process a move. This includes the position where a
 * move will originate from, and the position where a move will end.
 * 
 * @author John Breton
 * @version 1.0
 */
public class Command {
	// Final variable declaration.
	private static final String EMPTY = "XX"; // Used to initialize an instance variable that has not been passed a value.

	// Instance variables declarations.
	private CommandWord commandWord; // The CommandWord to be associated with this Command.
	private String startPos;         // Specifies the start position of a move.
	private String endPos;           // Specifies the end position of a move.

	/**
	 * Construct the Command. This is the default constructor for the Command class.
	 * This constructor is used for the valid commands: help, reset, start, and
	 * quit. For the move command, please use the secondary constructor.
	 * 
	 * @param commandWord The CommandWord (either start, help, or quit) to be registered to this Command.
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
	 * @param commandWord The command, for this constructor it should always be "move".
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
	 *         start position was specified when this Command was created, this will return "XX".
	 */
	public String getStartPos() {
		return startPos;
	}

	/**
	 * Return the end position associated with this Command.
	 * 
	 * @return The end position to use for accessing the Board, as a String. If no
	 *         end position was specified when this Command was created, this will return "XX".
	 */
	public String getEndPos() {
		return endPos;
	}

	/**
	 * Check if this Command was constructed with values for startPos and endPos.
	 * 
	 * @return True if the Command had startPos and endPos specified in its
	 *         constructor, false otherwise.
	 */
	public boolean hasCoordinates() {
		return !(startPos.equals(EMPTY) || endPos.equals(EMPTY));
	}

	/**
	 * Return whether this Command's commandWord has a valid value.
	 * 
	 * @return True if the commandWord of the Command contains a valid CommandWord,
	 *         false otherwise.
	 */
	public boolean isValidCommandWord() {
		return commandWord == CommandWord.INVALID;
	}
}