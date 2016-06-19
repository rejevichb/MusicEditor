package cs3500.music.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Brendan James Rejevich MusicPieceModel to represent a piece of music.
 */
public class MusicPieceModel implements IMusicModel {

    private ArrayList<Note> notes;
    private int totalNumBeats;
    int tempo;

    public MusicPieceModel() {
        this.notes = new ArrayList<>();
        this.totalNumBeats = 0;
        this.tempo = 200000;
    }

    //Copy constructor
    public MusicPieceModel(IMusicModel model) {
        this.notes = (ArrayList<Note>) model.getNotes();
        this.totalNumBeats = model.getTotNumBeats();
        this.tempo = model.getTempo();
    }

    public void addNote(Note n) {
        this.notes.add(n);
        Collections.sort(this.notes);
        if ((n.startBeat + n.duration) > totalNumBeats) {
            int add = (n.startBeat + n.duration) - totalNumBeats;
            totalNumBeats = totalNumBeats + add;
        }
    }

    public void removeNote(Note n) {
        if (this.notes.contains(n)) {
            this.notes.remove(n);
            resizeAfterRemoval();
        } else throw new IllegalArgumentException("That note does not exist in the piece");
    }

    /**
     * Adjusts the totalNumberOfBeats for this song after an note is removed. If the note is
     */
    private void resizeAfterRemoval() {
        int lastBeat = 0;
        for (Note note : notes) {
            if (note.getStartBeat() + note.getDuration() > lastBeat) {
                lastBeat = note.getStartBeat() + note.getDuration();
            }
        }
        this.totalNumBeats = lastBeat;
    }

    public void editNote(Note find, Note replace) {
        removeNote(find);
        addNote(replace);
    }

    public void combineMusic(IMusicModel m) {
        for (Note n : m.getNotes()) {
            this.addNote(n);
        }
    }

    //Amit, I am creating and adding a new note p to go into this object's new list from the given
    //list, however due to the overridden equals method in Note, if the following call is made
    //   >>  musicPieceModel1.appendMusic(musicPieceModel2)   <<
    // then the user can still edit and combine notes in the resulting new list of objects now in
    // musicPieceModel1 that were previously in musicPieceModel2 by instantiating a new note (either
    // manually, or to a call to musicPieceModel1.getNotes().get(someIndex). In my representation,
    // after the call to musicPieceModel2, the internal states of the notes from musicPieceModel2
    // that are now in musicPieceModel1 have been changed, and therefore it is not acceptable
    // to allow the note to be mutated - the note class is final.
    public void appendMusic(IMusicModel m) {
        int offset = this.getTotNumBeats();
        for (Note n : m.getNotes()) {
            Note p = new Note(n.getAbsPitch(), n.getStartBeat() + offset, n.getDuration(),
                    n.getInstrument(), n.getVolume());
            this.addNote(p);
        }
    }

    public String getVisualRepresentation() {
        int octaveLo = getOctaveLo();
        int octaveHi = getOctaveHi();
        int absolutePitchLo = 127;  // lowest pitch in lowest octave.
        int absolutePitchHi = 0;   // highest pitch in highest octave.

        Collections.sort(this.notes);

        for (Note n : notes) {
            //Set the pitchLo
            if (n.octaveFromAbsP(n.getAbsPitch()) == octaveLo
                    && n.getAbsPitch() < absolutePitchLo) {
                absolutePitchLo = n.getAbsPitch();
            }
            //Set the pitchHi
            if (n.octaveFromAbsP(n.getAbsPitch()) == octaveHi
                    && n.getAbsPitch() > absolutePitchHi) {
                absolutePitchHi = n.getAbsPitch();
            }
        }

        StringBuilder build = new StringBuilder();
        for (int p = absolutePitchLo; p <= absolutePitchHi; p++) {
            build.append(leadingSpaces(p));
            build.append(Note.getPitchFromInt(Note.pitchFromAbs(p)).toString());
            build.append(Note.octaveFromAbsP(p));
        }
        build.append("\n");


        //iterate over beats
        for (int b = 0; b <= totalNumBeats + 1; b++) {
            build.append(b);
            //these ifs are for padding the beats so that lines are not offset
            if (totalNumBeats < 100) {
                if (b < 10) {
                    build.append(" ");
                }
            }
            if (totalNumBeats >= 100 && totalNumBeats < 1000) {
                if (b < 10) {
                    build.append(" ");
                }
                if (b < 100) {
                    build.append(" ");
                }
            }
            if (totalNumBeats >= 1000) {
                if (b < 10) {
                    build.append(" ");
                }
                if (b < 100) {
                    build.append(" ");
                }
                if (b < 1000) {
                    build.append(" ");
                }
            }
            //loops over pitch
            for (int p = absolutePitchLo; p <= absolutePitchHi; p++) {
                build.append(draw(b, p));
                build.append(" ");
            }
            build.append("\n");
        }
        String out = build.toString();
        if (notes.size() == 0) {
            return "There are no notes in this piece of music";
        } else {
            return out;
        }
    }

    /**
     * It loops through each of the notes in this song and decides what to return, by checking if
     * there is a note in the same octave, pitch and startBeat, or in the same octave and pitch
     * where the current beat (passed in the parameter) is a sustained beat for the note.
     *
     * @param beat  the beat that
     * @param pitch the AbsolutePitch that is then converted to octave and pitch for comparison.
     * @return either "  X  ", "  |  ", or "     " based on the given beat and pitch.
     */
    private String draw(int beat, int pitch) {
        String aye = "";
        for (Note n : notes) {

            if (n.getStartBeat() == beat && n.getAbsPitch() == pitch) {
                aye = "  X  ";
                break;
            } else if (n.getStartBeat() < beat
                    && (n.getStartBeat() + n.getDuration()) >= beat
                    && n.getAbsPitch() == pitch) {
                aye = "  |  ";
                break;
            } else {
                aye = "     ";
            }
        }
        return aye;
    }

    /**
     * Returns the lowest octave found in this Pieces notes
     */
    private int getOctaveLo() {
        int low = 10;
        for (Note n : this.notes) {
            if (Note.octaveFromAbsP(n.getAbsPitch()) < low)
                low = Note.octaveFromAbsP(n.getAbsPitch());
        }
        return low;
    }

    /**
     * Returns the highest octave found in this Pieces notes
     */
    private int getOctaveHi() {
        int hi = 0;
        for (Note n : this.notes) {
            if (Note.octaveFromAbsP(n.getAbsPitch()) > hi)
                hi = Note.octaveFromAbsP(n.getAbsPitch());
        }
        return hi;
    }

    /**
     * Returns how many spaces should precede an AbsoluteNote value;
     *
     * @return String
     */
    private String leadingSpaces(int p) {
        String spaceCadet = "";
        int numSpaces = 5 - Note.getPitchFromInt(Note.pitchFromAbs(p)).toString().length();
        for (int i = 0; i < numSpaces; i++) {
            spaceCadet = spaceCadet + " ";
        }
        return spaceCadet;
    }

    public List<Note> getNotes() {
        List<Note> bigItUp = this.notes;
        return bigItUp;
    }

    public int getTotNumBeats() {
        int bigDawg = this.totalNumBeats;
        return bigDawg;
    }

    public List<Note> getNotesAtBeat(int b) {
        ArrayList<Note> ret = notes.stream().filter(n -> n.getStartBeat() == b ||
                (n.getStartBeat() < b && (n.getStartBeat() + n.getDuration()) >= b)).
                collect(Collectors.toCollection(ArrayList::new));
        return ret;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getTempo() {
        int copy = this.tempo;
        return copy;
    }
}

