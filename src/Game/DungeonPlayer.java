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
	
	private static final int MAXMOVEMENT = 7;
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

		//This MouseListener sets a NewPosition whenever the mouse is moved.
		homeFrame.addMouseMotionListener(new MouseMotionAdapter() {
	        public void mouseMoved(MouseEvent e) {
				newPosition.setLocation(e.getX(), e.getY());
	        }
	    });
		
		//This ActionListener increments the player towards NewPosition each time it's called
		ActionListener movementPerSecond = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 currentPosition = new Point(playerX, playerY);

	                // calculate distance between currentPosition and newPosition
	                double distance = currentPosition.distance(newPosition);

	                if (distance > 5) {
	                    // calculate the amount to move in each direction based on MAXMOVEMENT
	                    double dx = MAXMOVEMENT * (newPosition.getX() - currentPosition.getX()) / distance;
	                    double dy = MAXMOVEMENT * (newPosition.getY() - currentPosition.getY()) / distance;

	                    // update player's position
						
							playerX += (int) dx;
							playerY += (int) dy;
						

	                }

	                drawPlayer();
	            }
	        };
		
	    Timer timer = new Timer(10, movementPerSecond);
	    timer.start();
	    
		//Draw the player
		playerAvatar = new JLabel();
		ImageIcon playerSprite = new ImageIcon("src/Game/playerSprite.png");
		playerAvatar.setIcon(playerSprite);
		playerAvatar.setBounds(playerX, playerY, 50, 50);
		playerAvatar.setVisible(true);
		playerJFrame.add(playerAvatar);
	    /*try {
	    	
	    } catch (IOException FileNotFoundException) {
	    	System.out.println(FileNotFoundException + " resulted in failure");
	    }*/
	}
	
	private void getLocal(int x, int y) {
    	//This variable is the number of columns.
		int numberOfBoxes = DungeonGame.gridWidth;
    	int boxLength = homeFrame.getWidth()/numberOfBoxes;
    	int xIndex = x/boxLength;
    	int yIndex = y/boxLength;
    	System.out.println("The player is in Column: "+ xIndex + " Row: " + yIndex);
    }
	
	/*public void actionPerformed(ActionEvent e) {
    	Point mouseSpot = MouseInfo.getPointerInfo().getLocation();
        Point panelLocation = homeFrame.getLocationOnScreen();
        playerX = (int) (mouseSpot.getX() - panelLocation.getX());
        playerY = (int) (mouseSpot.getY() - panelLocation.getY());
    }*/
	
	public void drawPlayer() {
		// This code is adapted from an online example to fix image flicker.
		
		/*// Create an off-screen image buffer
	    Image offScreenImage = homeFrame.createImage(homeFrame.getWidth(), homeFrame.getHeight());
	    Graphics offScreenGraphics = offScreenImage.getGraphics();
	    
	    // Clear the previous image
	    offScreenGraphics.clearRect(playerX, playerY, playerAvatar.getWidth(), playerAvatar.getHeight());
	    
	    // Draw the new image on the off-screen buffer
	    offScreenGraphics.drawImage(playerAvatar.getIcon(), playerX, playerY, null);
	    
	    // Paint the off-screen buffer on the screen
	    homeFrame.getGraphics().drawImage(offScreenImage, 0, 0, null);
		*/ 

		playerAvatar.setBounds(playerX, playerY, 50, 50);
		homeFrame.repaint();
	}
	
}