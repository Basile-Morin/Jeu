package Interfaces;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public interface HasHealth {

    BufferedImage[] healthBars = new BufferedImage[11];

    void displayHealth(Graphics g);

    void addHealth(int health);
    void removeHealth(int health);

    default BufferedImage[] getHealthBars() {return healthBars;}

    static void initializeHealthBars(){
        for(int i = 0; i < healthBars.length; i++){
            try {
                healthBars[i] = ImageIO.read(Objects.requireNonNull(HasHealth.class.getResourceAsStream("/Image/healthBar/healthBar"+ i +".png")));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
