package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * IGuiView, an sub-view-interface that adds functionality to GUI views in particular.
 */
public interface IGuiView extends IMusicPieceView {

    /**
     * Adds a MouseListener to the IGuiView.
     */
    void addMouseListener(MouseListener mouseListener);

    /**
     * Adds an ActionListener to the IGuiVIew
     */
    void addActionListener(ActionListener actionListener);


    /**
     * Adds a KeyListener to the IGuiView
     */
    void addKeyListener(KeyListener keyListener);


    /**
     * Creates a popup on the right side of the JFrame, allowing the user to enter in note
     * information and add a note. A listener must also be added to the popup specifically int
     * order
     */
    void createPopup(ActionListener actionListener);

    /**
     * Hides the popup from the GuiFrame after the user accepts or cancels the note.
     */
    void hidePopup();

    /**
     * Returns the note that was inputted in the popup to the controller, so it can be added to the
     * model.
     *
     * @return Note
     */
    Note getNoteFromPopup();

    /**
     * Sets the time in the ConcreteGuiViewPanel for drawing of the red line.
     * @param t
     */
    void setTimeConstant(long t);

    /**
     * Repaints the GUIFrame
     */
    void repaintFrame();

    /**
     * Removes a note based on mouse events on the ConcreteGuiViewPanel
     * @param mouseListener
     */
    void removeNote(MouseListener mouseListener);

    /**
     * Returns the note that was removed.
     * @return
     */
    Note getRemovedNote();

    /**
     * Plays or pauses the red line and the audio based on the sequencers current state.
     */
    void playPause();

    /**
     * Resets the focus to the display panel after any mouse or action event
     */
    void resetFocus();

    /**
     * Restarts the music piece, both the red line and the MIDI.
     */
    void restart();

    int[] getRepeatInfo();

    void commenceRepeat(int x, int y, int num);

    void createRepeatPopup(ActionListener actionListener);

    void createEndingPopup(ActionListener actionListener);

    int[] getEndingInfo();

    void commenceEndings(int star1, int start2, int start3, int start4);

}
