package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import model.Board;
import resources.Resources;
import util.Move;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;

/**
 * This class represents the main menu frame of the game.
 *
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 * @version 4.0
 */

public class MainMenu extends JFrame implements ActionListener {
    private JButton btnStart, btnSelectLevel, btnBuildLevel, btnHelp, btnLoadGameButton, btnQuitGame;

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
        addMainMenuButton(btnLoadGameButton = new JButton("Open Saved Game"));
        addMainMenuButton(btnBuildLevel = new JButton("Level Builder"));
        addMainMenuButton(btnHelp = new JButton("Help"));
        addMainMenuButton(btnQuitGame = new JButton("Quit"));

        GUIUtilities.configureFrame(this);
    }

    /**
     * Adds a button to the specified pane, registering this frame as an
     * ActionListener.
     *
     * @param button The button to add and register this pane to as an
     *               ActionListener
     */
    private void addMainMenuButton(JButton button) {
        button.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 * GUIUtilities.SIDE_LENGTH)));
        button.setPreferredSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 * GUIUtilities.SIDE_LENGTH)));
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
        } else if (e.getSource() == btnLoadGameButton) {
            int returnVal = GUIUtilities.fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Board board;
                ArrayDeque<Move> undoMoveStack;
                ArrayDeque<Move> redoMoveStack;

                try {
                    Gson gson = new Gson();
                    JsonObject jsonObject = Resources.loadJsonObjectFromPath(GUIUtilities.fc.getSelectedFile().getAbsolutePath(), true);
                    if (jsonObject != null) {
                        board = Board.createBoard(jsonObject.get("name").getAsString(), jsonObject.get("board").getAsString());
                        undoMoveStack = gson.fromJson(jsonObject.get("undoMoves").getAsString(), new TypeToken<ArrayDeque<Move>>() {
                        }.getType());
                        redoMoveStack = gson.fromJson(jsonObject.get("redoMoves").getAsString(), new TypeToken<ArrayDeque<Move>>() {
                        }.getType());

                        if (board != null && undoMoveStack != null && redoMoveStack != null) {
                            this.dispose();
                            SwingUtilities.invokeLater(new GameView(board, board.getName().matches("-?\\d+") ? Integer.parseInt(board.getName()) : -1, undoMoveStack, redoMoveStack));
                        } else {
                            GUIUtilities.displayMessageDialog(this, "Invalid file selection!", "Invalid File");
                        }
                    }
                } catch (Exception ex) {
                    Resources.LOGGER.error("Unable to load Board object from file at " + GUIUtilities.fc.getSelectedFile().getAbsolutePath(), ex);
                    GUIUtilities.displayMessageDialog(this, "Invalid file selection!", "Invalid File");
                }
            }
        } else if (e.getSource() == btnSelectLevel) {
            this.dispose();
            SwingUtilities.invokeLater(LevelSelector::new);
        } else if (e.getSource() == btnHelp) {
            GUIUtilities.displayMessageDialog(this,
                    "Start: Starts the game\nSelect Level: Opens the level section menu\nOpen Saved Game: Continue from a previously saved game\nLevel Builder: Opens the level builder\nHelp: Displays the help menu\nQuit: Exits the application",
                    "Help");
        } else if (e.getSource() == btnBuildLevel) {
            this.dispose();
            SwingUtilities.invokeLater(LevelBuilder::new);
        } else if (e.getSource() == btnQuitGame) {
            if (GUIUtilities.displayOptionDialog(this, "Are you sure you want to exit?", "Exit Rabbits and Foxes!",
                    new String[]{"Yes", "No"}) == 0) {
                System.exit(0);
            }
        }
    }

    /**
     * Starts the Rabbits and Foxes game.
     *
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        GUIUtilities.applyDefaults();
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
