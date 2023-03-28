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

        /*
        gameJFrame.getContentPane().add(ObstacleObject[0]);
        gameJFrame.getContentPane().add(ObstacleObject[1]);
        gameJFrame.getContentPane().add(ObstacleObject[2]);
        */
    }

    public void draw(){

        //Config the rotation
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
            case VERTICAL:
                posX1=posX1+25;
                posX2=posX1;
                posY2=posY1+50;
                posX3=posX1;
                posY3=posY1;
                break;
            case HORIZONTAL:
                posY1=posY1+25;
                posX2=posX1+50;
                posY2=posY1;
                posX3=posX1;
                posY3=posY1;
                break;
        }



        //retrieve the image
        ImageIcon sprite = new ImageIcon("src/Game/cobblestone_texture.jpeg");
        Image tempImage = sprite.getImage();
        tempImage = tempImage.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        sprite = new ImageIcon(tempImage);      

        //Draw the images
        for(int i=0;i<ObstacleObject.length;i++){
            
            //sprite[i] = new ImageIcon("src/Game/playerSprite.png");
            /*System.out.println("Sprite has dimensions " + sprite[i].getIconWidth() + " by "+sprite[i].getIconHeight());
            
            //Resize it down to 50x50
            tempImage[i] = sprite[i].getImage();
            sprite[i] = new ImageIcon(tempImage[i].getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH));
    
            //Put it in the game
            System.out.println("i = "+i);*/
            
            ObstacleObject[i].setIcon(sprite);
            ObstacleObject[i].setBounds((int)posX1, (int)posY1, 50, 50);
            ObstacleObject[i].setVisible(true);
            gameJFrame.add(ObstacleObject[i]);
        }
        
        ObstacleObject[0].setBounds((int)posX1, (int)posY1, 50, 50);
        ObstacleObject[1].setBounds((int)posX2, (int)posY2, 50, 50);
        ObstacleObject[2].setBounds((int)posX3, (int)posY3, 50, 50);

        System.out.println("X1 = "+posX1+", Y1 = "+posY1);
        System.out.println("X2 = "+posX2+", Y2 = "+posY2);
        System.out.println("X3 = "+posX3+", Y3 = "+posY3);

        gameJFrame.revalidate();

    }

    public boolean checkCollision(float x,float y){

        if((x >= posX1 && x <= (posX1 + ObstacleObject[0].getWidth()) && y >= posY1 && y <= (posY1 + ObstacleObject[0].getHeight())) || (x >= posX2 && x <= (posX2 + ObstacleObject[1].getWidth()) && y >= posY2 && y <= (posY2 + ObstacleObject[1].getHeight())) || (x >= posX3 && x <= (posX3 + ObstacleObject[2].getWidth()) && y >= posY3 && y <= (posY3 + ObstacleObject[2].getHeight()))){
            return true;
        }
        return false;
    }

}