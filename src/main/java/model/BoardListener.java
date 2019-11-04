package model;

/**
 * This interface represents the behaviour that a board listener should have.
 * 
 * @author Samuel Gamelin
 * @version 2.0
 */
public interface BoardListener {
	/**
	 * Called when the Board changes. Updates the necessary views accordingly.
	 */
	void handleBoardChange();
}
