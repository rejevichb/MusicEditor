package cs3500.music.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * Created by Jameson on 6/20/16.
 */
public class MidiGuiCombo implements IGuiView {

    private IMidiView midi;
    private IGuiView gui;
    private java.util.List<Note> notes;
    private int totalNumBeats;
    int tempo;
    Timer aniTimer;


    public MidiGuiCombo(IMidiView midi, IGuiView gui) {
        this.midi = midi;
        this.gui = gui;
    }


    @Override
    public void initialize() {
        gui.initialize();


        midi.initialize();


        aniTimer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                gui.setTimeConstant(midi.getTime());
                gui.repaintFrame();

            }
        });
        aniTimer.start();


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

    @Override
    public void setTimeConstant(long t) {
        this.gui.setTimeConstant(t);
    }

    @Override
    public void repaintFrame() {
        gui.repaintFrame();
    }
}
