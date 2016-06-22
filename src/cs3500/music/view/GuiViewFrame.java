package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

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
    JFormattedTextField beatStart;
    JFormattedTextField beatDur;
    JComboBox octaveList;
    JComboBox pitchList;
    JComboBox instrumentList;


    /**
     * Creates new GuiView
     */
    public GuiViewFrame() {
        super();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Music Viewer v1.7        Authors: Jameson O'Connor, Brendan Rejevich");
        this.setSize(new Dimension(1350, 800));
        this.displayPanel = new ConcreteGuiViewPanel();
        this.setResizable(false);
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



    @Override
    public void createPopup(ActionListener actionListener) {
        addNotePanel = new JPanel();
        BoxLayout box = new BoxLayout(addNotePanel, BoxLayout.X_AXIS);
        addNotePanel.setLayout(box);

        FlowLayout flow = new FlowLayout();
        //addNotePanel.setLayout(flow);

        JLabel pitchL = new JLabel("Select a pitch");
        JLabel beatStartL = new JLabel("Input a start beat");
        JLabel durationL = new JLabel("Input a sustain in number of beats");
        JLabel octaveL = new JLabel("Enter Octave (from 0 - 10)");
        JLabel instrumentL = new JLabel("Enter Instrument Number (From 0 - 10");
        instrumentL.setHorizontalAlignment(JLabel.LEFT);
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


        Dimension textFieldSize = new Dimension(70, 40);

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);

        //formatter.setMinimum(0);
        //formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(false);


        beatStart = new JFormattedTextField(formatter);
        beatDur = new JFormattedTextField(formatter);
//        beatStart.setSize(textFieldSize);
//        beatDur.setSize(textFieldSize);
        beatStart.setPreferredSize(textFieldSize);
        beatDur.setPreferredSize(textFieldSize);



        Integer[] octaveNums = new Integer[11];
        Integer[] instrumentNums = new Integer[11];
        for (int i = 0; i <= 10; i++) {
            instrumentNums[i] = i;
            octaveNums[i] = i;
        }
        instrumentList = new JComboBox(instrumentNums);
        pitchList = new JComboBox(Pitch.values());
        octaveList = new JComboBox(octaveNums);
        pitchList.setSelectedIndex(0);
        //pitchList.addActionListener();
        addNotePanel.add(pitchL);
        addNotePanel.add(pitchList);
        addNotePanel.add(octaveL);
        addNotePanel.add(octaveList);
        addNotePanel.add(beatStartL);
        addNotePanel.add(beatStart);
        addNotePanel.add(durationL);
        addNotePanel.add(beatDur);
        addNotePanel.add(instrumentL);
        addNotePanel.add(instrumentList);
        addNotePanel.add(acceptButton);
        addNotePanel.add(cancelButton);

        addNotePanel.setSize(new Dimension(200, 800));
        addNotePanel.setVisible(true);
        this.add(addNotePanel, borderLayout.SOUTH);
        this.repaint();

    }


    @Override
    public void hidePopup() {
        this.borderLayout.removeLayoutComponent(addNotePanel);
        this.remove(addNotePanel);
        this.revalidate();
        //this.pack();
    }

//    @Override
//    public boolean validPopupData() {
//
//    }


    @Override
    public Note getNoteFromPopop() {
        int oct = octaveList.getSelectedIndex();
        int beatSt = Integer.valueOf(this.beatStart.getText());
        int beatDur = Integer.valueOf(this.beatDur.getText());
        int absPitch = ((oct * 12) + pitchList.getSelectedIndex());
        int instr = instrumentList.getSelectedIndex();

        Note n = new Note(absPitch, beatSt, beatDur, instr, 69);
        return n;
    }

    @Override
    public void setTimeConstant(long t) {
        this.displayPanel.timeVariable = t;
    }

    @Override
    public void repaintFrame() {
        this.displayPanel.repaint();
        //this.addNotePanel.setVisible(false);
        this.repaint();
        this.revalidate();

    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        this.addNoteButton.addActionListener(actionListener);
        this.removeNoteButton.addActionListener(actionListener);
    }



}



