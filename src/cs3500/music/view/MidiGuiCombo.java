package cs3500.music.view;

import java.awt.event.MouseListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * Created by Jameson on 6/20/16.
 */
public class MidiGuiCombo implements IGuiView {

    private IMusicPieceView midi;
    private IGuiView gui;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;


    public MidiGuiCombo(IMusicPieceView midi, IGuiView gui) {
        this.midi = midi;
        this.gui = gui;
    }


    @Override
    public void initialize() {
        //long pos = midi.getMicrosecondsPosition();
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

    }

    @Override
    public void removeMouseListener(MouseListener mouseListener) {

    }
}
