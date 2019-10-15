package app;

/**
 * The Command class holds information about any inputed commands. 
 * 
 * A Command is made of 4 parts. The first part is a CommandWord. This is done
 * to keep track of whether a valid CommandWord was specified by the input.
 * 
 * The next 3 parts of a Command are Strings. These will typically be represented
 * by an "X" unless the CommandWord specified by the input was CommandWord.MOVE. 
 * If that was the case, then these 3 Strings will store information that helps
 * process a move. This includes the x and y coordinates where a move will originate 
 * from, and the direction in which the move will take place.
 * 
 * @author John Breton
 * @version 1.0
 */
public class Command {
	// Final variable declaration.
	private static final String EMPTY = "X"; // Used to initialize an instance variable that has not been passed a value.
	
	// Instance variables declarations.
	private CommandWord commandWord; // The CommandWord to be associated with this Command.
	private String xPos; 			 // If the commandWord is move, specifies the x coordinate where the move will originate.
	private String yPos;			 // If the commandWord is move, specifies the y coordinate where the move will originate.
	private String direction;		 // If the commandWord is move, specifies the direction in which the move will take place.
	
	/**
	 * Construct the Command.
	 * This is the default constructor for the Command class.
	 * This constructor is used for the valid commands: start, help, and quit. 
	 * For the move command, please use the secondary constructor.
	 * 
	 * @param commandWord The CommandWord (either start, help, or quit) to be registered to this Command. 
	 */
	public Command(CommandWord commandWord) {
		this.commandWord = commandWord;
		this.xPos = EMPTY;
		this.yPos = EMPTY;
		this.direction = EMPTY;
	}
	
	/**
	 * Construct the Command.
	 * This is the secondary constructor for the Command class.
	 * This constructor should only be called when the CommandWord is "move".
	 * 
	 * @param commandWord The command, for this constructor it should always be "move".
	 * @param xPos The x position where the move will take place.
	 * @param yPos The y position where the move will take place.
	 * @param direction The direction in which the move will proceed.
	 */
	public Command(CommandWord commandWord, String xPos, String yPos, String direction) {
		this.commandWord = commandWord;
		this.xPos = xPos;
		this.yPos = yPos;
		this.direction = direction;
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
	 * Return the x position associated with this Command.
	 * 
	 * @exception NumberFormatException Will be thrown if the xPos String is not strictly the representation of an int.
	 * @return The x position to use for accessing the Board, as an integer.
	 * 		   If no x position was specified when this Command was created or xPos is not properly formated, this will return -1.
	 */
	public int getXPos() {
		try {
			return Integer.parseInt(xPos);
		} catch (Exception NumberFormatException) {
			return -1;
		}
	}
	
	/**
	 * Return the y position associated with this Command.
	 * 
	 * @exception NumberFormatException Will be thrown if the yPos String is not strictly the representation of an int.
	 * @return The y position to use for accessing the Board, as an integer.
	 *         If no y position was specified when this Command was constructed or yPos is not properly formated, this will return -1.
	 */
	public int getYPos() {
		try {
			return Integer.parseInt(yPos);
		} catch (Exception NumberFormatException) {
			return -1;
		}
	}
	
	/**
	 * Return the direction associated with this Command.
	 * 
	 * @return The direction in which to move a Piece, as a String.
	 *         If no direction was specified when this Command was constructed, this will return "X".
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * Check if this Command was constructed with values for xPos, yPos, and direction.
	 * 
	 * @return True if the Command has coordinates and a direction, false otherwise.
	 */
	public boolean hasCoordinatesAndDirection() {
		return !(xPos.equals(EMPTY) || yPos.equals(EMPTY) || direction.contentEquals(EMPTY));
	}
	
	/**
	 * Return whether this Command's commandWord has a valid value.
	 * 
	 * @return True if the commandWord of the Command contains a valid CommandWord, false otherwise.
	 */
	public boolean isValid() {
		return (commandWord == CommandWord.INVALID);
	}
}