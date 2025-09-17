package Displayable.Object;

import Displayable.Entities.Player;
import Interfaces.Displayable;
import main.GamePanel;

import java.util.ArrayList;

public class ObjectManager {
    public ArrayList<Object> objects;
    private final GamePanel gp;

    public ObjectManager(GamePanel gp) {
        this.gp = gp;
        objects = new ArrayList<>();
    }

    public void createObject(Class<? extends Object> clazz, int x, int y) {
        try {
            Object obj = clazz.getDeclaredConstructor(GamePanel.class, int.class, int.class)
                    .newInstance(gp, x, y);
            objects.add(obj);
            Displayable.getDisplayables().add(obj);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeObject(Object object) {
        objects.remove(object);
        Displayable.toRemove.add(object);
    }

    public void initializeObject(){
        for (int i = 0; i < 15; i++) {
            int x = (int) (Math.random() * gp.worldColNumber);
            int y = (int) (Math.random() * gp.worldLineNumber);
            createObject(Heart.class, x, y);
        }


        for (Object o : objects) {
            o.addToDisplay();
        }
    }
}
