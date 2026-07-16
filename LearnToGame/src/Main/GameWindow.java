package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        JFrame jframe = new JFrame("LearningToGame");
        gamePanel.setBackground(Color.black);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel, BorderLayout.CENTER);

        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.setBackground(Color.black);
        jframe.setVisible(true);

        jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {

            }
        });
    }
} // end of Main.GameWindow
