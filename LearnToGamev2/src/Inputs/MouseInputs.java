package Inputs;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Entities.Player;
import Main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    private Player player;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    } // end of MouseInputs

    @Override
    public void mouseClicked(MouseEvent e) {

    } // end of mouseClicked

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            gamePanel.getGame().getPlayer().setAttack(true);
        } // end of if
    }// end of mousePressed

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            gamePanel.getGame().getPlayer().setAttack(false);
        } // end of if
    }// end of mouseReleased

    @Override
    public void mouseEntered(MouseEvent e) {

    }// end of mouseEntered

    @Override
    public void mouseExited(MouseEvent e) {

    }// end of mouseExited

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
} // end of MouseInputs
