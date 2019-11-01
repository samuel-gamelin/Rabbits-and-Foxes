package model;

import java.util.EventObject;

/**
 * 
 * @author Samuel Gamelin
 * @version 2.0
 */
public class BoardEvent extends EventObject {
	private static final long serialVersionUID = 493863577913961127L;

	public BoardEvent(Board source) {
		super(source);
	}

}
