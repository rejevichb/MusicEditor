package cs3500.music.view;

import javax.swing.*;

import cs3500.music.model.IMusicModel;

/**
 * Created by brendanrejevich on 6/14/16.
 * This class represents the console view of the music composition
 */
public class ConsoleViewFrame implements IMusicPieceView {

    private final JPanel displayPanel;
    IMusicModel model;


    public ConsoleViewFrame() {
        this.displayPanel = new ConcreteGuiViewPanel();
    }


    @Override
    public void initialize() {
        System.out.print(model.getVisualRepresentation());

    }

    @Override
    public void setModelToView(IMusicModel model) {
        this.model = model;
    }


}

