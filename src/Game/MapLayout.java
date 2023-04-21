package Game;

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
			obstacleArr = new Obstacle[22];
			obstacleArr[0] = new Obstacle(gameWindow, 0, 0, Rotation.POINTING_TOP_LEFT);
	        obstacleArr[1] = new Obstacle(gameWindow, 100, 0, Rotation.T_HORIZONTAL);
	        obstacleArr[2] = new Obstacle(gameWindow, 200, 0, Rotation.T_HORIZONTAL);
	        obstacleArr[3] = new Obstacle(gameWindow, 300, 0, Rotation.T_HORIZONTAL);
	        obstacleArr[4] = new Obstacle(gameWindow, 400, 0, Rotation.T_HORIZONTAL);
	        obstacleArr[5] = new Obstacle(gameWindow, 500, 0, Rotation.POINTING_TOP_RIGHT);
	        
	        //Left column
	        obstacleArr[6] = new Obstacle(gameWindow, 0, 100, Rotation.L_VERTICAL);
	        obstacleArr[7] = new Obstacle(gameWindow, 0, 200, Rotation.L_VERTICAL);
	        obstacleArr[8] = new Obstacle(gameWindow, 0, 300, Rotation.L_VERTICAL);
	        obstacleArr[9] = new Obstacle(gameWindow, 0, 400, Rotation.L_VERTICAL);
	        obstacleArr[10] = new Obstacle(gameWindow, 0, 500, Rotation.POINTING_BOTTOM_LEFT);

	        //Right column
	        obstacleArr[11] = new Obstacle(gameWindow, 550, 100, Rotation.L_VERTICAL);
	        obstacleArr[12] = new Obstacle(gameWindow, 550, 200, Rotation.L_VERTICAL);
	        obstacleArr[13] = new Obstacle(gameWindow, 550, 300, Rotation.L_VERTICAL);
	        obstacleArr[14] = new Obstacle(gameWindow, 550, 400, Rotation.L_VERTICAL);
	        obstacleArr[15] = new Obstacle(gameWindow, 500, 500, Rotation.POINTING_BOTTOM_RIGHT);

	        //Bottom row
	        obstacleArr[16] = new Obstacle(gameWindow, 100, 550, Rotation.T_HORIZONTAL);
	        obstacleArr[17] = new Obstacle(gameWindow, 200, 550, Rotation.T_HORIZONTAL);
	        obstacleArr[18] = new Obstacle(gameWindow, 300, 550, Rotation.T_HORIZONTAL);
	        obstacleArr[19] = new Obstacle(gameWindow, 400, 550, Rotation.T_HORIZONTAL);

	        //Filler obstacles
	        obstacleArr[20] = new Obstacle(gameWindow, 150, 150, Rotation.POINTING_TOP_LEFT);
	        obstacleArr[21] = new Obstacle(gameWindow, 350, 350, Rotation.POINTING_BOTTOM_RIGHT);
	        
	        mapEnemies = new EnemyPlayer[5];
	        for (int i = 0; i < mapEnemies.length; i++) {
	        	mapEnemies[i] = new EnemyPlayer(gameWindow, 300+(10*i), 100+(5*i), 10, 10, null, null, i, i, gamePlayer, obstacleArr);
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
			
			mapEnemies = new EnemyPlayer[3];
			mapEnemies[0] = new EnemyPlayer(gameWindow, 25+50*2, 25+50*2, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
//			mapEnemies[0] = new EnemyPlayer(gameWindow, 25+50*2, 25+50*2, 10, 10, gamePlayer);
//			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, gamePlayer);
//			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, gamePlayer);
			
			break;
			
		case HOUSE:
			obstacleArr = scanMap(new int[][] {
				{0,0,0,0,0,0,0},
				{5,3,0,5,6,0,0},
				{7,8,0,4,8,0,0},
				{4,4,0,0,0,0,0},
				{7,4,0,0,0,0,0}
			});
			
			mapEnemies = new EnemyPlayer[3];
			mapEnemies[0] = new EnemyPlayer(gameWindow, 25+50*2, 25+50*2, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, null, null, 0, 0, gamePlayer, obstacleArr);
//			mapEnemies[0] = new EnemyPlayer(gameWindow, 25+50*2, 25+50*2, 10, 10, gamePlayer);
//			mapEnemies[1] = new EnemyPlayer(gameWindow, 25+50*5, 25+50*2, 10, 10, gamePlayer);
//			mapEnemies[2] = new EnemyPlayer(gameWindow, 25+50*3, 25+50*5, 10, 10, gamePlayer);
			
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
