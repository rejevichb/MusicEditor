package cs3500.music.view;

import java.awt.event.MouseListener;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;

/**
 * Created by Jameson on 6/20/16.
 */
public class MidiGuiCombo implements IGuiView {

    private IMusicPieceView midi;
    private IGuiView gui;
    IMusicModel m;

    public MidiGuiCombo(IMusicPieceView midi, IGuiView gui) {
        this.midi = midi;
        this.gui = gui;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void setModelToView(IMusicModel model) {
        IMusicModel copy = new MusicPieceModel(model);
        m = copy;
        midi.setModelToView(m);
        gui.setModelToView(m);
    }

    @Override
    public void addMouseListener(MouseListener mouseListener) {

    }

    @Override
    public void removeMouseListener(MouseListener mouseListener) {

    }
}
