package Displayable.Entities;

import Interfaces.Displayable;
import main.GamePanel;

import java.awt.*;

public class TestRectangle extends Rectangle implements Displayable {
    int screenX,screenY;
    GamePanel gp;
    public TestRectangle(int x, int y, int width, int height, GamePanel gp) {
        super(x, y, width, height);
        this.gp = gp;
    }
    @Override
    public void loadSprites() {
        ;
    }

    @Override
    public void update() {
        screenX=x-gp.player.getX()+gp.player.screenX;
        screenY=y-gp.player.getY()+gp.player.screenY;
    }

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.white);
        g.fillRect(screenX,screenY,width,height);
        deleteFromDisplay();
    }
}
