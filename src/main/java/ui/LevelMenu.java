package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import resources.Resources;

public class LevelMenu extends JFrame {
	
	private JButton btnSelectLevel;
	private JButton btnStartSelectLevel;
	
	public LevelMenu() {
		this.setTitle("Level Selector");
		this.setIconImage(Resources.WINDOW_ICON.getImage());
		this.setContentPane(new JLabel(Resources.LEVEL_SELECTOR_BACKGROUND));
		this.getContentPane().setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.add(btnStartSelectLevel, BorderLayout.SOUTH);
		this.add(padding, BorderLayout.CENTER);
		
	}
}
