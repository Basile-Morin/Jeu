package Displayable.Entities;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Interfaces.Displayable;
import Interfaces.HasCollision;
import main.GamePanel;
import Interfaces.HasHealth;

import static java.lang.Math.hypot;
import static java.lang.Math.signum;

abstract public class Entity implements Displayable, HasHealth, HasCollision {

    protected final GamePanel gp;
    protected final int width;
    protected final int height;
    protected final int imageStateTime;
    public final int bounceBackStartValue;
    public final Rectangle solidArea;
    public int screenX,screenY,imageCount;
    protected int x,y,
            speed;
    public int health, maxHealthValue=10;
    public String direction="down",lastDirection = "down";
    protected BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2,
            upLeft0, upLeft1, upLeft2, upRight0, upRight1, upRight2, downLeft0, downLeft1, downLeft2, downRight0, downRight1, downRight2;
    protected boolean imageState;
    protected boolean entityMoving;
    protected boolean dead;
    public boolean bounceBack;
    public Point bounceBackFrom;
    public double bounceBackValue;

    public static ArrayList<Entity> entities = new ArrayList<>();

    public Entity(GamePanel gp,int x, int y) {
        loadSprites();

        this.gp=gp;
        width=gp.tileSize;
        height=gp.tileSize;
        health=10;
        imageStateTime=30;
        bounceBackStartValue=4;
        solidArea=new Rectangle(16,16,32,32);

        this.x=x*gp.tileSize;
        this.y=y*gp.tileSize;

        entities.add(this);

        speed=2;
    }

    public Rectangle getCollisionArea() {
        return new Rectangle(solidArea.x+x,solidArea.y+y,solidArea.width,solidArea.height);
    }

    abstract public void loadSprites();

    @Override
    abstract public void update();

    @Override
    public void draw(Graphics2D g) {

        boolean moving=true;
        if (up1==null){
            g.setColor(Color.white);
            g.fillRect(screenX,screenY,width,height);
        }

        if (direction.equals("no direction")) {
            direction = lastDirection;
            moving=false;
        }
        switch (direction) {
            case "up":
                if (!moving){
                    g.drawImage(up0, screenX, screenY, width, height, null);                }
                else if (!imageState) {
                    g.drawImage(up1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(up2, screenX, screenY, width, height, null);
                }
                break;

            case "left":
                if (!moving){
                    g.drawImage(left0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(left1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(left2, screenX, screenY, width, height, null);
                }
                break;

            case "down":
                if (!moving){
                    g.drawImage(down0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(down1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(down2, screenX, screenY, width, height, null);
                }
                break;

            case "right":
                if (!moving){
                    g.drawImage(right0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(right1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(right2, screenX, screenY, width, height, null);
                }
                break;

            case "up left":
                if (!moving){
                    g.drawImage(upLeft0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(upLeft1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(upLeft2, screenX, screenY, width, height, null);
                }
                break;

            case "up right":
                if (!moving){
                    g.drawImage(upRight0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(upRight1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(upRight2, screenX, screenY, width, height, null);
                }
                break;

            case "down left":
                if (!moving){
                    g.drawImage(downLeft0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(downLeft1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(downLeft2, screenX, screenY, width, height, null);
                }
                break;

            case "down right":
                if (!moving){
                    g.drawImage(downRight0, screenX, screenY, width, height, null);
                }
                else if (!imageState) {
                    g.drawImage(downRight1, screenX, screenY, width, height, null);
                } else {
                    g.drawImage(downRight2, screenX, screenY, width, height, null);
                }
                break;

        }
        lastDirection = direction;

        if (entityMoving) imageCount++;
        if (imageCount == imageStateTime) {
            imageState = !imageState;
            imageCount = 0;

        }
        displayHealth(g);
    }

    public void updateScreenCoordinates(){
        screenX=x-gp.player.getX()+gp.player.screenX;
        screenY=y-gp.player.getY()+gp.player.screenY;
    }

    @Override
    public void displayHealth(Graphics g) {
        g.drawImage(healthBars[health], screenX, (int) (screenY-gp.tileSize/1.5), width, height, null);
    }


    @Override
    public void addHealth(int health){
        this.health+=health;
        if (this.health>maxHealthValue) this.health=maxHealthValue;
    }

    @Override
    public void removeHealth(int health){
        this.health-=health;
        if (this.health<=0) {
            dead=true;
            this.health=0;
        }
    }

    public Point2D.Double calculateBounceBackValues(){
        int adjacent = bounceBackFrom.x-this.x;
        int oppose = bounceBackFrom.y-this.y;

        while (adjacent==0 && oppose==0) {
            oppose = (int) (Math.random() * 3) - 1;
            adjacent = (int) (Math.random() * 3) - 1;
        }

        double hypothenuse = hypot(oppose,adjacent);

        double dX = -1*(this.bounceBackValue*(adjacent)/hypothenuse);
        double dY = -1*(this.bounceBackValue*(oppose)/hypothenuse);
        bounceBackValue-=0.1;
        if (bounceBackValue<=0){
            bounceBack=false;
        }
        return new Point2D.Double(dX,dY);
    }

    public void handleDirection(double dX, double dY){

        double dxRatio,dyRatio,tolerance=0.3;
        int absDX = (int) Math.abs(dX);
        int absDY = (int) Math.abs(dY);
        int total = absDX + absDY;



        if (total!=0) {
            dxRatio = (double) absDX / total;
            entityMoving=true;
            dyRatio = (double) absDY / total;
        } else {
            dxRatio = 0;
            dyRatio = 0;
            entityMoving=false;
        }

        if (dxRatio > tolerance && dyRatio > tolerance) {
            if (dX > 0 && dY < 0) {
                this.direction = "up right";
            } else if (dX > 0 && dY > 0) {
                this.direction = "down right";
            } else if (dX < 0 && dY < 0) {
                this.direction = "up left";
            } else if (dX < 0 && dY > 0) {
                this.direction = "down left";
            }
        } else if (dxRatio<tolerance && dyRatio<tolerance) {
            this.direction = "no direction";

        } else if (dxRatio <= tolerance) {
            if (dY < 0) {
                this.direction = "up";
            } else if (dY > 0) {
                this.direction = "down";
            }

        } else if (dyRatio <= tolerance) {
            if (dX < 0) {
                this.direction = "left";
            } else if (dX > 0) {
                this.direction = "right";
            }
        }
    }

    public Point handleWallsAndBounceback(double dX, double dY){
        final double originaldX=dX,originaldY=dY;
        if (bounceBack) {
            Point2D.Double bounceMovementCoordinates = calculateBounceBackValues();
            dX+= bounceMovementCoordinates.x;
            dY+= bounceMovementCoordinates.y;
        }

        boolean[] blockedDirection=gp.collisionChecker.checkTileCollision(this);
        if (blockedDirection[0] && dY<0) {
            dY=0;
            dX = this.speed*signum(originaldX)+(dX-originaldX);
        }
        else if (blockedDirection[2] && dY>0) {
            dY=0;
            dX = this.speed*signum(originaldX)+(dX-originaldX);
        }
        if (blockedDirection[3] && dX<0) {
            dX=0;
            dY = this.speed*signum(originaldY)+(dY-originaldY);
        }
        else if (blockedDirection[1] && dX>0) {
            dX=0;
            dY = this.speed*signum(originaldY)+(dY-originaldY);
        }
        return new Point((int) Math.round(dX),(int) Math.round(dY));
    }

    public void move(int dX, int dY) {
        this.x += dX;
        this.y += dY;
    }


    public int getSpeed(){return speed;}
    public int getY(){return y;}
    public int getX(){return x;}





}
