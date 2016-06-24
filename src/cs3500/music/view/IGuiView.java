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

    void hidePopup();

    Note getNoteFromPopup();

    void setTimeConstant(long t);

    void repaintFrame();

    void removeNote(MouseListener mouseListener);

    Note getRemovedNote();

    boolean canRemoveNote(int x, int y);

}
