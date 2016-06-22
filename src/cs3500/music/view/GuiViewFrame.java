package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

//import cs3500.music.controller.MouseHandler;

// Possibly of interest for handling mouse events

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


    private ConcreteGuiViewPanel displayPanel;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;
    JButton addNoteButton = new JButton("+");
    JButton removeNoteButton = new JButton("-");
    JPanel addNotePanel;
    BorderLayout borderLayout;


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

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);


        this.add(displayPanel, borderLayout.CENTER);

        JScrollPane scrolls = new JScrollPane(displayPanel);
        this.add(scrolls);
        JPanel buttonPanel = new JPanel();
        BoxLayout buttonlayout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
        buttonPanel.setLayout(buttonlayout);
        buttonPanel.setBackground(Color.getHSBColor(160, 50, 100));


        //AddNote Button
        //addNoteButton = new JButton("+");
        addNoteButton.setActionCommand("AddNote Button");
        addNoteButton.setPreferredSize(new Dimension(40, 40));
        addNoteButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonPanel.add(addNoteButton);

        //RemoveNote Button
        //removeNoteButton = new JButton("-");
        removeNoteButton.setActionCommand("RemoveNote Button");
        removeNoteButton.setPreferredSize(new Dimension(40, 40));
        removeNoteButton.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(removeNoteButton);

        this.add(buttonPanel, borderLayout.WEST);


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
        // this.displayPanel.addMouseListener(new MouseHandler());
    }

    @Override
    public void removeMouseListener(MouseListener mouseListener) {
        //TODO implement after MouseHandlerImpl
        // this should be just handled in the controller
    }


    //TODO new method

    @Override
    public void createPopup(ActionListener actionListener) {
        addNotePanel = new JPanel();
        addNotePanel.setLayout(new BoxLayout(addNotePanel, BoxLayout.Y_AXIS));


        JLabel pitchL = new JLabel("Select a pitch");
        JLabel beatStartL = new JLabel("Input a start beat");
        JLabel durationL = new JLabel("Input a sustain in number of beats         ");
        JLabel octaveL = new JLabel("Enter Octave (from 0 - 10)");
        pitchL.setHorizontalAlignment(JLabel.LEFT);
        beatStartL.setHorizontalAlignment(JLabel.LEFT);
        durationL.setHorizontalAlignment(JLabel.LEFT);
        octaveL.setHorizontalAlignment(JLabel.LEFT);

        JButton acceptButton = new JButton("Add Note");
        JButton cancelButton = new JButton("Cancel");
        acceptButton.setActionCommand("AcceptNewNoteData");
        cancelButton.setActionCommand("CancelNewNote");
        acceptButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);


        Dimension textFieldSize = new Dimension(50, 50);
        JTextField beatStart = new JTextField(1);
        JTextField beatDur = new JTextField(1);
        JTextField octave = new JTextField(1);
        beatStart.setSize(textFieldSize);
        beatDur.setSize(textFieldSize);
        octave.setSize(textFieldSize);


        JComboBox pitchList = new JComboBox(Pitch.values());
        pitchList.setSelectedIndex(0);
        //pitchList.addActionListener();
        addNotePanel.add(pitchL);
        addNotePanel.add(pitchList);
        addNotePanel.add(beatStartL);
        addNotePanel.add(beatStart);
        addNotePanel.add(durationL);
        addNotePanel.add(beatDur);
        addNotePanel.add(octaveL);
        addNotePanel.add(octave);
        addNotePanel.add(acceptButton);
        addNotePanel.add(cancelButton);

        addNotePanel.setSize(new Dimension(200, 800));
        addNotePanel.setVisible(true);
        this.add(addNotePanel, borderLayout.EAST);
        this.repaint();

    }

    //TODO new method

    @Override
    public void hidePopup() {
        this.borderLayout.removeLayoutComponent(addNotePanel);
        this.remove(addNotePanel);
        //this.pack();
    }

    @Override
    public void setTimeConstant(long t) {
        this.displayPanel.timeVariable = t;
    }

    @Override
    public void repaintFrame() {
//        this.displayPanel.repaint();
//        this.addNotePanel.repaint();
        this.repaint();

    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        this.addNoteButton.addActionListener(actionListener);
        this.removeNoteButton.addActionListener(actionListener);
    }



}



