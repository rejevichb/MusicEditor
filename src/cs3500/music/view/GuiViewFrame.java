package cs3500.music.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Collections;

import javax.sound.midi.MetaEventListener;
import javax.swing.*;
import javax.swing.text.NumberFormatter;

import cs3500.music.controller.MouseHandler;
import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;


// Possibly of interest for handling mouse events

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


    private ConcreteGuiViewPanel displayPanel;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;
    int absolutePitchHi;
    int absolutePitchLo;
    JButton addNoteButton = new JButton("+");
    JButton removeNoteButton = new JButton("-");
    JPanel addNotePanel;
    BorderLayout borderLayout;
    JFormattedTextField beatStart;
    JFormattedTextField beatDur;
    JComboBox octaveList;
    JComboBox pitchList;
    JComboBox instrumentList;
    JScrollPane scrolls;
    Note remove;

    /**
     * Creates new GuiView
     */
    public GuiViewFrame() {
        super();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Music Viewer v1.7        Authors: Jameson O'Connor, Brendan Rejevich");
        this.setSize(new Dimension(1300, 600));
        this.displayPanel = new ConcreteGuiViewPanel();
        this.setResizable(true);
    }

    private int getAbsoluteLo() {
        int returnVal = 127;
        for (Note n : this.notes) {
            //Set the pitchLo
            if (n.getAbsPitch() < returnVal) {
                returnVal = n.getAbsPitch();
            }
        }
        return returnVal;
    }

    private int getAbsoluteHi() {
        int returnVal = 0;
        for (Note n : this.notes) {
            //Set the pitchHi
            if (n.getAbsPitch() > returnVal) {
                returnVal = n.getAbsPitch();
            }
        }
        return returnVal;
    }



    @Override
    public void initialize() {
        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.


        int setY = ((absolutePitchHi - absolutePitchLo) * 20) + 150;
        int setX = this.totalNumBeats * 20 + 300;


        this.displayPanel.setPreferredSize(new Dimension(setX, setY));

        borderLayout = new BorderLayout();
        this.setLayout(borderLayout);


        this.add(displayPanel, borderLayout.CENTER);

        scrolls = new JScrollPane(displayPanel);
        this.add(scrolls);
        JPanel buttonPanel = new JPanel();
        BoxLayout buttonlayout = new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS);
        buttonPanel.setLayout(buttonlayout);
        buttonPanel.setBackground(Color.getHSBColor(160, 50, 100));

        InputMap im = scrolls.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        im.put(KeyStroke.getKeyStroke("DOWN"), "positiveUnitIncrement");
        im.put(KeyStroke.getKeyStroke("UP"), "negativeUnitIncrement");


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

        scrolls.setAutoscrolls(true);


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
        this.displayPanel.addMouseListener(mouseListener);
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
//        pitchL.setHorizontalAlignment(JLabel.LEFT);
//        beatStartL.setHorizontalAlignment(JLabel.LEFT);
//        durationL.setHorizontalAlignment(JLabel.LEFT);
//        octaveL.setHorizontalAlignment(JLabel.LEFT);

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
        getToolkit().beep();
//        Toolkit.getScreenSize() ---> returns a dimension.
        this.repaint();

    }


    @Override
    public void hidePopup() {
        this.borderLayout.removeLayoutComponent(addNotePanel);
        this.remove(addNotePanel);
        this.revalidate();

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
        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.


        int setY = ((absolutePitchHi - absolutePitchLo) * 20) + 150;
        int setX = this.totalNumBeats * 20 + 300;

        this.displayPanel.setPreferredSize(new Dimension(setX, setY));

        displayPanel.revalidate();
        this.displayPanel.repaint();

        this.revalidate();
        this.repaint();
    }

    @Override
    public void removeNote(MouseListener mouseListener) {
//        MusicController.Clicka mHandler = (MusicController.Clicka) mouseListener;
//
//        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
//        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.
//
//
//        int xSub = mHandler.getX() % 20;
//        int xPoint = (mHandler.getX() - xSub) / 20;
//
//        int ySub = mHandler.getY() % 20;
//        int yPoint = (mHandler.getX() - ySub) / 20;
//        yPoint += absolutePitchLo;
//
//
//        for (Note n : notes) {
//            if (n.getStartBeat() == xPoint && n.getAbsPitch() == yPoint) {
//                remove = n;
//            }
//        }

        this.displayPanel.removeNote(mouseListener);

    }

    @Override
    public Note getRemovedNote() {
        return displayPanel.getRemovedNote();
    }

    @Override
    public boolean canRemoveNote(int x, int y) {

        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.


        int xSub = x % 20;
        int xPoint = (x - xSub) / 20;

        int ySub = y % 20;
        int yPoint = (y - ySub) / 20;
        yPoint += absolutePitchLo;

        boolean ret = false;
        for (Note n : notes) {
            if (n.getStartBeat() == xPoint && n.getAbsPitch() == yPoint) {
                ret = true;
            }
        }
        return ret;
    }


    @Override
    public void addActionListener(ActionListener actionListener) {
        this.addNoteButton.addActionListener(actionListener);
        this.removeNoteButton.addActionListener(actionListener);
    }


}



