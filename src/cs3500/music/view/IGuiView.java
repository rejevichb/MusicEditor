package cs3500.music.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.Note;

/**
 * IGuiView, an sub-view-interface that adds functionality to GUI views in particular.
 */
public interface IGuiView extends IMusicPieceView {

    void addMouseListener(MouseListener mouseListener);

    void addActionListener(ActionListener actionListener);

    void addKeyListener(KeyListener keyListener);

    void removeMouseListener(MouseListener mouseListener);


    //TODO 3 new methods for gui

    void createPopup(ActionListener actionListener);

    void hidePopup();

//    boolean validPopupData();

    Note getNoteFromPopop();

    void setTimeConstant(long t);

    void repaintFrame();

}
