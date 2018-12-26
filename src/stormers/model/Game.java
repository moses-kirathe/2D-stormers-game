package stormers.model;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int MAX_MISSILES = 10;
    public static final Random RND = new Random();
    private static final int INVASION_PERIOD = 250;   // on average, one stormer each 250 updates

    private List<SMissile> smallMissiles;
    private List<BMissile> bigMissiles;
    private List<Stormer> slowStormer;
    private Tank tank;
    private boolean isGameOver;
    private int numStormersDestroyed;



    // EFFECTS:  creates empty lists of smallMissiles and slowStormer, centres tank on screen
    public Game() {
        smallMissiles = new ArrayList<SMissile>();
        bigMissiles = new ArrayList<BMissile>();
        slowStormer = new ArrayList<Stormer>();
        setUp();
    }

    // MODIFIES: this
    // EFFECTS:  updates tank, smallMissiles and slowStormer
    public void update() {
        moveSmallMissiles();
        moveBigMissiles();
        moveStormers();
        tank.move();

        checkMissiles();
        invade();
        checkCollisions();
        checkGameOver();
    }

    // MODIFIES: this
    // EFFECTS:  turns tank, fires smallMissiles and resets game in response to
    //           given key pressed code
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE)
            firesmallMissile();

        else if(keyCode == KeyEvent.VK_F)
            fireBigMissile();

        else if (keyCode == KeyEvent.VK_R && isGameOver)
            setUp();
        else if (keyCode == KeyEvent.VK_X)
            System.exit(0);
        else
            tankControl(keyCode);
    }

    // Exercise: fill in the documentation for this method
    public boolean isOver() {
        return isGameOver;
    }

    public int getNumMissiles() {
        return smallMissiles.size() + bigMissiles.size();
    }

    public int getNumStormersDestroyed() {
        return numStormersDestroyed;
    }

    public List<Stormer> getStormers() {
        return slowStormer;
    }

    public List<SMissile> getSmallMissiles() {
        return smallMissiles;
    }

    public List<BMissile> getBigMissiles(){ return bigMissiles;}

    public Tank getTank() {
        return tank;
    }

    // MODIFIES: this
    // EFFECTS:  clears list of smallMissiles and slowStormer, initializes tank
    private void setUp() {
        slowStormer.clear();
        smallMissiles.clear();
        bigMissiles.clear();
        tank = new Tank(WIDTH / 2);
        isGameOver = false;
        numStormersDestroyed = 0;
    }

    // MODIFIES: this
    // EFFECTS:  fires a missile if max number of smallMissiles in play has
    //           not been exceeded, otherwise silently returns
    private void firesmallMissile() {
        if (smallMissiles.size() < MAX_MISSILES) {
            SMissile m = new SMissile(tank.getX(), Tank.Y_POS);
            smallMissiles.add(m);
        }
    }

    private void fireBigMissile() {
        if (bigMissiles.size() < MAX_MISSILES) {
            BMissile bm = new BMissile(tank.getX(), Tank.Y_POS);
            bigMissiles.add(bm);
        }
    }

    // MODIFIES: this
    // EFFECTS: turns tank in response to key code
    private void tankControl(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT)
            tank.faceLeft();
        else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT)
            tank.faceRight();
    }

    // MODIFIES: this
    // EFFECTS: moves the smallMissiles
    private void moveSmallMissiles() {
        for (SMissile next : smallMissiles) {
            next.move();
        }
    }

    private void moveBigMissiles(){
        for (BMissile next : bigMissiles) {
            next.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the slowStormer
    private void moveStormers() {
        for (Stormer next : slowStormer) {
            next.move();
        }
    }

    // MODIFIES: this
    // EFFECTS:  removes any missile that has traveled off top of screen
    private void checkMissiles() {
        List<SMissile> missilesToRemove = new ArrayList<SMissile>();
        List<BMissile> bmissilesToRemove = new ArrayList<BMissile>();

        for (SMissile next : smallMissiles) {
            if (next.getY() < 0) {
                missilesToRemove.add(next);
            }
        }

        for (BMissile nextbm : bigMissiles) {
            if (nextbm.getY() < 0) {
                bmissilesToRemove.add(nextbm);
            }
        }

        smallMissiles.removeAll(missilesToRemove);
        bigMissiles.removeAll(bmissilesToRemove);
    }


    private void invade() {
        if (RND.nextInt(INVASION_PERIOD) < 1) {
            Stormer i = new Stormer(RND.nextInt(Game.WIDTH), 0);
            slowStormer.add(i);
        }
    }

    // MODIFIES: this
    // EFFECTS:  removes any stormer that has been shot with a missile
    //           and removes corresponding missile from play
    private void checkCollisions() {
        List<Stormer> stormersToRemove = new ArrayList<Stormer>();
        List<SMissile> smissilesToRemove = new ArrayList<SMissile>();
        List<BMissile> bmissilesToRemove = new ArrayList<BMissile>();

        for (Stormer target : slowStormer) {
            if (checkStormerHit(target, smissilesToRemove, bmissilesToRemove)) {
                stormersToRemove.add(target);
            }
        }

        slowStormer.removeAll(stormersToRemove);
        smallMissiles.removeAll(smissilesToRemove);
        bigMissiles.removeAll(bmissilesToRemove);
    }

    // Exercise:  fill in the documentation for this method
    private boolean checkStormerHit(Stormer target, List<SMissile> missilesToRemove, List<BMissile> bmissilesToRemove) {
        for (SMissile next : smallMissiles) {
            if (target.collidedWithMissile(next)) {
                missilesToRemove.add(next);
                numStormersDestroyed++;
                return true;
            }
        }

        for (BMissile nextbm : bigMissiles) {
            if (target.collidedWithBigMissile(nextbm)) {
                bmissilesToRemove.add(nextbm);
                numStormersDestroyed++;
                return true;
            }
        }

        return false;
    }


    // MODIFIES: this
    // EFFECTS:  if an stormer has landed, game is marked as
    //           over and lists of slowInvader & smallMissiles cleared
    private void checkGameOver() {
        for (Stormer next : slowStormer) {
            if (next.getY() > HEIGHT) {
                isGameOver = true;
            }
        }
    }
}
