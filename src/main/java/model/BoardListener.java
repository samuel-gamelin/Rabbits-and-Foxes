package model;
/**
 * 
 * @author 
 * @version 2.0
 */
public interface BoardListener {
	/**
	 * Handles the provided event and updates the necessary views accordingly.
	 * 
	 * @param e The event to handle
	 */
	void handleBoardEvent(BoardEvent e);
}
