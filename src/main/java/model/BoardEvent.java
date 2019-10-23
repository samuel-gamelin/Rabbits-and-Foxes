package model;

import java.util.EventObject;

public class BoardEvent extends EventObject {
	private static final long serialVersionUID = 493863577913961127L;

	public BoardEvent(Board source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

}
