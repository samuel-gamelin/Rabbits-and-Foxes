package resources;

import com.google.gson.*;
import lombok.extern.log4j.Log4j;
import model.Board;
import ui.GUIUtilities;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class provides a simple way to access audio and graphical resources
 * along with logging facilities used by the game.
 *
 * @author Samuel Gamelin
 * @author John Breton
 * @version 4.0
 */
@Log4j
public final class Resources {

    /**
     * A clip used for an invalid move sound.
     */
    public static final Clip INVALID_MOVE = loadClip(getFileURL("sounds/wrong.wav"));

    /**
     * A clip used for a "level completed" or "solved" sound.
     */
    public static final Clip SOLVED = loadClip(getFileURL("sounds/solved.wav"));

    /**
     * An icon used as the frame's window icon.
     */
    public static final ImageIcon WINDOW_ICON = loadIcon("images/rabbit-white.png", 4, 5);

    /**
     * An icon used to represent a brown rabbit.
     */
    public static final ImageIcon RABBIT_BROWN = loadIcon("images/rabbit-brown.png", 0.6, 0.75);

    /**
     * An icon used to represent a white rabbit.
     */
    public static final ImageIcon RABBIT_WHITE = loadIcon("images/rabbit-white.png", 0.6, 0.75);

    /**
     * An icon used to represent a gray rabbit.
     */
    public static final ImageIcon RABBIT_GRAY = loadIcon("images/rabbit-gray.png", 0.6, 0.75);

    /**
     * An icon used to represent a fox head facing up.
     */
    public static final ImageIcon FOX_HEAD_UP = loadIcon("images/fox-head-up.png", 0.75, 1);

    /**
     * An icon used to represent a fox head facing down.
     */
    public static final ImageIcon FOX_HEAD_DOWN = loadIcon("images/fox-head-down.png", 0.75, 1);

    /**
     * An icon used to represent a fox head facing left.
     */
    public static final ImageIcon FOX_HEAD_LEFT = loadIcon("images/fox-head-left.png", 1, 0.75);

    /**
     * An icon used to represent a fox head facing right.
     */
    public static final ImageIcon FOX_HEAD_RIGHT = loadIcon("images/fox-head-right.png", 1, 0.75);

    /**
     * An icon used to represent a fox tail facing up.
     */
    public static final ImageIcon FOX_TAIL_UP = loadIcon("images/fox-tail-up.png", 0.7, 1);

    /**
     * An icon used to represent a fox tail facing down.
     */
    public static final ImageIcon FOX_TAIL_DOWN = loadIcon("images/fox-tail-down.png", 0.75, 1);

    /**
     * An icon used to represent a fox tail facing left.
     */
    public static final ImageIcon FOX_TAIL_LEFT = loadIcon("images/fox-tail-left.png", 1, 0.7);

    /**
     * An icon used to represent a fox tail facing right.
     */
    public static final ImageIcon FOX_TAIL_RIGHT = loadIcon("images/fox-tail-right.png", 1.03, 0.7);

    /**
     * An icon used to represent a mushroom.
     */
    public static final ImageIcon MUSHROOM = loadIcon("images/mushroom.png", 0.75, 0.75);

    /**
     * An icon used to represent the main menu background.
     */
    public static final ImageIcon MAIN_MENU_BACKGROUND = loadIcon("images/mainmenu.png", 5, 5);

    /**
     * An icon used to represent the level selector background.
     */
    public static final ImageIcon LEVEL_SELECTOR_BACKGROUND = loadIcon("images/levelselectorbackground.png", 5, 5);

    /**
     * An icon used to represent the board.
     */
    public static final ImageIcon BOARD = loadIcon("images/board.png", 5, 5);

    /**
     * An icon used for the side panel in the level builder.
     */
    public static final ImageIcon SIDE_PANEL = loadIcon("images/sidepanel.png", 2, 5);

    /**
     * A String constant representing the key value for user levels in the
     * LevelData.json file.
     */
    private static final String USER_LEVELS = "userLevels";

    /**
     * A String constant representing the key value for default levels in the
     * LevelData.json file.
     */
    private static final String DEFAULT_LEVELS = "defaultLevels";

    /**
     * A constant representing the relative path of the LevelData.json file.
     */
    private static final String LEVEL_DATA_PATH = "levels/LevelData.json";

    /**
     * The total number of levels available.
     */
    public static final int NUMBER_OF_LEVELS = getNumberOfLevels();

    /**
     * A constant representing the path of the customLevelData.json file.
     */
    private static final String CUSTOM_LEVEL_DATA_PATH =
            System.getProperty("user.home") + File.separator + ".Rabbits and Foxes!" + File.separator +
                    "CustomLevelData.json";

    /**
     * Making the constructor private, preventing any instantiation of this class.
     */
    private Resources() {
    }

    /**
     * Returns a scaled version of the icon based on the primary display's size. A
     * scale value of 1 represents 1/5 of the width/height of the viewing area.
     *
     * @param path   The path to the icon
     * @param xScale The percentage to scale the icon in the x direction
     * @param yScale The percentage to scale the icon in the y direction
     * @return A scaled version of the icon
     */
    private static ImageIcon loadIcon(String path, double xScale, double yScale) {
        return new ImageIcon(new ImageIcon(getFileURL(path)).getImage().getScaledInstance((int) (
                xScale * GUIUtilities.SIDE_LENGTH / Board.SIZE), (int) (yScale * GUIUtilities.SIDE_LENGTH /
                Board.SIZE), Image.SCALE_SMOOTH));
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
            log.error("Could not load audio resource at " + path, e);
        }
        return null;
    }

    /**
     * Provides the number of default levels available in the LevelData.json file.
     *
     * @return The total number of levels in the game. Returns -1 if no valid
     * LevelData.json file is found.
     */
    private static int getNumberOfLevels() {
        try {
            return Objects.requireNonNull(loadJsonObjectFromPath(LEVEL_DATA_PATH, false)).get(DEFAULT_LEVELS).getAsJsonArray().size();
        } catch (Exception e) {
            log.error("Unable to obtain total number of levels from LevelData.json file", e);
            return -1;
        }
    }

    /**
     * Retrieves an ImageIcon based on its field name, as declared in this class.
     *
     * @param fieldName The field name associated with the desired ImageIcon
     * @return The ImageIcon associated with the provided field name. Null if no
     * such field exists.
     */
    public static ImageIcon getImageIconByName(String fieldName) {
        try {
            return (ImageIcon) Resources.class.getDeclaredField(fieldName).get(Resources.class);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Could not obtain the required field from the Resources class", e);
        }
        return null;
    }

    /**
     * Loads a JsonObject from the specified path.
     *
     * @param path           The path of the JSON file
     * @param isAbsolutePath True if the provided path is absolute, false if it is
     *                       relative (to the root of the classpath)
     * @return The loaded JsonObject, or null if that path or file contents are
     * invalid
     */
    public static JsonObject loadJsonObjectFromPath(String path, boolean isAbsolutePath) {
        if (!isAbsolutePath) {
            try (InputStreamReader inputStreamReader =
                         new InputStreamReader(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(path)), Charset.defaultCharset())) {
                JsonObject temp = JsonParser.parseReader(inputStreamReader).getAsJsonObject();
                inputStreamReader.close();
                return temp;
            } catch (IOException e) {
                log.error("Could not load the file at " + path, e);
            }
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path),
                    Charset.defaultCharset()))) {
                return JsonParser.parseReader(bufferedReader).getAsJsonObject();
            } catch (IOException e) {
                log.error("Could not load the file at " + path, e);
            }
        }
        return null;
    }

    /**
     * Load and return a new default Board based on the supplied level. The levels
     * are loaded from a JSON file.
     *
     * @param level The level to load.
     * @return The Board associated with the passed-in level. Null if the level does
     * not exist or the LevelData.json file is not found.
     */
    public static Board getDefaultBoardByLevel(int level) {
        try {
            for (JsonElement element :
                    Objects.requireNonNull(loadJsonObjectFromPath(LEVEL_DATA_PATH, false)).get(DEFAULT_LEVELS).getAsJsonArray()) {
                if (element.getAsJsonObject().get("name").getAsInt() == level) {
                    return Board.createBoard(String.valueOf(level),
                            element.getAsJsonObject().get("board").getAsString());
                }
            }
        } catch (Exception e) {
            log.error("Unable to obtain level from LevelData.json file for level " + level);
        }
        return null;
    }

    /**
     * Returns a list of all default boards as specified by the LevelData.json file.
     *
     * @return A list of all default boards, or an empty list if any problems are
     * encountered
     */
    public static List<Board> getAllDefaultBoards() {
        List<Board> boardList = new ArrayList<>();

        try {
            Objects.requireNonNull(loadJsonObjectFromPath(LEVEL_DATA_PATH, false)).get(DEFAULT_LEVELS).getAsJsonArray().forEach(element -> boardList.add(Board.createBoard(element.getAsJsonObject().get("name").getAsString(), element.getAsJsonObject().get("board").getAsString())));
        } catch (Exception e) {
            log.error("Unable to obtain all default levels from the LevelData.json file");
            return new ArrayList<>();
        }

        return boardList;
    }

    /**
     * Returns a list of all user boards as specified by the LevelData.json file.
     *
     * @return A list of all user boards
     */
    public static List<Board> getAllUserBoards() {
        List<Board> boardList = new ArrayList<>();

        try {
            Objects.requireNonNull(loadJsonObjectFromPath(CUSTOM_LEVEL_DATA_PATH, true)).get(USER_LEVELS).getAsJsonArray().forEach(element -> boardList.add(Board.createBoard(element.getAsJsonObject().get("name").getAsString(), element.getAsJsonObject().get("board").getAsString())));
        } catch (Exception e) {
            log.error("Unable to obtain all user levels from the customLevelData.json file");
            return new ArrayList<>();
        }

        return boardList;
    }

    /**
     * Adds a user-defined level to the LevelData.json file based on the provided
     * Board, if the level with that name does not already exist.
     *
     * @param board The Board to add to the LevelData.json file
     * @return True if the user-defined level was saved, false otherwise
     */
    public static boolean addUserLevel(Board board) {
        JsonObject originalJsonObject = loadJsonObjectFromPath(CUSTOM_LEVEL_DATA_PATH, true);

        if (originalJsonObject != null) {
            for (JsonElement object : originalJsonObject.get(USER_LEVELS).getAsJsonArray()) {
                if (object.getAsJsonObject().get("name").getAsString().equals(board.getName())) {
                    return false;
                }
            }
        }
        try {
            FileOutputStream out = new FileOutputStream(CUSTOM_LEVEL_DATA_PATH);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", board.getName());
            jsonObject.addProperty("board", board.toString());

            if (originalJsonObject != null) {
                originalJsonObject.get(USER_LEVELS).getAsJsonArray().add(jsonObject);
            }

            out.write(gson.toJson(originalJsonObject).getBytes());
            out.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Unable to save user-defined level to the customLevelData.json file", e);
            return false;
        }
    }

    /**
     * Removes a user-defined level from the LevelData.json file based on the
     * provided level name, if the level with that name exists.
     *
     * @param name The name of the level to remove from the LevelData.json file
     */
    public static void removeUserLevel(String name) {
        JsonObject originalJsonObject = loadJsonObjectFromPath(CUSTOM_LEVEL_DATA_PATH, true);

        if (originalJsonObject != null) {
            for (JsonElement object : originalJsonObject.get(USER_LEVELS).getAsJsonArray()) {
                if (object.getAsJsonObject().get("name").getAsString().equals(name)) {
                    try {
                        FileOutputStream out = new FileOutputStream(CUSTOM_LEVEL_DATA_PATH);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();

                        originalJsonObject.get(USER_LEVELS).getAsJsonArray().remove(object);

                        out.write(gson.toJson(originalJsonObject).getBytes());
                        out.close();
                        return;
                    } catch (Exception e) {
                        log.error("Unable to save user-defined level to the customLevelData.json file", e);
                        return;
                    }
                }
            }
        }
    }
}
