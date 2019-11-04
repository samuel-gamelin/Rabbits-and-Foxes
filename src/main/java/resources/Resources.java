package resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

import model.Board;

/**
 * This class provides a simple way to access graphical resources used by the
 * game.
 * 
 * @author Samuel Gamelin
 * @version 2.0
 */
public final class Resources {
	/**
	 * Making the constructor private, preventing any instantiation of this class.
	 */
	private Resources() {}	

	/**
	 * A percentage (75%) of the current display's height, which will be used in calculations to determine appropriate scaling of icons.
	 */
	public static final double SIDE_LENGTH = 0.75 * Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	
	// Music
	public static final File MUSIC = loadMusic();
	
	// JFrame icon
	public static final ImageIcon WINDOW_ICON = new ImageIcon(Resources.class.getClassLoader().getResource("window-icon.png"));

	// Rabbit icons
	public static final ImageIcon RABBIT1 = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("rabbit1.png")), 0.6, 0.75);
	public static final ImageIcon RABBIT2 = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("rabbit2.png")), 0.6, 0.75);

	// Fox head icons
	public static final ImageIcon FOX_HEAD_UP = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-up.png")), 0.75, 1);
	public static final ImageIcon FOX_HEAD_DOWN = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-down.png")), 0.75, 1);
	public static final ImageIcon FOX_HEAD_LEFT = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-left.png")), 1, 0.75);
	public static final ImageIcon FOX_HEAD_RIGHT = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-head-right.png")), 1, 0.75);

	// Fox tail icons
	public static final ImageIcon FOX_TAIL_UP = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-up.png")), 0.7, 1);
	public static final ImageIcon FOX_TAIL_DOWN = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-down.png")), 0.75, 1);
	public static final ImageIcon FOX_TAIL_LEFT = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-left.png")), 1, 0.7);
	public static final ImageIcon FOX_TAIL_RIGHT = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("fox-tail-right.png")), 1, 0.7);

	// Mushroom icon
	public static final ImageIcon MUSHROOM = scaleIcon(new ImageIcon(Resources.class.getClassLoader().getResource("mushroom.png")), 0.75, 0.75);

	// Board icon
	public static final ImageIcon BOARD = new ImageIcon(new ImageIcon(Resources.class.getClassLoader().getResource("board.png")).getImage().getScaledInstance((int) SIDE_LENGTH, (int) SIDE_LENGTH, Image.SCALE_SMOOTH));

	/**
	 * Returns a scaled version of the icon based on the primary display's size.
	 * 
	 * @param icon The icon to scale
	 * @param xScale The percentage to scale the icon in the x direction
	 * @param yScale The percentage to scale the icon in the y direction
	 * @return A scaled version of the icon
	 */
	private static ImageIcon scaleIcon(ImageIcon icon, double xScale, double yScale) {
		return new ImageIcon(icon.getImage().getScaledInstance((int) (xScale * SIDE_LENGTH / Board.SIZE), (int) (yScale * SIDE_LENGTH / Board.SIZE), Image.SCALE_SMOOTH));
	}
	
	/**
	 * Find and prepare the music file that will be used in the game.
	 * 
	 * @return The music file to be used in the game.
	 */
	private static File loadMusic() {
		try {
			return new File(Resources.class.getClassLoader().getResource("./music.wav").toURI()); 
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}
}
