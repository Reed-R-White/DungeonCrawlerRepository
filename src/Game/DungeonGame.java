/**
 * The DungeonGame class creates the game window, player character, and enemy characters. 
 * It contains a game loop which continuously updates the position and actions 
 * of the enemy characters and checks for collisions with the player character.
 */

package Game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.Cursor;
import java.awt.MouseInfo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import Game.MapLayout.mapType;

/**
 * The driver/main class for the game.
 */
public class DungeonGame implements ActionListener, MouseListener {

	// private static final long serialVersionUID = 1L;
	public static final int GAMEWINDOWSIZE = 900;
	public Obstacle[] obstacleArr = new Obstacle[22];

	public static final int gridWidth = 20;

	private JFrame gameWindow;

	private DungeonPlayer player1;
	ArrayList<EnemyPlayer> enemies;
	private Point currentPosition;

	private int boostTimer = 0;
	private int boostCoolDown = 0;

	private double dx, dy, distance;
	
	private static final int ATTACKCOOLDOWN = 0;

	private MapLayout currentMap;
	private EnemyPlayer[] currentEnemies;
	private int attackTimer = 0;
	private int currentLevel;
	private int xMouseOffsetToContentPaneFromJFrame;
	private int yMouseOffsetToContentPaneFromJFrame;
	private Point targetPoint;
	boolean isColliding = false;
	JFrame gameGUI;


    public DungeonGame(JFrame gameGUI) {
		this.gameGUI = gameGUI;
		gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE+25);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        //Add the player
        gameWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        
        Container gameContentPane = gameWindow.getContentPane();

        // Event mouse position is given relative to JFrame, where
        // dolphin's image in JLabel is given relative to ContentPane,
        // so adjust for the border ( / 2  since border is on either side)
        int borderWidth = (gameWindow.getWidth() - gameContentPane.getWidth()) / 2;
        // assume side border = bottom border; ignore title bar
        xMouseOffsetToContentPaneFromJFrame = borderWidth;
        yMouseOffsetToContentPaneFromJFrame = gameWindow.getHeight() - gameContentPane.getHeight() - borderWidth;
        
        player1 = new DungeonPlayer(gameWindow, 100, 100);
        
        currentPosition = new Point(player1.getX(),player1.getY());

        //currentMap = new MapLayout(mapType.MAZE, gameWindow, player1);
        currentLevel = 1;
		loadMap();
        
        gameWindow.addKeyListener(new KeyListener() {
        	//Simple print statements here need to be replaced by enemy hit logic
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		        	for(EnemyPlayer enemy: currentEnemies)
			        	if (attackTimer <= 0) {
			        		attackTimer = ATTACKCOOLDOWN;
				        	if(player1.attack(enemy)) {
				            	enemy.takeDamage(player1.getDamage());
				            }
			        	}
		        }
		        
		        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		        	for(EnemyPlayer enemy: currentEnemies)
			        	if (attackTimer <= 0) {
			        		attackTimer = ATTACKCOOLDOWN;
				        	if(player1.sweepAttack(enemy)) {
				            	enemy.takeDamage(player1.getDamage());
				            }
			        	}
		        }
		        
//		        else if (e.getKeyCode() == KeyEvent.VK_W) {
//		        	if (boostCoolDown <= 0) {
//		        		boostTimer = 10;
//		        	}
//		        	System.out.println(xMouseOffsetToContentPaneFromJFrame+"\n"+yMouseOffsetToContentPaneFromJFrame);
//		        }
		        
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


		gameWindow.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				player1.setNewTarget(new Point(e.getX(), e.getY()));
				System.out.println(e.getX()+" "+ e.getY());
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				// throw new UnsupportedOperationException("Unimplemented method
				// 'mouseDragged'");
			}

		});

		 gameWindow.addMouseListener(this);
        

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
                    player1.MAXMOVEMENT = 3; //Return the player speed to normal.
                    //Start the cooldown
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
                    
                    // Check collision
                    isColliding = false;
                    for(Obstacle obj : obstacleArr){
                        if(obj.checkCollision((float)(player1.getX()+dx), (float)(player1.getY()+dy)) || obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE+dx), (float)(player1.getY()+dy)) || obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE+dx), (float)(player1.getY()+player1.PLAYERSIZE+dy)) || obj.checkCollision((float)(player1.getX()+dx), (float)(player1.getY()+player1.PLAYERSIZE+dy))){
                            isColliding = true;
                            //System.out.println("Collision at " + player1.getX() + ", " + player1.getY());
                            player1.setNewTarget(currentPosition);
                            break;
                        }
                    }

                    if(!isColliding){
                        // Marginally move the player by the x and y.
                        player1.movePlayer((int)dx,(int)dy);
                    }
                }
	            
	            boolean levelOver = true;
	            
                for (EnemyPlayer enemy : currentMap.getEnemyList()) {
                	if(enemy.getEnemyHealth() >= 0) {
                		levelOver = false;
                		
						double distance = Math.sqrt(Math.pow(enemy.getX() - player1.getX(), 2) + Math.pow(enemy.getY()- player1.getY(), 2));
						if (distance <=40){
							enemy.attack(player1);
						}
						
						player1.reduceInvincibility();
						enemy.move();
    					enemy.checkPlayer(player1);                		
                		//gameWindow.repaint();
                	}
                }
                
				

                if(levelOver) {
                	currentLevel += 1;
                	clearFrame();
                	loadMap();
                }
            }
        });

	    timer.start();

        //gameWindow.addMouseListener(this);
        gameWindow.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
            	targetPoint = new Point(e.getX() - xMouseOffsetToContentPaneFromJFrame - player1.PLAYERSIZE/2 ,e.getY()-yMouseOffsetToContentPaneFromJFrame - player1.PLAYERSIZE/2);
            	
                player1.setNewTarget(targetPoint);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub
                //throw new UnsupportedOperationException("Unimplemented method 'mouseDragged'");
            }
            
        });
    }
    
    private void loadMap() {
    	
    	switch(currentLevel) {
    	case 1:
			currentMap = new MapLayout(mapType.DEFAULT, gameWindow, player1);
    		break;
    	case 2:
    		currentMap = new MapLayout(mapType.MAZE, gameWindow, player1);
    		break;
    	case 3:
    		currentMap = new MapLayout(mapType.HOUSE, gameWindow, player1);
    		break;
    	default:
    		currentMap = new MapLayout(mapType.DEFAULT, gameWindow, player1);
    	}
    	currentMap.drawMap();
        obstacleArr = currentMap.getObjectArray();
        currentEnemies = currentMap.getEnemyList();
    }
    
    private void clearFrame() {
    	for (Obstacle obj : obstacleArr) {
    		for(JLabel component: obj.getObjects()) {
    			gameWindow.remove(component);
    		}
    	}
    	for (EnemyPlayer enem : currentEnemies) {
    		gameWindow.remove(enem.getJLabel());
    	}
    }


    //
    
    
//    public static void main(String[] args) {
//        new DungeonGame();
//    }

    /**
     * Sets the next target location for the player to seek.
     * The new target will be the position of the mouse click.
     */
    public void mousePressed(MouseEvent e) {
		Point newTarget = new Point(e.getX(), e.getY());
        player1.setNewTarget(newTarget);
        System.out.println("new target: "+e.getX()+", "+e.getY());

	}

	/**
	 * Adds an enemy character to the game with a randomized movement pattern.
	 * 
	 * @param startingX  The starting x position of the enemy character.
	 * @param startingY  The starting y position of the enemy character.
	 * @param gameWindow The JFrame that the game is displayed in.
	 */
	private void addEnemy(float startingX, float startingY, JFrame gameWindow) {
		Random random = new Random();
		ArrayList<Integer> movementPatternX = new ArrayList<Integer>();
		ArrayList<Integer> movementPatternY = new ArrayList<Integer>();

		for (int i = 0; i < 10; i++) {
			int directionX = random.nextInt(3) - 1;
			int directionY = random.nextInt(3) - 1;
			for (int j = 0; j < 40; j++) {
				movementPatternX.add(directionX);
				movementPatternY.add(directionY);
			}

		}

		EnemyPlayer enemy = new EnemyPlayer(gameWindow, startingX, startingY, 50, 50, 100, 10, player1, obstacleArr);
		enemies.add(enemy);
	}

	// Weird auto-generated things that the program needs to compile but are
	// absolutely useless.

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
