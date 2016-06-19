package cs3500.music.view;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;

/**
 * Created by brendanrejevich on 6/14/16.
 * This class represents the console view of the music composition, as in the first assignment.
 */
public class ConsoleViewFrame implements IMusicPieceView {

    private final JPanel displayPanel;
    IMusicModel model;


    public ConsoleViewFrame() {
        this.displayPanel = new ConcreteGuiViewPanel();
    }


    @Override
    public void initialize() {
        System.out.format(model.getVisualRepresentation());

    }

    @Override
    public void setModelToView(IMusicModel m) {
        IMusicModel defence = new MusicPieceModel(m);
        this.model = defence;
    }

    @Override
    public String toString() {
        return model.getVisualRepresentation();
    }
}

