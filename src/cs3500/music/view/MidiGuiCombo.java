package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
//TODO fixme

/**
 *
 */
public class MidiGuiCombo implements IGuiView, IMidiView {

    private IMidiView midi;
    private IGuiView gui;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;


    public MidiGuiCombo(IMidiView midi, IGuiView gui) {
        this.midi = midi;
        this.gui = gui;
    }


    @Override
    public void initialize() {
        gui.initialize();
        midi.initialize();
    }


    @Override
    public void modelDataToView(IMusicModel m) {
        midi.modelDataToView(m);
        gui.modelDataToView(m);
        this.notes = m.getNotes();
        this.totalNumBeats = m.getTotNumBeats();
        this.tempo = m.getTempo();
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {
        gui.addMouseListener(mouseListener);
    }

    @Override
    public void addActionListener(ActionListener actionListener) {
        gui.addActionListener(actionListener);
    }

    @Override
    public void addKeyListener(KeyListener keyListener) {

    }

    @Override
    public void createPopup(ActionListener actionListener) {
        gui.createPopup(actionListener);
    }

    @Override
    public void hidePopup() {
        gui.hidePopup();
    }

    @Override
    public Note getNoteFromPopup() {
        return gui.getNoteFromPopup();
    }

    @Override
    public void setTimeConstant(long t) {
        this.gui.setTimeConstant(t);
    }

    @Override
    public void repaintFrame() {
        gui.repaintFrame();
    }

    @Override
    public void removeNote(MouseListener mouseListener) {
        this.gui.removeNote(mouseListener);
    }

    @Override
    public Note getRemovedNote() {
        return gui.getRemovedNote();
    }


    @Override
    public void playPause() {
        midi.playPause();
    }

    @Override
    public void resetFocus() {
        gui.resetFocus();
    }

    @Override
    public void restart() {
        midi.restart();
        gui.restart();
    }

    @Override
    public void setSequencerRepeat(int start, int end, int numberRepeats) {
        midi.setSequencerRepeat(start, end, numberRepeats);
    }

    @Override
    public int[] getRepeatInfo() {
        return gui.getRepeatInfo();
    }

    @Override
    public void commenceRepeat(int start, int end, int num) {
        midi.setSequencerRepeat(start, end, num);
        gui.commenceRepeat(start, end, num);
    }

    @Override
    public void createRepeatPopup(ActionListener actionListener) {
        gui.createRepeatPopup(actionListener);
    }

    @Override
    public void createEndingPopup(ActionListener actionListener) {
        gui.createEndingPopup(actionListener);
    }

    @Override
    public void addMetaEventListener(MetaEventListener metaEventListener) {
        midi.addMetaEventListener(metaEventListener);
    }
}
