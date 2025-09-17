package main;
import javax.swing.*;
import Displayable.Entities.*;
import Displayable.Entities.Player;
import Displayable.Map.MapManager;
import Displayable.Object.ObjectManager;
import Interfaces.Displayable;
import Interfaces.HasHealth;
import java.awt.*;

import static Interfaces.Displayable.*;

public class GamePanel extends JPanel implements Runnable {

    private static final int originalTileSize =16;
    private static final int scale =4;
    public final int tileSize = scale * originalTileSize; //48 pixels
    private final int maxScreenCol =16;
    private final int maxScreenLine =12;
    public final int screenWidth = maxScreenCol * tileSize; //768 pixels
    public final int screenHeight = maxScreenLine * tileSize;  //576 pixels
    private final int FPS=60;
    private final double drawInterval = (double) 1000 /FPS;
    public final int worldColNumber=32;
    public final int worldLineNumber =24;

    Thread gameThread;
    public KeyHandler keyHandler;
    public MapManager mapManager;
    public CollisionChecker collisionChecker;
    public ObjectManager objectManager;
    public AttackHandler attackHandler;
    public Player player;

    public GamePanel(KeyHandler kh){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);
        keyHandler = kh;
        player=new Player(this,7,7);
        mapManager = new MapManager(this);
        collisionChecker = new CollisionChecker(this);
        objectManager = new ObjectManager(this);
        attackHandler = new AttackHandler(this);

        kh.gp=this;

    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double nextDrawTime = System.currentTimeMillis()+drawInterval;
        double remainingTime;
        initialize();

        while(gameThread!=null){
//            long drawStart = System.nanoTime();
            update();

            repaint();
//            long drawEnd = System.nanoTime();
//            long drawTime = drawEnd-drawStart;
//            System.out.println(drawTime);

            try
            {
                remainingTime = nextDrawTime - System.currentTimeMillis();
                if(remainingTime<0){remainingTime=0;}
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void initialize(){

        player.addToDisplay();
        Enemy enemy = new Enemy(this, 1,7);
        enemy.addToDisplay();
        HasHealth.initializeHealthBars();
        objectManager.initializeObject();

    }

    public void update(){

        if (!toAdd.isEmpty()) {
            displayables.addAll(toAdd);
            toAdd.clear();
        }
        if (!toRemove.isEmpty()){
            displayables.removeAll(toRemove);
            toRemove.clear();
        }

        for (Displayable dis : Displayable.getDisplayables()) dis.update();
    }


    @Override
    public void paintComponent(Graphics g){
        if (player==null) return; //player != null Ssi initialize a tourné, s'il l'est on n'execute pas la fonction

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        mapManager.drawMap(g2);
        for (Displayable dis : displayables) dis.draw(g2);

        g2.dispose();
    }
}
