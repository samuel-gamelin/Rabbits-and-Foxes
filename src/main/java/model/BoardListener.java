package model;

/**
 * This interface represents the behaviour that a board listener should have.
 * 
 * @author Samuel Gamelin
 * @version 3.0
 */
@FunctionalInterface
public interface BoardListener {
	/**
	 * Called when the Board changes. Updates the necessary views accordingly.
	 */
	void handleBoardChange();
}
