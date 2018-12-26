package stormers.model;

import java.awt.*;

public class SMissile extends Missile{
    public static int SIZE_X = 5;
    public static int SIZE_Y = 9;

    public static int DY = -15;
    public static Color COLOR = new Color(255, 0, 0);

    private int x;
    private int y;

    // EFFECTS: missile is positioned at coordinates (x, y)
    public SMissile(int x, int y) {
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
