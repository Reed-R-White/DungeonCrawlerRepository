package Game;

import java.awt.Point;

import javax.swing.JFrame;

public class MapLayout {
	enum mapType{
		DEFAULT,
		MAZE,
		COORIDORS,
		HOUSE
	}
	
	private Obstacle[] obstacleArr;
	private EnemyPlayer[] mapEnemies;
	private JFrame mapJFrame;
	
	public MapLayout(mapType selectedMap, JFrame gameWindow, DungeonPlayer gamePlayer) {
		
		mapJFrame = gameWindow;
		
		
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
			gamePlayer.setCurrentPosition(new Point(300, 300));

			//Create the enemies
	        mapEnemies = new EnemyPlayer[5];
	        for (int i = 0; i < mapEnemies.length; i++) {
	        	mapEnemies[i] = new EnemyPlayer(gameWindow, 300+(10*i), 100+(5*i), 10, 10, 50, i, gamePlayer, obstacleArr);
	        	
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
			gamePlayer.setCurrentPosition(new Point(300, 300));

			//Create the enemies
			mapEnemies = new EnemyPlayer[3];
			mapEnemies[0] = new EnemyPlayer(gameWindow, 70, 70, 10, 10, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, 0, 0, gamePlayer, obstacleArr);
			break;
			
		case HOUSE:
			obstacleArr = scanMap(new int[][] {
				{0,0,0,0,0,0,0},
				{5,3,0,5,6,0,0},
				{7,8,0,4,8,0,0},
				{4,4,0,0,0,0,0},
				{7,4,0,0,0,0,0}
			});
			
			//Set the player position
			gamePlayer.setCurrentPosition(new Point(300, 300));

			//Create the enemies
			mapEnemies = new EnemyPlayer[3];
			mapEnemies[0] = new EnemyPlayer(gameWindow, 25+50*2, 25+50*2, 10, 10, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, 0, 0, gamePlayer, obstacleArr);	
			break;
		}
		
	}
	
	public Obstacle[] getObjectArray() {
		return obstacleArr;
	}
	
	public EnemyPlayer[] getEnemyList() {
		return mapEnemies;
	}
	
	private Obstacle[] scanMap(int[][] mapTemplate) {
		int objectCount = 0;
		for (int x = 0; x < mapTemplate.length; x++) {
			for (int y = 0; y < mapTemplate[x].length; y++) {
				if (mapTemplate[x][y] > 0 && mapTemplate[x][y] < 10) {
					objectCount += 1;
				}
			}
		}
		
		Obstacle[] mapObstacles = new Obstacle[objectCount];
		int mapObstacleIndex = 0;
		int sideLength = 120;
		
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
	
	public void drawMap() {
		for(Obstacle obj : obstacleArr){
            obj.draw();
        }
	}

}
