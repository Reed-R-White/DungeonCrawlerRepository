
package Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import Game.MapLayout.mapType;

/**
 * The DungeonGame class creates the game window, player character, and enemy characters. 
 * It contains a game loop which continuously updates the position and actions 
 * of the enemy characters and checks for collisions with the player character.
 * 
 * @authors Charlie Said, Ryan O'Valley, and Reed White
 */
public class DungeonGame implements ActionListener {

	//Game variables
	public static final int GAMEWINDOWSIZE = 850;
	public Obstacle[] obstacleArr;// = new Obstacle[22];
	private JFrame gameWindow;
	private MapLayout currentMap;
	private int currentLevel;
	private int xMouseOffsetToContentPaneFromJFrame;
	private int yMouseOffsetToContentPaneFromJFrame;
	
	//Entity variables
	private DungeonPlayer player1;
	private Point currentPosition;
	private double dx, dy, distance;
	private EnemyPlayer[] currentEnemies;
	private int attackTimer = 0;
	//private int boostTimer = 0;
	//private int boostCoolDown = 0;
	private static final int ATTACKCOOLDOWN = 0;
	private Point targetPoint;
	private boolean isCollidingX = false;
	private boolean isCollidingY = false;
	private Timer timer;
	private JFrame GameGui;

	/**
	 * Creates a DungeonGame, complete with the player, enemy, and obstacles.  
	 * The Game also starts a timer loop that repaints every 10 milliseconds.
	 * 
	 * @param GameGui The initial user interface the program uses.  The Dungeon Game will revert to this when the player dies or when all the levels have been cleared.
	 */
    public DungeonGame(JFrame GameGui) {
		
		//Set up the window
		gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE+25);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
		Container gameContentPane = gameWindow.getContentPane();
		gameContentPane.setBackground(Color.decode("#B2AC88"));
        gameWindow.setVisible(true);
		this.GameGui = GameGui;
        gameWindow.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

        //Create the variables for the player's offset from the mouse.
        int borderWidth = (gameWindow.getWidth() - gameContentPane.getWidth()) / 2;
        xMouseOffsetToContentPaneFromJFrame = borderWidth;
        yMouseOffsetToContentPaneFromJFrame = gameWindow.getHeight() - gameContentPane.getHeight() - borderWidth;
        
		//Add the player
        player1 = new DungeonPlayer(gameWindow, 70, 70);
        currentPosition = new Point(player1.getX(),player1.getY());

        //Set up the map
        currentLevel = 1;
		loadMap();
        
		//Add controls for different keystrokes
        gameWindow.addKeyListener(new KeyListener() {
        	
		    public void keyPressed(KeyEvent e) {
		        
				//The Space key triggers a stabbing attack, which deals extra damage.
				double damageModifier = 1.3;
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
		        	for(EnemyPlayer enemy: currentEnemies){
			        	if (attackTimer <= 0) {
			        		attackTimer = ATTACKCOOLDOWN;
				        	if(player1.attack(enemy)) {
				            	enemy.takeDamage((int)(player1.getDamage()*damageModifier));
				            }
			        	}
					}
		        }
		        
				//The Shift key triggers a sweeping attack
		        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
		        	for(EnemyPlayer enemy: currentEnemies){
			        	if (attackTimer <= 0) {
			        		attackTimer = ATTACKCOOLDOWN;
				        	if(player1.sweepAttack(enemy)) {
				            	enemy.takeDamage(player1.getDamage());
				            }
			        	}
					}
		        }
		    }
		    
			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}
		});


		//Set up the MouseListener logic.
		gameWindow.addMouseMotionListener(new MouseMotionListener() {

			/**
			 * Whenever the mouse is moved, set the player's new target to the mouse's new location.
			 * @param e The mouse move event.
			 */
			@Override
			public void mouseMoved(MouseEvent e) {
				//player1.setNewTarget(new Point(e.getX(), e.getY()));

				targetPoint = new Point(e.getX() - xMouseOffsetToContentPaneFromJFrame - player1.PLAYERSIZE/2 ,e.getY()-yMouseOffsetToContentPaneFromJFrame - player1.PLAYERSIZE/2);
            	
                player1.setNewTarget(targetPoint);
			}

			//Necessary but useless method.
			@Override
			public void mouseDragged(MouseEvent e) {}

		});

        //Set up the timer
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
            	
				//If the player is dead, return to the main menu.
				if (player1.getHealth() <= 0) {
					gameWindow.setVisible(false);
					gameWindow.dispose();
					GameGui.setVisible(true);
					timer.stop();
            	}
            	
				//Update the attack timer.
            	if (attackTimer > 0) {
            		attackTimer -= 1;
            	}
            	
                //Update the current position.
                currentPosition.setLocation(player1.getX(), player1.getY());;

		        // calculate distance between currentPosition and newPosition
		        distance = currentPosition.distance(player1.getNewTarget());

	            if (distance > 10) {
                    // calculate the amount to move in each direction based on MAXMOVEMENT
                    dx = player1.MAXMOVEMENT * (player1.getNewTarget().getX() - currentPosition.getX()) / distance;
                    dy = player1.MAXMOVEMENT * (player1.getNewTarget().getY() - currentPosition.getY()) / distance;
				} else {
					dx = 0;
					dy = 0;
				}

                // Check collision for X
                isCollidingX = false;
                for(Obstacle obj : obstacleArr){
                    if(obj.checkCollision((float)(player1.getX()+dx), (float)(player1.getY())) 
						|| obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE+dx), (float)(player1.getY())) 
						|| obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE+dx), (float)(player1.getY()+player1.PLAYERSIZE)) 
						|| obj.checkCollision((float)(player1.getX()+dx), (float)(player1.getY()+player1.PLAYERSIZE))){
                        									
						isCollidingX = true;
                            	
                        player1.setNewTarget(currentPosition);
                        break;
                    }
                }

                if(!isCollidingX){
                    // Marginally move the player by the x and y.
                    player1.movePlayerX((int)dx);
                }

				isCollidingY = false;
                for(Obstacle obj : obstacleArr){
                    if(obj.checkCollision((float)(player1.getX()), (float)(player1.getY()+dy)) 
						|| obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE), (float)(player1.getY()+dy)) 
						|| obj.checkCollision((float)(player1.getX()+player1.PLAYERSIZE), (float)(player1.getY()+player1.PLAYERSIZE+dy)) 
						|| obj.checkCollision((float)(player1.getX()), (float)(player1.getY()+player1.PLAYERSIZE+dy))){
                            
						isCollidingY = true;
                            	
                    	player1.setNewTarget(currentPosition);
                    	break;
                    }
                }

                if(!isCollidingY){
                    // Marginally move the player by the x and y.
                    player1.movePlayerY((int)dy);
                }
                
	            
				//Enemy loop:
	            boolean levelOver = true;

                for (EnemyPlayer enemy : currentMap.getEnemyList()) {
                	if(enemy.getEnemyHealth() >= 0) {
                		levelOver = false;
                		
						//If the enemy is within range of the player, attack it
						double distance = Math.sqrt(Math.pow(enemy.getX() - player1.getX(), 2) + Math.pow(enemy.getY()- player1.getY(), 2));
						if (distance <=enemy.getEnemySize()+10){
							enemy.attack(player1);
						}

						enemy.checkPlayer(player1);

						enemy.determineDeltas();

						//Check collision with obstacles.
						if (enemy.getX() + enemy.getDeltaX() >= 0 && enemy.getX() + enemy.getDeltaX() <= DungeonGame.GAMEWINDOWSIZE - enemy.getEnemySize() && 
						!enemy.collidesWithObstacle(enemy.getX() + enemy.getDeltaX(), enemy.getY()) && 
						!enemy.collidesWithObstacle(enemy.getX() + enemy.getDeltaX() + enemy.getEnemySize(), enemy.getY()) && 
						!enemy.collidesWithObstacle(enemy.getX() + enemy.getDeltaX(), enemy.getY() + enemy.getEnemySize()) && 
						!enemy.collidesWithObstacle(enemy.getX() + enemy.getDeltaX() + enemy.getEnemySize(), enemy.getY() + enemy.getEnemySize())){

							enemy.moveX();
						} else {
							enemy.setDeltaX(0);
						}
							
						if(enemy.getY() + enemy.getDeltaY() >= 0 && enemy.getY() + enemy.getDeltaY() <= DungeonGame.GAMEWINDOWSIZE - enemy.EnemySize && 
						!enemy.collidesWithObstacle(enemy.getX(), enemy.getY() + enemy.getDeltaY()) && 
						!enemy.collidesWithObstacle(enemy.getX(), enemy.getY() + enemy.getDeltaY() + enemy.EnemySize) && 
						!enemy.collidesWithObstacle(enemy.getX()+enemy.EnemySize, enemy.getY() + enemy.getDeltaY()) && 
						!enemy.collidesWithObstacle(enemy.getX()+enemy.EnemySize, enemy.getY() + enemy.getDeltaY() + enemy.EnemySize)) {
							
							enemy.moveY();
						} else {
							enemy.setDeltaY(0);
						}
						
						if (enemy.getDeltaX() < 1 && enemy.getDeltaY() < 1){
							enemy.incrementBoredom();
						} else {
							enemy.decrementBoredom();
						}

						//System.out.println("DeltaX: "+enemy.getDeltaX()+", DeltaY: "+enemy.getDeltaY()+", Boredom counter at: "+enemy.getBoredom());
                	} 
                }

				//After each hit, the player has a short period of invulnerability.
				//This method counts that timer down so the player can be hit again soon.
				player1.reduceInvincibility();

				gameWindow.repaint();

				//Advance to the next level
                if(levelOver) {
                	currentLevel += 1;

					if(currentLevel>3){
						gameWindow.setVisible(false);
						gameWindow.dispose();
						GameGui.setVisible(true);
						timer.stop();
					} else {
						clearFrame();
						loadMap();
					}
                }
            }
        });

	    timer.start();
    }
    
	/**
	 * Draws the map, associated enemies, and the player to correspond with the current level.
	 */
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
			break;
    	}
    	currentMap.drawMap();
        obstacleArr = currentMap.getObstacleArray();
        currentEnemies = currentMap.getEnemyList();
    }
    
	/**
	 * Remove all obstacles and enemies from the map.
	 */
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

	@Override
	public void actionPerformed(ActionEvent e) {}
}
