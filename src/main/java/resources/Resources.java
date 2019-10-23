package resources;

import javax.swing.ImageIcon;

public final class Resources {
	private Resources() {}
	
	// Rabbit icons
	public static final ImageIcon RABBIT_RIGHT = new ImageIcon(Resources.class.getClassLoader().getResource("rabbit.png"));
	
	// Fox icons
	public static final ImageIcon FOX_HEAD = new ImageIcon(Resources.class.getClassLoader().getResource("fox-head.png"));
	public static final ImageIcon FOX_TAIL = new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail.png"));
	
	// Mushroom icon
	public static final ImageIcon MUSHROOM = new ImageIcon(Resources.class.getClassLoader().getResource("mushroom.png"));
}
