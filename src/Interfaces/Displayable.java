package Interfaces;
import java.awt.*;
import java.util.ArrayList;

public interface Displayable {
    ArrayList<Displayable> displayables = new ArrayList<>();
    ArrayList<Displayable> toRemove = new ArrayList<>();
    ArrayList<Displayable> toAdd = new ArrayList<>();

    void loadSprites();

    void update();

    void draw(Graphics2D g);


    default void addToDisplay() {
        toAdd.add(this);
    }

    default void deleteFromDisplay() {
        toRemove.add(this);
    }


    static ArrayList<Displayable> getDisplayables() {
        return displayables;
    }
}


