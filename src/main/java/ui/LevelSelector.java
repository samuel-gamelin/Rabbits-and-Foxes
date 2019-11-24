package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import model.Board;
import model.Fox;
import model.Mushroom;
import model.Piece;
import model.Rabbit;
import resources.Resources;
import view.GameView;

/**
 * This class represents the level selector for the game.
 * 
 * @author John Breton
 * @author Dani Hashweh
 */
public class LevelSelector extends JFrame implements ActionListener {
    private static final BevelBorder SELECTED = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
    private static final BevelBorder DEFAULT = new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.BLACK);
    private static final double BOARD_DISPLAY_SIZE = 3;
    private List<Board> allDefaultLevels, allCustomLevels;
    private JButton btnStartSelectLevel, btnMainMenu, btnCustomLevels, btnNextPage, btnLastPage, btnLeftLevel,
            btnMiddleLevel, btnRightLevel;
    private JTextPane levelLabelLeft, levelLabelMiddle, levelLabelRight;
    private JPanel pageButtons, actionButtons, allButtons;
    private JLabel[][] tiles1, tiles2, tiles3;
    private int pageNumber, lastPage;
    private boolean custom;

    /**
     * 
     */
    public LevelSelector() {
        custom = false;
        pageNumber = 1;

        levelLabelLeft = new JTextPane();
        createLevelTextArea(levelLabelLeft);
        levelLabelMiddle = new JTextPane();
        createLevelTextArea(levelLabelMiddle);
        levelLabelRight = new JTextPane();
        createLevelTextArea(levelLabelRight);

        this.setContentPane(new JLabel(Resources.LEVEL_SELECTOR_BACKGROUND));
        this.getContentPane().setLayout(new BorderLayout());

        btnStartSelectLevel = new JButton("Start");
        formatButton(btnStartSelectLevel);
        btnStartSelectLevel.setEnabled(false);
        btnMainMenu = new JButton("Back to Main Menu");
        formatButton(btnMainMenu);
        btnCustomLevels = new JButton("Go to Custom Levels");
        formatButton(btnCustomLevels);
        btnNextPage = new JButton("Next Page");
        formatButton(btnNextPage);
        btnLastPage = new JButton("Last Page");
        btnLastPage.setEnabled(false);
        formatButton(btnLastPage);

        tiles1 = new JLabel[5][5];
        createTiles(tiles1);
        tiles2 = new JLabel[5][5];
        createTiles(tiles2);
        tiles3 = new JLabel[5][5];
        createTiles(tiles3);

        btnLeftLevel = new JButton();
        createLevelDisplayButton(btnLeftLevel, 1);

        btnMiddleLevel = new JButton();
        createLevelDisplayButton(btnMiddleLevel, 2);

        btnRightLevel = new JButton();
        createLevelDisplayButton(btnRightLevel, 3);

        pageButtons = new JPanel();
        pageButtons.setOpaque(false);
        pageButtons.setLayout(new BoxLayout(pageButtons, BoxLayout.LINE_AXIS));
        pageButtons.add(Box.createHorizontalGlue());
        pageButtons.add(btnLastPage);
        pageButtons.add(Box.createHorizontalGlue());
        pageButtons.add(btnNextPage);
        pageButtons.add(Box.createHorizontalGlue());

        actionButtons = new JPanel();
        actionButtons.setOpaque(false);
        actionButtons.setLayout(new BoxLayout(actionButtons, BoxLayout.LINE_AXIS));
        actionButtons.add(Box.createHorizontalGlue());
        actionButtons.add(btnMainMenu);
        actionButtons.add(Box.createHorizontalGlue());
        actionButtons.add(btnStartSelectLevel);
        actionButtons.add(Box.createHorizontalGlue());
        actionButtons.add(btnCustomLevels);
        actionButtons.add(Box.createHorizontalGlue());

        allButtons = new JPanel();
        allButtons.setOpaque(false);
        allButtons.setLayout(new BoxLayout(allButtons, BoxLayout.PAGE_AXIS));
        allButtons.add(pageButtons);
        allButtons.add(Box.createRigidArea(new Dimension(0, (int) Resources.SIDE_LENGTH / 25)));
        allButtons.add(actionButtons);
        allButtons.add(Box.createRigidArea(new Dimension(0, (int) Resources.SIDE_LENGTH / 50)));

        this.add(allButtons, BorderLayout.SOUTH);

        allDefaultLevels = Resources.getAllDefaultBoards();
        allCustomLevels = Resources.getAllUserBoards();

        determinePageNumber(allDefaultLevels);

        JPanel boards = new JPanel();
        boards.setLayout(new BoxLayout(boards, BoxLayout.LINE_AXIS));
        boards.setOpaque(false);

        boards.add(btnLeftLevel);
        boards.add(btnMiddleLevel);
        boards.add(btnRightLevel);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.LINE_AXIS));
        textPanel.setOpaque(false);
        
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelLeft);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelMiddle);
        textPanel.add(Box.createHorizontalGlue());
        textPanel.add(levelLabelRight);
        textPanel.add(Box.createHorizontalGlue());

        updateView(allDefaultLevels);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(boards);
        mainPanel.add(textPanel);

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 3.5))), BorderLayout.NORTH);

        this.setTitle("Level Selector");
        GUIUtilities.configureFrame(this);
    }

    private void determinePageNumber(List<Board> levelList) {
        if (levelList.size() % 3 == 0) {
            lastPage = levelList.size() / 3;
        } else {
            lastPage = (levelList.size() / 3) + 1;
        }
    }

    /**
     * 
     */
    private void createLevelTextArea(JTextPane text) {
        StyledDocument doc = text.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        text.setEditable(false);
        text.setOpaque(false);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        text.setForeground(Color.WHITE);
        text.setHighlighter(null);
        
    }

    /**
     * 
     * @param button
     */
    private void createLevelDisplayButton(JButton button, int location) {
        button.setIcon(new ImageIcon(
                Resources.BOARD.getImage().getScaledInstance((int) (Resources.SIDE_LENGTH / BOARD_DISPLAY_SIZE),
                        (int) (Resources.SIDE_LENGTH / BOARD_DISPLAY_SIZE), Image.SCALE_SMOOTH)));
        button.setOpaque(false);
        button.setBorder(DEFAULT);
        button.setContentAreaFilled(false);
        button.setLayout(new GridLayout(5, 5));
        button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) Resources.SIDE_LENGTH / 3));
        button.addActionListener(this);
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                switch (location) {
                case 1: {
                    button.add(tiles1[x][y]);
                    break;
                }
                case 2: {
                    button.add(tiles2[x][y]);
                    break;
                }
                case 3: {
                    button.add(tiles3[x][y]);
                }
                }
            }
        }
    }

    /**
     * 
     * @param button
     */
    private void updateView(List<Board> levelList) {
        clearSelectedBorder();
        if (pageNumber != lastPage) {
            updateLevelSelectorView(tiles1, levelList.get(pageNumber * 3 - 3));
            updateLevelSelectorView(tiles2, levelList.get(pageNumber * 3 - 2));
            updateLevelSelectorView(tiles3, levelList.get(pageNumber * 3 - 1));
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
            int levelsRemaining = levelList.size() % 3;
            switch (levelsRemaining) {
            case 1:
                updateLevelSelectorView(tiles1, levelList.get(pageNumber * 3 - 3));
                updateLevelSelectorView(tiles2, new Board("Empty"));
                if (!custom) {
                    levelLabelLeft.setText("Level " + levelList.get(pageNumber * 3 - 3).getName());
                } else {
                    levelLabelLeft.setText(levelList.get(pageNumber * 3 - 3).getName());
                }
                btnMiddleLevel.setEnabled(false);
                levelLabelMiddle.setText("Empty");
                updateLevelSelectorView(tiles3, new Board("Empty"));
                btnRightLevel.setEnabled(false);
                levelLabelRight.setText("Empty");
                break;
            case 2:
                updateLevelSelectorView(tiles1, levelList.get(pageNumber * 3 - 3));
                updateLevelSelectorView(tiles2, levelList.get(pageNumber * 3 - 2));
                updateLevelSelectorView(tiles3, new Board("Empty"));
                btnRightLevel.setEnabled(false);
                if (!custom) {
                    levelLabelLeft.setText("Level " + levelList.get(pageNumber * 3 - 3).getName());
                    levelLabelMiddle.setText("Level " + levelList.get(pageNumber * 3 - 2).getName());
                } else {
                    levelLabelLeft.setText(levelList.get(pageNumber * 3 - 3).getName());
                    levelLabelMiddle.setText(levelList.get(pageNumber * 3 - 2).getName());
                }
                levelLabelRight.setText("Empty");
                break;
            }
        }
    }

    /**
     * 
     */
    private void createTiles(JLabel[][] tiles) {
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                tiles[x][y] = new JLabel();
                // Clear button default colours and make it transparent
                tiles[x][y].setOpaque(false);
                tiles[x][y].setBorder(new EmptyBorder(0, 7, 0, 7));
            }
        }
    }

    private void updateLevelSelectorView(JLabel[][] tiles, Board board) {
        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    if (piece instanceof Mushroom) {
                        tiles[x][y].setIcon(new ImageIcon(
                                Resources.MUSHROOM.getImage().getScaledInstance((int) (Resources.SIDE_LENGTH * 0.05),
                                        (int) (Resources.SIDE_LENGTH * 0.05), Image.SCALE_SMOOTH)));
                    } else if (piece instanceof Rabbit) {
                        try {
                            tiles[x][y].setIcon(new ImageIcon(((ImageIcon) Resources.class
                                    .getDeclaredField("RABBIT_" + ((Rabbit) (piece)).getColour()).get(Resources.class))
                                            .getImage().getScaledInstance((int) (Resources.SIDE_LENGTH * 0.05),
                                                    (int) Resources.SIDE_LENGTH / 15, Image.SCALE_SMOOTH)));
                        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                            Resources.LOGGER.error("Could not obtain the required field from the Resources class", e);
                        }
                    } else {
                        try {
                            tiles[x][y].setIcon(new ImageIcon(((ImageIcon) Resources.class
                                    .getDeclaredField("FOX_" + ((Fox) (piece)).getFoxType() + "_" + ((Fox) (piece)).getDirection())
                                    .get(Resources.class)).getImage().getScaledInstance((int) (Resources.SIDE_LENGTH * 0.05),
                                     (int) (Resources.SIDE_LENGTH / 15), Image.SCALE_SMOOTH)));
                        } catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
                            Resources.LOGGER.error("Could not obtain the required field from the Resources class", e);
                        }
                    }
                } else {
                    tiles[x][y].setIcon(null);
                }
            }
        }
    }

    /**
     * Used to
     * 
     * @param button
     */
    private void formatButton(JButton button) {
        button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 3, (int) (0.20 * Resources.SIDE_LENGTH)));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFont(new Font("Times New Roman", Font.PLAIN, 28));
        button.addActionListener(this);
    }

    /**
     * 
     * @param button
     */
    private void levelSelected(JButton button) {
        clearSelectedBorder();
        button.setBorder(SELECTED);
        btnStartSelectLevel.setEnabled(true);
    }

    private void clearSelectedBorder() {
        btnLeftLevel.setBorder(DEFAULT);
        btnMiddleLevel.setBorder(DEFAULT);
        btnRightLevel.setBorder(DEFAULT);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStartSelectLevel) {
            if (btnLeftLevel.getBorder().equals(SELECTED)) {
                this.dispose();
                int level = (pageNumber * 3) - 2;
                if (!custom) {
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                } else {
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 3), -1));
                }
            } else if (btnMiddleLevel.getBorder().equals(SELECTED)) {
                this.dispose();
                int level = (pageNumber * 3) - 1;
                if (!custom) {
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                } else {
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 2), -1));
                }
            } else {
                this.dispose();
                int level = (pageNumber * 3);
                if (!custom) {
                    SwingUtilities.invokeLater(new GameView(Resources.getDefaultBoardByLevel(level), level));
                } else {
                    SwingUtilities.invokeLater(new GameView(allCustomLevels.get(pageNumber * 3 - 1), -1));
                }
            }
        } else if (e.getSource() == btnMainMenu) {
            this.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        } else if (e.getSource() == btnLeftLevel) {
            levelSelected(btnLeftLevel);
        } else if (e.getSource() == btnMiddleLevel) {
            levelSelected(btnMiddleLevel);
        } else if (e.getSource() == btnRightLevel) {
            levelSelected(btnRightLevel);
        } else if (e.getSource() == btnNextPage) {
            pageNumber++;
            btnLastPage.setEnabled(true);
            if (pageNumber == lastPage) {
                btnNextPage.setEnabled(false);
            }
            btnStartSelectLevel.setEnabled(false);
            if (custom) {
                updateView(allCustomLevels);
            } else {
                updateView(allDefaultLevels);
            }
        } else if (e.getSource() == btnLastPage) {
            pageNumber--;
            btnNextPage.setEnabled(true);
            if (pageNumber == 1) {
                btnLastPage.setEnabled(false);
            }
            btnStartSelectLevel.setEnabled(false);
            if (custom) {
                updateView(allCustomLevels);
            } else {
                updateView(allDefaultLevels);
            }
        } else if (e.getSource() == btnCustomLevels) {
            if (custom) {
                custom = false;
                btnCustomLevels.setText("Go to Custom Levels");
                pageNumber = 1;
                determinePageNumber(allDefaultLevels);
                if (lastPage == 1) {
                    btnNextPage.setEnabled(false);
                }
                updateView(allDefaultLevels);
            } else {
                custom = true;
                btnCustomLevels.setText("Go to Default Levels");
                pageNumber = 1;
                determinePageNumber(allCustomLevels);
                if (lastPage == 1) {
                    btnNextPage.setEnabled(false);
                }
                updateView(allCustomLevels);
            }
        }
    }
}
