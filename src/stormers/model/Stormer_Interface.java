package stormers.model;

public interface Stormer_Interface {
    public int getX();

    public int getY();

    // MODIFIES: this
    // EFFECTS:  stormer is moved down the screen DY units and randomly takes
    //           a step of size no greater than JIGGLE_X to the left or right
    public void move();

    // MODIFIES: none
    // EFFECTS:  returns true if this stormer has collided with missile m,
    //           false otherwise
    public boolean collidedWithMissile(Missile m);

    // MODIFIES: this
    // EFFECTS: tank is constrained to remain within horizontal bounds of game
    public void handleBoundary();
}
