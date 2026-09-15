package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame("LearningToGame");

        gamePanel.setBackground(Color.black);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel, BorderLayout.CENTER);
        jframe.setResizable(false);

        jframe.pack();                    // size the window to the panel (no more MAXIMIZED_BOTH)
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override public void windowGainedFocus(WindowEvent e) { }
            @Override public void windowLostFocus(WindowEvent e) { }
        });
    } // end of GameWindow

} // end of GameWindow