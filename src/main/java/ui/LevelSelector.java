package ui;

import model.*;
import model.Fox.FoxType;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the level selector for the game.
 *
 * @author John Breton
 * @author Dani Hashweh
 */
public class LevelSelector extends JFrame implements ActionListener {

    private static final int BUTTON_SPACING = (int) GUIUtilities.SIDE_LENGTH / 50;
    private static final double BUTTON_Y_FACTOR = 0.20;
    private static final int BUTTON_X_FACTOR = 3;
    private static final double BOARD_DISPLAY_SIZE = 3.4;
    private static final double X_SCALE_FACTOR = 0.05;
    private static final int Y_SCALE_FACTOR = 17;

    private static final BevelBorder SELECTED = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
    private static final BevelBorder DEFAULT = new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);

    private JButton btnStartLevel, btnMainMenu, btnCustomLevels, btnNextPage, btnLastPage, btnLeftLevel,
            btnMiddleLevel, btnRightLevel, btnDeleteLevel;
    private JTextPane levelLabelLeft, levelLabelMiddle, levelLabelRight;
    private JLabel[][] tilesLeft, tilesMiddle, tilesRight;

    private List<Board> allDefaultLevels, allCustomLevels;
    private int pageNumber, lastPage;
    private boolean custom;

    /**
     * Construct a new LevelSelector.
     */
    public LevelSelector() {
        // Initializing default values for a few variables.
        custom = false;
        pageNumber = 1;

        /*
         * Storing lists containing the stored levels in the JSON document. Also
         * determining what the last page is based on the number of levels in the
         * default level list.
         */
        allDefaultLevels = Resources.getAllDefaultBoards();
        allCustomLevels = Resources.getAllUserBoards();
        determineLastPage(allDefaultLevels);

        // Set the layout and background of the level selector JFrame.
        this.setContentPane(new JLabel(Resources.LEVEL_SELECTOR_BACKGROUND));
        this.getContentPane().setLayout(new BorderLayout());

        // Create the JTextPanes to display level names
        setUpJTextPane(levelLabelLeft = new JTextPane());
        setUpJTextPane(levelLabelMiddle = new JTextPane());
        setUpJTextPane(levelLabelRight = new JTextPane());

        // Create the JButtons used for interacting with the level selector.
        setUpMenuButton(btnStartLevel = new JButton("Start"));
        setUpMenuButton(btnMainMenu = new JButton("Back to Main Menu"));
        setUpMenuButton(btnCustomLevels = new JButton("Go to Custom Levels"));
        setUpMenuButton(btnNextPage = new JButton("Next Page"));
        setUpMenuButton(btnLastPage = new JButton("Last Page"));
        setUpMenuButton(btnDeleteLevel = new JButton("Delete Level"));
        btnDeleteLevel.setBackground(Color.RED);
        btnDeleteLevel.setForeground(Color.WHITE);
        btnDeleteLevel.setVisible(false);
        btnDeleteLevel.setEnabled(false);
        btnStartLevel.setBackground(new Color(0, 156, 0));
        btnStartLevel.setEnabled(false);
        btnLastPage.setEnabled(false);

        // Create the "tiles" for the board preview.
        setUpTiles(tilesLeft = new JLabel[5][5]);
        setUpTiles(tilesMiddle = new JLabel[5][5]);
        setUpTiles(tilesRight = new JLabel[5][5]);

        // Creating the buttons that will hold the board previews for the levels.
        setUpLevelDisplayButton(btnLeftLevel = new JButton(), 1);
        setUpLevelDisplayButton(btnMiddleLevel = new JButton(), 2);
        setUpLevelDisplayButton(btnRightLevel = new JButton(), 3);

        /* 
         * Creating a JPanel to hold the buttons associated with 
         * moving up or down a page.
         * Also adding the associated buttons to the panel, along with glue.
         */
        JPanel pageButtons = new JPanel();
        setUpJPanel(pageButtons, true);
        pageButtons.add(Box.createHorizontalGlue());
        pageButtons.add(btnLastPage);
        pageButtons.add(Box.createHorizontalGlue());
        pageButtons.add(btnNextPage);
        pageButtons.add(Box.createHorizontalGlue());

        /*
         * Creating a JPanel to hold the buttons associated with returning to the main
         * menu, starting a level, and going to the custom/default level selection
         * screen. Also adding the associated buttons to the panel, along with glue.
         */
        JPanel actionButtons = new JPanel();
        setUpJPanel(actionButtons, true);
        actionButtons.add(Box.createHorizontalGlue());
        actionButtons.add(btnMainMenu);
        actionButtons.add(Box.createHorizontalGlue());
        actionButtons.add(btnCustomLevels);
        actionButtons.add(Box.createHorizontalGlue());
        
        // Creating a JPanel to hold the start button.
        JPanel startPanel = new JPanel();
        setUpJPanel(startPanel, true);
        startPanel.add(btnStartLevel);

        // Creating a JPanel to hold the delete level button.
        JPanel deletePanel = new JPanel();
        setUpJPanel(deletePanel, true);
        deletePanel.add(btnDeleteLevel);

        // Creating a JPanel to store all of the buttons for the level selector.
        JPanel allButtons = new JPanel();
        allButtons.setOpaque(false);
        allButtons.setLayout(new BoxLayout(allButtons, BoxLayout.PAGE_AXIS));
        allButtons.add(startPanel);
        allButtons.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        allButtons.add(deletePanel);
        allButtons.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        allButtons.add(pageButtons);
        allButtons.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));
        allButtons.add(actionButtons);
        allButtons.add(Box.createRigidArea(new Dimension(0, BUTTON_SPACING)));

        // Creating a JPanel to store the board previews for the levels on the current page.
        JPanel boards = new JPanel();
        setUpJPanel(boards, true);
        boards.add(Box.createHorizontalGlue());
        boards.add(btnLeftLevel);
        boards.add(Box.createHorizontalGlue());
        boards.add(btnMiddleLevel);
        boards.add(Box.createHorizontalGlue());
        boards.add(btnRightLevel);
        boards.add(Box.createHorizontalGlue());

        // Creating a JPanel to store all of the level names on the current page.
        JPanel textPanel = new JPanel();
        setUpJPanel(textPanel, true);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelLeft);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelMiddle);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelRight);
        textPanel.add(Box.createHorizontalGlue());

        // Creating a JPanel to store the other JPanels created above.
        JPanel mainPanel = new JPanel();
        setUpJPanel(mainPanel, false);
        mainPanel.add(boards);
        mainPanel.add(textPanel);

        // Ensuring the level displays have been updated with the levels from page 1.
        updateView(allDefaultLevels);

        // Adding everything to and subsequently setting up the JFrame.
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(allButtons, BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(0, (int) (GUIUtilities.SIDE_LENGTH / 3.5))), BorderLayout.NORTH);
        this.setTitle("Level Selector");
        GUIUtilities.configureFrame(this);
    }

    /**
     * Initialize a 2D array of JLabels to prepare for storing an image of a piece
     * depending on the level being previewed.
     *
     * @param tiles The JLabel 2D array being set up
     */
    private void setUpTiles(JLabel[][] tiles) {
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                tiles[x][y] = new JLabel();
                tiles[x][y].setOpaque(false);
            }
        }
    }

    /**
     * Initialize a JPanel with default behaviour.
     *
     * @param panel      The JPanel being set up
     * @param layoutType True for horizontal, false for vertical
     */
    private void setUpJPanel(JPanel panel, boolean layoutType) {
        panel.setOpaque(false);
        if (layoutType)
            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        else
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
    }

    /**
     * Initialize a JButton used for interacting with the level selector with
     * default behaviour.
     *
     * @param button The JButton being set up
     */
    private void setUpMenuButton(JButton button) {
        button.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / BUTTON_X_FACTOR),
                (int) (BUTTON_Y_FACTOR * GUIUtilities.SIDE_LENGTH)));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.PLAIN, GUIUtilities.FONT_SIZE));
        button.addActionListener(this);
    }

    /**
     * Initialize a JTextPane with default behaviour.
     *
     * @param pane the JTextPane being set up
     */
    private void setUpJTextPane(JTextPane pane) {
        StyledDocument doc = pane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        pane.setPreferredSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                (int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE)));
        pane.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                (int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE)));
        pane.setEditable(false);
        pane.setOpaque(false);
        pane.setFont(new Font("Times New Roman", Font.PLAIN, GUIUtilities.FONT_SIZE));
        pane.setForeground(Color.WHITE);
        pane.setHighlighter(null);
    }

    /**
     * Initialize a JButton used to display the level previews.
     *
     * @param button The JButton being set up for level preview display purposes
     * @param tileNumber 1 for left, 2 for middle, 3 for right.
     */
    private void setUpLevelDisplayButton(JButton button, int tileNumber) {
        button.setIcon(new ImageIcon(
                Resources.BOARD.getImage().getScaledInstance((int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                        (int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE), Image.SCALE_SMOOTH)));
        button.setOpaque(false);
        button.setBorder(DEFAULT);
        button.setContentAreaFilled(false);
        button.setLayout(new GridLayout(5, 5));
        button.setMaximumSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                (int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE)));
        button.addActionListener(this);
        button.setPreferredSize(new Dimension((int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                (int) (GUIUtilities.SIDE_LENGTH / BOARD_DISPLAY_SIZE)));
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                switch (tileNumber) {
                    case 1: {
                        button.add(tilesLeft[x][y]);
                        break;
                    }
                    case 2: {
                        button.add(tilesMiddle[x][y]);
                        break;
                    }
                    case 3: {
                        button.add(tilesRight[x][y]);
                    }
                }
            }
        }
    }

    /**
     * Update the view of the main level selector JFrame. This involves updating all
     * of the JButtons responsible for displaying board previews. 
     *
     * @param levelList The list we are using to update the level previews
     */
    private void updateView(List<Board> levelList) {
        clearSelectedBorder();
        if (pageNumber != lastPage || levelList.size() % 3 == 0) {
            updateLevelPreview(tiles1, levelList.get(pageNumber * 3 - 3));
            updateLevelPreview(tiles2, levelList.get(pageNumber * 3 - 2));
            updateLevelPreview(tiles3, levelList.get(pageNumber * 3 - 1));
            btnMiddleLevel.setEnabled(true);
            btnRightLevel.setEnabled(true);
            if (!custom) {
                levelLabelLeft.setText("Level " + levelList.get(pageNumber * 3 - 3).getName());
                levelLabelMiddle.setText("Level " + levelList.get(pageNumber * 3 - 2).getName());
                levelLabelRight.setText("Level " + levelList.get(pageNumber * 3 - 1).getName());
            } else {
                levelLabelLeft.setText(levelList.get(pageNumber * 3 - 3).getName());
                levelLabelMiddle.setText(levelList.get(pageNumber * 3 - 2).getName());
                levelLabelRight.setText(levelList.get(pageNumber * 3 - 1).getName());
            }
        } else {
            switch (levelList.size() % 3) {
                case 1:
                    updateLevelPreview(tilesLeft, levelList.get(pageNumber * 3 - 3));
                    updateLevelPreview(tilesMiddle, new Board("Empty"));
                    updateLevelPreview(tilesRight, new Board("Empty"));
                    if (!custom) {
                        levelLabelLeft.setText("Level " + levelList.get(pageNumber * 3 - 3).getName());
                    } else {
                        levelLabelLeft.setText(levelList.get(pageNumber * 3 - 3).getName());
                    }
                    btnMiddleLevel.setEnabled(false);
                    btnRightLevel.setEnabled(false);
                    levelLabelMiddle.setText("Empty");
                    levelLabelRight.setText("Empty");
                    break;
                case 2:
                    updateLevelPreview(tilesLeft, levelList.get(pageNumber * 3 - 3));
                    updateLevelPreview(tilesMiddle, levelList.get(pageNumber * 3 - 2));
                    updateLevelPreview(tilesRight, new Board("Empty"));
                    if (!custom) {
                        levelLabelLeft.setText("Level " + levelList.get(pageNumber * 3 - 3).getName());
                        levelLabelMiddle.setText("Level " + levelList.get(pageNumber * 3 - 2).getName());
                    } else {
                        levelLabelLeft.setText(levelList.get(pageNumber * 3 - 3).getName());
                        levelLabelMiddle.setText(levelList.get(pageNumber * 3 - 2).getName());
                    }
                    btnRightLevel.setEnabled(false);
                    levelLabelRight.setText("Empty");
                    break;
            }
        }
        this.revalidate();
        this.repaint();
    }

    /**
     * Update the level previews for the current page.
     *
     * @param tiles The tiles being updated with images from the new level
     * @param board The board used to update the images displayed on the tiles
     */
    private void updateLevelPreview(JLabel[][] tiles, Board board) {
        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    if (piece instanceof Mushroom) {
                        tiles[x][y].setIcon(new ImageIcon(Resources.MUSHROOM.getImage().getScaledInstance(
                                (int) (GUIUtilities.SIDE_LENGTH * X_SCALE_FACTOR),
                                (int) (GUIUtilities.SIDE_LENGTH * X_SCALE_FACTOR), Image.SCALE_SMOOTH)));
                        tiles[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                    } else if (piece instanceof Rabbit) {
                        tiles[x][y].setIcon(
                                new ImageIcon((Objects.requireNonNull(Resources.getImageIconByName("RABBIT_" + ((Rabbit) (piece)).getColour())))
                                        .getImage().getScaledInstance((int) (GUIUtilities.SIDE_LENGTH * X_SCALE_FACTOR),
                                                (int) GUIUtilities.SIDE_LENGTH / Y_SCALE_FACTOR, Image.SCALE_SMOOTH)));
                        tiles[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                    } else {
                        tiles[x][y].setIcon(new ImageIcon((Objects.requireNonNull(Resources.getImageIconByName(
                                "FOX_" + ((Fox) (piece)).getFoxType() + "_" + ((Fox) (piece)).getDirection())))
                                .getImage().getScaledInstance((int) (GUIUtilities.SIDE_LENGTH * X_SCALE_FACTOR),
                                        (int) (GUIUtilities.SIDE_LENGTH / Y_SCALE_FACTOR),
                                        Image.SCALE_SMOOTH)));
                        tiles[x][y].setHorizontalAlignment(SwingConstants.CENTER);
                        if (((Fox) (piece)).getFoxType() == FoxType.HEAD) {
                            switch (((Fox) (piece)).getDirection()) {
                                case LEFT:
                                    tiles[x][y].setHorizontalAlignment(SwingConstants.RIGHT);
                                    break;
                                case RIGHT:
                                    tiles[x][y].setHorizontalAlignment(SwingConstants.LEFT);
                                    break;
                                default:
                                    break;
                            }
                        } else {
                            switch (((Fox) (piece)).getDirection()) {
                                case LEFT:
                                    tiles[x][y].setHorizontalAlignment(SwingConstants.LEFT);
                                    break;
                                case RIGHT:
                                    tiles[x][y].setHorizontalAlignment(SwingConstants.RIGHT);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } else {
                    tiles[x][y].setIcon(null);
                }
            }
        }

    }

    /**
     * Modify border of the selected level preview JButton to stand out against the
     * borders of the other level preview JButtons.
     *
     * @param button The JButton that was selected
     */
    private void levelSelected(JButton button) {
        clearSelectedBorder();
        button.setBorder(SELECTED);
        btnStartLevel.setEnabled(true);
        if (custom)
            btnDeleteLevel.setEnabled(true);
    }

    /**
     * Clear the borders on the level preview JButtons.
     */
    private void clearSelectedBorder() {
        btnLeftLevel.setBorder(DEFAULT);
        btnMiddleLevel.setBorder(DEFAULT);
        btnRightLevel.setBorder(DEFAULT);
    }

    /**
     * Calculate the last page for a given level list.
     *
     * @param levelList The list for which the last page is being calculated for
     */
    private void determineLastPage(List<Board> levelList) {
        lastPage = levelList.size() / 3;
        if (levelList.size() % 3 != 0)
            lastPage++;
    }

    /**
     * Determine the appropriate action based on the JButton that was just pressed.
     *
     * @param e The ActionEvent that has just taken place
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStartLevel) {
            this.dispose();
            if (btnLeftLevel.getBorder().equals(SELECTED)) {
                int level = (pageNumber * 3) - 2;
                if (!custom)
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                else
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 3), -1));
            } else if (btnMiddleLevel.getBorder().equals(SELECTED)) {
                int level = (pageNumber * 3) - 1;
                if (!custom)
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                else
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 2), -1));
            } else {
                int level = (pageNumber * 3);
                if (!custom)
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                else
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 1), -1));
            }
        } else if (e.getSource() == btnMainMenu) {
            this.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        } else if (e.getSource() == btnLeftLevel || e.getSource() == btnMiddleLevel || e.getSource() == btnRightLevel) {
            levelSelected((JButton) e.getSource());
        } else if (e.getSource() == btnNextPage) {
            pageNumber++;
            btnLastPage.setEnabled(true);
            btnNextPage.setEnabled(pageNumber != lastPage);
            btnStartLevel.setEnabled(false);
            if (custom) {
                updateView(allCustomLevels);
                btnDeleteLevel.setEnabled(false);
            } else {
                updateView(allDefaultLevels);
            }
        } else if (e.getSource() == btnLastPage) {
            pageNumber--;
            btnNextPage.setEnabled(true);
            btnLastPage.setEnabled(pageNumber != 1);
            btnStartLevel.setEnabled(false);
            if (custom) {
                updateView(allCustomLevels);
                btnDeleteLevel.setEnabled(false);
            } else {
                updateView(allDefaultLevels);
            }
        } else if (e.getSource() == btnCustomLevels) {
            if (allCustomLevels.isEmpty()) {
                GUIUtilities.displayMessageDialog(this,
                        "Could not locate any custom levels.\nTry making some in the level builder!",
                        "No custom levels found");
            } else {
                pageNumber = 1;
                if (custom) {
                    custom = false;
                    btnCustomLevels.setText("Go to Custom Levels");
                    determineLastPage(allDefaultLevels);
                    updateView(allDefaultLevels);
                    btnDeleteLevel.setVisible(false);
                } else {
                    custom = true;
                    btnCustomLevels.setText("Go to Default Levels");
                    determineLastPage(allCustomLevels);
                    updateView(allCustomLevels);
                    btnDeleteLevel.setEnabled(false);
                    btnDeleteLevel.setVisible(true);
                }
                btnNextPage.setEnabled(lastPage != 1);
                btnLastPage.setEnabled(false);
                btnStartLevel.setEnabled(false);
            }
        } else if (e.getSource() == btnDeleteLevel && GUIUtilities.displayOptionDialog(this, "Are you sure you want to delete this level?\nThis cannot be undone.", "Delete Level",
                new String[]{"Yes", "No"}) == 0) {
            if (btnLeftLevel.getBorder().equals(SELECTED))
                Resources.removeUserLevel(levelLabelLeft.getText());
            else if (btnMiddleLevel.getBorder().equals(SELECTED))
                Resources.removeUserLevel(levelLabelMiddle.getText());
            else
                Resources.removeUserLevel(levelLabelRight.getText());
            allCustomLevels = Resources.getAllUserBoards();
            determineLastPage(allCustomLevels);
            pageNumber = 1;
            btnLastPage.setEnabled(false);
            if (allCustomLevels.size() != 0) {
                updateView(allCustomLevels);
                btnDeleteLevel.setEnabled(false);
            } else {
                custom = false;
                btnCustomLevels.setText("Go to Custom Levels");
                determineLastPage(allDefaultLevels);
                updateView(allDefaultLevels);
                btnDeleteLevel.setVisible(false);
            }
            btnNextPage.setEnabled(lastPage != 1);
            btnStartLevel.setEnabled(false);
        }
    }
}
