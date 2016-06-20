package cs3500.music.view;

import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;

// Possibly of interest for handling mouse events

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


    private ConcreteGuiViewPanel displayPanel;
    IMusicModel model;

    /**
     * Creates new GuiView
     */
    public GuiViewFrame() {
        super();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Music Viewer v1.6        Authors: Jameson O'Connor, Brendan Rejevich");
        this.setSize(new Dimension(400, 400));
        this.displayPanel = new ConcreteGuiViewPanel();

    }

    @Override
    public void initialize() {
        int absolutePitchLo = 127;  // lowest pitch in lowest octave.
        int absolutePitchHi = 0;   // highest pitch in highest octave.

        for (Note n : this.model.getNotes()) {
            //Set the pitchLo
            if (n.getAbsPitch() < absolutePitchLo) {
                absolutePitchLo = n.getAbsPitch();
            }
            //Set the pitchHi
            if (n.getAbsPitch() > absolutePitchHi) {
                absolutePitchHi = n.getAbsPitch();
            }
        }

        int setY = ((absolutePitchHi - absolutePitchLo) * 20) + 150;
        int setX = this.model.getTotNumBeats() * 20 + 300;


        this.displayPanel.setPreferredSize(new Dimension(setX, setY));
        this.add(displayPanel);

        JScrollPane scrolls = new JScrollPane(displayPanel);
        this.add(scrolls);


        displayPanel.repaint();
        this.setVisible(true);


    }

    @Override
    public void setModelToView(IMusicModel m) {
        IMusicModel defense = new MusicPieceModel(m);

        this.model = defense;
        this.displayPanel.model = defense;
        this.displayPanel.notes = defense.getNotes();


    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        //TODO implement after MouseHandlerImpl
    }

    @Override
    public void removeMouseListener(MouseListener mouseListener) {
        //TODO implement after MouseHandlerImpl
    }



}



