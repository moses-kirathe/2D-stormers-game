package stormers.ui;

import stormers.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Stormers extends JFrame {
    private static final int INTERVAL = 20;
    private Game game;
    private GamePanel gp;
    private ScorePanel sp;

    // EFFECTS: sets up window in which Planet Stormer_Interface game will be played
    public Stormers() {
        super("Stormers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setUndecorated(false);
        game = new Game();
        gp = new GamePanel(game);
        sp = new ScorePanel(game);
        add(gp);
        add(sp, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    // MODIFIES: none
    // EFFECTS:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!game.isOver()) {
                    game.update();
                    sp.update();
                }
                gp.repaint();
            }
        });

        t.start();
    }

    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }
    }

    // Play the game
    public static void main(String[] args) {
        new Stormers();
    }
}
