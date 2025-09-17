package Displayable.Object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Object{
    public final int healValue;

    public Heart(GamePanel gp, int x,int y,int healValue){
        super(gp,x,y);
        this.healValue = healValue;
        width= gp.tileSize/3;
        height= gp.tileSize/3;
    }

    public Heart(GamePanel gp,int x,int y){
        this(gp, x ,y , 2);
    }

    @Override
    public void update(){
        super.update();
        if (collision){
            if (gp.player.health==gp.player.maxHealthValue) return;
            gp.player.addHealth(healValue);
            super.manager.removeObject(this);
        }
    }

    @Override
    public void loadSprites() {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/object/heart.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
