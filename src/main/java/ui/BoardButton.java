package ui;

import javax.swing.JButton;

/**
 * 
 * @author Mohamed Radwan
 *
 */
public class BoardButton extends JButton {
    private int x;
    private int y;

    public BoardButton(int x, int y) {
        this.x = x;
        this.y = y;
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorder(GUIUtilities.BLANK_BORDER);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
