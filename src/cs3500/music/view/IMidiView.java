package cs3500.music.view;

import javax.sound.midi.MetaEventListener;

/**
 * Brendan
 */
public interface IMidiView extends IMusicPieceView {

//    long getTime();

    void addMetaEventListener(MetaEventListener metaEventListener);
}
