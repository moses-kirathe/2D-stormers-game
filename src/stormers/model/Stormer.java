package stormers.model;

import java.awt.*;

public class Stormer implements Stormer_Interface {
    public static final int SIZE_X = 15;
    public static final int SIZE_Y = 9;
    public static final int DY = 5;
    public static final Color COLOR = new Color(10, 50, 188);
    private static final int JIGGLE_X = 1;

    private int x;
    private int y;

    // EFFECTS: stormer is positioned at coordinates (x, y)
    public Stormer(int x, int y) {
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
    // EFFECTS:  stormer is moved down the screen DY units and randomly takes
    //           a step of size no greater than JIGGLE_X to the left or right
    @Override
    public void move() {
        x = x + Game.RND.nextInt(2 * JIGGLE_X + 1) - JIGGLE_X;
        y = y + DY;

        handleBoundary();
    }

    // MODIFIES: none
    // EFFECTS:  returns true if this stormer has collided with missile m,
    //           false otherwise
    @Override
    public boolean collidedWithMissile(Missile m) {
        Rectangle stormerBoundingRect = new Rectangle(getX() - SIZE_X / 2, getY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(m.getX() - SMissile.SIZE_X / 2, m.getY() - SMissile.SIZE_Y/ 2,
                SMissile.SIZE_X, SMissile.SIZE_Y);
        return stormerBoundingRect.intersects(missileBoundingRect);
    }

    public boolean collidedWithBigMissile(BMissile bm) {
        Rectangle stormerBoundingRect = new Rectangle(getX() - SIZE_X / 2, getY() - SIZE_Y / 2, SIZE_X, SIZE_Y);
        Rectangle missileBoundingRect = new Rectangle(bm.getX() - BMissile.SIZE_X / 2, bm.getY() - BMissile.SIZE_Y/ 2,
                BMissile.SIZE_X, BMissile.SIZE_Y);
        return stormerBoundingRect.intersects(missileBoundingRect);
    }
    // MODIFIES: this
    // EFFECTS: tank is constrained to remain within horizontal bounds of game
    @Override
    public void handleBoundary() {
        if (x < 0)
            x = 0;
        else if (x > Game.WIDTH)
            x = Game.WIDTH;
    }
}

