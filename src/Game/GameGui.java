package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameGui extends JFrame {
	
	JLabel title;
	JButton play;
	JButton quit;
	JPanel panel;
	JPanel buttonPanel;
	JPanel titlePanel;
	
	
	public GameGui(){
		title = new JLabel("Dungeon Game");
		play = new JButton("Start Game");
		quit = new JButton("Quit");
		panel = new JPanel();
		panel.setBackground(Color.decode("#B2AC88"));
		panel.setLayout(new BorderLayout());
		
		
		titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.Y_AXIS));
		panel.add(titlePanel, BorderLayout.NORTH);
		title.setFont(new Font("Times New Roman", Font.BOLD, 70));
		title.setForeground(Color.WHITE);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
	    title.setBorder(border);
	    titlePanel.add(title);
		titlePanel.add(Box.createRigidArea(new Dimension(0,100)));
		titlePanel.setBackground(Color.decode("#B2AC88"));
		title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.Y_AXIS));
		panel.add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.add(play);
		buttonPanel.add(Box.createRigidArea(new Dimension(0,20)));
		buttonPanel.setBackground(Color.decode("#B2AC88"));
		play.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		buttonPanel.add(quit);
		quit.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
		setLocationRelativeTo(null);
		setVisible(true);
		
		quit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				
			}
			
		});
		
		play.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new DungeonGame();
				setVisible(false);
				dispose();
				
			}
			
		});
		
	}
	
	public static void main(String[] args) {
		new GameGui();
	}
}
