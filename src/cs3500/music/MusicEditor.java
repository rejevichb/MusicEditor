package cs3500.music;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.util.MusicReader;
import cs3500.music.view.FactoryView;
import cs3500.music.view.IGuiView;
import cs3500.music.view.IMusicPieceView;


public class MusicEditor {
    public static void main(String[] args) throws IOException, InvalidMidiDataException {

        CompositionBuilder builder = new CompositionBuilderImpl();
        Readable f = new FileReader(new File(args[0]));

        IMusicModel model = MusicReader.parseFile(f, builder);

        if (args[1].equalsIgnoreCase("combo") || args[1].equalsIgnoreCase("visual")) {
            IGuiView view = (IGuiView) FactoryView.create(args[1]);
            MusicController controller = new MusicController(model, view);
            controller.modelToView();
            view.initialize();
        } else {
            IMusicPieceView view = FactoryView.create(args[1]); //I changed this for user input
            MusicController controller = new MusicController(model, view);
            controller.modelToView();
            view.initialize();
        }
    }
}
