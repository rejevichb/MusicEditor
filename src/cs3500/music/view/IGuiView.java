package cs3500.music.view;

import java.awt.event.MouseListener;

/**
 * IGuiView, an sub-view-interface that adds functionality to GUI views in particular.
 */
public interface IGuiView extends IMusicPieceView {

    void addMouseListener(MouseListener mouseListener);

    void removeMouseListener(MouseListener mouseListener);

}
