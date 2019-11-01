package resources;

import javax.swing.ImageIcon;

/**
 * This class provides a simple way to access graphical resources used by the
 * game.
 * 
 * @author Samuel Gamelin
 * @version 2.0
 */
public final class Resources {
	/**
	 * 
	 */
	private Resources() {
	}

	// Rabbit icons
	public static final ImageIcon RABBIT1 = new ImageIcon(Resources.class.getClassLoader().getResource("rabbit1.png"));
	public static final ImageIcon RABBIT2 = new ImageIcon(Resources.class.getClassLoader().getResource("rabbit2.png"));

	// Fox head icons
	public static final ImageIcon FOX_HEAD_VERTICAL = new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-vertical.png"));
	public static final ImageIcon FOX_HEAD_HORIZONTAL = new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-horizontal.png"));

	// Fox tail icons
	public static final ImageIcon FOX_TAIL_VERTICAL = new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-vertical.png"));
	public static final ImageIcon FOX_TAIL_HORIZONTAL = new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-horizontal.png"));

	// Mushroom icon
	public static final ImageIcon MUSHROOM = new ImageIcon(Resources.class.getClassLoader().getResource("mushroom.png"));

	// Board icon
	public static final ImageIcon BOARD = new ImageIcon(Resources.class.getClassLoader().getResource("board.png"));
}
