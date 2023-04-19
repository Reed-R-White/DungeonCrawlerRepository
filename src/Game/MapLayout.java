package Game;

import javax.swing.JFrame;

public class MapLayout {
	enum mapType{
		DEFAULT,
		MAZE,
		COORIDORS
	}
	
	private Obstacle[] obstacleArr;
	
	public MapLayout(mapType selectedMap, JFrame gameWindow) {
		
		switch (selectedMap) {
		case DEFAULT:
			obstacleArr = new Obstacle[22];
			obstacleArr[0] = new Obstacle(gameWindow, 0, 0, Rotation.POINTING_TOP_LEFT);
	        obstacleArr[1] = new Obstacle(gameWindow, 100, 0, Rotation.HORIZONTAL);
	        obstacleArr[2] = new Obstacle(gameWindow, 200, 0, Rotation.HORIZONTAL);
	        obstacleArr[3] = new Obstacle(gameWindow, 300, 0, Rotation.HORIZONTAL);
	        obstacleArr[4] = new Obstacle(gameWindow, 400, 0, Rotation.HORIZONTAL);
	        obstacleArr[5] = new Obstacle(gameWindow, 500, 0, Rotation.POINTING_TOP_RIGHT);
	        
	        //Left column
	        obstacleArr[6] = new Obstacle(gameWindow, 0, 100, Rotation.VERTICAL);
	        obstacleArr[7] = new Obstacle(gameWindow, 0, 200, Rotation.VERTICAL);
	        obstacleArr[8] = new Obstacle(gameWindow, 0, 300, Rotation.VERTICAL);
	        obstacleArr[9] = new Obstacle(gameWindow, 0, 400, Rotation.VERTICAL);
	        obstacleArr[10] = new Obstacle(gameWindow, 0, 500, Rotation.POINTING_BOTTOM_LEFT);

	        //Right column
	        obstacleArr[11] = new Obstacle(gameWindow, 500, 100, Rotation.VERTICAL);
	        obstacleArr[12] = new Obstacle(gameWindow, 500, 200, Rotation.VERTICAL);
	        obstacleArr[13] = new Obstacle(gameWindow, 500, 300, Rotation.VERTICAL);
	        obstacleArr[14] = new Obstacle(gameWindow, 500, 400, Rotation.VERTICAL);
	        obstacleArr[15] = new Obstacle(gameWindow, 500, 500, Rotation.POINTING_BOTTOM_RIGHT);

	        //Bottom row
	        obstacleArr[16] = new Obstacle(gameWindow, 100, 500, Rotation.HORIZONTAL);
	        obstacleArr[17] = new Obstacle(gameWindow, 200, 500, Rotation.HORIZONTAL);
	        obstacleArr[18] = new Obstacle(gameWindow, 300, 500, Rotation.HORIZONTAL);
	        obstacleArr[19] = new Obstacle(gameWindow, 400, 500, Rotation.HORIZONTAL);

	        //Filler obstacles
	        obstacleArr[20] = new Obstacle(gameWindow, 150, 150, Rotation.POINTING_TOP_LEFT);
	        obstacleArr[21] = new Obstacle(gameWindow, 350, 350, Rotation.POINTING_BOTTOM_RIGHT);
		}
	}
	
	public Obstacle[] getObjectArray() {
		return obstacleArr;
	}
	
	public void drawMap() {
		for(Obstacle obj : obstacleArr){
            obj.draw();
        }
	}

}
