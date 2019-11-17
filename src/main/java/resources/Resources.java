package resources;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Board;

/**
 * This class provides a simple way to access audio and graphical resources used
 * by the game.
 * 
 * @author Samuel Gamelin
 * @author John Breton
 * @version 3.0
 */
public final class Resources {
	/**
	 * The total number of levels available.
	 */
	public static final int NUMBER_OF_LEVELS = getNumberOfLevels();

	/**
	 * A percentage (75%) of the current display's height (or width, depending on
	 * which is greater), which will be used in calculations to determine
	 * appropriate scaling of icons and GUI elements.
	 */
	public static final double SIDE_LENGTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() > Toolkit
			.getDefaultToolkit().getScreenSize().getHeight()
					? 0.75 * Toolkit.getDefaultToolkit().getScreenSize().getHeight()
					: 0.75 * Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	// Incorrect move sound
	public static final Clip INVALID_MOVE = loadClip(getFileURL("sounds/wrong.wav"));

	// Level solved sound
	public static final Clip SOLVED = loadClip(getFileURL("sounds/solved.wav"));

	// JFrame icon
	public static final ImageIcon WINDOW_ICON = loadIcon("images/rabbit3.png", 4, 5);

	// Rabbit icons
	public static final ImageIcon RABBIT1 = loadIcon("images/rabbit1.png", 0.6, 0.75);
	public static final ImageIcon RABBIT2 = loadIcon("images/rabbit2.png", 0.6, 0.75);
	public static final ImageIcon RABBIT3 = loadIcon("images/rabbit3.png", 0.6, 0.75);

	// Fox head icons
	public static final ImageIcon FOX_HEAD_UP = loadIcon("images/fox-head-up.png", 0.75, 1);
	public static final ImageIcon FOX_HEAD_DOWN = loadIcon("images/fox-head-down.png", 0.75, 1);
	public static final ImageIcon FOX_HEAD_LEFT = loadIcon("images/fox-head-left.png", 1, 0.75);
	public static final ImageIcon FOX_HEAD_RIGHT = loadIcon("images/fox-head-right.png", 1, 0.75);

	// Fox tail icons
	public static final ImageIcon FOX_TAIL_UP = loadIcon("images/fox-tail-up.png", 0.7, 1);
	public static final ImageIcon FOX_TAIL_DOWN = loadIcon("images/fox-tail-down.png", 0.75, 1);
	public static final ImageIcon FOX_TAIL_LEFT = loadIcon("images/fox-tail-left.png", 1, 0.7);
	public static final ImageIcon FOX_TAIL_RIGHT = loadIcon("images/fox-tail-right.png", 1.03, 0.7);

	// Mushroom icon
	public static final ImageIcon MUSHROOM = loadIcon("images/mushroom.png", 0.75, 0.75);

	// Main menu icon
	public static final ImageIcon MAIN_MENU_BACKGROUND = loadIcon("images/mainmenu.png", 5, 5);

	// Board icon
	public static final ImageIcon BOARD = loadIcon("images/board.png", 5, 5);

	/**
	 * Making the constructor private, preventing any instantiation of this class.
	 */
	private Resources() {
	}

	/**
	 * Returns a scaled version of the icon based on the primary display's size. A
	 * scale value of 1 represents 1/5 of the width/height of the viewing area.
	 * 
	 * @param icon   The icon to scale
	 * @param xScale The percentage to scale the icon in the x direction
	 * @param yScale The percentage to scale the icon in the y direction
	 * @return A scaled version of the icon
	 */
	private static ImageIcon loadIcon(String path, double xScale, double yScale) {
		return new ImageIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(path)).getImage()
				.getScaledInstance((int) (xScale * SIDE_LENGTH / Board.SIZE), (int) (yScale * SIDE_LENGTH / Board.SIZE),
						Image.SCALE_SMOOTH));
	}

	/**
	 * Load and return the requested file. Typically used to load in music and sound
	 * files.
	 * 
	 * @param path The path at which the resource is located
	 * @return The file at the specified location
	 */
	private static URL getFileURL(String path) {
		return Thread.currentThread().getContextClassLoader().getResource(path);
	}

	/**
	 * Loads and returns an audio clip used for sound playback.
	 * 
	 * @param path The path of the audio resource
	 * @return The audio clip at the specified path. Null if no such path exists.
	 */
	private static Clip loadClip(URL path) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(path));
			clip.addLineListener(e -> {
				if (e.getType() == LineEvent.Type.STOP) {
					clip.stop();
					clip.flush();
					clip.setFramePosition(0);
				}
			});
			return clip;
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace(System.out);
			return null;
		}
	}

	/**
	 * Provides the number of levels available in the LevelData.json file.
	 * 
	 * @return The total number of levels in the game. Returns -1 if no valid
	 *         LevelData.json file is found.
	 */
	private static int getNumberOfLevels() {
		try (InputStreamReader inputStreamReader = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("levels/LevelData.json"))) {
			return ((JSONArray) new JSONParser().parse(inputStreamReader)).size();

		} catch (IOException | ParseException e) {
			e.printStackTrace(System.out);
			return -1;
		}
	}

	/**
	 * Load and return a new Board based on the current level. The levels are loaded
	 * from a JSONArray.
	 * 
	 * @param level The level to load.
	 * @return The Board associated with the passed-in level. Null if the level does
	 *         not exist.
	 */
	public static Board getLevel(int level) {
		try (InputStreamReader inputStreamReader = new InputStreamReader(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("levels/LevelData.json"))) {
			return Board.createBoard(
					(String) ((JSONObject) (((JSONArray) new JSONParser().parse(inputStreamReader)).get(level - 1)))
							.get("Level " + level));
		} catch (IOException | ParseException e) {
			e.printStackTrace(System.out);
			return null;
		}
	}
}
