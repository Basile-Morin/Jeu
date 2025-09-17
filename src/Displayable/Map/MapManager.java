package Displayable.Map;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class MapManager {
    private final GamePanel gp;
    private final int worldColNumber;
    private final int worldLineNumber;
    private int[][] map;
    private Tile[] tile ;
    public MapManager(GamePanel gamePanel) {
        this.gp = gamePanel;
        worldColNumber = gamePanel.worldColNumber;
        worldLineNumber = gamePanel.worldLineNumber;
        this.initializeTileSprite();
        this.initializeMap();

    }

    public int[][] getMap(){return this.map;}
    public Tile[] getTile(){return this.tile;}

    public void initializeMap(){
        map=new int[worldColNumber][worldLineNumber];
        try {
            InputStream is =getClass().getResourceAsStream("/Displayable/Map/map.txt");
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            for (int y = 0; y< worldLineNumber; y++){
                String line=br.readLine();
                String[] numbers=line.split(" ");
                for (int x=0;x<worldColNumber;x++){
                    String number=numbers[x];
                    map[x][y]=Integer.parseInt(number);

                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
    public void initializeTileSprite(){
        try {
            tile = new Tile[2];

            tile[0] = new Tile();
            tile[0].image =ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/tiles/grass.png")));
            tile[0].collision=false;

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Image/tiles/water.png")));
            tile[1].collision=true;



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void drawMap(Graphics2D g){
        int playerX = gp.player.getX();
        int playerY = gp.player.getY();
        int playerScreenX= gp.player.screenX;
        int playerScreenY= gp.player.screenY;
        for(int y = 0; y< worldLineNumber; y++){
            for(int x=0;x<worldColNumber;x++){
                int tileNum =map[x][y];
                int tileScreenX= (x * gp.tileSize) - playerX +playerScreenX; //CaseX - JoueurX + JoueurX sur l'ecran
                int tileScreenY= (y * gp.tileSize) - playerY +playerScreenY;   //CaseY - JoueurY + JoueurY sur l'ecran
                if (tileScreenX<-gp.tileSize || tileScreenY<-gp.tileSize || tileScreenX>gp.worldColNumber* gp.tileSize || tileScreenY>gp.worldLineNumber*gp.tileSize) continue;
                g.drawImage(tile[tileNum].image, tileScreenX, tileScreenY, gp.tileSize, gp.tileSize, null);

            } // for y
        }//for x

    }

    public static class Tile {
        public BufferedImage image;
        public boolean collision;
    }
}
