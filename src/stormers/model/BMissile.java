package stormers.model;

import java.awt.*;

public class BMissile extends Missile{
    public static final Color COLOR = new Color(0, 51, 0);;
    public static int SIZE_X = 20;
    public static int SIZE_Y = 18;
    public static int DY = -4;


    private int x;
    private int y;

    // EFFECTS: missile is positioned at coordinates (x, y)
    public BMissile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    // MODIFIES: this
    // EFFECTS: missile is moved DY units (up the screen)
    @Override
    public void move() {
        y = y + DY;
    }
}
