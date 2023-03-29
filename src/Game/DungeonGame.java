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

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonGame {
    private static final long serialVersionUID = 1L;
    private static final int GAMEWINDOWSIZE = 600;
    public Obstacle[] obstacleArr = new Obstacle[20];
    public static final int gridWidth = 20;

    private JFrame gameWindow;
    
    private DungeonPlayer player1;

    public DungeonGame() {
        gameWindow = new JFrame("Dungeon Game");
        gameWindow.setSize(GAMEWINDOWSIZE, GAMEWINDOWSIZE);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setVisible(true);
        //Add the player
        player1 = new DungeonPlayer(gameWindow);

        //Add the obstacles
        obstacleArr[0] = new Obstacle(gameWindow, 50, 50, Rotation.POINTING_BOTTOM_LEFT);
        obstacleArr[0].draw();
        
        //obstacleArr[1] = new Obstacle(gameWindow, 300, 50, Rotation.POINTING_TOP_RIGHT);
        //obstacleArr[1].draw();
        
    }

    public static void main(String[] args) {
        new DungeonGame();
    }
}




