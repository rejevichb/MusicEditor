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

    void addMetaEventListener(MetaEventListener metaEventListener);

    void play(boolean b);

    void pause();
}
