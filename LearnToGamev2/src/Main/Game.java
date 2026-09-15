package Main;

import Entities.Player;

import java.awt.*;

import Levels.Level;
import Levels.LevelHandler;
import Utilz.Constants;
import static Utilz.Constants.PlayerConstants.Player_Width;
import static Utilz.Constants.PlayerConstants.Player_Height;

public class Game implements Runnable {

    private Player player;
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Thread gameThread;
    private LevelHandler levelHandler;

    private final int FPS = 120;

    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    } // end of Game

    private void initClasses() {
        levelHandler = new LevelHandler(this);
        player = new Player(Player_Width, Player_Height);
        // Hand the tile grid to the player so it can fall on / bump into tiles.
        player.loadLvlData(levelHandler.getCurrentLevel().getLevelData());
    } // end of initClasses

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    } // end of startGameLoop

    public void update() {
        levelHandler.update();
        player.Update();
    } // end of update

    public void render(Graphics g) {
        levelHandler.draw(g);
        player.Render(g);
    } // end of render

    @Override
    public void run() {
        double timePerFrame = 1_000_000_000.0 / FPS;
        long lastFrame = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                update();
                gamePanel.repaint();
                lastFrame = now;
            }
        }
    } // end of run

    public Player getPlayer() { return player; }

} // end of Main.Game