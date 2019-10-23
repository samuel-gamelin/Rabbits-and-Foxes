package model;

public interface BoardListener {
	/**
	 * Handles the provided event and updates the necessary views accordingly.
	 * 
	 * @param e The event to handle
	 */
	void handleBoardEvent(BoardEvent e);
}
