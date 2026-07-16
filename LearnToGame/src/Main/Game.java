package Main;

import Entities.Player;

import java.awt.*;

public class Game implements Runnable {

    Player player;
    GamePanel gamePanel;
    GameWindow gameWindow;
    Thread gameThread;

    static int gameHeight = 1000;
    static int gameWidth = 1000;

    private final int FPS = 120;

    public Game() {
        player = new Player(200, 200);
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocusInWindow();
        startGameLoop();
    } // end of Game

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    } // end of startGameLoop

    public void update() {
        player.Update();
    } // end of update

    public void render(Graphics g) {
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