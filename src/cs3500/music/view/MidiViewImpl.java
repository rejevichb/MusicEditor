package cs3500.music.view;

import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements IMusicPieceView {

    private IMusicModel model;
    private List<Note> notes;
    private final Synthesizer synth;
    private final Receiver receiver;


    public MidiViewImpl() {
        Synthesizer temp = null;
        Receiver tempA = null;
        try {
            temp = MidiSystem.getSynthesizer();
            tempA = temp.getReceiver();
            temp.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();

        }
        this.synth = temp;
        this.receiver = tempA;
    }


    /**
     * Relevant classes and methods from the javax.sound.midi library: <ul> <li>{@link
     * MidiSystem#getSynthesizer()}</li> <li>{@link Synthesizer} <ul> <li>{@link
     * Synthesizer#open()}</li> <li>{@link Synthesizer#getReceiver()}</li> <li>{@link
     * Synthesizer#getChannels()}</li> </ul> </li> <li>{@link Receiver} <ul> <li>{@link
     * Receiver#send(MidiMessage, long)}</li> <li>{@link Receiver#close()}</li> </ul> </li>
     * <li>{@link MidiMessage}</li> <li>{@link ShortMessage}</li> <li>{@link MidiChannel} <ul>
     * <li>{@link MidiChannel#getProgram()}</li> <li>{@link MidiChannel#programChange(int)}</li>
     * </ul> </li> </ul>
     *
     * @see <a href="https://en.wikipedia.org/wiki/General_MIDI"> https://en.wikipedia.org/wiki/General_MIDI
     * </a>
     */

    public void playNote() throws InvalidMidiDataException {

        Collections.sort(this.notes, Collections.reverseOrder());

        if (synth.getMicrosecondPosition() == -1 ) {
            throw new RuntimeException("MIDI device does not support timing");
        }

        for (Note n : this.notes) {
            MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(),
                    n.getAbsPitch(), n.getVolume());
            MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(),
                    n.getAbsPitch(), n.getVolume());
            this.receiver.send(start, synth.getMicrosecondPosition() + (n.getStartBeat()  * model.getTempo()));

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                //Cannot run the sleep if this is thrown and therefore no sound will be heard.
            }

            // need to implement swing timer
            // look at tutorial and make sure it works
            this.receiver.send(stop, synth.getMicrosecondPosition() + ((n.getStartBeat() + n.getDuration())  * model.getTempo()));
        }
    }


    public void initialize() {

        try {
            this.playNote();
        } catch (InvalidMidiDataException e) {
            //play note exception caught
        }
        this.receiver.close(); // Only call this once you're done playing *all* notes
    }

    public void setModelToView(IMusicModel model) {
        this.model = model;
        this.notes = model.getNotes();
    }
}
