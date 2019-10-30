package view;

import java.awt.event.*; 
import javax.swing.*;
import controller.GameController;
import model.Board;
import model.BoardEvent;
import model.BoardListener;
import resources.Resources;

/**
 * This class represents the view with which the user interacts in order to play
 * the game.
 * 
 * @author Dani Hashweh
 * @author Mohamed Radwan
 * @author Samuel Gamelin
 * @version 2.0
 */
public class GameView extends JFrame implements BoardListener {
	private static final long serialVersionUID = 1L;

	private JMenuBar menuBar;

	private JMenuItem menuStart;
	private JMenuItem menuPause;
	private JMenuItem menuReset;
	private JMenuItem menuQuit;
	private JMenuItem menuHelp; 

	private JButton buttons[][];

	private Board board;
	private GameController gameController;

	public GameView() {
		board = new Board();
		board.addListener(this);

		gameController = new GameController(board);
		
		this.setContentPane(new JLabel(Resources.BOARD));
		this.setSize(875,925);
		setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		menuBar = new JMenuBar();

		menuStart = new JMenuItem("Start");
		menuPause = new JMenuItem("Pause");
		menuReset = new JMenuItem("Reset");
		menuHelp = new JMenuItem("Help");
		menuQuit = new JMenuItem("Quit");

		menuBar.add(menuStart);
		menuBar.add(menuPause);
		menuBar.add(menuReset);
		menuBar.add(menuHelp);
		menuBar.add(menuQuit);
		
		this.setJMenuBar(menuBar);

		
		menuQuit.addActionListener(e-> {
			if (JOptionPane.showConfirmDialog(null,"Are you sure you want to exit?","Exit JumpIN!",
		            JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
		            System.exit(0);
		});

		menuHelp.addActionListener(e-> {
			JOptionPane.showMessageDialog(this, "Start: Starts the game. \n"
											+	"Pause: Pauses the game, and clicking it again, will resume the game. \n"
											+ 	"Reset: Restarts the game. \n"
											+   "Quit: Exits the application"
					);
		});
		
		menuStart.addActionListener(e-> {
			
		});
		
//		buttons = new JButton[Board.SIZE][Board.SIZE];
//		for (int i = 0; i < Board.SIZE; i++) {
//			for (int j = 0; j < Board.SIZE; j++) {
//				JButton button = new JButton("_");
//				buttons[i][j] = button;
//				this.add(button);
//			}
//		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameView();
			}
		});
	}
}
