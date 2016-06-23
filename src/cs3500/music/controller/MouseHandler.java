package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Class to handle MouseEvents and to be used in Controller.
 */
public class MouseHandler implements MouseListener {

    private int x = 0;
    private int y = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        x = e.getX();
        y = e.getY();
//

    }


    public int getX() {
        int copy = this.x;
        return copy;
    }

    public int getY() {
        int copy = this.y;
        return copy;
    }





    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
