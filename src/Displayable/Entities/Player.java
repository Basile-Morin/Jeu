package Displayable.Entities;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import main.GamePanel;
import main.KeyHandler;


public class Player extends Entity {
    private final KeyHandler keyH;
    private boolean isInvincible;
    private int invincibilityTimeLeft;



    public Player(GamePanel gp, int x, int y) {
        super(gp, x, y);
        this.keyH = gp.keyHandler;
        screenX = (gp.screenWidth - gp.tileSize) / 2;//360 pixels;
        screenY = (gp.screenHeight - gp.tileSize) / 2; //264 pixels
        speed=4;

    }

    @Override
    public void loadSprites() {
        if (up1 == null) {
            try {
                down0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown0.png")));
                down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown1.png")));
                down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDown2.png")));
                up0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp0.png")));
                up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp1.png")));
                up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUp2.png")));
                left0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft0.png")));
                left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft1.png")));
                left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyLeft2.png")));
                right0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight0.png")));
                right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight1.png")));
                right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyRight2.png")));
                upLeft0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft0.png")));
                upLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft1.png")));
                upLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpLeft2.png")));
                downLeft0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft0.png")));
                downLeft1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft1.png")));
                downLeft2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownLeft2.png")));
                upRight0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight0.png")));
                upRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight1.png")));
                upRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyUpRight2.png")));
                downRight0 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight0.png")));
                downRight1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight1.png")));
                downRight2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/boy/boyDownRight2.png")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void update() {

        gp.attackHandler.update();
        handleInvincibility();
        handleEnemyCollision();

        Point movement = this.findPlayerMovementFromKeyboard();  //mouvement que le joueur essaye de faire
        Point adjustedMovement = handleWallsAndBounceback(movement.x, movement.y);

        handleDirection(adjustedMovement.x,adjustedMovement.y);

        move(adjustedMovement.x, adjustedMovement.y);
    }

    private void handleInvincibility() {
        if (!isInvincible) return;
        invincibilityTimeLeft--;
        if (invincibilityTimeLeft > 0) return;
        isInvincible = false;
    }

    private void handleEnemyCollision() {
        Enemy collisionEnemy = gp.collisionChecker.checkPlayerEnemyCollision(this);

        if (collisionEnemy != null && !isInvincible) {
            triggerBounceBackFrom(collisionEnemy);
            startInvincibility();
            removeHealth(1);
        }
    }

    private void startInvincibility() {
        int invincibilityTime = 60;
        isInvincible = true;
        invincibilityTimeLeft = invincibilityTime;
    }

    private void triggerBounceBackFrom(Enemy enemy) {
        enemy.bounceBack = true;
        enemy.bounceBackValue = enemy.bounceBackStartValue;
        enemy.bounceBackFrom = new Point(x, y);

        bounceBackValue = bounceBackStartValue;
        bounceBack = true;
        bounceBackFrom = new Point(enemy.x, enemy.y);
    }

    public Point findPlayerMovementFromKeyboard(){
        int dX=0,dY=0;

        if (keyH.zPressed && !keyH.sPressed) {
            dY = -1*speed;
        } else if (keyH.sPressed && !keyH.zPressed) {
            dY = speed;
        }

        if (keyH.qPressed && !keyH.dPressed) {
            dX = -1*speed;
        } else if (keyH.dPressed && !keyH.qPressed) {
            dX = speed;
        }

        return new Point(dX,dY);
    }




}
