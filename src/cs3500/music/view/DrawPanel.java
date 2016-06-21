package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.Note;

/**
 * Created by Jameson on 6/21/16.
 */
public class DrawPanel extends JPanel {
    protected java.util.List<Note> notes;
    protected int totalNumBeats;
    protected int tempo;

    public DrawPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics = (Graphics2D) g;

        //graphics.drawLine();
    }
}
