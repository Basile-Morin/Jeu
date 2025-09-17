package Displayable.Entities;
import Interfaces.Displayable;
import Interfaces.HasCollision;
import main.AttackHandler;
import main.GamePanel;

import java.awt.*;
import java.util.ArrayList;

public class CollisionChecker {
    GamePanel gp;
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }


    public Enemy checkPlayerEnemyCollision(Player player){
        for (Displayable dis : Displayable.getDisplayables()){
            if (dis instanceof Enemy && checkCollision(player, (Enemy) dis)) return (Enemy) dis;
        }
        return null;
    }

    public ArrayList<Enemy> checkEnemiesHit(HasCollision attack){
        ArrayList<Enemy> enemies = new ArrayList<>();
        for (Displayable dis : Displayable.getDisplayables()){
            if (dis instanceof Enemy && checkCollision(attack, (Enemy) dis)) enemies.add((Enemy) dis);
        }
        return enemies.isEmpty() ? null : enemies;
    }

    public boolean checkCollision(HasCollision entity1, HasCollision entity2){
        Rectangle rect1 = entity1.getCollisionArea();
        Rectangle rect2 = entity2.getCollisionArea();

        return rect1.intersects(rect2);
    }
    public boolean[] checkTileCollision(Entity entity){
        boolean[] blockedDirection = new boolean[4];
        boolean verticalCollision,horizontalCollision;
        int entityCollisionLeftWorldX = entity.getX()+ entity.solidArea.x;
        int entityCollisionTopWorldY = entity.getY()+ entity.solidArea.y;
        int entityCollisionRightWorldX = entity.getX()+ entity.solidArea.x+ entity.solidArea.width;
        int entityCollisionBottomWorldY = entity.getY()+ entity.solidArea.y+ entity.solidArea.height;

        int entityCollisionLeftCol = entityCollisionLeftWorldX/gp.tileSize;
        int entityCollisionTopRow = entityCollisionTopWorldY/gp.tileSize;
        int entityCollisionRightCol = entityCollisionRightWorldX/gp.tileSize;
        int entityCollisionBottomRow = entityCollisionBottomWorldY/gp.tileSize;

        int newEntityCollisionRightCol,newEntityCollisionBottomRow,newEntityCollisionLeftCol,newEntityCollisionTopRow;

        newEntityCollisionTopRow = Math.floorDiv(entityCollisionTopWorldY - entity.getSpeed(),gp.tileSize);
        verticalCollision = checkUpDown(newEntityCollisionTopRow,entityCollisionLeftCol,entityCollisionRightCol);
        blockedDirection[0]= verticalCollision;


        newEntityCollisionBottomRow = (entityCollisionBottomWorldY + entity.getSpeed())/gp.tileSize;
        verticalCollision = checkUpDown(newEntityCollisionBottomRow,entityCollisionLeftCol,entityCollisionRightCol);
        blockedDirection[2]= verticalCollision;


        newEntityCollisionLeftCol = Math.floorDiv(entityCollisionLeftWorldX - entity.getSpeed(),gp.tileSize);
        horizontalCollision = checkLeftRight(newEntityCollisionLeftCol,entityCollisionTopRow,entityCollisionBottomRow);
        blockedDirection[3]= horizontalCollision;

        newEntityCollisionRightCol = (entityCollisionRightWorldX + entity.getSpeed())/gp.tileSize;
        horizontalCollision = checkLeftRight(newEntityCollisionRightCol,entityCollisionTopRow,entityCollisionBottomRow);
        blockedDirection[1]= horizontalCollision;
        return blockedDirection;
    }

    public boolean checkLeftRight(int col,int topRow,int bottomRow){
        int tileNum1,tileNum2;
        if (col<0 || col > gp.worldColNumber-1){return true;}
        tileNum1 = gp.mapManager.getMap()[col][topRow];
        tileNum2 = gp.mapManager.getMap()[col][bottomRow];
        return (gp.mapManager.getTile()[tileNum1].collision || gp.mapManager.getTile()[tileNum2].collision);
    }

    public boolean checkUpDown(int row,int leftCol,int rightCol){
        int tileNum1,tileNum2;
        if (row<0 || row>gp.worldLineNumber-1){return true;}
        tileNum1 = gp.mapManager.getMap()[leftCol][row];
        tileNum2 = gp.mapManager.getMap()[rightCol][row];
        return (gp.mapManager.getTile()[tileNum1].collision || gp.mapManager.getTile()[tileNum2].collision);
    }
}
