package cs3500.music.view;

import java.util.Collections;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
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
 * A class for MIDI playback.
 */
public class MidiViewImpl implements IMidiView {

    private List<Note> notes;
    private int totalNumBeats;
    int tempo;

    protected Sequencer seqR;
    private Transmitter seqTrans;
    private final Synthesizer synth;
    private final Receiver receiver;
    private boolean isPlaying = false;

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
        //TODO this is how we should be drawing red line
        this.seqTrans = tempSeqTrans;
        this.synth = tempSynth;
        this.receiver = tempReceiver;
        if (this.seqTrans != null && this.receiver != null) {
            this.seqTrans.setReceiver(this.receiver);
        }
    }

    /**
     * Takes the notes from this model, creates a new Sequence containing one track of all notes in
     * the model, and sets the sequence to the Sequencer. The resolution of the sequence is
     * initially set to the model tempo, but will be overridden later by calling setTempoInMPQ and
     * passing it the microseconds per quarter note.
     */
    public void modelToSequencer() throws InvalidMidiDataException {
        Sequence ret = null;
        try {
            ret = new Sequence(Sequence.PPQ, this.tempo);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        ret.createTrack();
        Track track = ret.getTracks()[0];

        Collections.sort(this.notes, Collections.reverseOrder());

        //populates Sequence's track with delta-time stamped MIDI messages
        for (Note n : this.notes) {
            MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                    n.getAbsPitch(), n.getVolume());
            MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                    n.getAbsPitch(), n.getVolume());

            MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat()) * (ret.getResolution()));
            MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration())
                    * ret.getResolution()));

            track.add(start);
            track.add(stop);
        }

        for (int i = 0; i < totalNumBeats + 2; i += 1) {
            MetaMessage action = new MetaMessage();
            MidiEvent fire = new MidiEvent(action, i * ret.getResolution());
            track.add(fire);
        }

        //set the generated Sequence to the Sequencer
        try {
            this.seqR.setSequence(ret);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates and sets a Sequence to the Sequencer. Opens the Sequencer, sets it's tempo, and
     * starts playing.
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

        seqR.setTempoInMPQ(this.tempo);


        if (synth.getMicrosecondPosition() >= seqR.getMicrosecondLength()) {
            seqR.stop();
            this.receiver.close();
            this.seqTrans.close();
            this.synth.close();
            this.seqR.close();
        }
    }

    public void modelDataToView(IMusicModel m) {
        this.notes = m.getNotes();
        this.totalNumBeats = m.getTotNumBeats();
        this.tempo = m.getTempo();
    }

    @Override
    public void addMetaEventListener(MetaEventListener metaEventListener) {
        this.seqR.addMetaEventListener(metaEventListener);
    }

    @Override
    public void playPause() {
        seqR.setTempoInMPQ(this.tempo * 2);
        if (seqR.isRunning()) {
            seqR.setTempoInMPQ(this.tempo);
            seqR.stop();
        } else {
            seqR.stop();
            seqR.setTempoInMPQ(this.tempo);
            seqR.start();
        }
    }


    private void updateSeqr() {
        initialize();

    }
}
