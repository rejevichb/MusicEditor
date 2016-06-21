package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.*;

import cs3500.music.controller.MouseHandler;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;

// Possibly of interest for handling mouse events

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


    private ConcreteGuiViewPanel displayPanel;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;


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

        for (Note n : this.notes) {
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
        int setX = this.totalNumBeats * 20 + 300;


        this.displayPanel.setPreferredSize(new Dimension(setX, setY));
        this.add(displayPanel);

        JScrollPane scrolls = new JScrollPane(displayPanel);
        this.add(scrolls);


        displayPanel.repaint();
        this.setVisible(true);


    }

    @Override
    public void modelDataToView(IMusicModel m) {
        this.displayPanel.notes = m.getNotes();
        this.displayPanel.totalNumBeats = m.getTotNumBeats();
        this.displayPanel.tempo = m.getTempo();
        this.notes = m.getNotes();
        this.totalNumBeats = m.getTotNumBeats();
        this.tempo = m.getTempo();
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        //FIXME look at this... associate a name or number with each mouse handler??? how to know
        //This should be just handled in the controller
        // which one to remove?
        this.displayPanel.addMouseListener(new MouseHandler());
    }

    @Override
    public void removeMouseListener(MouseListener mouseListener) {
        //TODO implement after MouseHandlerImpl
        // this should be just handled in the controller
    }


    @Override
    public void addActionListener(ActionListener actionListener) {
        //echoButton.addActionListener(actionListener);
        //exitButton.addActionListener(actionListener);

    }



}



