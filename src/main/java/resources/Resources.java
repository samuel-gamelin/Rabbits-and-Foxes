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

	// Fox icons
	public static final ImageIcon FOX_HEAD_UP = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-head-up.png"));
	public static final ImageIcon FOX_HEAD_DOWN = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-head-down.png"));
	public static final ImageIcon FOX_HEAD_LEFT = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-head-left.png"));
	public static final ImageIcon FOX_HEAD_RIGHT = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-head-right.png"));

	public static final ImageIcon FOX_TAIL_UP = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-tail-up.png"));
	public static final ImageIcon FOX_TAIL_DOWN = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-tail-down.png"));
	public static final ImageIcon FOX_TAIL_LEFT = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-tail-left.png"));
	public static final ImageIcon FOX_TAIL_RIGHT = new ImageIcon(
			Resources.class.getClassLoader().getResource("fox-tail-right.png"));

	// Mushroom icon
	public static final ImageIcon MUSHROOM = new ImageIcon(
			Resources.class.getClassLoader().getResource("mushroom.png"));

	// Board icon
	public static final ImageIcon BOARD = new ImageIcon(Resources.class.getClassLoader().getResource("board.png"));
}