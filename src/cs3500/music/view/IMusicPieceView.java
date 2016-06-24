package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import cs3500.music.model.IMusicModel;

/**
 * Interface for the three views being implemented.  Console view, Gui view and Midi.
 */
public interface IMusicPieceView {


    /**
     * Initialize the view specified. For the gui view initialize creates a JFrame and a JPanel. In
     * the JPanel this method creates the specified notes on a grid of beats on the x-axis and the
     * notes and octaves on the y-axis of this piece.
     *
     * For the console view this method uses the musicDisplay method from the model so that
     */
    void initialize();


    /**
     * Sets this.model in whichever view to the model passed in.
     * @param model
     */
    void modelDataToView(IMusicModel model);


}
