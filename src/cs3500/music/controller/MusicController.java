package cs3500.music.controller;


import java.util.List;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicPieceView;

/**
 * Music Controller that coordinates communication between the model and the views.
 */
public class MusicController {

    IMusicPieceView view;
    IMusicModel model;

    public MusicController(IMusicModel model, IMusicPieceView view) {
        this.model = model;
        this.view = view;
    }

    //defensive copies - get notes from model.
    public List<Note> getNotes() {
        List<Note> n = model.getNotes();
        return n;
    }

    public void notesToView() {
        view.setModelToView(this.model);
    }

    public List<Note> getNotesAtBeat(int b) {
        return model.getNotesAtBeat(b);
    }

    public int getTotNumBeats() {
        return model.getTotNumBeats();
    }


}