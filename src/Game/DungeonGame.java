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
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame {
    private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    
    private JFrame gameWindow;
    
    private DungeonPlayer player1;
    private ArrayList<EnemyPlayer> enemies;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        player1 = new DungeonPlayer(gameWindow);
        enemies = new ArrayList<>();
        addEnemy(200,200,gameWindow);
        
        while(true) {
        	for(int i=0; i < enemies.size(); i++) {
        		EnemyPlayer enemy = enemies.get(i);
        		enemy.move();
        		enemy.checkPlayer(player1);
        		enemy.drawPlayer();
        	}
        }
    }
    
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

		EnemyPlayer enemy = new EnemyPlayer(gameWindow, startingX, startingY, 10, 10, movementPatternX, movementPatternY);
		enemies.add(enemy);
    }

    public static void main(String[] args) {
        new DungeonGame();
    }
}




