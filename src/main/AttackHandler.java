package main;

import Displayable.Entities.Enemy;
import Displayable.Entities.Player;
import Displayable.Entities.TestRectangle;
import Interfaces.HasCollision;

import java.awt.*;
import java.util.ArrayList;

public class AttackHandler {
    private final GamePanel gp;
    private final Player player;
    private boolean playerCanAttack;
    private final int attackLongLenght,attackShortLenght;
    public int currentAttackDamage=2;
    public int currentAttackCooldown = 15; // Durée en frames (30 = 0.5s à 60 FPS)
    private int cooldownTimer = 0;

    public AttackHandler(GamePanel gp){
        this.gp = gp;
        this.player=gp.player;
        playerCanAttack=true;
        attackLongLenght=gp.tileSize;
        attackShortLenght=player.solidArea.height;

    }

    public void update() {
        if (playerCanAttack) return;

        if (cooldownTimer > 0) cooldownTimer--;
        else playerCanAttack = true;
    }



    public void handlePlayerAttack(){
        if (!playerCanAttack) return;

        String direction = player.lastDirection;
        int x = player.getX() + player.solidArea.x;
        int y = player.getY() + player.solidArea.y;
        int width = gp.tileSize;
        int height = gp.tileSize;

        switch (direction) {
            case "up" -> {
                y -= attackLongLenght;
                width = attackShortLenght;
                height = attackLongLenght;
            }
            case "down" -> {
                y += player.solidArea.height;
                width = attackShortLenght;
                height = attackLongLenght;
            }
            case "left" -> {
                x -= attackLongLenght;
                width = attackLongLenght;
                height = attackShortLenght;
            }
            case "right" -> {
                x += player.solidArea.width;
                width = attackLongLenght;
                height = attackShortLenght;
            }
            case "up right" -> {
                x += player.solidArea.width;
                y -= gp.tileSize;
            }
            case "down right" -> {
                x += player.solidArea.width;
                y += player.solidArea.height;
            }
            case "up left" -> {
                x -= gp.tileSize;
                y -= gp.tileSize;
            }
            case "down left" -> {
                x -= gp.tileSize;
                y += player.solidArea.height;
            }
        }
        Attack attack = new Attack(x, y, width, height, currentAttackDamage);

        TestRectangle rect1= new TestRectangle(attack.x,attack.y,attack.width,attack.height,gp);
        rect1.addToDisplay();

        playerCanAttack = false;
        cooldownTimer = currentAttackCooldown;
        handleEnemies(attack);

    }

    public void handleEnemies(Attack attack){
        ArrayList<Enemy> enemies = gp.collisionChecker.checkEnemiesHit(attack);
        if (enemies==null) {
            System.out.println("No Hit.");
            return;
        }

        for (Enemy enemy : enemies){
            enemy.bounceBack = true;
            enemy.bounceBackValue = enemy.bounceBackStartValue;
            enemy.bounceBackFrom = new Point(player.getX(),player.getY());
            enemy.removeHealth(currentAttackDamage);
            System.out.println("Hit !");
        }
    }


    public static class Attack extends Rectangle implements HasCollision{
        int attackDamage;
        public Attack(int x, int y, int width, int height, int attackDamage){
            super(x,y,width,height);
            this.attackDamage=attackDamage;
        }
        @Override
        public Rectangle getCollisionArea() {return this;}
    }


}
