package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.view.IMusicPieceView;
import cs3500.music.view.MidiGuiCombo;

/**
 * Music Controller that coordinates communication between the model and the views.
 */
public class MusicController implements ActionListener {

    IMusicPieceView view;
    IMusicModel model;
    KeyboardHandler keyHandler;
    MouseHandler mouseHandler;


    public MusicController(IMusicModel model, IMusicPieceView view) {
        this.model = model;
        this.view = view;

        if (view.getClass() == MidiGuiCombo.class) {
            this.keyHandler = new KeyboardHandler();
            this.mouseHandler = new MouseHandler();
        } else {
            this.keyHandler = null;
            this.mouseHandler = null;
        }

//        configureKeyBoardListener();
//        this.view.addActionListener(this);
    }

    /**
     * Sends the model in the controller to the view in the controller
     */
    public void modelToView() {
        view.modelDataToView(new MusicPieceModel(this.model));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
//            //read from the input textfield
//            case "Echo Button":
//                String text = view.getInputString();
//                //send text to the model
//                model.setString(text);
//
//                //clear input textfield
//                view.clearInputString();
//                //finally echo the string in view
//                text = model.getString();
//                view.setEchoOutput(text);
//
//                //set focus back to main frame so that keyboard events work
//                view.resetFocus();
//
//                break;
//            case "Exit Button":
//                System.exit(0);
//                break;
        }
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

    static class MapFactory {

        public static Map<Integer, Runnable> create(String s) {
            HashMap<Integer, Runnable> ret = new HashMap<>();
            switch (s) {
                case "released":
                    //populate hash map from file somehow????
                    break;
                case "pressed":
                    //populate hash map from file somehow????
                    break;
                case "typed":
                    //populate hash map from file somehow????
                    break;
            }
            return ret;
        }
    }

    public Map<Integer, Runnable> getMap(String s) {
        return MapFactory.create(s);
    }
}