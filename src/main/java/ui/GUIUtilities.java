package ui;

import model.*;
import resources.Resources;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.*;

/**
 * This class represents the GUI utilities shared by the game's frames.
 *
 * @author Samuel Gamelin
 * @author Dani Hashweh
 * @author John Breton
 * @author Mohamed Radwan
 * @version 4.0
 */
public final class GUIUtilities {
    /**
     * An empty border.
     */
    static final EmptyBorder BLANK_BORDER = new EmptyBorder(0, 0, 0, 0);

    /**
     * A selected border.
     */
    static final BevelBorder SELECTED_BORDER = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);

    /**
     * A border for the start of a hint.
     */
    static final BevelBorder HINT_BORDER_START = new BevelBorder(BevelBorder.RAISED, Color.YELLOW, Color.YELLOW);

    /**
     * A border for the end of a hint.
     */
    static final BevelBorder HINT_BORDER_END = new BevelBorder(BevelBorder.RAISED, Color.GREEN, Color.GREEN);

    /**
     * A border for the start of a hint.
     */
    static final BevelBorder POSSIBLE_POSITION_BORDER = new BevelBorder(BevelBorder.RAISED, Color.BLUE, Color.BLUE);

    /**
     * A percentage (80%) of the current display's height (or width, depending on
     * which is greater), which will be used in calculations to determine
     * appropriate scaling of icons and GUI elements.
     */
    public static final double SIDE_LENGTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth() > Toolkit
            .getDefaultToolkit().getScreenSize().getHeight()
            ? (0.8 * Toolkit.getDefaultToolkit().getScreenSize().getHeight())
            : (0.8 * Toolkit.getDefaultToolkit().getScreenSize().getWidth());

    /**
     * Font size, as determined by display dimensions.
     */
    static final int FONT_SIZE = (int) (SIDE_LENGTH / 25);

    /**
     * A file chooser used to pick files.
     */
    static JFileChooser fc = new JFileChooser();

    /**
     * A private constructor, preventing any instantiation of this class.
     */
    private GUIUtilities() {
    }

    /**
     * Configures the provided frame with default frame configurations.
     *
     * @param frame The frame to configure
     */
    public static void configureFrame(JFrame frame) {
        frame.setIconImage(Resources.WINDOW_ICON.getImage());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (GUIUtilities.displayOptionDialog(frame, "Are you sure you want to exit?", "Exit Rabbits and Foxes!",
                        new String[]{"Yes", "No"}) == 0) {
                    System.exit(0);
                }
            }
        });
    }

    /**
     * Forces the look and feel of the application to remain consistent across
     * platforms, and removes the focus border form all buttons.
     */
    public static void applyDefaults() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Resources.LOGGER.error("Could not set the default look and feel", e);
        }
        UIManager.getLookAndFeelDefaults().put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
        fc = new JFileChooser();
    }

    /**
     * Displays an informational message dialog.
     *
     * @param parent  The parent component of this option dialog
     * @param message The message to display
     * @param title   The title of the dialog box
     */
    public static void displayMessageDialog(Component parent, String message, String title) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Displays an option dialog, returning the choice selected by the user.
     *
     * @param parent  The parent component of this option dialog
     * @param message The message to display
     * @param title   The title of the dialog box
     * @param options The options to be provided in the dialog - the initial option
     *                selected is the first element of the provided object array
     * @return The choice made by the user
     */
    public static int displayOptionDialog(Component parent, String message, String title, Object[] options) {
        return JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
    }

    /**
     * Binds the specified keystroke to the specified JComponent.
     *
     * @param component  The component on which the keystroke should be bound
     * @param keystroke  The keystroke to bind
     * @param actionName The name of keystroke action
     * @param method     The method to execute when the keystroke is activated
     */
    public static void bindKeyStroke(JComponent component, String keystroke, String actionName, Runnable method) {
        if (keystroke.length() == 1) {
            component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keystroke.charAt(0)),
                    actionName);
        } else {
            component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keystroke), actionName);
        }
        component.getActionMap().put(actionName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                method.run();
            }
        });
    }

    /**
     * Creates and returns a JButton suitable for the game's menu bar.
     *
     * @param text           The text inside the button
     * @param enableShortcut True if the corresponding shortcut should be enabled,
     *                       false otherwise
     * @return The newly created JButton
     */
    public static JButton createMenuBarButton(String text, boolean enableShortcut) {
        JButton button = new JButton("<html><p style='text-align:center;'>" + text + "</p></html>");
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);

        if (enableShortcut) {
            GUIUtilities.bindKeyStroke(button, String.valueOf(Character.toLowerCase(text.charAt(0))), text,
                    button::doClick);
        }
        return button;
    }

    /**
     * Configures and returns a button intended to be used as part of a board.
     *
     * @param component The component to which the newly-created JButton should be tied (for mouse events) and added to
     * @param x The x-coordinate of the JButton
     * @param y The y-coordinate of the JButton
     * @param <C> Type parameter restrictions for the specified component
     * @return The newly-created JButton
     */
    public static <C extends Container & MouseListener> JButton generateGameBoardButton(C component, int x, int y) {
        JButton button = new JButton();
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(GUIUtilities.BLANK_BORDER);
        button.setName(x + "," + y);
        button.addMouseListener(component);
        component.add(button);

        return button;
    }

    /**
     * Stylizes a button with default configurations that is intended to be used in any frame.
     *
     * @param button The button to stylize
     * @param actionListener The listener for this button
     */
    public static void stylizeButton(JButton button, ActionListener actionListener) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Times New Roman", Font.PLAIN, GUIUtilities.FONT_SIZE));
        button.addActionListener(actionListener);
    }

    /**
     * Updates the visual representation of the specified two-dimensional array of buttons based on the provided board.
     *
     * @param buttons The buttons that are to be updated
     * @param board   The corresponding board
     */
    public static void updateView(JButton[][] buttons, Board board) {
        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Piece piece = board.getPiece(x, y);
                if (piece != null) {
                    if (piece instanceof Mushroom) {
                        buttons[x][y].setIcon(Resources.MUSHROOM);
                    } else if (piece instanceof Rabbit) {
                        buttons[x][y].setIcon(Resources.getImageIconByName("RABBIT_" + ((Rabbit) (piece)).getColour()));
                    } else {
                        buttons[x][y].setIcon(Resources.getImageIconByName(
                                "FOX_" + ((Fox) (piece)).getFoxType() + "_" + ((Fox) (piece)).getDirection()));
                    }
                } else {
                    buttons[x][y].setIcon(null);
                }
            }
        }
    }
}
