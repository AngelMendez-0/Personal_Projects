package Main;

import Inputs.KeyboardInputs;
import Inputs.MouseInputs;
import Utilz.Constants;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Game game;

    public GamePanel(Game game) {
        this.game = game;
        setPanelSize();                 // <-- make the panel exactly the grid size

        setFocusable(true);
        requestFocusInWindow();

        MouseInputs mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    } // end of GamePanel

    // Panel size = number of tiles * tile size, so the level fills the window.
    private void setPanelSize() {
        int width  = Constants.GameConstants.GAME_WIDTH_TILES  * Constants.GameConstants.TILE_SIZE; // 800
        int height = Constants.GameConstants.GAME_HEIGHT_TILES * Constants.GameConstants.TILE_SIZE; // 576
        setPreferredSize(new Dimension(width, height));
    } // end of setPanelSize

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    } // end of paintComponent

    public Game getGame() { return game; }

} // end of GamePanel