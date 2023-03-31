package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class DungeonPlayer {
	
	public static final int MAXMOVEMENT = 7;
	private int playerX;
	private int playerY;
	private Point newPosition;
	private Point currentPosition;
	private JLabel playerAvatar;
	private JFrame homeFrame;
	
	public DungeonPlayer(JFrame playerJFrame) {
		homeFrame = playerJFrame;
		playerX = playerJFrame.getWidth()/2;
		playerY = playerJFrame.getHeight()/2;

		newPosition = new Point(playerX, playerY);

		
		//Draw the player for the first time
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
		playerAvatar.setIcon(playerSprite);
		playerAvatar.setBounds(playerX, playerY, 50, 50);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);
	    
	}
	
	private void getLocal(int x, int y) {
    	//This variable is the number of columns.
		int numberOfBoxes = DungeonGame.gridWidth;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }

   	public void movePlayer(int dx, int dy){

		

		   // update player's position
		   playerX += (int) dx;
		   playerY += (int) dy;

		drawPlayer();
	}

	public void setNewTarget(int x,int y){
		newPosition.setLocation(x, y);
	}
	
	public Point getNewTarget(){
		return newPosition;
	}

	public int getX(){
		return playerX;
	}

	public int getY(){
		return playerY;
	}

	public void drawPlayer() {

		playerAvatar.setBounds(playerX-5, playerY-5, 50, 50);
		homeFrame.repaint();
	}
	
}
