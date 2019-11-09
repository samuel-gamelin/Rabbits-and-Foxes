package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ColorUIResource;

import controller.GameController;
import controller.GameController.ClickValidity;
import model.Board;
import model.BoardListener;
import model.Fox;
import model.Mushroom;
import model.Piece;
import model.Rabbit;
import model.Rabbit.RabbitColour;
import resources.Resources;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author John Breton
 * @version 3.0
 */
public class GameView extends MouseAdapter implements BoardListener, ActionListener, MouseListener {
	private JFrame mainMenuFrame;
	private JFrame gameFrame;

	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuQuit;
	private JButton menuHint;

	private JButton btnStart;
	private JButton btnHelp;
	private JButton btnQuit;

	private JButton[][] buttons;

	private Board board;

	private GameController gameController;

	private BevelBorder selectedBorder;
	private EmptyBorder blankBorder;

	private Clip wrongMove;

	/**
	 * Creates the application GUI.
	 */
	public GameView() {
		// Forces the look and feel of the application to remain consistent across
		// platforms
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// Removes focus border from all buttons
		UIManager.getLookAndFeelDefaults().put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));

		// Setting up the borders used for JButtons
		selectedBorder = new BevelBorder(BevelBorder.RAISED, Color.RED, Color.RED);
		blankBorder = new EmptyBorder(0, 0, 0, 0);

		/**
		 * 
		 * Main menu
		 * 
		 */
		mainMenuFrame = new JFrame("Rabbit and Foxes!");
		mainMenuFrame.setIconImage(Resources.WINDOW_ICON.getImage());

		// Box Layout for main menu
		JLabel mainMenuPane = new JLabel(Resources.MAIN_MENU_BACKGROUND);
		mainMenuPane.setLayout(new BoxLayout(mainMenuPane, BoxLayout.Y_AXIS));
		Container mainMenuContainer = mainMenuFrame.getContentPane();

		btnStart = new JButton("Start");
		btnHelp = new JButton("Help");
		btnQuit = new JButton("Quit");
		addMenuButton(mainMenuPane, btnStart);
		addMenuButton(mainMenuPane, btnHelp);
		addMenuButton(mainMenuPane, btnQuit);
		mainMenuContainer.add(mainMenuPane);

		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenuFrame.setSize((int) Resources.SIDE_LENGTH, (int) Resources.SIDE_LENGTH);
		mainMenuFrame.setResizable(false);
		mainMenuFrame.setLocationRelativeTo(null);
		mainMenuFrame.pack();
		mainMenuFrame.setVisible(true);

		/**
		 * 
		 * Game frame
		 * 
		 */

		gameFrame = new JFrame("Rabbit and Foxes!");
		// BorderLayout for game frame
		Container gamePane = gameFrame.getContentPane();
		gamePane.setLayout(new BorderLayout());

		// Menu bar
		JMenuBar menuBar = new JMenuBar();
		
		menuHint = createMenuBarButton("Hint", true);
		menuHelp = createMenuBarButton("Help", false);
		menuReset = createMenuBarButton("Reset", false);
		menuQuit = createMenuBarButton("Quit", true);
		
		menuBar.add(menuHint);
		menuBar.add(menuHelp);
		menuBar.add(menuReset);
		menuBar.add(menuQuit);
		
		gamePane.add(menuBar, BorderLayout.NORTH);

		// GridLayout for board frame
		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));
		gamePane.add(boardLabel, BorderLayout.CENTER);

		// Organize the game frame
		gameFrame.setIconImage(Resources.WINDOW_ICON.getImage());
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.pack();
		gameFrame.setLocationRelativeTo(null);

		// Create the board and controller
		board = new Board();
		board.addListener(this);
		gameController = new GameController(board);

		// Create all buttons
		buttons = new JButton[5][5];

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[j][i] = new JButton();
				// Clear button default colours and make it transparent
				buttons[j][i].setOpaque(false);
				buttons[j][i].setContentAreaFilled(false);
				buttons[j][i].setBorder(blankBorder);

				boardLabel.add(buttons[j][i]);
				buttons[j][i].addMouseListener(this);

				// Register an anonymous listener on the button which notifies the controller
				// whenever a move is made (i.e. a button is clicked)
				final int x = j;
				final int y = i;

				// Register an anonymous listener on the button which notifies the controller
				// whenever a move is made (i.e. a button is clicked)
				buttons[j][i].addActionListener(e -> {
					ClickValidity clickResult = gameController.registerClick(x, y);
					if (clickResult.equals(ClickValidity.VALID)) {
						buttons[x][y].setBorder(selectedBorder);
					} else if (clickResult.equals(ClickValidity.VALID_MOVEMADE)) {
						clearButtonBorders();
					} else if (clickResult.equals(ClickValidity.INVALID)
							|| clickResult.equals(ClickValidity.INVALID_MOVEMADE)) {
						clearButtonBorders();
						gameController.clearPendingPosition();
						if (wrongMove == null || !wrongMove.isActive()) {
							try {
								wrongMove = AudioSystem.getClip();
								wrongMove.open(AudioSystem.getAudioInputStream(Resources.INVALID_MOVE));
							} catch (Exception ex) {
								ex.printStackTrace(System.out);
							}
							wrongMove.start();
						}
					}
				});
			}
		}

		// Configure the escape key to cancel the pending move
		boardLabel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "clear");
		boardLabel.getActionMap().put("clear", new AbstractAction() {
			private static final long serialVersionUID = -7863091829633095216L;

			@Override
			public void actionPerformed(ActionEvent e) {
				clearButtonBorders();
				gameController.clearPendingPosition();
			}
		});

		updateView();

		btnStart.addActionListener(e -> {
			mainMenuFrame.dispose();
			gameFrame.setVisible(true);
			helpDialog();
		});
		btnHelp.addActionListener(this);
		btnQuit.addActionListener(this);

		menuReset.addActionListener(this);
		menuHelp.addActionListener(this);
		menuQuit.addActionListener(this);
		menuHint.addActionListener(this);
	}

	/**
	 * Adds a button to the specified pane. Used in building the menu.
	 * 
	 * @param pane   The pane to which to add the specified button
	 * @param button The button to add
	 */
	private void addMenuButton(Container pane, JButton button) {
		pane.add(Box.createRigidArea(new Dimension(0, (int) (Resources.SIDE_LENGTH / 7))));
		button.setMaximumSize(new Dimension((int) Resources.SIDE_LENGTH / 2, (int) (0.15 * Resources.SIDE_LENGTH)));
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		pane.add(button);
	}

	/**
	 * Creates and returns a JButton suitable for the game's menu bar.
	 * 
	 * @param text The text inside the button
	 * @return The newly created JButton
	 */
	private JButton createMenuBarButton(String text, boolean enableShortcut) {
		JButton button = new JButton("<html><p style='text-align:center;'>" + text + "</p></html>");
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		button.setBorderPainted(false);
		
		if (enableShortcut) {
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
					.put(KeyStroke.getKeyStroke(Character.toLowerCase(text.charAt(0))), text);
			button.getActionMap().put(text, new AbstractAction() {
				private static final long serialVersionUID = -4044080289796171300L;
	
				@Override
				public void actionPerformed(ActionEvent e) {
					button.doClick();
				}
			});
		}
		return button;
	}

	/**
	 * Clears the borders on all buttons.
	 */
	private void clearButtonBorders() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setBorder(blankBorder);
			}
		}
	}

	/**
	 * Updates the visual representation of the board.
	 */
	private void updateView() {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				Piece piece = board.getPiece(i, j);
				if (piece != null) {
					if (piece instanceof Mushroom) {
						(buttons[i][j]).setIcon(Resources.MUSHROOM);
					} else if (piece instanceof Rabbit) {
						if (((Rabbit) (piece)).getColour().equals(RabbitColour.BROWN)) {
							(buttons[i][j]).setIcon(Resources.RABBIT1);
						} else if (((Rabbit) (piece)).getColour().equals(RabbitColour.WHITE)) {
							(buttons[i][j]).setIcon(Resources.RABBIT2);
						} else {
							(buttons[i][j]).setIcon(Resources.RABBIT3);
						}
					} else if (piece instanceof Fox) {
						try {
							(buttons[i][j]).setIcon((ImageIcon) Resources.class.getDeclaredField(
									"FOX_" + ((Fox) (piece)).getFoxType() + "_" + ((Fox) (piece)).getDirection())
									.get(Resources.class));
						} catch (Exception e) {
							e.printStackTrace(System.out);
						}
					}
				} else {
					buttons[i][j].setIcon(null);
				}
			}
		}
	}

	/**
	 * Enables or disables all buttons, depending on the specified state.
	 * 
	 * @param state The state in which all buttons should be set
	 */
	private void gameEndBoard(boolean state) {
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				buttons[i][j].setEnabled(state);
			}
		}
	}

	/**
	 * Resets the game.
	 */
	private void gameWinReset() {
		this.board = gameController.reset();
		this.board.addListener(this);
		updateView();
		gameEndBoard(true);
	}

	/**
	 * Pops up help dialog.
	 */
	private void helpDialog() {
		JOptionPane.showMessageDialog(null, "<html><body><p style='width: 200px; text-align: justify'>"
				+ "Rabbits and Foxes is a game in which you must get all rabbits to safety by having them land in brown holes. "
				+ "To do this, rabbits can only jump over other pieces and must land in an empty hole. "
				+ "Foxes can slide along their initial direction as long as no other piece obstructs their way.<br><br>"
				+ "Hint (h): Outlines the next best move<br>" + "Help: Displays the help menu<br>"
				+ "Reset  (r):   Restarts the game<br>" + "Quit   (q):   Exits the application<br>"
				+ "Escape (ESC): Clears the pending move" + "</p></body></html>", "Help",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Handles button input for the menus.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnHelp) {
			JOptionPane.showMessageDialog(null,
					"Start: Starts the game\n" + "Help: Displays the help menu\n" + "Quit: Exits the application",
					"Help", JOptionPane.INFORMATION_MESSAGE);
		} else if (e.getSource() == menuHint) {
			gameController.printHint();
		} else if (e.getSource() == menuHelp) {
			helpDialog();
		} else if (e.getSource() == btnQuit || e.getSource() == menuQuit) {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Rabbit and Foxes!",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				System.exit(0);
		} else if ((e.getSource() == menuReset) && (JOptionPane.showConfirmDialog(null,
				"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbit and Foxes!",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
			gameWinReset();
		}
	}

	@Override
	public void handleBoardChange() {
		updateView();
		if (board.isInWinningState()) {
			clearButtonBorders();
			if (JOptionPane.showOptionDialog(null, "Congrats, you win! Thank you for playing. Reset or quit?",
					"Game over!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					new String[] { "Reset", "Quit" }, null) == 0) {
				gameWinReset();
			} else {
				System.exit(0);
			}
		}
	}

	/**
	 * Highlights a JButton when we enter the component with the mouse cursor.
	 * 
	 * @param e The mouse event that triggers when the mouse enters the JButton
	 */
	public void mouseEntered(MouseEvent e) {
		if (!((JButton) e.getSource()).getBorder().equals(selectedBorder)) {
			((JButton) e.getSource()).setBorder(UIManager.getBorder("Button.border"));
		}
	}

	/**
	 * Stops highlighting a JButton when the mouse cursor leaves the component.
	 * 
	 * @param e The mouse event that triggers when the mouse leaves the JButton
	 */
	public void mouseExited(MouseEvent e) {
		if (!((JButton) e.getSource()).getBorder().equals(blankBorder)
				&& !((JButton) e.getSource()).getBorder().equals(selectedBorder)) {
			((JButton) e.getSource()).setBorder(blankBorder);
		}
	}

	/**
	 * Starts the Rabbits and Foxes game.
	 * 
	 * @param args The command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GameView());
	}
}
