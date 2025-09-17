package Displayable.Object;

import Interfaces.Displayable;
import Interfaces.HasCollision;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Object implements Displayable, HasCollision {
    public BufferedImage image;
    public String name;
    public boolean collision;
    public int x,y,screenX,screenY,width,height;
    protected final GamePanel gp;
    protected final ObjectManager manager;


    public Object(GamePanel gp,int x,int y){
        this.gp=gp;
        this.manager=gp.objectManager;
        collision=false;
        loadSprites();
        this.x=x* gp.tileSize;
        this.y=y* gp.tileSize;

    }

    @Override
    public Rectangle getCollisionArea() {
        return new Rectangle(x + (gp.tileSize - width) / 2,y + (gp.tileSize - height) / 2,width,height);
    }

    abstract public void loadSprites();


    @Override
    public void update(){
        updateScreenCoordinates();
        collision = gp.collisionChecker.checkCollision(this, gp.player);

    }


    public void updateScreenCoordinates(){
        screenX=x-gp.player.getX()+gp.player.screenX;
        screenY=y-gp.player.getY()+gp.player.screenY;

    }

    @Override
    public void draw(Graphics2D g) {

        g.drawImage(image, screenX + (gp.tileSize - width) / 2, screenY + (gp.tileSize - height) / 2, width, height, null);
    }
}
