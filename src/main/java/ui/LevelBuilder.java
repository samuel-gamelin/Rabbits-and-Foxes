package ui;

import model.*;
import model.Fox.Direction;
import model.Rabbit.RabbitColour;
import resources.Resources;
import util.Move;
import util.Move.MoveDirection;
import util.Solver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class represents a level builder which allows the user to create and
 * save their own levels.
 *
 * @author Mohamed Radwan
 * @author Abdalla El Nakla
 */
public class LevelBuilder extends JFrame implements ActionListener, MouseListener, BoardListener {

    /**
     * Menu Items to be added to the JMenuBar
     */
    private final JButton menuReset, menuHelp, deletePiece, flipFox, menuMainScreen, saveBoard;

    /**
     *
     */
    private final JButton[][] buttons;

    /**
     * Represents the Head of a vertical Fox in the up direction.
     */
    private JButton verticalFH;

    /**
     * Represents the Tail of a vertical Fox in the up direction.
     */
    private JButton verticalFT;

    /**
     * Represents the Head of a Horizontal Fox in the left direction.
     */
    private JButton horizontalFH;

    /**
     * Represents the Tail of a Horizontal Fox in the left direction.
     */
    private JButton horizontalFT;

    /**
     * Represents a white Rabbit.
     */
    private JButton rabbitWhite;

    /**
     * Represents a gray Rabbit.
     */
    private JButton rabbitGray;

    /**
     * Represents a brown Rabbit.
     */
    private JButton rabbitBrown;

    /**
     * Represents a mushroom.
     */
    private JButton mushroom;

    private ImageIcon currentIcon;

    /**
     * The Board that this view listens to.
     */
    private Board board;

    /**
     * Represents the number of foxes currently placed on the board.
     */
    private int numberOfFoxes;

    /**
     * Represents the number of mushrooms currently placed on the board.
     */
    private int numberOfMushrooms;

    /**
     * X position of a potential item to be modified.
     */
    private int itemToBeModifiedX;

    /**
     * Y position of a potential item to be modified.
     */
    private int itemToBeModifiedY;

    public LevelBuilder() {
        this.board = new Board("");
        board.addListener(this);
        this.setTitle("Level Builder");
        currentIcon = new ImageIcon();
        numberOfMushrooms = 0;
        numberOfFoxes = 0;
        itemToBeModifiedX = 0;
        itemToBeModifiedY = 0;

        /*
         * Menu bar items
         */
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menuMainScreen = GUIUtilities.createMenuBarButton("<html><u>M</u>ain Menu</html>", true));
        menuBar.add(menuReset = GUIUtilities.createMenuBarButton("<html><u>R</u>eset</html>", true));
        menuBar.add(deletePiece = GUIUtilities.createMenuBarButton("<html><u>D</u>elete Piece</html>", true));
        menuBar.add(flipFox = GUIUtilities.createMenuBarButton("<html><u>F</u>lip Fox</html>", true));
        menuBar.add(saveBoard = GUIUtilities.createMenuBarButton("<html><u>S</u>ave</html>", true));
        menuBar.add(menuHelp = GUIUtilities.createMenuBarButton("<html><u>H</u>elp</html>", true));
        this.setJMenuBar(menuBar);

        // Create a JLabel for the board
        JLabel gameBoardJPanel = new JLabel(Resources.BOARD);
        gameBoardJPanel.setLayout(new GridLayout(5, 5));

        buttons = new JButton[5][5];
        for (int y = 0; y < Board.SIZE; y++) {
            for (int x = 0; x < Board.SIZE; x++) {
                buttons[x][y] = GUIUtilities.generateGameBoardButton(x, y);

                buttons[x][y].addMouseListener(this);
                gameBoardJPanel.add(buttons[x][y]);

                final int xCopy = x;
                final int yCopy = y;

                buttons[x][y].addActionListener(e -> {
                    GUIUtilities.clearButtonBorders(buttons);
                    if (board.isOccupied(xCopy, yCopy)) {
                        // Location of an item that will potential be modified
                        buttons[xCopy][yCopy].setBorder(GUIUtilities.SELECTED_BORDER);
                        itemToBeModifiedX = xCopy;
                        itemToBeModifiedY = yCopy;
                    } else {
                        itemToBeModifiedX = -1;
                        itemToBeModifiedY = -1;
                        placePiece(xCopy, yCopy);
                    }
                });

            }
        }

        // West side for the board.
        this.add(gameBoardJPanel, BorderLayout.WEST);
        // East side for the tile for objects to be selected from.
        this.add(piecePanelSetup(), BorderLayout.EAST);

        GUIUtilities.configureFrame(this);

        // Add action listener for menu items
        menuMainScreen.addActionListener(this);
        menuReset.addActionListener(this);
        menuHelp.addActionListener(this);
        saveBoard.addActionListener(this);
        deletePiece.addActionListener(this);
        flipFox.addActionListener(this);
    }

    private void placePiece(int x, int y) {
        // if the position is already occupied the item cannot be placed.
        if (board.isOccupied(x, y)) {
            return;
        }
        // check if the tile is brown or green
        boolean currentTile = board.tileType(x, y);

        if (currentIcon == Resources.FOX_HEAD_UP && y + 1 < 5 && y + 1 > -1 && !board.isOccupied(x, y + 1) &&
            currentTile && board.tileType(x, y + 1)) {
            Fox fox = new Fox(Direction.UP, numberOfFoxes == 1);
            board.setPiece(fox, x, y);
            board.setPiece(fox.getOtherHalf(), x, y + 1);
            numberOfFoxes++;
        } else if (currentIcon == Resources.FOX_TAIL_UP && y - 1 < 5 && y - 1 > -1 && !board.isOccupied(x, y - 1) &&
                   currentTile && board.tileType(x, y - 1)) {
            Fox fox = new Fox(Direction.UP, numberOfFoxes == 1);
            board.setPiece(fox, x, y - 1);
            board.setPiece(fox.getOtherHalf(), x, y);
            numberOfFoxes++;
        } else if (currentIcon == Resources.FOX_HEAD_LEFT && x + 1 < 5 && x + 1 > -1 && !board.isOccupied(x + 1, y) &&
                   currentTile && board.tileType(x + 1, y)) {
            Fox fox = new Fox(Direction.LEFT, numberOfFoxes == 1);
            board.setPiece(fox, x, y);
            board.setPiece(fox.getOtherHalf(), x + 1, y);
            numberOfFoxes++;
        } else if (currentIcon == Resources.FOX_TAIL_LEFT && x - 1 < 5 && x - 1 > -1 && !board.isOccupied(x - 1, y) &&
                   currentTile && board.tileType(x - 1, y)) {
            Fox fox = new Fox(Direction.LEFT, numberOfFoxes == 1);
            board.setPiece(fox, x - 1, y);
            board.setPiece(fox.getOtherHalf(), x, y);
            numberOfFoxes++;
        } else if (currentIcon == Resources.RABBIT_WHITE) {
            board.setPiece(new Rabbit(RabbitColour.WHITE), x, y);
            rabbitWhite.setEnabled(false);
        } else if (currentIcon == Resources.RABBIT_GRAY) {
            board.setPiece(new Rabbit(RabbitColour.GRAY), x, y);
            rabbitGray.setEnabled(false);
        } else if (currentIcon == Resources.RABBIT_BROWN) {
            board.setPiece(new Rabbit(RabbitColour.BROWN), x, y);
            rabbitBrown.setEnabled(false);
        } else if (currentIcon == Resources.MUSHROOM) {
            board.setPiece(new Mushroom(), x, y);
            numberOfMushrooms++;
        }
        // If all the mushrooms have been used disable the button.
        if (numberOfMushrooms == 3) {
            mushroom.setEnabled(false);
        }
        // If all the foxes have been used disable to button.
        if (numberOfFoxes == 2) {
            horizontalFH.setEnabled(false);
            horizontalFT.setEnabled(false);
            verticalFH.setEnabled(false);
            verticalFT.setEnabled(false);
        }
        GUIUtilities.updateView(buttons, board);
        currentIcon = null;
    }

    /**
     * This method is used construct the side panel used for the pieces.
     *
     * @return The JPanel that was created
     */
    private JLabel piecePanelSetup() {
        // create the side panel as a grid
        JLabel pieceJPanel = new JLabel(Resources.SIDE_PANEL);
        pieceJPanel.setLayout(new GridLayout(5, 2));
        pieceJPanel.add(verticalFH = buttonIconSetup(Resources.FOX_HEAD_UP));
        pieceJPanel.add(rabbitWhite = buttonIconSetup(Resources.RABBIT_WHITE));
        pieceJPanel.add(verticalFT = buttonIconSetup(Resources.FOX_TAIL_UP));
        pieceJPanel.add(rabbitGray = buttonIconSetup(Resources.RABBIT_GRAY));
        pieceJPanel.add(mushroom = buttonIconSetup(Resources.MUSHROOM));
        pieceJPanel.add(rabbitBrown = buttonIconSetup(Resources.RABBIT_BROWN));
        pieceJPanel.add(horizontalFH = buttonIconSetup(Resources.FOX_HEAD_LEFT));
        pieceJPanel.add(horizontalFT = buttonIconSetup(Resources.FOX_TAIL_LEFT));
        // Add button spacing for the bottom of the menu to account for scaling
        /*
         * Used to correct the size for the side panel.
         */

        pieceJPanel.add(buttonIconSetup(null));
        pieceJPanel.add(buttonIconSetup(null));
        // Add action listeners for the piece buttons on the side panel
        verticalFH.addActionListener(this);
        verticalFT.addActionListener(this);
        horizontalFH.addActionListener(this);
        horizontalFT.addActionListener(this);
        rabbitWhite.addActionListener(this);
        rabbitGray.addActionListener(this);
        rabbitBrown.addActionListener(this);
        mushroom.addActionListener(this);

        return pieceJPanel;
    }

    /**
     * Add images to the buttons
     *
     * @param image The image to use for the button
     * @return The newly-created button
     */
    private JButton buttonIconSetup(ImageIcon image) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(GUIUtilities.BLANK_BORDER);
        button.setIcon(image);
        return button;
    }

    /**
     * Resets the board.
     */
    private void resetBoard() {
        board = new Board("");
        board.addListener(this);
        numberOfMushrooms = 0;
        numberOfFoxes = 0;
        horizontalFH.setEnabled(true);
        horizontalFT.setEnabled(true);
        verticalFH.setEnabled(true);
        verticalFT.setEnabled(true);
        mushroom.setEnabled(true);
        rabbitWhite.setEnabled(true);
        rabbitBrown.setEnabled(true);
        rabbitGray.setEnabled(true);
        currentIcon = null;
        GUIUtilities.updateView(buttons, board);
    }

    /**
     * This method place the piece on the board when the mouse is moved into a
     * square on the grid.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        if (((JButton) e.getSource()).getIcon() == null) {
            if (currentIcon == Resources.RABBIT_WHITE || currentIcon == Resources.RABBIT_BROWN ||
                currentIcon == Resources.RABBIT_GRAY || currentIcon == Resources.MUSHROOM) {
                ((JButton) e.getSource()).setIcon(currentIcon);
                return;
            }
            String[] position = ((JButton) e.getSource()).getName().split(",");
            int x = Integer.parseInt(position[0]);
            int y = Integer.parseInt(position[1]);
            boolean currentTile = board.tileType(x, y);
            if (currentTile) {
                if (currentIcon == Resources.FOX_HEAD_UP && y + 1 < 5 && y + 1 > -1 && !board.isOccupied(x, y + 1) &&
                    board.tileType(x, y + 1)) {
                    ((JButton) e.getSource()).setIcon(currentIcon);
                    buttons[x][y + 1].setIcon(Resources.FOX_TAIL_UP);
                } else if (currentIcon == Resources.FOX_TAIL_UP && y - 1 < 5 && y - 1 > -1 &&
                           !board.isOccupied(x, y - 1) && board.tileType(x, y - 1)) {
                    ((JButton) e.getSource()).setIcon(currentIcon);
                    buttons[x][y - 1].setIcon(Resources.FOX_HEAD_UP);
                } else if (currentIcon == Resources.FOX_HEAD_LEFT && x + 1 < 5 && x + 1 > -1 &&
                           !board.isOccupied(x + 1, y) && board.tileType(x + 1, y)) {
                    ((JButton) e.getSource()).setIcon(currentIcon);
                    buttons[x + 1][y].setIcon(Resources.FOX_TAIL_LEFT);
                } else if (currentIcon == Resources.FOX_TAIL_LEFT && x - 1 < 5 && x - 1 > -1 &&
                           (!board.isOccupied(x - 1, y) && board.tileType(x - 1, y))) {
                    ((JButton) e.getSource()).setIcon(currentIcon);
                    buttons[x - 1][y].setIcon(Resources.FOX_HEAD_LEFT);
                }
            }
        }
    }

    /**
     * This method is used to delete the piece when the mouse moves outside a square
     * on the grid.
     */
    @Override
    public void mouseExited(MouseEvent e) {
        String[] position = ((JButton) e.getSource()).getName().split(",");
        int x = Integer.parseInt(position[0]);
        int y = Integer.parseInt(position[1]);
        if (!board.isOccupied(x, y)) {
            ((JButton) e.getSource()).setIcon(null);
            if (currentIcon == Resources.FOX_HEAD_UP && y + 1 < 5 && y + 1 > -1 && (!board.isOccupied(x, y + 1))) {
                buttons[x][y + 1].setIcon(null);
            } else if (currentIcon == Resources.FOX_TAIL_UP && y - 1 < 5 && y - 1 > -1 &&
                       (!board.isOccupied(x, y - 1))) {
                buttons[x][y - 1].setIcon(null);
            } else if (currentIcon == Resources.FOX_HEAD_LEFT && x + 1 < 5 && x + 1 > -1 &&
                       (!board.isOccupied(x + 1, y))) {
                buttons[x + 1][y].setIcon(null);
            } else if (currentIcon == Resources.FOX_TAIL_LEFT && x - 1 < 5 && x - 1 > -1 &&
                       (!board.isOccupied(x - 1, y))) {
                buttons[x - 1][y].setIcon(null);
            }
        }
    }

    /**
     * Updates the buttons when there is a change on the board
     */
    @Override
    public void handleBoardChange() {
        GUIUtilities.updateView(buttons, board);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuMainScreen &&
            GUIUtilities.displayOptionDialog(null, "Are you sure you want to return to main menu?",
                    "Return to " + "Main " + "Menu", new String[]{"Yes", "No"}) == 0) {
            this.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        } else if (e.getSource() == saveBoard) {
            Board boardCopy = new Board(board);
            do {
                Move temp = Solver.getNextBestMove(boardCopy);
                if (temp.direction() == MoveDirection.INVALID || temp.xStart == -1) {
                    JPanel panel = new JPanel(new BorderLayout(0, 15));
                    panel.add(new JLabel("This board cannot be solved. Please try again!"), BorderLayout.NORTH);
                    JOptionPane.showMessageDialog(this, panel, "Save", JOptionPane.INFORMATION_MESSAGE);
                }
                boardCopy.move(temp);
            } while ((!boardCopy.isInWinningState()));
            String levelNameString;
            do {
                levelNameString = JOptionPane.showInputDialog("Please enter a name for the level: ");
                if (levelNameString == null) return;
                while (levelNameString.matches("-?\\d+"))
                    levelNameString = JOptionPane.showInputDialog("No numbers are allowed in the level name: ");
                board.setName(levelNameString);
            } while (!(Resources.addUserLevel(board)));
            JPanel panel = new JPanel(new BorderLayout(0, 15));
            panel.add(new JLabel("The board has been saved successfully"), BorderLayout.NORTH);
            JOptionPane.showMessageDialog(this, panel, "Save", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == menuHelp) {
            JPanel panel = new JPanel(new BorderLayout(0, 15));
            panel.add(new JLabel("<html><body><p style='width: 200px; text-align: justify'>" +
                                 "This is the level builder for Rabbits and Foxes!" + "<br><br>" +
                                 "Place Piece: Select a piece form the side menu by clicking on it. The piece can " +
                                 "then be placed on the board by clicking on an empty location." + "<br>" + "<br>" +
                                 "Delete Piece: Select a piece from the board and click this button to delete the " +
                                 "piece" + "<br>" + "<br>" +
                                 "Flip Fox: A fox can be flipped after placement. Select a fox from the board and " +
                                 "click this button to flip it 180 degrees." + "<br>" + "<br>" +
                                 "Please note: In this game the user is restricted to 3 mushrooms, 2 foxes, and 3 " +
                                 "different colour rabbits." + "</p></body></html>"), BorderLayout.NORTH);
            JOptionPane.showMessageDialog(this, panel, "Help Dialog", JOptionPane.INFORMATION_MESSAGE);
        } else if ((e.getSource() == menuReset) && (GUIUtilities.displayOptionDialog(this,
                "Are you sure you want to reset the level builder? (Your " +
                "progress will be lost)", "Reset Rabbits and Foxes!", new String[]{"Yes", "No"}) == 0)) {
            resetBoard();
        } else if (e.getSource() == deletePiece) {
            if (itemToBeModifiedX == -1 && itemToBeModifiedY == -1) {
                JPanel panel = new JPanel(new BorderLayout(0, 15));
                panel.add(new JLabel("Please select a piece to delete."), BorderLayout.NORTH);
                JOptionPane.showMessageDialog(this, panel, "Delete", JOptionPane.INFORMATION_MESSAGE);
            } else if (board.isOccupied(itemToBeModifiedX, itemToBeModifiedY)) {
                if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.RABBIT_WHITE) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    rabbitWhite.setEnabled(true);
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.RABBIT_BROWN) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    rabbitBrown.setEnabled(true);
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.RABBIT_GRAY) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    rabbitGray.setEnabled(true);
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.MUSHROOM) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    if (numberOfMushrooms == 3) {
                        mushroom.setEnabled(true);
                    }
                    numberOfMushrooms--;
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_UP ||
                           buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_DOWN) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY + 1);
                    foxRemoved();
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_UP ||
                           buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_DOWN) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY - 1);
                    foxRemoved();
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_LEFT ||
                           buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_RIGHT) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    board.removePiece(itemToBeModifiedX + 1, itemToBeModifiedY);
                    foxRemoved();
                } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_LEFT ||
                           buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_RIGHT) {
                    board.removePiece(itemToBeModifiedX, itemToBeModifiedY);
                    board.removePiece(itemToBeModifiedX - 1, itemToBeModifiedY);
                    foxRemoved();
                }
            }
            itemToBeModifiedX = -1;
            itemToBeModifiedY = -1;
            GUIUtilities.updateView(buttons, board);
        } else if (e.getSource() == flipFox) {
            boolean flipped = false;
            if (itemToBeModifiedX != -1 && itemToBeModifiedY != -1) {
                if (board.getPiece(itemToBeModifiedX, itemToBeModifiedY) instanceof Fox) {
                    Fox oldFox = (Fox) board.getPiece(itemToBeModifiedX, itemToBeModifiedY);
                    if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_UP) {
                        Fox fox = new Fox(Direction.DOWN, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY + 1);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_UP) {
                        Fox fox = new Fox(Direction.DOWN, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY - 1);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_LEFT) {
                        Fox fox = new Fox(Direction.RIGHT, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX + 1, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_LEFT) {
                        Fox fox = new Fox(Direction.RIGHT, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX - 1, itemToBeModifiedY);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_DOWN) {
                        Fox fox = new Fox(Direction.UP, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY - 1);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_DOWN) {
                        Fox fox = new Fox(Direction.UP, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY + 1);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_HEAD_RIGHT) {
                        Fox fox = new Fox(Direction.LEFT, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX - 1, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX, itemToBeModifiedY);
                        flipped = true;
                    } else if (buttons[itemToBeModifiedX][itemToBeModifiedY].getIcon() == Resources.FOX_TAIL_RIGHT) {
                        Fox fox = new Fox(Direction.LEFT, oldFox.id());
                        board.setPiece(fox, itemToBeModifiedX, itemToBeModifiedY);
                        board.setPiece(fox.getOtherHalf(), itemToBeModifiedX + 1, itemToBeModifiedY);
                        flipped = true;
                    }
                }
            }

            if (!flipped) {
                JPanel panel = new JPanel(new BorderLayout(0, 15));
                panel.add(new JLabel("Please select a fox."), BorderLayout.NORTH);
                JOptionPane.showMessageDialog(this, panel, "Rotate", JOptionPane.INFORMATION_MESSAGE);
            } else {
                itemToBeModifiedX = -1;
                itemToBeModifiedY = -1;
                GUIUtilities.updateView(buttons, board);
            }
        } else if (e.getSource() == verticalFH) {
            currentIcon = Resources.FOX_HEAD_UP;
        } else if (e.getSource() == verticalFT) {
            currentIcon = Resources.FOX_TAIL_UP;
        } else if (e.getSource() == horizontalFH) {
            currentIcon = Resources.FOX_HEAD_LEFT;
        } else if (e.getSource() == horizontalFT) {
            currentIcon = Resources.FOX_TAIL_LEFT;
        } else if (e.getSource() == rabbitWhite) {
            currentIcon = Resources.RABBIT_WHITE;
        } else if (e.getSource() == rabbitGray) {
            currentIcon = Resources.RABBIT_GRAY;
        } else if (e.getSource() == rabbitBrown) {
            currentIcon = Resources.RABBIT_BROWN;
        } else if (e.getSource() == mushroom) {
            currentIcon = Resources.MUSHROOM;
        }
        GUIUtilities.clearButtonBorders(buttons);
    }

    /**
     * This method is used to delete the foxes that have been placed on the board.
     */
    private void foxRemoved() {
        if (numberOfFoxes == 2) {
            horizontalFH.setEnabled(true);
            horizontalFT.setEnabled(true);
            verticalFH.setEnabled(true);
            verticalFT.setEnabled(true);
        }
        numberOfFoxes--;
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
}
