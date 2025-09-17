package Displayable.Entities;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Math.*;

public class Enemy extends Entity {


    public Enemy(GamePanel gp, int x, int y){
        super(gp,x,y);
    }
    protected static BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2,
            upLeft0, upLeft1, upLeft2, upRight0, upRight1, upRight2, downLeft0, downLeft1, downLeft2, downRight0, downRight1, downRight2;    int speed=2;

    @Override
    public void loadSprites() {
        if (Enemy.up1 == null) {
            try {
                Enemy.down0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown0.png")));
                Enemy.down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown1.png")));
                Enemy.down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown2.png")));
                Enemy.up0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp0.png")));
                Enemy.up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp1.png")));
                Enemy.up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp2.png")));
                Enemy.left0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft0.png")));
                Enemy.left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft1.png")));
                Enemy.left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft2.png")));
                Enemy.right0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight0.png")));
                Enemy.right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight1.png")));
                Enemy.right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight2.png")));
                Enemy.upLeft0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft0.png")));
                Enemy.upLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft1.png")));
                Enemy.upLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft2.png")));
                Enemy.downLeft0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft0.png")));
                Enemy.downLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft1.png")));
                Enemy.downLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft2.png")));
                Enemy.upRight0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight0.png")));
                Enemy.upRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight1.png")));
                Enemy.upRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight2.png")));
                Enemy.downRight0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight0.png")));
                Enemy.downRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight1.png")));
                Enemy.downRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight2.png")));

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            super.down0 = Enemy.down0;
            super.down1 = Enemy.down1;
            super.down2 = Enemy.down2;
            super.up0 = Enemy.up0;
            super.up1 = Enemy.up1;
            super.up2 = Enemy.up2;
            super.left0 = Enemy.left0;
            super.left1 = Enemy.left1;
            super.left2 = Enemy.left2;
            super.right0 = Enemy.right0;
            super.right1 = Enemy.right1;
            super.right2 = Enemy.right2;
            super.upLeft0 = Enemy.upLeft0;
            super.upLeft1 = Enemy.upLeft1;
            super.upLeft2 = Enemy.upLeft2;
            super.downLeft0 = Enemy.downLeft0;
            super.downLeft1 = Enemy.downLeft1;
            super.downLeft2 = Enemy.downLeft2;
            super.upRight0 = Enemy.upRight0;
            super.upRight1 = Enemy.upRight1;
            super.upRight2 = Enemy.upRight2;
            super.downRight0 = Enemy.downRight0;
            super.downRight1 = Enemy.downRight1;
            super.downRight2 = Enemy.downRight2;
        }
    }

    @Override
    public void update() {
        moveTo(gp.player);
        super.updateScreenCoordinates();
        if(dead) deleteFromDisplay();
    }

    @Override
    public void displayHealth(Graphics g) {
        g.drawImage(healthBars[health], screenX+gp.tileSize/4,screenY-gp.tileSize/2, width/2, height/2, null);
    }


    public void moveTo(Entity target){

        Point directionVector=computeDirectionVector(target);
        Point adjustedDirectionVector= handleWallsAndBounceback(directionVector.x,directionVector.y);

        move(adjustedDirectionVector.x,adjustedDirectionVector.y);
        handleDirection(adjustedDirectionVector.x,adjustedDirectionVector.y);

    }

    public Point computeDirectionVector(Entity target){
        int adjacent = target.getX()-this.x;
        int oppose = target.getY()-this.y;
        double hypothenuse = hypot(oppose,adjacent);
        if (hypothenuse == 0) return new Point(0, 0);

        double dX = (this.speed*(adjacent)/hypothenuse);
        double dY = (this.speed*(oppose)/hypothenuse);
        return new Point((int) Math.round(dX), (int) Math.round(dY));
    }

}
