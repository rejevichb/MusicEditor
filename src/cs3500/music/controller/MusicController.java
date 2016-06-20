package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicPieceView;

/**
 * Music Controller that coordinates communication between the model and the views.
 */
public class MusicController implements ActionListener, KeyListener {

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


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class Init implements Runnable {
        @Override
        public void run() {
            view.initialize();
        }
    }

    //SOMEHOW READ KEYMAPS VIA FILE..????
    //figure out which file to read based on which view i'm in
    //      check somehow to see if we have the sub-view-interface IGuiView and if so perform
    //      certain operations on the panel, playing music, etc.
}