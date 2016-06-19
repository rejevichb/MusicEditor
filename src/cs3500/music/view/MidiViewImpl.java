package cs3500.music.view;

import java.util.Collections;
import java.util.List;

import javax.sound.midi.*;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;

/**
 * A class for MIDI playback.
 */
public class MidiViewImpl implements IMusicPieceView {

    private IMusicModel model;
    private List<Note> notes;

    private Sequencer seqR;
    private Transmitter seqTrans;
    private final Synthesizer synth;
    private final Receiver receiver;


    public MidiViewImpl() {
        Sequencer tempSeqR = null;
        Transmitter tempSeqTrans = null;
        Synthesizer tempSynth = null;
        Receiver tempReceiver = null;
        try {
            tempSeqR = MidiSystem.getSequencer();
            tempSeqTrans = tempSeqR.getTransmitter();
            tempSynth = MidiSystem.getSynthesizer();
            tempReceiver = tempSynth.getReceiver();
            tempSynth.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
        this.seqR = tempSeqR;
        this.seqTrans = tempSeqTrans;
        this.synth = tempSynth;
        this.receiver = tempReceiver;
        if (this.seqTrans != null && this.receiver != null) {
            this.seqTrans.setReceiver(this.receiver);
        }
    }

    /**
     * Takes the notes from this model, creates a new Sequence containing one track of all notes
     * in the model, and sets the sequence to the Sequencer. The resolution of the sequence is initially
     * set to the model tempo, but will be overridden later by calling setTempoInMPQ and passing it
     * the microseconds per quarter note.
     * @throws InvalidMidiDataException
     */
    public void modelToSequencer() throws InvalidMidiDataException {
        Sequence ret = null;
        try {
            ret = new Sequence(Sequence.PPQ, model.getTempo());
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        ret.createTrack();
        Track track = ret.getTracks()[0];


        Collections.sort(this.notes, Collections.reverseOrder());

        //populates Sequence's track with delta-time stamped MIDI messages
        for (Note n : this.notes) {
            MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument(), n.getAbsPitch(), n.getVolume());
            MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument(), n.getAbsPitch(), n.getVolume());
            MidiEvent start = new MidiEvent(noteOn,   (n.getStartBeat()) * (ret.getResolution()));
            MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration()) * ret.getResolution()));
            track.add(start);
            track.add(stop);
        }

        //set the generated Sequence to the Sequencer
        try {
            this.seqR.setSequence(ret);
        }
        catch (InvalidMidiDataException e){
            e.printStackTrace();
        }
    }

    /*
    getMicrosecondLength()
    getMicrosecondsPosition()
    ticksPerSecond = resolution * (currentTempoInBeatsPerMinute / 60.0);
    tickSize = 1.0 / ticksPerSecond;
     */


    /**
     * Creates and sets a Sequence to the Sequencer. Opens the Sequencer, sets it's tempo,
     * and starts playing.
     */
    public void initialize() {

        try {
            modelToSequencer();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        try {
            seqR.open();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }

        seqR.setTempoInMPQ(model.getTempo());

        seqR.start();

        if (! seqR.isRunning()) {
            seqR.stop();
            this.receiver.close(); // Only call this once you're done playing *all* notes
        }
    }

    public void setModelToView(IMusicModel m) {
        IMusicModel defence = new MusicPieceModel(m);
        this.model = defence;
        this.notes = defence.getNotes();
    }
}
