package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame implements ActionListener, MouseListener {
    
    //private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    public Obstacle[] obstacleArr = new Obstacle[20];
    public static final int gridWidth = 20;

    private JFrame gameWindow;
    
    private DungeonPlayer player1;
    private Point currentPosition;
    private double dx,dy,distance;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        //Add the player
        player1 = new DungeonPlayer(gameWindow);
        currentPosition = new Point(player1.getX(),player1.getY());

        //Add the obstacles
        obstacleArr[0] = new Obstacle(gameWindow, 50, 50, Rotation.POINTING_BOTTOM_LEFT);
        obstacleArr[0].draw();
        
        //obstacleArr[1] = new Obstacle(gameWindow, 300, 50, Rotation.POINTING_TOP_RIGHT);
        //obstacleArr[1].draw();

        //Set up the timer
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                currentPosition.setLocation(player1.getX(), player1.getY());;

		        // calculate distance between currentPosition and newPosition
		        distance = currentPosition.distance(player1.getNewTarget());

	            if (distance > 5) {
                    // calculate the amount to move in each direction based on MAXMOVEMENT
                    dx = DungeonPlayer.MAXMOVEMENT * (player1.getNewTarget().getX() - currentPosition.getX()) / distance;
                    dy = DungeonPlayer.MAXMOVEMENT * (player1.getNewTarget().getY() - currentPosition.getY()) / distance;
                    // marginally move the player by the x and y.
                    
                    if(obstacleArr[0].checkCollision((float)(player1.getX()+dx), (float)(player1.getY()+dy))==false){
                        player1.movePlayer((int)dx,(int)dy);
                    }
                    
                }
            }
        });
	    timer.start();

        gameWindow.addMouseListener(this);;
        
    }


    public static void main(String[] args) {
        new DungeonGame();
    }

    public void mousePressed(MouseEvent e) {
		player1.setNewTarget(e.getX(), e.getY());
        System.out.println("new target: "+e.getX()+", "+e.getY());
	}

    //Auto-generated ActionPerformed stub.  Do I need this?
    @Override
    public void actionPerformed(ActionEvent arg0) {
        //player1.movePlayer((int)dx,(int)dy);
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseClicked'");
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseEntered'");
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseExited'");
    }

    
    @Override
    public void mouseReleased(MouseEvent arg0) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'mouseReleased'");
    }
}




