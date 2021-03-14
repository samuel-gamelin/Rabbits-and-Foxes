package ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import controller.GameController;
import controller.GameController.ClickValidity;
import lombok.extern.log4j.Log4j;
import model.Board;
import model.BoardListener;
import util.Move;
import util.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayDeque;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 *
 * @author John Breton
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author Abdalla El Nakla
 * @author Mohamed Radwan
 */
@Log4j
public class GameView extends JFrame implements ActionListener, BoardListener, MouseListener, Runnable {

    private final JButton menuReset, menuHelp, menuHint, menuUndo, menuRedo, menuMain, menuSaveButton, menuQuit;

    private final JButton[][] buttons;

    /**
     * The controller associated with this view.
     */
    private final GameController gameController;

    private JCheckBox showPossibleMovesBox;

    /**
     * The Board that this view listens to.
     */
    private Board board;

    /**
     * Represents the state of the game. True if the game is running, false
     * otherwise.
     */
    private boolean gameState;

    /**
     * Stores the next best move for the current board. Determined as soon as
     * the board is loaded.
     */
    private Move bestMove;

    /**
     * Creates the application GUI.
     *
     * @param board The board that this GameView should have
     * @param level The current level of the game. Only applicable to default
     *              levels. For user levels, a negative value must be provided.
     */
    public GameView(Board board, int level) {
        this.board = board;
        this.board.addListener(this);
        this.gameController = new GameController(board, level);
        this.gameState = true;

        updateFrameTitle();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(menuMain = GUIUtilities.createMenuBarButton("<html><u>M</u>ain Menu</html>", true));
        menuBar.add(menuHint = GUIUtilities.createMenuBarButton("<html><u>H</u>int</html>", true));
        menuBar.add(menuUndo = GUIUtilities.createMenuBarButton("<html><u>U</u>ndo</html>", true));
        menuBar.add(menuRedo = GUIUtilities.createMenuBarButton("<html><u>R</u>edo</html>", true));
        menuBar.add(menuReset = GUIUtilities.createMenuBarButton("Reset", false));
        menuBar.add(menuSaveButton = GUIUtilities.createMenuBarButton("<html><u>S</u>ave Game</html>", true));
        menuBar.add(menuHelp = GUIUtilities.createMenuBarButton("Help", false));
        menuBar.add(menuQuit = GUIUtilities.createMenuBarButton("<html><u>Q</u>uit</html>", true));

        // Generate the hints as soon as possible to minimize waiting time.
        Thread hintThread = new Thread(this, "Hint");
        hintThread.start();

        setContentPane(new JLabel(Resources.BOARD));
        getContentPane().setLayout(new GridLayout(5, 5));

        // Create all buttons
        buttons = new JButton[5][5];

        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                buttons[x][y] = GUIUtilities.generateGameBoardButton(x, y);

                buttons[x][y].addMouseListener(this);
                add(buttons[x][y]);

                final int xCopy = x;
                final int yCopy = y;

                // Register an anonymous listener on the button which notifies the controller
                // whenever a move is made (i.e. a button is clicked)
                buttons[x][y].addActionListener(e -> {
                    ClickValidity clickResult = gameController.registerClick(xCopy, yCopy);

                    // Highlights all possible moves for the selected piece.
                    if (showPossibleMovesBox.isSelected()) {
                        gameController.getPossibleMoves(xCopy, yCopy).parallelStream().forEach(move -> {
                            buttons[move.xStart][move.yStart].setBorder(GUIUtilities.HINT_BORDER_START);
                            buttons[move.xEnd][move.yEnd].setBorder(GUIUtilities.POSSIBLE_POSITION_BORDER);
                        });
                    }

                    if (clickResult == ClickValidity.VALID) {
                        buttons[xCopy][yCopy].setBorder(GUIUtilities.SELECTED_BORDER);
                    } else if (clickResult == ClickValidity.VALID_MOVE_MADE) {
                        GUIUtilities.clearButtonBorders(buttons);
                    } else {
                        clearMove();
                        if (Resources.INVALID_MOVE != null && !Resources.INVALID_MOVE.isActive() && gameState) {
                            Resources.INVALID_MOVE.start();
                        }
                    }
                });

            }
        }

        // Configure the escape key to cancel the pending move, setup the check box and
        GUIUtilities.bindKeyStroke((JComponent) getContentPane(), "ESCAPE", "clear", this::clearMove);
        showPossibleMovesBox = new JCheckBox();
        showPossibleMovesBox.addItemListener(e -> showPossibleMovesBox.setSelected(
                e.getStateChange() == ItemEvent.SELECTED));
        GUIUtilities.updateView(buttons, board);

        menuMain.addActionListener(this);
        menuReset.addActionListener(this);
        menuHelp.addActionListener(this);
        menuSaveButton.addActionListener(this);
        menuHint.addActionListener(this);
        menuUndo.addActionListener(this);
        menuRedo.addActionListener(this);
        menuQuit.addActionListener(this);

        GUIUtilities.configureFrame(this);
    }

    /**
     * Creates the application GUI given a board, level, along with undo and redo
     * move stacks.
     *
     * @param board         The board that this GameView should have
     * @param level         The current level of the game. Only applicable to
     *                      default levels. For user levels, a negative value must
     *                      be provided.
     * @param undoMoveStack The stack of moves that can be undone
     * @param redoMoveStack The stack of moves that can be redone
     */
    public GameView(Board board, int level, ArrayDeque<Move> undoMoveStack, ArrayDeque<Move> redoMoveStack) {
        this(board, level);
        gameController.setUndoMoveStack(undoMoveStack);
        gameController.setRedoMoveStack(redoMoveStack);
    }

    /**
     * Pops up the in-game help dialog.
     */
    private void displayHelpDialog() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));

        panel.add(new JLabel("Show possible moves?"), BorderLayout.CENTER);
        panel.add(showPossibleMovesBox, BorderLayout.EAST);

        panel.add(new JLabel("<html><body><p style='width: 200px; text-align: justify'>" +
                "Rabbits and Foxes is a game in which you must get all rabbits to safety by having them " +
                "land in brown holes. " +
                "To do this, rabbits can only jump over other pieces and must land in an empty hole. " +
                "Foxes can slide along their initial direction as long as no other piece obstructs their" +
                " way.<br><br>" + "Hint (h): Outlines the next best move<br>" +
                "Help: Displays the help menu<br>" + "Reset:   Restarts the game<br>" +
                "Quit   (q):   Exits the application<br>" + "Escape (ESC): Clears the pending move" +
                "</p></body></html>"), BorderLayout.NORTH);
        JOptionPane.showMessageDialog(this, panel, "Help Dialog", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clears button borders and the pending position.
     */
    private void clearMove() {
        GUIUtilities.clearButtonBorders(buttons);
        gameController.clearPendingPosition();
    }

    /**
     * Resets the game.
     */
    private void resetGame() {
        board = gameController.reset();
        // Generate the hints as soon as possible to minimize waiting time.
        Thread hintThread = new Thread(this, "Hint");
        hintThread.start();
        board.addListener(this);
        GUIUtilities.clearButtonBorders(buttons);
        GUIUtilities.updateView(buttons, board);
    }

    /**
     * Updates the game frame's title with the new level name.
     */
    private void updateFrameTitle() {
        setTitle("Rabbit and Foxes! Level: " + board.getName());
    }

    /**
     * Saves the state of the game as a JSON object in a file at the specified path.
     *
     * @param path The absolute path of the file that will contain the saved data
     *             for this game
     * @return True if the game was saved successfully, false otherwise (i.e. the
     * path already exists)
     */
    public boolean save(String path) {
        if (new File(path).isFile()) {
            return false;
        }

        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),
                Charset.defaultCharset()))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", board.getName());
            jsonObject.addProperty("board", board.toString());
            jsonObject.addProperty("undoMoves", gson.toJson(gameController.getUndoMoveStack()));
            jsonObject.addProperty("redoMoves", gson.toJson(gameController.getRedoMoveStack()));

            gson.toJson(jsonObject, writer);
            return true;
        } catch (Exception e) {
            log.error("Unable to save Board object to file at " + path, e);
            return false;
        }
    }

    @Override
    public void handleBoardChange() {
        GUIUtilities.updateView(buttons, board);
        if (board.isInWinningState()) {
            if (Resources.SOLVED != null && gameState) {
                Resources.SOLVED.start();
            }
            GUIUtilities.clearButtonBorders(buttons);
            if (gameState) {
                if (!gameController.isDefaultLevel()) {
                    int choice = GUIUtilities.displayOptionDialog(this, "Congrats, you solved it! Would you like " +
                                    "to reset or go to the main menu?",
                            "Solved!", new String[]{"Reset", "Main Menu", "Quit"});
                    if (choice == 0) {
                        resetGame();
                    } else if (choice == 1) {
                        gameState = false;
                        dispose();
                        SwingUtilities.invokeLater(MainMenu::new);
                    } else {
                        System.exit(0);
                    }
                } else {
                    if (gameController.getCurrentLevel() != Resources.NUMBER_OF_LEVELS) {
                        int choice = GUIUtilities.displayOptionDialog(this, "Congrats, you solved it! Would you like " +
                                        "to go to the next puzzle?", "Solved!",
                                new String[]{"Next", "Reset", "Quit"});
                        if (choice == 0) {
                            gameController.incrementLevel();
                            resetGame();
                            updateFrameTitle();
                        } else if (choice == 1) {
                            resetGame();
                        } else {
                            System.exit(0);
                        }
                    } else {
                        if (GUIUtilities.displayOptionDialog(this, "You have finished the game! Would you like to go " +
                                        "to the main menu or exit?", "End Game",
                                new String[]{"Main Menu", "Quit"}) ==
                                0) {
                            gameState = false;
                            dispose();
                            SwingUtilities.invokeLater(MainMenu::new);
                        } else {
                            System.exit(0);
                        }
                    }
                }
            }

        }
    }

    /**
     * Handles button input for the menus.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuMain && GUIUtilities.displayOptionDialog(null,
                "Are you sure you want to return to the main menu? (Any " + "unsaved " +
                        "progress will be lost)", "Return to Main Menu", new String[]{"Yes", "No"}) == 0) {
            dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        } else if (e.getSource() == menuHint) {
            bestMove = gameController.getNextBestMove();
            if (!buttons[bestMove.xStart][bestMove.yStart].getBorder().equals(GUIUtilities.SELECTED_BORDER)) {
                buttons[bestMove.xStart][bestMove.yStart].setBorder(GUIUtilities.HINT_BORDER_START);
            }
            buttons[bestMove.xEnd][bestMove.yEnd].setBorder(GUIUtilities.HINT_BORDER_END);
        } else if (e.getSource() == menuSaveButton) {
            int returnVal = GUIUtilities.fc.showSaveDialog(this);
            while (returnVal == JFileChooser.APPROVE_OPTION &&
                    !save(GUIUtilities.fc.getSelectedFile().getAbsolutePath())) {
                GUIUtilities.displayMessageDialog(this, "File already exists!", "Invalid File Selection");
                returnVal = GUIUtilities.fc.showSaveDialog(this);
            }
        } else if (e.getSource() == menuHelp) {
            displayHelpDialog();
        } else if ((e.getSource() == menuReset) && (GUIUtilities.displayOptionDialog(this,
                "Are you sure you want to reset the game? (Any unsaved " +
                        "progress will be lost)", "Reset Rabbits and Foxes!", new String[]{"Yes", "No"}) == 0)) {
            resetGame();
        } else if (e.getSource() == menuUndo) {
            GUIUtilities.clearButtonBorders(buttons);
            if (!gameController.undoMove()) {
                GUIUtilities.displayMessageDialog(this, "No moves to undo", "Undo Move");
            }
        } else if (e.getSource() == menuRedo) {
            GUIUtilities.clearButtonBorders(buttons);
            if (!gameController.redoMove()) {
                GUIUtilities.displayMessageDialog(this, "No moves to redo", "Redo Move");
            }
        } else if (e.getSource() == menuQuit) {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }

    /**
     * Highlights a JButton when we enter the component with the mouse cursor.
     *
     * @param e The mouse event that is triggered when the mouse enters the JButton
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (((JButton) e.getSource()).getBorder().equals(GUIUtilities.BLANK_BORDER)) {
            ((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
        }
    }

    /**
     * Stops highlighting a JButton when the mouse cursor leaves the component.
     *
     * @param e The mouse event that is triggered when the mouse leaves the JButton
     */
    @Override
    public void mouseExited(MouseEvent e) {
        if (((JButton) e.getSource()).getBorder().equals(UIManager.getBorder("Button.border"))) {
            ((JButton) e.getSource()).setBorder(GUIUtilities.BLANK_BORDER);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void run() {
        if (Thread.currentThread().getName().equals("Hint")) {
            menuHint.setEnabled(false);
            menuHint.setText("Generating Hint...");
            bestMove = gameController.getNextBestMove();
            menuHint.setText("<html><u>H</u>int</html>");
            menuHint.setEnabled(true);
        }
    }
}
