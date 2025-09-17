package main;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        KeyHandler keyHandler = new KeyHandler();
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Jeu");
        frame.addKeyListener(keyHandler);

        GamePanel gamePanel = new GamePanel(keyHandler);
        gamePanel.startGameThread();
        frame.add(gamePanel);
        frame.pack();

    }
}