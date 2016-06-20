package cs3500.music.view;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.util.MusicReader;

import static org.junit.Assert.*;

/**
 * Created by brendanrejevich on 6/19/16.
 */
public class MidiViewImplTest {

    /**
     * Mock of our class MidiViewImpl.  So that we can create a log of notes and visually test which
     * notes are being passed to our MiDi.
     */
    class MockMidiView extends MidiViewImpl {

        protected MockMidiView() {
            super();
        }

        /**
         * creates a log to see which notes are flowing in and out of our synth.
         *
         * @return StringBuilder that represents a log of data
         */
        public StringBuilder logModelToSequence() {
            super.initialize();

            StringBuilder builder = new StringBuilder();

            Track t = seqR.getSequence().getTracks()[0];

            for (int i = 0; i < t.size(); i++) {
                MidiEvent event = t.get(i);
                long timeStamp = event.getTick();
                if (event.getMessage() instanceof ShortMessage) {
                    ShortMessage message = (ShortMessage) event.getMessage();
                    int command = message.getCommand();
                    int instrument = message.getChannel();
                    int pitch = message.getData1();
                    int vol = message.getData2();
                    builder.append("Message #").append(i + 1).append("\n");
                    builder.append("TimeStamp: ").append(timeStamp).append("\n");
                    builder.append("Command: ");
                    builder.append(command).append("\n");
                    builder.append("Instrument: ").append(instrument).append("\n");
                    builder.append("Pitch: ").append(pitch).append("\n");
                    builder.append("Vol: ").append(vol).append("\n\n");
                }
            }
            return builder;
        }
    }

    @Test
    public void midiViewImplTest() {

        MockMidiView mock = new MockMidiView();
        IMusicModel test = new MusicPieceModel();

        CompositionBuilder<IMusicModel> builder = new CompositionBuilderImpl();
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i, i + 5, 1, i + 60, 64);
        }
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i + 10, i + 15, 1, i + 60, 64);
        }
        builder.setTempo(20000);

        IMusicModel out = builder.build();
        mock.setModelToView(out);

        StringBuilder log = mock.logModelToSequence();
        assertEquals("Message #1\n" +
                "TimeStamp: 0\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 60\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #2\n" +
                "TimeStamp: 60000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 63\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #3\n" +
                "TimeStamp: 100000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 60\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #4\n" +
                "TimeStamp: 120000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 66\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #5\n" +
                "TimeStamp: 160000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 63\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #6\n" +
                "TimeStamp: 180000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 69\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #7\n" +
                "TimeStamp: 200000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 60\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #8\n" +
                "TimeStamp: 220000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 66\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #9\n" +
                "TimeStamp: 260000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 63\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #10\n" +
                "TimeStamp: 280000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 69\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #11\n" +
                "TimeStamp: 300000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 60\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #12\n" +
                "TimeStamp: 320000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 66\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #13\n" +
                "TimeStamp: 360000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 63\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #14\n" +
                "TimeStamp: 380000\n" +
                "Command: 144\n" +
                "Instrument: 1\n" +
                "Pitch: 69\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #15\n" +
                "TimeStamp: 420000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 66\n" +
                "Vol: 64\n" +
                "\n" +
                "Message #16\n" +
                "TimeStamp: 480000\n" +
                "Command: 128\n" +
                "Instrument: 1\n" +
                "Pitch: 69\n" +
                "Vol: 64\n" +
                "\n", log.toString());
    }


    @Test
    public void maryTest() {
        CompositionBuilder builder = new CompositionBuilderImpl();
        Readable f = null;
        try {
            f = new FileReader(new File("/Users/brendanrejevich/Desktop/hw/mary-little-lamb.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        IMusicModel model = MusicReader.parseFile(f, builder);
        MockMidiView view = new MockMidiView(); //I changed this for user input

        MusicController controller = new MusicController(model, view);

        controller.modelToView();
        System.out.println(view.logModelToSequence());
    }

}