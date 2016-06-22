package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.util.SoundUtils;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicPieceView;

import static javax.sound.sampled.FloatControl.Type.SAMPLE_RATE;

/**
 * Music Controller that coordinates communication between the model and the views.
 */
public class MusicController implements ActionListener {

    IMusicPieceView view;
    IGuiView guiView;
    IMusicModel model;
    KeyListener keyHandler;
    MouseListener mouseHandler;


    public MusicController(IMusicModel model, IMusicPieceView view) {
        this.model = model;
        this.view = view;
        this.guiView = null;
        this.keyHandler = null;
        this.mouseHandler = null;

    }

    public MusicController(IMusicModel model, IGuiView view) {
        this.model = model;
        this.view = null;
        this.guiView = view;

        this.mouseHandler = new MouseHandler();
        this.keyHandler = new KeyboardHandler();

        view.addMouseListener(mouseHandler);
        view.addKeyListener(keyHandler);
        view.addActionListener(this);
    }


    /**
     * Sends the model in the controller to the view in the controller
     */
    public void modelToView() {
        if (view == null) {
            guiView.modelDataToView(new MusicPieceModel(this.model));
        } else if (guiView == null) {
            view.modelDataToView(new MusicPieceModel(this.model));
        }

    }


    class PopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "CancelNewNote":
                    guiView.hidePopup();
                    guiView.repaintFrame();
                    try {
                        SoundUtils.tone(1000, 100);
                        Thread.sleep(10000);
                    } catch (Exception exep) {
                        System.exit(202);
                    }

//                    System.exit(69);
                    break;
                case "AcceptNewNoteData":
                    //if (guiView.validPopupData()) {

                    model.addNote(guiView.getNoteFromPopop());
                    //}
                    //else {
                    //guiView.invalidatePopup();
                    //}
                    modelToView();
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            //read from the input textfield
            case "AddNote Button":
                guiView.createPopup(new PopupListener());
                guiView.repaintFrame();
                break;
            case "RemoveNote Button":
                System.out.println("Fuck her right in the pussy");
                System.exit(301);
                break;
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