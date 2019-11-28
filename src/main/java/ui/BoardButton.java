package ui;

import javax.swing.JButton;

/**
 * This class adds the x and y location to the JButton objects created on the
 * board.
 * 
 * @author Mohamed Radwan
 *
 */
public class BoardButton extends JButton {
    private int xLocation;
    private int yLocation;

    /**
     * Default constructor
     */
    public BoardButton() {
        this.xLocation = -1;
        this.yLocation = -1;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorder(GUIUtilities.BLANK_BORDER);
    }

    /**
     * This constructor used for the level builder to give each button its location
     * on the board.
     * 
     * @param x
     * @param y
     */
    public BoardButton(int x, int y) {
        this.xLocation = x;
        this.yLocation = y;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorder(GUIUtilities.BLANK_BORDER);
    }

    public int getxLocation() {
        return xLocation;
    }

    public int getyLocation() {
        return yLocation;
    }

}
