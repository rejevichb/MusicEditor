package cs3500.music.controller;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.util.SoundUtils;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicPieceView;
import cs3500.music.view.MidiGuiCombo;


/**
 * Music Controller that coordinates communication between the model and the views.
 */
public class MusicController implements ActionListener {

    private IMusicPieceView view;
    private IGuiView guiView;
    private IMusicModel model;
    private MouseListener mouseHandler;
    private MetaHandler metaHandler;
    private int time = 0;
    private boolean removeActive = false;


    public MusicController(IMusicModel model, IMusicPieceView view) {
        this.model = model;
        this.view = view;
        this.guiView = null;
        this.mouseHandler = null;
        this.metaHandler = null;
    }

    public MusicController(IMusicModel model, IGuiView view) {
        this.model = model;
        this.view = null;
        this.guiView = view;

        this.mouseHandler = new MouseHandler();
        this.metaHandler = new MetaHandler();

        guiView.addMouseListener(mouseHandler);
        guiView.addActionListener(this);


        if (guiView instanceof MidiGuiCombo) {
            MidiGuiCombo combo = (MidiGuiCombo) guiView;
            combo.addMetaEventListener(metaHandler);
        }
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

    /**
     * The MetaHandler listens to messages via the metaEventListener on our Sequencer, which is
     * populated with a MetaMessage stamp at every beat, allowing us to add the messages that are
     * related to resolution. Therefore, when the tempo is changed,the MetaMessages will fire at the
     * right time, as they are calculated as a multiple of the resolution of the sequencer.
     */
    private class MetaHandler implements MetaEventListener {
        @Override
        public void meta(MetaMessage meta) {
            int localTime = time;
            guiView.setTimeConstant(localTime);
            guiView.repaintFrame();
            time += 20;
        }
    }

    /**
     * A new instance of this class is created every time the user presses the (+) button. The
     * listener supports two options, cancel adding a new note, or confirm the new note and add it
     * to the  composition.
     */
    private class PopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "CancelNewNote":
                    guiView.hidePopup();  //this method actually clears the popup for reuse.
                    guiView.repaintFrame();
                    try {
                        SoundUtils.tone(1000, 100);
                        Thread.sleep(100);
                    } catch (Exception exep) {
                        System.exit(202);
                    }
                    guiView.resetFocus();
                    break;
                case "AcceptNewNoteData":
                    model.addNote(guiView.getNoteFromPopup());
                    modelToView();
                    guiView.repaintFrame();
                    guiView.resetFocus();
                    break;
            }
        }
    }


    private class AddRepeatPopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "AcceptRepeat":
                    int[] tempInfo = guiView.getRepeatInfo();
                    guiView.commenceRepeat(tempInfo[0], tempInfo[1], tempInfo[2]);
                    guiView.repaintFrame();
                    guiView.hidePopup();
                    try {
                        SoundUtils.tone(400, 900, 5.0);
                        Thread.sleep(100);
                    } catch (Exception exep) {
                        System.exit(202);
                    }
                    break;
                case "CancelRepeat":
                    guiView.hidePopup();
                    guiView.repaintFrame();
                    try {
                        SoundUtils.tone(200, 500);
                        Thread.sleep(100);
                    } catch (Exception exep) {
                        System.exit(202);
                    }
                    guiView.resetFocus();
                    break;
            }
        }
    }



    /**
     * Handles mouse events when a user is clicking to remove a note. Boolean flag removeActive
     * ensures that the user must press the (-) button every time he wants to remove a note -> only
     * one note can be removed at a time.
     */
    public class MouseHandler implements MouseListener {
        private int x;
        private int y;

        @Override
        public void mouseClicked(MouseEvent e) {
            this.x = e.getX();
            this.y = e.getY();
            guiView.resetFocus();
            if (removeActive) {
                model.removeNote(guiView.getRemovedNote());
                modelToView();
                guiView.repaintFrame();
                removeActive = false;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }




    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Repeat":
                guiView.createRepeatPopup(new AddRepeatPopupListener());
                guiView.repaintFrame();
                guiView.resetFocus();
                break;
            case "AddNote Button":
                guiView.createPopup(new PopupListener());
                guiView.repaintFrame();
                guiView.resetFocus();
                break;
            case "RemoveNote Button":
                removeActive = true;
                guiView.removeNote(mouseHandler);
                guiView.repaintFrame();
                guiView.resetFocus();
                break;
            case "playPause":
                guiView.modelDataToView(this.model);
                guiView.playPause();
                guiView.repaintFrame();
                guiView.resetFocus();
                break;
            case "restart":
                time = 0;
                guiView.restart();
                break;




        }
    }

}