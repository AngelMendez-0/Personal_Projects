package Main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        // Game / GameWindow
        this.game = game;
        setFocusable(true);
        requestFocusInWindow();

        // Mouse / KeyBoard inputs
        MouseInputs mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    } // end of GamePanel

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    } // end of paint

    public Game getGame() { return game; }

}