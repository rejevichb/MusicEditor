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

    /**
     * Sends the model in the controller to the view in the controller
     */
    public void modelToView() {
        view.setModelToView(this.model);
    }


}