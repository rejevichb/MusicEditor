package cs3500.music.controller;

import org.junit.Test;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.view.GuiViewFrame;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMidiView;
import cs3500.music.view.MidiGuiCombo;
import cs3500.music.view.MidiViewImpl;


/**
 * Created by brendanrejevich on 6/25/16.
 */
public class MusicControllerTest {


    IMidiView midi = new MidiViewImpl();
    IGuiView gui = new GuiViewFrame();
    IGuiView guiView = new MidiGuiCombo(midi, gui);


    @Test
    public void testModelToView() {

        CompositionBuilder<IMusicModel> builder = new CompositionBuilderImpl();
        //adding 4 notes
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i, i + 5, 1, i + 60, 64);
        }
        //adding 4 notes
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i + 10, i + 15, 1, i + 60, 64);
        }
        builder.setTempo(20000);
        IMusicModel model = builder.build();

        MusicController testController = new MusicController(model, guiView);

        testController.modelToView();

        //FIXME test from here.
    }

}