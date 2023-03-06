package Game;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Obstacle {
    
    protected final JFrame gameJFrame;
    protected final JLabel[] ObstacleObject = new JLabel[3];

    private float posX1,posY1,posX2,posY2,posX3,posY3;
    private Rotation rotation;

    public Obstacle(JFrame gameJFrame,float startingX,float startingY,Rotation rotation){

        this.gameJFrame = gameJFrame;
        posX1 = startingX;
        posY1 = startingY;
        this.rotation = rotation;

        
        ObstacleObject[0] = new JLabel();
        ObstacleObject[1] = new JLabel();
        ObstacleObject[2] = new JLabel();

        gameJFrame.getContentPane().add(ObstacleObject[0]);
        gameJFrame.getContentPane().add(ObstacleObject[1]);
        gameJFrame.getContentPane().add(ObstacleObject[2]);
        
    }

    public void draw(){

        //retrieve the image
        ImageIcon sprite[] = new ImageIcon[3];
        Image[] tempImage = new Image[sprite.length];
        
        for(int i=0;i<ObstacleObject.length;i++){
            
            sprite[i] = new ImageIcon("Pepe1.jpeg");
            System.out.println("Sprite has dimensions " + sprite[i].getIconWidth() + " by "+sprite[i].getIconHeight());
            
            //Resize it down to 50x50
            tempImage[i] = sprite[i].getImage();
            sprite[i] = new ImageIcon(tempImage[i].getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH));
    
            //Put it in the game
            System.out.println("i = "+i);
            System.out.println("Sprite has dimensions " + sprite[i].getIconWidth() + " by "+sprite[i].getIconHeight());
            
            ObstacleObject[i].setIcon(sprite[i]);

            ObstacleObject[i].setBounds((int)posX1+50, (int)posY1, sprite[i].getIconWidth(), sprite[i].getIconHeight());
            
            ObstacleObject[i].setVisible(true);
        }

        /*[a b]
         *[c d]
         */

        switch(rotation){
            case POINTING_BOTTOM_RIGHT:
                posX1=posX1+50;
                posX2=posX1;
                posY2=posY1+50;
                posX3=posX1-50;
                posY3=posY1+50;
                break;
            case POINTING_BOTTOM_LEFT:
                posY2=posY1+50;
                posX2=posX1;
                posX3=posX1+50;
                posY3=posY2;
                break;
            case POINTING_TOP_RIGHT:
                posY2=posY1;
                posX2=posX1+50;
                posX3=posX2;
                posY3=posY2+50;
                break;
            case POINTING_TOP_LEFT:
                posY2=posY1;
                posX2=posX1+50;
                posX3=posX1;
                posY3=posY1+50;
                break;
        }
        

        ObstacleObject[0].setBounds((int)posX1, (int)posY1, sprite[0].getIconWidth(), sprite[0].getIconHeight());
        ObstacleObject[1].setBounds((int)posX2, (int)posY2, sprite[1].getIconWidth(), sprite[1].getIconHeight());
        ObstacleObject[2].setBounds((int)posX3, (int)posY3, sprite[2].getIconWidth(), sprite[2].getIconHeight());
    }

    public boolean checkCollision(float x,float y){

        
        if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight())) || (x >= posX2 && x <= (posX2 + ObstacleObject[1].getWidth()) && y >= posY2 && y <= (posY2 + ObstacleObject[1].getHeight())) || (x >= posX3 && x <= (posX3 + ObstacleObject[2].getWidth()) && y >= posY3 && y <= (posY3 + ObstacleObject[2].getHeight()))){
            return true;

        }
        return false;
        
    }

}