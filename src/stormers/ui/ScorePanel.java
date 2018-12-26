package stormers.ui;

import stormers.model.Game;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel{

    private static final String INVADERS_TXT = "Invaders shot down: ";
    private static final String MISSILES_TXT = "Missiles remaining: ";
    private static final int LBL_WIDTH = 200;
    private static final int LBL_HEIGHT = 30;
    private Game game;
    private JLabel invadersLbl;
    private JLabel missilesLbl;

    // EFFECTS: sets the background colour and draws the initial labels;
    //          updates this with the game whose score is to be displayed
    public ScorePanel(Game g) {
        game = g;
        setBackground(new Color(180, 180, 180));
        invadersLbl = new JLabel(INVADERS_TXT + game.getNumStormersDestroyed());
        invadersLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        missilesLbl = new JLabel(MISSILES_TXT + Game.MAX_MISSILES);
        missilesLbl.setPreferredSize(new Dimension(LBL_WIDTH, LBL_HEIGHT));
        add(invadersLbl);
        add(Box.createHorizontalStrut(10));
        add(missilesLbl);
    }

    // MODIFIES: this
    // EFFECTS:  updates number of invaders shot and number of missiles
    //           remaining to reflect current state of game
    public void update() {
        invadersLbl.setText(INVADERS_TXT + game.getNumStormersDestroyed());
        missilesLbl.setText(MISSILES_TXT + (Game.MAX_MISSILES - game.getNumMissiles()));
        repaint();
    }
}
