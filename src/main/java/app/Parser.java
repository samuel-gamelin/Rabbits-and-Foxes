/**
 * 
 */
package app;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Parser class is responsible for parsing user input. This input is used to
 * determine
 * 
 * @author John Breton
 * @version 1.0
 */
public class Parser {
	// Instance variable
	Scanner scanner;

	/**
	 * Constructor for the Parser class. The parser is a Scanner that scans for user
	 * input.
	 */
	public Parser() {
		scanner = new Scanner(System.in);
	}

	/**
	 * 
	 * @return An array containing the strings of the command (Minimum of 1 String,
	 *         maximum of 3).
	 */
	public ArrayList<String> readCommand() {
		ArrayList<String> commands = new ArrayList<String>();
		while (scanner.hasNext()) {
			commands.add(scanner.next());
		}
		return commands;
	}

}
