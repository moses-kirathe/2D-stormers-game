package stormers.model;

import java.awt.*;

public class Tank {
    public static final int SIZE_X = 15;
    public static final int SIZE_Y = 8;
    public static final int DX = 6;
    public static final int Y_POS = Game.HEIGHT - 60;
    public static final Color COLOR = new Color(0, 128, 20);

    public boolean isFacingRight = false;


    private int x;
    private int y;

    // EFFECTS: places tank at position (x, Y_POS) moving right.
    public Tank(int x) {
        this.x = x;
        this.y = Y_POS;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // EFFECTS: returns true if tank is facing right, false otherwise
    public boolean isFacingRight() {
        return isFacingRight;
    }

    // MODIFIES: this
    // EFFECTS: tank is facing right
    public void faceRight() {
        isFacingRight = true;
    }

    // MODIFIES: this
    // EFFECTS: tank is facing left
    public void faceLeft() {
        isFacingRight = false;
    }

    // MODIFIES: this
    // EFFECTS:  tank is moved DX units in whatever direction it is facing and is
    //           constrained to remain within horizontal bounds of game

    public void move() {
        if (isFacingRight) {
            x = x + DX;
        } else {
            x = x - DX;
        }
        handleBoundary();
    }

    // MODIFIES: this
    // EFFECTS: tank is constrained to remain within horizontal bounds of game
    public void handleBoundary() {
        if (x > Game.WIDTH) {
            x = Game.WIDTH;
        }
        if (x < 0) {
            x = 0;
        }
    }

}