package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Board;
import model.BoardListener;
import model.Fox;
import model.Mushroom;
import model.Rabbit;
import model.Rabbit.RabbitColour;
import resources.Resources;

/**
 * This class represents a level builder which allows the user to create and
 * save their own levels.
 * 
 * @author Abdalla El Nakla
 * @author Mohamed Radwan
 * @version 4.0
 */
public class LevelBuilder extends JFrame implements ActionListener, MouseListener, BoardListener {

	private static final int NUMBER_OF_RABBITS = 3;
	private static final int NUMBER_OF_FOXES = 2;
	private static final int NUMBER_OF_MUSHROOMS = 3;

	private JButton menuReset;
	private JButton menuHelp;
	private JButton menuMainScreen;
	private JButton saveBoard;

	private int numRab = 1;
	private int numMush = 1;
	private int numFox = 1;

	private JButton mushroom;
	private JButton rabbit;
	private JButton fox;
	private ImageIcon currentIcon;
	private ImageIcon foxTail;

	JMenuItem upFox, downFox, rightFox, leftFox;

	private Board board;
	private JButton[][] buttons;

	public LevelBuilder() {

		this.board = new Board("");
		board.addListener(this);
		this.setTitle("Level Builder");

		currentIcon = null;
		foxTail = null;

		JLabel boardLabel = new JLabel(Resources.BOARD);
		boardLabel.setLayout(new GridLayout(5, 5));

		this.add(boardLabel, BorderLayout.CENTER);

		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		menuMainScreen = GUIUtilities.createMenuBarButton("Main Menu", true);
		menuReset = GUIUtilities.createMenuBarButton("Reset", false);
		saveBoard = GUIUtilities.createMenuBarButton("Save", true);
		menuHelp = GUIUtilities.createMenuBarButton("Help", false);

		menuBar.add(menuMainScreen);
		menuBar.add(menuReset);
		menuBar.add(saveBoard);
		menuBar.add(menuHelp);

		JMenu fileMenu = new JMenu("Fox");

		upFox = new JMenuItem("Upward");
		downFox = new JMenuItem("downward");
		leftFox = new JMenuItem("Left");
		rightFox = new JMenuItem("Right");

		fileMenu.add(upFox);
		fileMenu.add(downFox);
		fileMenu.add(leftFox);
		fileMenu.add(rightFox);
		menuBar.add(fileMenu);

		mushroom = ui.GUIUtilities.createMenuBarButton("Mushroom", true);
		rabbit = ui.GUIUtilities.createMenuBarButton("Rabbits", true);
		menuBar.add(mushroom);
		menuBar.add(rabbit);

		this.setJMenuBar(menuBar);
		this.setContentPane(new JLabel(Resources.BOARD));
		this.getContentPane().setLayout(new GridLayout(5, 5));

		JLabel gameContentPane = (JLabel) this.getContentPane();

		buttons = new JButton[5][5];
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				buttons[x][y] = new JButton();
				// Clear button default colours and make it transparent
				buttons[x][y].setOpaque(false);
				buttons[x][y].setContentAreaFilled(false);
				buttons[x][y].setBorder(GUIUtilities.BLANK_BORDER);
				buttons[x][y].setName(x + "," + y);
				gameContentPane.add(buttons[x][y]);
				buttons[x][y].addMouseListener(this);

				final int xCopy = x;
				final int yCopy = y;

				buttons[x][y].addActionListener(e -> {
					buttons[xCopy][yCopy].setIcon(currentIcon);
					if (currentIcon == Resources.MUSHROOM) {
						board.setPiece(new Mushroom(), xCopy, yCopy);
						numberOfMushrooms();
					} else if (currentIcon == Resources.FOX_HEAD_UP) {
						Fox upFox = new Fox(Fox.Direction.UP, true);
						board.setPiece(upFox, xCopy, yCopy);
						board.setPiece(upFox.getOtherHalf(), xCopy, yCopy+1);
						numberOfFox();
					} else if (currentIcon == Resources.FOX_HEAD_DOWN) {
						Fox downFox = new Fox(Fox.Direction.DOWN, true);
						board.setPiece(downFox, xCopy, yCopy);
						board.setPiece(downFox.getOtherHalf(), xCopy, yCopy - 1);
						numberOfFox();
					} else if (currentIcon == Resources.FOX_HEAD_LEFT) {
						Fox leftFox = new Fox(Fox.Direction.LEFT, true);
						board.setPiece(leftFox, xCopy, yCopy);
						board.setPiece(leftFox.getOtherHalf(), xCopy + 1, yCopy);
						numberOfFox();
					} else if (currentIcon == Resources.FOX_HEAD_RIGHT) {
						Fox rightFox = new Fox(Fox.Direction.RIGHT, true);
						board.setPiece(rightFox, xCopy, yCopy);
						board.setPiece(rightFox.getOtherHalf(), xCopy - 1, yCopy);
						numberOfFox();
					} else if (currentIcon == Resources.RABBIT_WHITE) {
						board.setPiece(new Rabbit(RabbitColour.WHITE), xCopy, yCopy);
						numberOfRabbits();
					} else if (currentIcon == Resources.RABBIT_BROWN) {
						board.setPiece(new Rabbit(RabbitColour.BROWN), xCopy, yCopy);
						numberOfRabbits();
					} else if (currentIcon == Resources.RABBIT_GRAY) {
						board.setPiece(new Rabbit(RabbitColour.GRAY), xCopy, yCopy);
						numberOfRabbits();
					} else {
						currentIcon = null;
					}
				});

			}
		}
		mushroom.addActionListener(this);
		rabbit.addActionListener(this);
		upFox.addActionListener(this);
		downFox.addActionListener(this);
		leftFox.addActionListener(this);
		rightFox.addActionListener(this);

		menuMainScreen.addActionListener(this);
		menuReset.addActionListener(this);
		menuHelp.addActionListener(this);
		saveBoard.addActionListener(this);

		GUIUtilities.configureFrame(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuMainScreen
				&& GUIUtilities.displayOptionDialog(null, "Are you sure you want to return to main menu?",
						"Return to Main Menu", new String[] { "Yes", "No" }) == 0) {
			this.dispose();
			SwingUtilities.invokeLater(MainMenu::new);
		} else if (e.getSource() == saveBoard) {
			String levelNameString;
			do {
				levelNameString = JOptionPane.showInputDialog("Please enter a name for the level: ");
				while (levelNameString != null && levelNameString.matches("-?\\d+")) {
					levelNameString = JOptionPane.showInputDialog("No numbers are allowed in the level name: ");
				}
				board.setName(levelNameString);
			} while (!(Resources.addUserLevel(board) || levelNameString == null));
		} else if (e.getSource() == menuHelp) {
			JPanel panel = new JPanel(new BorderLayout(0, 15));
			panel.add(new JLabel("<html><body><p style='width: 200px; text-align: justify'>" + "" + "" + "<br><br>"
					+ "<br>" + "<br>" + "<br>" + "<br>" + "" + "</p></body></html>"), BorderLayout.NORTH);
			JOptionPane.showMessageDialog(this, panel, "Help Dialog", JOptionPane.INFORMATION_MESSAGE);
		} else if ((e.getSource() == menuReset) && (GUIUtilities.displayOptionDialog(this,
				"Are you sure you want to reset the game? (Your progress will be lost)", "Reset Rabbits and Foxes!",
				new String[] { "Yes", "No" }) == 0)) {
			resetBoard();
		} else if (e.getSource() == mushroom) {
			currentIcon = Resources.MUSHROOM;
		} else if (e.getSource() == upFox) {
			currentIcon = Resources.FOX_HEAD_UP;
			foxTail = Resources.FOX_TAIL_UP;
		} else if (e.getSource() == downFox) {
			currentIcon = Resources.FOX_HEAD_DOWN;
			foxTail = Resources.FOX_TAIL_DOWN;
		} else if (e.getSource() == leftFox) {
			currentIcon = Resources.FOX_HEAD_LEFT;
			foxTail = Resources.FOX_TAIL_LEFT;

		} else if (e.getSource() == rightFox) {
			currentIcon = Resources.FOX_HEAD_RIGHT;
			foxTail = Resources.FOX_TAIL_RIGHT;
		} else if (e.getSource() == rabbit) {
			if (numRab == 1) {
				currentIcon = Resources.RABBIT_WHITE;
			} else if (numRab == 2) {
				currentIcon = Resources.RABBIT_BROWN;
			} else if (numRab == 3) {
				currentIcon = Resources.RABBIT_GRAY;
			} else {
				currentIcon = null;
			}

		}
	}
	/**
	 * Resets the board.
	 */
	private void resetBoard() {
		board = new Board("");
		board.addListener(this);
		GUIUtilities.updateView(buttons, board);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (((JButton) e.getSource()).getIcon() == null) {
			((JButton) e.getSource()).setIcon(currentIcon);
			

			
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (!board.isOccupied(Integer.valueOf(((JButton) e.getSource()).getName().substring(0, 1)),
				(Integer.valueOf(((JButton) e.getSource()).getName().substring(2, 3))))) {
			((JButton) e.getSource()).setIcon(null);
		}
	}

	@Override
	public void handleBoardChange() {
		GUIUtilities.updateView(buttons, board);
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

	// Added for testing frame
	public static void main(String[] args) {
		SwingUtilities.invokeLater(LevelBuilder::new);
	}

	private void numberOfMushrooms() {
		if (numMush != NUMBER_OF_MUSHROOMS) {
			numMush++;
			mushroom.setEnabled(true);
		} else {
			mushroom.setEnabled(false);
			GUIUtilities.displayMessageDialog(this, "No more Mushrooms", "Mushrooms");
			currentIcon = null;

		}
	}

	private void numberOfRabbits() {
		if (numRab != NUMBER_OF_RABBITS) {
			numRab++;
			rabbit.setEnabled(true);
		} else {
			rabbit.setEnabled(false);
			GUIUtilities.displayMessageDialog(this, "No more rabbits", "rabbits");
			currentIcon = null;

		}
	}

	private void numberOfFox() {
		if (numFox != NUMBER_OF_FOXES) {
			numFox++;
			fox.setEnabled(true);
		} else {
			fox.setEnabled(false);
			GUIUtilities.displayMessageDialog(this, "No more foxes", "fox");
			currentIcon = null;

		}
	}

}
