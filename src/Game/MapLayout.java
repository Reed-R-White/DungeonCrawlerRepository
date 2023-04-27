//Fully commented.

package Game;

import java.awt.Image;
import java.awt.Point;


import javax.swing.ImageIcon;


import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This class creates and tracks different maps that the game can load.
 * 
 * @author Reed White, Charlie Said, and Ryan O'Valley
 */
public class MapLayout {
	
	//Enum to track which map design is called for.
	enum mapType{
		DEFAULT,
		MAZE,
		COORIDORS,
		HOUSE
	}
	
	private Obstacle[] obstacleArr;
	private EnemyPlayer[] mapEnemies;
	private JFrame mapJFrame;
	private int objectSize = 40;
	
	/**
	 * Constructor for the MapLayout, which sets up the specified map design, and the enemies in that map.
	 * It also sets the player's location to a specific starting point on the map.
	 * 
	 * @param selectedMap An enum, either DEFAULT, MAZE, COORIDORS, or HOUSE, specifying which map design to use.
	 * @param gameWindow The JFrame of the game.
	 * @param gamePlayer The player in the game.
	 */
	public MapLayout(mapType selectedMap, JFrame gameWindow, DungeonPlayer gamePlayer) {
		
		mapJFrame = gameWindow;
		
		//Create the arrays of obstacles
		switch (selectedMap) {
		case DEFAULT:
			obstacleArr = scanMap(new int[][] {
				{5,3,3,3,3,3,6},
				{1,0,0,0,0,0,2},
				{1,0,5,0,0,0,2},
				{1,0,0,0,8,0,2},
				{1,0,0,0,0,0,2},
				{7,4,4,4,4,4,8},
			});
	        
			//Set the player position
			gamePlayer.setCurrentPosition(new Point(70, 70));

			//Create the enemies
	        mapEnemies = new EnemyPlayer[5];
	        for (int i = 0; i < mapEnemies.length; i++) {

	        	mapEnemies[i] = new EnemyPlayer(gameWindow, 300+(10*i), 100+(5*i), 10, 10, 50, 5, gamePlayer, obstacleArr);
	        	//mapEnemies[i] = new EnemyPlayer(gameWindow, 300+(10*i), 100+(5*i), 10, 10, gamePlayer);

	        }
	        break;
	        
		case MAZE:
			obstacleArr = scanMap(new int[][] {
				{5,3,6,3,3,3,6},
				{5,6,2,9,6,9,6},
				{1,3,2,2,3,2,2},
				{1,7,2,5,8,3,2},
				{7,4,4,4,8,8,8},
			});
			
			//Set the player position

			gamePlayer.setCurrentPosition(new Point(400, 300));

			//Create the enemies
			mapEnemies = new EnemyPlayer[3];
			mapEnemies[0] = new EnemyPlayer(gameWindow, 70, 70, 10, 10, 60, 5, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 70, 500, 10, 10, 60, 5, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 700, 70, 10, 10, 60, 5, gamePlayer, obstacleArr);
			

			break;
			
		case HOUSE:
			obstacleArr = scanMap(new int[][] {
				{5,3,3,3,3,3,6},
				{5,3,0,5,6,0,2},
				{7,8,0,4,8,0,2},
				{4,4,0,0,0,0,2},
				{7,4,4,4,4,4,8}
			});
			
			//Set the player position
			gamePlayer.setCurrentPosition(new Point(100, 200));

			//Create the enemies
			mapEnemies = new EnemyPlayer[3];

			mapEnemies[0] = new EnemyPlayer(gameWindow, 70, 70, 10, 10, 100, 5, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 500, 70, 10, 10, 100, 5, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 500, 400, 10, 10, 200, 10, gamePlayer, obstacleArr);
			

			break;
		}
		
	}
	
	/**
	 * Getter for the array of Obstacles on the map.
	 * 
	 * @return An array of Obstacles that are currently on the map.
	 */
	public Obstacle[] getObstacleArray() {
		return obstacleArr;
	}
	
	/**
	 * Getter for all the enemies currently in the map.
	 * 
	 * @return An array of the EnemyPlayers in the map.
	 */
	public EnemyPlayer[] getEnemyList() {
		return mapEnemies;
	}
	
	/**
	 * This method creates the array of obstacles based on a map key, in the form or a 2D int array.
	 * 
	 * @param mapTemplate a 2D int array specifying which obstacles to draw where
	 * @return An array of obstacles fully constructed.
	 */
	private Obstacle[] scanMap(int[][] mapTemplate) {
		int objectCount = 0;
		
		//Count the number of obstacles needed to draw.
		for (int x = 0; x < mapTemplate.length; x++) {
			for (int y = 0; y < mapTemplate[x].length; y++) {
				if (mapTemplate[x][y] > 0 && mapTemplate[x][y] < 10) {
					objectCount += 1;
				}
			}
		}
		
		//Set up variables
		Obstacle[] mapObstacles = new Obstacle[objectCount];
		int mapObstacleIndex = 0;
		int sideLength = 120;
		
		//Draw each obstacle according to its location and type.
		for (int y = 0; y < mapTemplate.length; y++) {
			for (int x = 0; x < mapTemplate[y].length; x++) {
				if (mapTemplate[y][x] > 0 && mapTemplate[y][x] < 10) {
					
					switch (mapTemplate[y][x]) {
						case 1:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.L_VERTICAL);
							break;
						case 2:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.R_VERTICAL);
							break;
						case 3:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.T_HORIZONTAL);
							break;
						case 4:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.B_HORIZONTAL);
							break;
						case 5:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.POINTING_TOP_LEFT);
							break;
						case 6:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.POINTING_TOP_RIGHT);
							break;
						case 7:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.POINTING_BOTTOM_LEFT);
							break;
						case 8:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.POINTING_BOTTOM_RIGHT);
							break;
						case 9:
							mapObstacles[mapObstacleIndex] = new Obstacle(mapJFrame, sideLength*x, sideLength*y, Rotation.SINGLE);
							break;
					}
					mapObstacleIndex += 1;
				}
			}
		}
		
		return mapObstacles;
	}
	

	public void drawMap(int currentLevel) {
		
		ImageIcon sprite = new ImageIcon("src/Game/dungeonBackground.jpg");
        Image tempImage = sprite.getImage();
        tempImage = tempImage.getScaledInstance(objectSize, objectSize, java.awt.Image.SCALE_SMOOTH);
        sprite = new ImageIcon(tempImage);
		
        for(int row = 0; row < DungeonGame.GAMEWINDOWSIZE; row += objectSize) {
        	for(int col = 0; col < DungeonGame.GAMEWINDOWSIZE; col += objectSize) {
        		
        		boolean hasWall = false;
        		
        		for (Obstacle obj : obstacleArr) {
        			if (obj.getPosX1() == row && obj.getPosY1() == col) {
        				hasWall = true;
        			} else if (obj.posX2 == row && obj.posY2 == col) {
        				hasWall = true;
        			} else if (obj.posX3 == row && obj.posY3 == col) {
        				hasWall = true;
        			} else if (obj.posX4 == row && obj.posY4 == col) {
        				hasWall = true;
        			} else if (obj.posX5 == row && obj.posY5 == col) {
        				hasWall = true;
        			}
        		}
        		
        		if (row == 0 && col == 0 && currentLevel == 3) {
        			hasWall = false;
        		} else if (row == 200 && col == 560 && currentLevel == 3) {
        			hasWall = false;
        		}
        		
        		if (!hasWall) {
	        		/*JLabel bg = new JLabel();
	        		bg.setIcon(sprite);
	    			bg.setVisible(true);
	    			bg.setBounds(row, col, objectSize, objectSize);
	    			mapJFrame.add(bg);//*/
        		}
        	}
        }
		for(Obstacle obj : obstacleArr){
            obj.draw();
        }
		mapJFrame.repaint();
	}
}
