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

    private Sequence sequence;
    protected Sequencer seqR;
    private Transmitter seqTrans;
    private final Synthesizer synth;
    private final Receiver receiver;

    private int startBeatR;
    private int endBeatR;
    private int numberRepeat;

    private int startBeat1;
    private int startBeat2;
    private int startBeat3;
    private int startBeat4;
    private boolean flag;



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

        this.startBeatR = 0;
        this.endBeatR = 0;
        this.numberRepeat = 0;
        flag = true;

        this.startBeat1 = 0;
        this.startBeat2 = 0;
        this.startBeat3 = 0;
        this.startBeat4 = 0;

    }

    /**
     * Takes the notes from this model, creates a new Sequence containing one track of all notes in
     * the model, and sets the sequence to the Sequencer. The resolution of the sequence is
     * initially set to the model tempo, but will be overridden later by calling setTempoInMPQ and
     * passing it the microseconds per quarter note.
     */
    public void modelToSequencer() throws InvalidMidiDataException {
        Sequence ret = null;
        int loopCount = numberRepeat;
        try {
            ret = new Sequence(Sequence.PPQ, this.tempo);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

        ret.createTrack();
        Track track = ret.getTracks()[0];

        Collections.sort(this.notes, Collections.reverseOrder());

        //populates Sequence's track with delta-time stamped MIDI messages
        if (flag) {

            for (Note n : this.notes) {
                if (n.getStartBeat() < endBeatR || endBeatR == 0) {
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
                int offSet = (endBeatR - startBeatR);
                boolean emptyLoopCout = false;

                for (int j = 1; j <= loopCount; j++) {
                    if (n.getStartBeat() > startBeatR && !(n.getStartBeat() > endBeatR)
                            && loopCount >= 1) {
                        MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                                n.getAbsPitch(), n.getVolume());
                        MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                                n.getAbsPitch(), n.getVolume());

                        MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() + offSet) * (ret.getResolution()));
                        MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat()
                                + n.getDuration()) + offSet)
                                * ret.getResolution());
                        track.add(start);
                        track.add(stop);
                        offSet = (j + 1) * (endBeatR - startBeatR);
                    }
                    emptyLoopCout = true;

                }
                if (n.getStartBeat() > endBeatR && emptyLoopCout && endBeatR != 0) {
                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                            n.getAbsPitch(), n.getVolume());
                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                            n.getAbsPitch(), n.getVolume());
                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() + (offSet * loopCount)) * (ret.getResolution()));
                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat()
                            + n.getDuration()) + (offSet * loopCount))
                            * ret.getResolution());
                    track.add(start);
                    track.add(stop);

                }
            }


            if (endBeatR == 0) {
                for (int i = 0; i < totalNumBeats + 2; i += 1) {
                    MetaMessage action = new MetaMessage();
                    MidiEvent fire = new MidiEvent(action, i * ret.getResolution());
                    track.add(fire);
                }
            } else if (endBeatR != 0) {
                for (int j = 0; j < (totalNumBeats + 2 + (loopCount * (endBeatR - startBeatR))); j += 1) {
                    MetaMessage action = new MetaMessage();
                    MidiEvent fire = new MidiEvent(action, j * ret.getResolution());
                    track.add(fire);
                }
            }
        } else {
            for (Note n : this.notes) {
                if (n.getStartBeat() < startBeat2) {
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

                if (n.getStartBeat() < startBeat1) {
                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                            n.getAbsPitch(), n.getVolume());
                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                            n.getAbsPitch(), n.getVolume());

                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() + startBeat2) * (ret.getResolution()));
                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration() + startBeat2)
                            * ret.getResolution()));

                    track.add(start);
                    track.add(stop);
                }
            }


//                if (n.getStartBeat() > startBeat2 && n.getStartBeat() < startBeat3 && startBeat3 != 0) {
//                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//
//                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() + startBeat2 + startBeat1) * (ret.getResolution()));
//                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration() + startBeat2 + startBeat1)
//                            * ret.getResolution()));
//
//                    track.add(start);
//                    track.add(stop);
//
//                }
//                if (n.getStartBeat() < startBeat1) {
//                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//
//                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() +  startBeat1 + startBeat3) * (ret.getResolution()));
//                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration() + startBeat1 + startBeat3)
//                            * ret.getResolution()));
//
//                    track.add(start);
//                    track.add(stop);
//                }
//
//                if (n.getStartBeat() > startBeat3 && n.getStartBeat() < startBeat4 && startBeat4 != 0) {
//                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//
//                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() +  startBeat1 + startBeat3 + (startBeat4 -startBeat3) ) * (ret.getResolution()));
//                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration() + startBeat1 + startBeat3 + (startBeat4 - startBeat3))
//                            * ret.getResolution()));
//
//                    track.add(start);
//                    track.add(stop);
//                }
//                if (n.getStartBeat() < startBeat1) {
//                    MidiMessage noteOn = new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//                    MidiMessage noteOff = new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
//                            n.getAbsPitch(), n.getVolume());
//
//                    MidiEvent start = new MidiEvent(noteOn, (n.getStartBeat() +  startBeat1 + startBeat3) * (ret.getResolution()));
//                    MidiEvent stop = new MidiEvent(noteOff, ((n.getStartBeat() + n.getDuration() + startBeat1 + startBeat3)
//                            * ret.getResolution()));
//
//                    track.add(start);
//                    track.add(stop);
//                }

        }

        if (startBeat1 != 0) {
            for (int j = 0; j <= totalNumBeats + startBeat2; j++) {
                MetaMessage action = new MetaMessage();
                MidiEvent fire = new MidiEvent(action, j * ret.getResolution());
                track.add(fire);
            }
        }


        MetaMessage finalMessage = new MetaMessage(0x2F, new byte[1], 1);
        MidiEvent finalEvent = new MidiEvent(finalMessage, (totalNumBeats + 2) * ret.getResolution());
        track.add(finalEvent);

        this.sequence = ret;

        //set the generated Sequence to the Sequencer
        try {
            this.seqR.setSequence(sequence);
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

        seqR.setTempoInMPQ(this.tempo * 2);

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

    @Override
    public void restart() {
        seqR.stop();
        seqR.close();
        this.initialize();
        this.startBeatR = 0;
        this.endBeatR = 0;
        this.numberRepeat = 0;
        this.startBeat1 = 0;
        this.startBeat2 = 0;
        this.startBeat3 = 0;
        this.startBeat4 = 0;

    }

    @Override
    public void setSequencerRepeat(int start, int end, int numberRepeats) {
        this.startBeatR = start;
        this.endBeatR = end;
        this.numberRepeat = numberRepeats;
        this.initialize();


    }

    @Override
    public void setSequencerEnding(int start1, int start2, int start3, int start4) {
        this.startBeat1 = start1;
        this.startBeat2 = start2;
        this.startBeat3 = start3;
        this.startBeat4 = start4;
        this.flag = false;
        this.initialize();


    }


}
