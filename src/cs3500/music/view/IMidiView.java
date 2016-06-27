package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import javax.sound.midi.MetaEventListener;

//TODO
/**
 *
 */
public interface IMidiView extends IMusicPieceView {

    /**
     * Adds a metaEventListener to the sequencer in the MIDI View
     */
    void addMetaEventListener(MetaEventListener metaEventListener);

    /**
     * Plays or pauses the red line and the audio based on the sequencers current state.
     */
    void playPause();

    /**
     * Restarts the music piece, both the red line and the MIDI.
     */
    void restart();

    void setSequencerRepeat(int start, int end, int numberRepeats);

}
