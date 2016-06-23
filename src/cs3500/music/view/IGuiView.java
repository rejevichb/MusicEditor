package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.sound.midi.MetaEventListener;

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
     * @param actionListener
     */
    void addActionListener(ActionListener actionListener);


    /**
     * Adds a KeyListener to the IGuiView
     * @param keyListener
     */
    void addKeyListener(KeyListener keyListener);

    void removeMouseListener(MouseListener mouseListener);

    /**
     * Creates a popup on the right side of the JFrame, allowing the user to
     * enter in note information and add a note. A listener must also be added to the popup
     * specifically int order
     * @param actionListener
     */
    void createPopup(ActionListener actionListener);

    void hidePopup();

    Note getNoteFromPopop();

    void setTimeConstant(long t);

    void repaintFrame();

    void removeNote(MouseListener mouseListener);

    Note getRemovedNote();

    boolean canRemoveNote(int x, int y);

}
