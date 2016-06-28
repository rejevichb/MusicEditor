package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.text.NumberFormat;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;


/**
 * GUI Frame that handles drawing the GUI panel, including the AddNote popup bar,
 * repainting the frame, removing a note via pixel coordinates through the controller, and
 * adding mouse listeners to it's ConcreteGuiDisplayPanel.
 */
public class GuiViewFrame extends javax.swing.JFrame implements IGuiView {


    private ConcreteGuiViewPanel displayPanel;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;
    int absolutePitchHi;
    int absolutePitchLo;
    JScrollPane scrolls;

    //For the addNote popup
    JPanel addNotePanel;
    JButton addNoteButton = new JButton("+");
    JButton removeNoteButton = new JButton("-");
    JButton playPauseButton = new JButton("► / ||");
    JButton restartButton = new JButton("⟳");
    JButton repeatButton = new JButton("Repeat");
    BorderLayout borderLayout;
    JFormattedTextField addNoteBeatStart;
    JFormattedTextField addNoteBeatDur;
    JComboBox octaveList;
    JComboBox pitchList;
    JComboBox instrumentList;


    //For the repeat popup
    JPanel addRepeatPanel;
    JFormattedTextField addRepeatBeatStart;
    JFormattedTextField addRepeatBeatEnd;
    JFormattedTextField addRepeatNumLoops;
    int[] repeatInfo = new int[3];


    //For ending popup
    JPanel endingPanel;
    JComboBox endingBox;
    JButton newEnding = new JButton("VarEnd");
    JFormattedTextField addEndingBeat1;
    JFormattedTextField addEndingBeat2;
    JFormattedTextField addEndingBeat3;
    JFormattedTextField addEndingBeat4;
    int[] endingInfo = new int[4];




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
        this.setFocusable(true);
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
        addNoteButton.setActionCommand("AddNote Button");
        addNoteButton.setPreferredSize(new Dimension(40, 40));
        addNoteButton.setVerticalAlignment(SwingConstants.CENTER);
        buttonPanel.add(new JLabel("Add"));
        buttonPanel.add(addNoteButton);


        //RemoveNote Button
        removeNoteButton.setActionCommand("RemoveNote Button");
        removeNoteButton.setPreferredSize(new Dimension(40, 40));
        removeNoteButton.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(new JLabel("Remove"));
        buttonPanel.add(removeNoteButton);

        //PlayPause Button
        playPauseButton.setActionCommand("playPause");
        playPauseButton.setPreferredSize(new Dimension(60, 40));
        buttonPanel.add(new JLabel("Play/Pause"));
        buttonPanel.add(playPauseButton);

        //Restart button
        restartButton.setActionCommand("restart");
        restartButton.setPreferredSize(new Dimension(60, 40));
        buttonPanel.add(new JLabel("Reset"));
        buttonPanel.add(restartButton);


        //Repeat button
        repeatButton.setActionCommand("Repeat");
        repeatButton.setPreferredSize(new Dimension(50, 40));
        buttonPanel.add(new JLabel("Add Repeat"));
        buttonPanel.add(repeatButton);

        newEnding.setActionCommand("endings");
        newEnding.setPreferredSize(new Dimension(50, 40));
        buttonPanel.add(new JLabel("Varied endings"));
        buttonPanel.add(newEnding);

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
    public void createPopup(ActionListener actionListener) {
        addNotePanel = new JPanel();
        BoxLayout box = new BoxLayout(addNotePanel, BoxLayout.X_AXIS);
        addNotePanel.setLayout(box);

        JLabel pitchL = new JLabel("Select a pitch");
        JLabel beatStartL = new JLabel("Input a start beat");
        JLabel durationL = new JLabel("Input a sustain in number of beats");
        JLabel octaveL = new JLabel("Enter Octave (from 0 - 10)");
        JLabel instrumentL = new JLabel("Enter Instrument Number (From 0 - 10");

        JButton acceptButton = new JButton("Add Note");
        JButton cancelButton = new JButton("Cancel");
        acceptButton.setActionCommand("AcceptNewNoteData");
        cancelButton.setActionCommand("CancelNewNote");
        acceptButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);

        Dimension textFieldSize = new Dimension(70, 40);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(false);
        format.setGroupingUsed(false);

        addNoteBeatStart = new JFormattedTextField(formatter);
        addNoteBeatDur = new JFormattedTextField(formatter);
        addNoteBeatStart.setPreferredSize(textFieldSize);
        addNoteBeatDur.setPreferredSize(textFieldSize);


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

        addNotePanel.add(pitchL);
        addNotePanel.add(pitchList);
        addNotePanel.add(octaveL);
        addNotePanel.add(octaveList);
        addNotePanel.add(beatStartL);
        addNotePanel.add(addNoteBeatStart);
        addNotePanel.add(durationL);
        addNotePanel.add(addNoteBeatDur);
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
        if (addNotePanel != null) {
            this.borderLayout.removeLayoutComponent(addNotePanel);
            this.remove(addNotePanel);
            this.revalidate();
        } else if (addRepeatPanel != null) {
            this.borderLayout.removeLayoutComponent(addRepeatPanel);
            this.remove(addRepeatPanel);
            this.revalidate();
        } else if (endingPanel != null) {
            this.borderLayout.removeLayoutComponent(endingPanel);
            this.remove(endingPanel);
            this.revalidate();
        }
    }


    @Override
    public Note getNoteFromPopup() {
        int oct = octaveList.getSelectedIndex();
        int beatSt = Integer.valueOf(this.addNoteBeatStart.getText());
        int beatDur = Integer.valueOf(this.addNoteBeatDur.getText());
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
        this.displayPanel.removeNote(mouseListener);
    }

    @Override
    public Note getRemovedNote() {
        return displayPanel.getRemovedNote();
    }

    @Override
    public void playPause() {
        //empty implementation
    }

    @Override
    public void resetFocus() {
        this.setFocusable(true);
        this.requestFocus();
    }

    @Override
    public void restart() {
        setTimeConstant(0);
        this.repaint();
        displayPanel.repeatActive = false;
    }

    @Override
    public int[] getRepeatInfo() {
        repeatInfo[0] = Integer.valueOf(this.addRepeatBeatStart.getText());
        repeatInfo[1] = Integer.valueOf(this.addRepeatBeatEnd.getText());
        repeatInfo[2] = Integer.valueOf(this.addRepeatNumLoops.getText());
        int[] ret = this.repeatInfo.clone();
        return ret;
    }

    @Override
    public void commenceRepeat(int start, int end, int num) {
        displayPanel.repeatActive = true;
        displayPanel.repeatStart = start;
        displayPanel.repeatEnd = end;
    }

    @Override
    public void createRepeatPopup(ActionListener actionListener) {
        addRepeatPanel = new JPanel();
        BoxLayout box = new BoxLayout(addRepeatPanel, BoxLayout.X_AXIS);
        addRepeatPanel.setLayout(box);

        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(false);
        format.setGroupingUsed(false);

        addRepeatBeatStart = new JFormattedTextField(formatter);
        addRepeatBeatEnd = new JFormattedTextField(formatter);
        addRepeatNumLoops = new JFormattedTextField(formatter);


        JLabel startRepeatL = new JLabel("Repeat StartBeat");
        JLabel endRepeatL = new JLabel("Repeat EndBeat");
        JLabel loopCountRepeatL = new JLabel("Times to repeat");

        JButton acceptRepeat = new JButton("Add Repeat");
        JButton cancelRepeat = new JButton("Cancel");
        acceptRepeat.setActionCommand("AcceptRepeat");
        cancelRepeat.setActionCommand("CancelRepeat");
        acceptRepeat.addActionListener(actionListener);
        cancelRepeat.addActionListener(actionListener);

        addRepeatPanel.add(startRepeatL);
        addRepeatPanel.add(addRepeatBeatStart);
        addRepeatPanel.add(endRepeatL);
        addRepeatPanel.add(addRepeatBeatEnd);
        addRepeatPanel.add(loopCountRepeatL);
        addRepeatPanel.add(addRepeatNumLoops);
        addRepeatPanel.add(acceptRepeat);
        addRepeatPanel.add(cancelRepeat);


        addRepeatPanel.setSize(new Dimension(200, 800));
        this.add(addRepeatPanel, borderLayout.NORTH);
        addRepeatPanel.setVisible(true);

        //this.revalidate();
        this.repaint();
    }

    @Override
    public void createEndingPopup(ActionListener actionListener) {
        endingPanel = new JPanel();
        BoxLayout box = new BoxLayout(endingPanel, BoxLayout.Y_AXIS);
        endingPanel.setLayout(box);

        JLabel numEnd = new JLabel("Number of endings");
        JLabel startE1 = new JLabel("Ending 1:");
        JLabel startE2 = new JLabel("Ending 2:");
        JLabel startE3 = new JLabel("Ending 3:");
        JLabel startE4 = new JLabel("Ending 4:");


        Integer[] endingNums = new Integer[4];
        for (int i = 0; i < 4; i++) {
            endingNums[i] = i + 1;
        }
        endingBox = new JComboBox(endingNums);


        NumberFormat format = NumberFormat.getIntegerInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);

        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(false);
        format.setGroupingUsed(false);

        addEndingBeat1 = new JFormattedTextField(formatter);
        addEndingBeat2 = new JFormattedTextField(formatter);
        addEndingBeat3 = new JFormattedTextField(formatter);
        addEndingBeat4 = new JFormattedTextField(formatter);

        JButton accept = new JButton("Add Endings");
        JButton cancel = new JButton("Cancel");
        accept.setActionCommand("AcceptEnding");
        cancel.setActionCommand("Cancel");
        accept.addActionListener(actionListener);
        cancel.addActionListener(actionListener);

        endingPanel.add(numEnd);
        endingPanel.add(endingBox);
        endingPanel.add(startE1);
        endingPanel.add(addEndingBeat1);
        endingPanel.add(startE2);
        endingPanel.add(addEndingBeat2);
        endingPanel.add(startE3);
        endingPanel.add(addEndingBeat3);
        endingPanel.add(startE4);
        endingPanel.add(addEndingBeat4);
        endingPanel.add(accept);
        endingPanel.add(cancel);

        endingPanel.setSize(new Dimension(200, 800));
        this.add(endingPanel, borderLayout.EAST);
        endingPanel.setVisible(true);

        this.repaint();
    }

    @Override
    public int[] getEndingInfo() {
        endingInfo[0] = Integer.valueOf(addEndingBeat1.getText());
        endingInfo[1] = Integer.valueOf(addEndingBeat2.getText());
        endingInfo[2] = Integer.valueOf(addEndingBeat3.getText());
        endingInfo[3] = Integer.valueOf(addEndingBeat4.getText());
        return endingInfo.clone();
    }

    @Override
    public void commenceEndings(int start1, int start2, int start3, int start4) {
        displayPanel.endActive = true;
        displayPanel.starter1 = start1;
        displayPanel.starter2 = start2;
        displayPanel.starter3 = start3;
        displayPanel.starter4 = start4;

    }


    @Override
    public void addActionListener(ActionListener actionListener) {
        this.addNoteButton.addActionListener(actionListener);
        this.removeNoteButton.addActionListener(actionListener);
        this.playPauseButton.addActionListener(actionListener);
        this.restartButton.addActionListener(actionListener);
        this.repeatButton.addActionListener(actionListener);
        this.newEnding.addActionListener(actionListener);
    }


}



