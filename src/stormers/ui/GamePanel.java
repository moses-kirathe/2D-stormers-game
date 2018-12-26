package stormers.ui;

import stormers.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GamePanel extends JPanel{

    private static final String OVER = "Game Over!";
    private static final String REPLAY = "R to replay";
    private Game game;


    // EFFECTS:  sets size and background colour of panel,
    //           updates this with the game to be displayed
    public GamePanel(Game g) {

        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.WHITE);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isOver()) {
            gameOver(g);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the game onto g
    private void drawGame(Graphics g) {
        drawTank(g);
        drawStormers(g);
        drawMissiles(g);
    }

    // MODIFIES: g
    // EFFECTS:  draws the tank onto g
    private void drawTank(Graphics g) {
        Tank t = game.getTank();
        try {
            final BufferedImage image = ImageIO.read(new File("/Users/moseskirathe/Desktop/<hello-world>/git-repos/stormers/src/resources/tank.png"));
            final BufferedImage resizedimg = scale(image, 100, 100);
            g.drawImage(resizedimg, t.getX() - Tank.SIZE_X / 2,Tank.Y_POS - Tank.SIZE_Y / 2, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Polygon tankFront = createTankFront(t);
        g.fillPolygon(tankFront);

        //ImageIcon img = new ImageIcon("/Users/mos/Desktop/CS\\ 210\\ PROJECTS/LABS/B01-SpaceInvadersStarter/B01-SpaceInvadersStarter/src/resources/tank.png");

    }

    // EFFECTS: returns a polygon that represents front of tank
    private Polygon createTankFront(Tank t) {
        Polygon tankFront = new Polygon();

        if (t.isFacingRight()) {
            tankFront.addPoint(t.getX() + Tank.SIZE_X / 2, Tank.Y_POS + Tank.SIZE_Y / 2);
            tankFront.addPoint(t.getX() + Tank.SIZE_X, Tank.Y_POS);
            tankFront.addPoint(t.getX() + Tank.SIZE_X / 2, Tank.Y_POS - Tank.SIZE_Y / 2);
        }
        else {
            tankFront.addPoint(t.getX() - Tank.SIZE_X / 2, Tank.Y_POS + Tank.SIZE_Y / 2);
            tankFront.addPoint(t.getX() - Tank.SIZE_X, Tank.Y_POS);
            tankFront.addPoint(t.getX() - Tank.SIZE_X / 2, Tank.Y_POS - Tank.SIZE_Y / 2);
        }

        return tankFront;
    }

    // MODIFIES: g
    // EFFECTS:  draws the stormers onto g
    private void drawStormers(Graphics g) {
        for(Stormer next : game.getStormers()) {
            drawStormer(g, next);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the stormer i onto g
    private void drawStormer(Graphics g, Stormer i) {
        Color savedCol = g.getColor();
        g.setColor(Stormer.COLOR);
        g.fillOval(i.getX() - Stormer.SIZE_X / 2, i.getY() - Stormer.SIZE_Y / 2, Stormer.SIZE_X, Stormer.SIZE_Y);
        g.setColor(savedCol);
    }


    // MODIFIES: g
    // EFFECTS:  draws the missiles onto g
    private void drawMissiles(Graphics g) {
        for(SMissile next : game.getSmallMissiles()) {
            drawSmallMissile(g, next);
        }

        for(BMissile bnext : game.getBigMissiles()) {
            drawBigMissile(g, bnext);
        }
    }

    // MODIFIES: g
    // EFFECTS:  draws the missile m onto g
    private void drawSmallMissile(Graphics g, SMissile m) {
        Color savedCol = g.getColor();
        g.setColor(SMissile.COLOR);
        g.fillOval(m.getX() - SMissile.SIZE_X / 2, m.getY() - SMissile.SIZE_Y / 2, SMissile.SIZE_X, SMissile.SIZE_Y);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws the missile m onto g
    private void drawBigMissile(Graphics g, BMissile bm) {
        Color savedCol = g.getColor();
        g.setColor(BMissile.COLOR);
        g.fillOval(bm.getX() - BMissile.SIZE_X / 2, bm.getY() - BMissile.SIZE_Y / 2, BMissile.SIZE_X, BMissile.SIZE_Y);
        g.setColor(savedCol);
    }

    // MODIFIES: g
    // EFFECTS:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color( 0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, Game.HEIGHT / 2);
        centreString(REPLAY, g, fm, Game.HEIGHT / 2 + 50);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS:  centres the string str horizontally onto g at vertical position yPos
    private void centreString(String str, Graphics g, FontMetrics fm, int yPos) {
        int width = fm.stringWidth(str);
        g.drawString(str, (Game.WIDTH - width) / 2, yPos);
    }

    public BufferedImage scale(BufferedImage img, int targetWidth, int targetHeight) {

        int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage ret = img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;

        int w = img.getWidth();
        int h = img.getHeight();

        int prevW = w;
        int prevH = h;

        do {
            if (w > targetWidth) {
                w /= 2;
                w = (w < targetWidth) ? targetWidth : w;
            }

            if (h > targetHeight) {
                h /= 2;
                h = (h < targetHeight) ? targetHeight : h;
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }

            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);

            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        if (g2 != null) {
            g2.dispose();
        }

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }

        return ret;

    }
}
