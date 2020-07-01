package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;
import model.Board;
import resources.Resources;
import util.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;

/**
 * This class represents the main menu frame of the game.
 *
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 */
@Log4j
public class MainMenu extends JFrame implements ActionListener {

    private final JButton btnStart;
    private final JButton btnSelectLevel;
    private final JButton btnBuildLevel;
    private final JButton btnHelp;
    private final JButton btnLoadGame;
    private final JButton btnQuitGame;

    /**
     * Constructs a MainMenu frame, populating it with options (as buttons) that the user can choose from.
     */
    public MainMenu() {
        this.setTitle("Main Menu");
        this.setContentPane(new JLabel(Resources.MAIN_MENU_BACKGROUND));
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.add(Box.createRigidArea(new Dimension(0, (int) (GUIUtilities.SIDE_LENGTH / 12))), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, (int) (GUIUtilities.SIDE_LENGTH / 16))), BorderLayout.SOUTH);

        this.add(Box.createVerticalGlue());
        addMainMenuButton(btnStart = new JButton("Start"));
        addMainMenuButton(btnSelectLevel = new JButton("Select Level"));
        addMainMenuButton(btnLoadGame = new JButton("Open Saved Game"));
        addMainMenuButton(btnBuildLevel = new JButton("Level Builder"));
        addMainMenuButton(btnHelp = new JButton("Help"));
        addMainMenuButton(btnQuitGame = new JButton("Quit"));

        GUIUtilities.configureFrame(this);
    }

    /**
     * Starts the Rabbits and Foxes game.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        Path path = Paths.get(System.getProperty("user.home") + File.separator + ".Rabbits and Foxes!");
        File customLevelFolder = new File(path.toString());

        if (!customLevelFolder.exists()) {
            customLevelFolder.mkdir();
            try {
                FileOutputStream out = new FileOutputStream(
                        customLevelFolder.getPath() + File.separator + "CustomLevelData.json");
                out.write("{\n  \"userLevels\": [\n  ]\n}".getBytes());
                out.close();
            } catch (IOException ex) {
                log.error("Could not create required CustomLevelData.json file!\nNo user.home directory " +
                        "found (I think you may have bigger problems than playing this game)!", ex);
            }
        }
        // Check to see if the OS is Windows based (in which case some additional work is needed to make the folder
        // hidden).
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            try {
                Files.setAttribute(path, "dos:hidden", true);
            } catch (IOException ex) {
                log.error("Unable to make .Rabbits and Foxes! a hidden folder, will be visible in user.home");
            }
        }

        GUIUtilities.applyDefaults();
        SwingUtilities.invokeLater(MainMenu::new);
    }

    /**
     * Adds a button to the specified pane, registering this frame as an
     * ActionListener.
     *
     * @param button The button to add and register this pane to as an
     *               ActionListener
     */
    private void addMainMenuButton(JButton button) {
        button.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 *
                GUIUtilities.SIDE_LENGTH)));
        button.setPreferredSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 *
                GUIUtilities.SIDE_LENGTH)));
        GUIUtilities.stylizeButton(button, this);
        add(button, BorderLayout.CENTER);
        add(Box.createVerticalGlue());
    }

    /**
     * Handles button input for the menu options.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart) {
            this.dispose();
            SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(1), 1));
        } else if (e.getSource() == btnLoadGame) {
            int returnVal = GUIUtilities.fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Board board;
                ArrayDeque<Move> undoMoveStack;
                ArrayDeque<Move> redoMoveStack;

                try {
                    Gson gson = new Gson();
                    JsonObject jsonObject =
                            Resources.loadJsonObjectFromPath(GUIUtilities.fc.getSelectedFile().getAbsolutePath(), true);
                    if (jsonObject != null) {
                        board = Board.createBoard(jsonObject.get("name").getAsString(),
                                jsonObject.get("board").getAsString());
                        undoMoveStack = gson.fromJson(jsonObject.get("undoMoves").getAsString(),
                                new TypeToken<ArrayDeque<Move>>() {
                                }.getType());
                        redoMoveStack = gson.fromJson(jsonObject.get("redoMoves").getAsString(),
                                new TypeToken<ArrayDeque<Move>>() {
                                }.getType());

                        if (board != null && undoMoveStack != null && redoMoveStack != null) {
                            this.dispose();
                            SwingUtilities.invokeLater(new GameView(board, board.getName().matches("-?\\d+") ?
                                    Integer.parseInt(board.getName()) : -1, undoMoveStack, redoMoveStack));
                        } else {
                            GUIUtilities.displayMessageDialog(this, "Invalid file selection!", "Invalid File");
                        }
                    }
                } catch (Exception ex) {
                    log.error("Unable to load Board object from file at " +
                            GUIUtilities.fc.getSelectedFile().getAbsolutePath(), ex);
                    GUIUtilities.displayMessageDialog(this, "Invalid file selection!", "Invalid File");
                }
            }
        } else if (e.getSource() == btnSelectLevel) {
            this.dispose();
            SwingUtilities.invokeLater(LevelSelector::new);
        } else if (e.getSource() == btnHelp) {
            GUIUtilities.displayMessageDialog(this, "Start: Starts the game\nSelect Level: Opens the level section " +
                    "menu\nOpen Saved Game: Continue from a previously saved " +
                    "game\nLevel Builder: Opens the level builder\nHelp: Displays the" +
                    " help menu\nQuit: Exits the application", "Help");
        } else if (e.getSource() == btnBuildLevel) {
            this.dispose();
            SwingUtilities.invokeLater(LevelBuilder::new);
        } else if (e.getSource() == btnQuitGame) {
            if (GUIUtilities.displayOptionDialog(this, "Are you sure you want to exit?", "Exit Rabbits and Foxes!",
                    new String[]{"Yes", "No"}) ==
                    0) {
                System.exit(0);
            }
        }
    }
}
