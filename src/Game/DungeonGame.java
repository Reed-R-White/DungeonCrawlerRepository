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
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame implements ActionListener, MouseListener {
    
    //private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    private static final int ATTACKCOOLDOWN = 20;
    public Obstacle[] obstacleArr = new Obstacle[20];
    public static final int gridWidth = 20;

    private JFrame gameWindow;
    
    private DungeonPlayer player1;
    private Point currentPosition;
    
    private int boostTimer = 0;
    private int boostCoolDown = 0;
    private int attackTimer = 0;
    
    private double dx,dy,distance;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        //Add the player
        player1 = new DungeonPlayer(gameWindow);
        EnemyPlayer test = new EnemyPlayer(gameWindow, 10, 10, 10, 10, player1);
        
        gameWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        
        gameWindow.addKeyListener(new KeyListener() {
        	//Simple print statements here need to be replaced by enemy hit logic
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		        	if (attackTimer <= 0) {
		        		attackTimer = ATTACKCOOLDOWN;
			        	if(player1.attack(new Point((int) test.getX(), (int) test.getY()))) {
			            	System.out.println("hit");
			            }
			            else {
			            	System.out.println("miss");
			            }
		        	}
		        }
		        
		        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		        	if (attackTimer <= 0) {
		        		attackTimer = ATTACKCOOLDOWN;
			        	if(player1.sweepAttack(new Point((int) test.getX(), (int) test.getY()))) {
			            	System.out.println("hit");
			            }
			            else {
			            	System.out.println("miss");
			            }
		        	}
		        }
		        
		        else if (e.getKeyCode() == KeyEvent.VK_W) {
		        	if (boostCoolDown <= 0) {
		        		boostTimer = 10;
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
        
        currentPosition = new Point(player1.getX(),player1.getY());

        //Add the obstacles
        obstacleArr[0] = new Obstacle(gameWindow, 50, 50, Rotation.POINTING_BOTTOM_LEFT);
        obstacleArr[0].draw();

        //Set up the timer
        Timer timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	if (player1.getHealth() <= 0) {
            		gameWindow.dispatchEvent(new WindowEvent(gameWindow, WindowEvent.WINDOW_CLOSING));
            	}
            	
            	if (boostTimer > 0) {
            		boostTimer -= 1;
            		player1.MAXMOVEMENT = 6;
            		boostCoolDown = 100;
            	}
            	else if (boostTimer <= 0) {
            		player1.MAXMOVEMENT = 3;
            		if (boostCoolDown > 0) {
            			boostCoolDown -= 1;
            		}
            	}
            	
            	if (attackTimer > 0) {
            		attackTimer -= 1;
            	}
                
                currentPosition.setLocation(player1.getX(), player1.getY());;

		        // calculate distance between currentPosition and newPosition
		        distance = currentPosition.distance(player1.getNewTarget());

	            if (distance > 5) {
                    // calculate the amount to move in each direction based on MAXMOVEMENT
                    dx = player1.MAXMOVEMENT * (player1.getNewTarget().getX() - currentPosition.getX()) / distance;
                    dy = player1.MAXMOVEMENT * (player1.getNewTarget().getY() - currentPosition.getY()) / distance;
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
		Point newTarget = new Point(e.getX(), e.getY());
        player1.setNewTarget(newTarget);
        System.out.println("new target: "+e.getX()+", "+e.getY());
	}

    //Weird auto-generated things that the program needs to compile but are absolutely useless.

    @Override
    public void actionPerformed(ActionEvent arg0) {
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
    }
}




