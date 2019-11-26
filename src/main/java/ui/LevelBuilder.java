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
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Board;
import model.BoardListener;
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

	// test
	private JButton mushroom;
	private JButton whiteRabbit;
	private ImageIcon currentIcon;

	private Board board;
	private JButton[][] buttons;

	public LevelBuilder() {
		this.board = new Board("");
		board.addListener(this);
		this.setTitle("Level Builder");

		currentIcon = null;

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

		// For testing to be removed
		mushroom = ui.GUIUtilities.createMenuBarButton("Mushroom", true);
		whiteRabbit = ui.GUIUtilities.createMenuBarButton("White Rabbit", true);
		menuBar.add(mushroom);
		menuBar.add(whiteRabbit);

		this.setJMenuBar(menuBar);
		this.setContentPane(new JLabel(Resources.BOARD));
		this.getContentPane().setLayout(new GridLayout(5, 5));

		JLabel gameContentPane = (JLabel) this.getContentPane();

		buttons = new JButton[5][5];
		for (int y = 0; y < Board.SIZE; y++) {
			for (int x = 0; x < Board.SIZE; x++) {
				buttons[x][y] = new JButton();
				buttons[x][y].setToolTipText(x + "," + y);
				buttons[x][y].setOpaque(false);
				buttons[x][y].setContentAreaFilled(false);
				buttons[x][y].setBorder(GUIUtilities.BLANK_BORDER);

				gameContentPane.add(buttons[x][y]);
				buttons[x][y].addMouseListener(this);

				final int xCopy = x;
				final int yCopy = y;

				buttons[x][y].addActionListener(e -> {
					buttons[xCopy][yCopy].setIcon(currentIcon);
					if (currentIcon == Resources.MUSHROOM) {
						board.setPiece(new Mushroom(), xCopy, yCopy);
					} else {
						board.setPiece(new Rabbit(RabbitColour.WHITE), xCopy, yCopy);
					}
				});

			}
		}
		// For testing
		mushroom.addActionListener(this);
		whiteRabbit.addActionListener(this);

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
				while(levelNameString.matches("-?\\d+")) {
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
		} else if (e.getSource() == whiteRabbit) {
			currentIcon = Resources.RABBIT_WHITE;
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
		String[] positionString = ((JButton) e.getSource()).getToolTipText().split(",");
		int xPosition = Integer.parseInt(positionString[0]);
		int yPosition = Integer.parseInt(positionString[1]);
		if (!board.isOccupied(xPosition, yPosition)) {
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
}
