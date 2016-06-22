package cs3500.music.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.util.SoundUtils;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicPieceView;

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
                        Thread.sleep(100);
                    } catch (Exception exep) {
                        System.exit(202);
                    }

//                    System.exit(69);
                    break;
                case "AcceptNewNoteData":
                    //if (guiView.validPopupData()) {

                    model.addNote(guiView.getNoteFromPopop());
                    guiView.repaintFrame();

                    //}
                    //else {
                    //guiView.invalidatePopup();
                    //}
                    modelToView();
                    break;
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

                System.exit(301);
                break;
        }
    }
}