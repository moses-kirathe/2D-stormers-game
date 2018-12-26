package stormers.model;

public abstract class Missile {

    abstract int getX();

    abstract int getY();

    // MODIFIES: this
    // EFFECTS: missile is moved DY units (up the screen)
    abstract void move();
}

