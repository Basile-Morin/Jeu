package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean zPressed, qPressed, sPressed, dPressed;
    GamePanel gp;
    public KeyHandler() {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code=e.getKeyCode();
//        System.out.println("code: " + code); // Pour tester les codes des touches
        if (code == 90) {
            zPressed = true;
        } else if (code == 81) {
            qPressed = true;
        } else if (code == 83) {
            sPressed = true;
        } else if (code == 68) {
            dPressed = true;
        } else if (code == 38) {
            gp.attackHandler.handlePlayerAttack();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == 90) {
            zPressed = false;
        } else if (code == 81) {
            qPressed = false;
        } else if (code == 83) {
            sPressed = false;
        } else if (code == 68) {
            dPressed = false;
        }
    }
}


