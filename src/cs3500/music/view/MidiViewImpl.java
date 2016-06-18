package cs3500.music.view;

import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements IMusicPieceView {

    private IMusicModel model;
    private List<Note> notes;

    private Sequencer seq;
    private Transmitter seqTrans;
    private final Synthesizer synth;
    private final Receiver receiver;


    public MidiViewImpl() {
        Sequencer tempSeq = null;
        Transmitter tempSeqTrans = null;
        Synthesizer tempSynth = null;
        Receiver tempReceiver = null;
        try {
            tempSeq = MidiSystem.getSequencer();
            tempSeqTrans = tempSeq.getTransmitter();
            tempSynth = MidiSystem.getSynthesizer();
            tempReceiver = tempSynth.getReceiver();
            tempSynth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        this.seq = tempSeq;
        this.seqTrans = tempSeqTrans;
        this.synth = tempSynth;
        this.receiver = tempReceiver;
        if (this.seqTrans != null && this.receiver != null) {
            this.seqTrans.setReceiver(this.receiver);
        }
    }

    /*
    To play music, a device generally receives MidiMessages through a Receiver,
    which in turn has usually received them from a Transmitter that belongs to a Sequencer.
    The device that owns this Receiver is a Synthesizer, which will generate audio directly.
     */

    public Sequence modelToSequence() throws InvalidMidiDataException {
        Sequence ret = null;
        try {    //FIXME 20 = resolution specified in ticks per beat
            ret = new Sequence(Sequence.PPQ, 20, 1);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
        ret.createTrack();
        Track track = ret.getTracks()[0];

        for (Note n : this.notes) {
            MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(), n.getAbsPitch(), n.getVolume());
            MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(), n.getAbsPitch(), n.getVolume());
            MidiEvent start = new MidiEvent(noteOn, 200);  //FIXME calculate timeStamp
            MidiEvent stop = new MidiEvent(noteOff, 200);  //FIXME calculate timeStamp
            track.add(start);
            track.add(stop);
//            this.receiver.send(start, synth.getMicrosecondPosition() + (n.getStartBeat()  * model.getTempo()));
//            this.receiver.send(stop, synth.getMicrosecondPosition() + ((n.getStartBeat() + n.getDuration())  * model.getTempo()));
        }
        return ret;
    }

    /*
    getMicrosecondsPosition()

    ticksPerSecond = resolution * (currentTempoInBeatsPerMinute / 60.0);
    tickSize = 1.0 / ticksPerSecond;
     */



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
    //FIXME USE SEQUENCER instead of Thread.sleep for the timing.
    //TODO also look at ShortMessage (start and stop) parameter value calculations from model
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
            this.receiver.send(stop, synth.getMicrosecondPosition() + ((n.getStartBeat() + n.getDuration()) * model.getTempo()));
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
