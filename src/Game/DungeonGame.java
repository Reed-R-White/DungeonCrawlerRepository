package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame {
    private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    
    private JFrame gameWindow;
    
    private DungeonPlayer player1;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        player1 = new DungeonPlayer(gameWindow);
        EnemyPlayer test = new EnemyPlayer(gameWindow, 10, 10, 10, 10, player1);
        
        gameWindow.addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		            if(player1.attack(new Point((int) test.getX(), (int) test.getY()))) {
		            	System.out.println("hit");
		            }
		            else {
		            	System.out.println("miss");
		            }
		        }
		    }
		    
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
    }

    public static void main(String[] args) {
        new DungeonGame();
    }
}




