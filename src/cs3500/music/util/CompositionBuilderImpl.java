package cs3500.music.util;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;

/**
 *
 */
public class CompositionBuilderImpl implements CompositionBuilder<IMusicModel> {

    IMusicModel model;

    public CompositionBuilderImpl() {
        this.model = new MusicPieceModel();
    }

    @Override
    public IMusicModel build() {
        return model;
    }

    @Override
    public CompositionBuilder<IMusicModel> setTempo(int tempo) {
        this.model.setTempo(tempo);
        return this;
    }

    @Override
    public CompositionBuilder<IMusicModel> addNote(int start, int end, int instrument, int pitch, int volume) {
        Note p = new Note(pitch, start, end - start, instrument, volume);
        this.model.addNote(p);
        return this;
    }
}

