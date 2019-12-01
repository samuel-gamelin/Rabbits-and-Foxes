package ui;

import model.Board;
import resources.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the main menu frame of the game.
 *
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 * @version 4.0
 */

public class MainMenu extends JFrame implements ActionListener {
    private JButton btnStart, btnSelectLevel, btnBuildLevel, btnHelp, btnLoadGame, btnQuitGame;

    /**
     * The file chooser that the user will interact with should they choose to load a save file.
     */
    private JFileChooser fc;

    /**
     * Constructs a MainMenu frame, populating it with options (as buttons) that the user can choose from.
     */
    public MainMenu() {
        fc = new JFileChooser();

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
     * Adds a button to the specified pane, registering this frame as an
     * ActionListener.
     *
     * @param button The button to add and register this pane to as an
     *               ActionListener
     */
    private void addMainMenuButton(JButton button) {
        button.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 * GUIUtilities.SIDE_LENGTH)));
        button.setPreferredSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / 2.5), (int) (0.10 * GUIUtilities.SIDE_LENGTH)));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.PLAIN, GUIUtilities.FONT_SIZE));
        button.addActionListener(this);
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
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Board board = Board.loadBoard(fc.getSelectedFile().getAbsolutePath());
                if (board != null) {
                    this.dispose();
                    if (board.getName().matches("-?\\d+"))
                        SwingUtilities.invokeLater(new GameView(board, Integer.parseInt(board.getName())));
                    else {
                        SwingUtilities.invokeLater(new GameView(board, -1));
                    }
                } else {
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
