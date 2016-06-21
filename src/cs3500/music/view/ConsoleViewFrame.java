package cs3500.music.view;

import javax.swing.*;

import cs3500.music.model.IMusicModel;


/**
 * Created by brendanrejevich on 6/14/16.
 * This class represents the console view of the music composition, as in the first assignment.
 */
public class ConsoleViewFrame implements IMusicPieceView {

    private IMusicModel model;
    private final JPanel displayPanel;


    public ConsoleViewFrame() {
        this.displayPanel = new ConcreteGuiViewPanel();
    }


    @Override
    public void initialize() {

        System.out.format(this.model.getVisualRepresentation());

    }

    @Override
    public void modelDataToView(IMusicModel m) {
        this.model = m;
    }

    @Override
    public String toString() {
        return model.getVisualRepresentation();
    }
}

