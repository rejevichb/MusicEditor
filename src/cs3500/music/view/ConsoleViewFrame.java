package cs3500.music.view;


import java.util.List;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;

/**
 * Created by brendanrejevich on 6/14/16.
 * This class represents the console view of the music composition, as in the first assignment.
 */
public class ConsoleViewFrame implements IMusicPieceView {

    private IMusicModel model;
    private final JPanel displayPanel;
    private List<Note> notes;
    int tempo;


    public ConsoleViewFrame() {
        this.displayPanel = new ConcreteGuiViewPanel();
    }


    @Override
    public void initialize() {

        System.out.format(this.model.getVisualRepresentation());

    }

    @Override
    public void modelDataToView(IMusicModel m) {
        this.notes = m.getNotes();
        this.tempo = m.getTempo();

        IMusicModel temp = new MusicPieceModel();

        for (Note n : this.notes) {
            temp.addNote(n);
        }
        temp.setTempo(this.tempo);
    }

    @Override
    public String toString() {
        return model.getVisualRepresentation();
    }
}

