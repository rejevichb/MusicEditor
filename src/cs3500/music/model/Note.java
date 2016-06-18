package cs3500.music.model;

import static cs3500.music.model.Pitch.*;

/**
 * Class to represent a note.
 */
public class Note implements Comparable<Note> {

    int duration;
    int startBeat;
    private int absPitch;
    int instrument;
    int volume;

    public Note(Pitch pitch, int octave, int startBeat, int duration) {
        if (octave > 10 || octave < 0 || startBeat < 0 || startBeat > 10000 || duration < 0) {
            throw new IllegalArgumentException("Please pass in valid parameter values");
        }
        this.duration = duration;
        this.startBeat = startBeat;
        this.absPitch = ((octave * 12) + pitch.getIntValue());
        this.volume = 64;
        this.instrument = 0;
    }

    public Note(int absPitch, int startBeat, int duration, int instrument, int volume) {
        if (absPitch < 0 || startBeat < 0 || startBeat > 10000 || duration < 0) {
            throw new IllegalArgumentException("Please pass in valid parameter values");
        }
        this.absPitch = absPitch;
        this.startBeat = startBeat;
        this.duration = duration;
        this.instrument = instrument;
        this.volume = volume;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Note)) {
            return false;
        }
        Note that = (Note) o;
        return that.duration == this.duration && that.absPitch == this.absPitch
                && this.startBeat == that.startBeat;
    }

    @Override
    public int compareTo(Note that) {
        int ret = -1000;
        if (this.startBeat > that.startBeat) {
            ret = -200;
        } else if (this.startBeat < that.startBeat) {
            ret = 200;
        } else if (this.startBeat == that.startBeat) {
            ret = 0;
        }
        return ret;
    }

    @Override
    public String toString() {
        return this.getPitchFromInt(pitchFromAbs(getAbsPitch())).toString() +
                this.octaveFromAbsP(getAbsPitch()) + " " +
                this.getStartBeat() + "-" + (this.getStartBeat() + this.getDuration());
    }


    /**
     * getter method to return a defensive the absolute Pitch of this note
     *
     * @return this note's absolutePitch
     */
    public int getAbsPitch() {
        int ret = this.absPitch;
        return ret;
    }

    /**
     * getter method to return a defensive the duration of this note
     *
     * @return this note's duration
     */
    public int getDuration() {
        int ret = this.duration;
        return ret;
    }


    /**
     * getter method to return a defensive copy start beat of this note
     *
     * @return this note's start beat
     */
    public int getStartBeat() {
        int ret = this.startBeat;
        return ret;
    }

    /**
     * getter method to return a defensive copy of this note's instrument
     *
     * @return an int representing this note's instrument in midi
     */
    public int getInstrument() {
        int copy = this.instrument;
        return copy;
    }


    /**
     * getter method to return a defensive copy of this note's volume
     *
     * @return an int representing the notes volume in midi
     */
    public int getVolume() {
        int copy = this.volume;
        return copy;
    }


    /**
     * Returns the integer value of a pitch from an absolute pitch value
     *
     * @param absPitch an absolute pitch integer value
     * @return the integer value of a pitch from an absolute pitch value
     */
    public static int pitchFromAbs(int absPitch) {
        return absPitch % 12;
    }

    /**
     * Returns the integer value of an octave from an absolute pitch value
     *
     * @param absPitch absolute pitch value
     * @return integer value of the octave that the given absolute pitch is in
     */
    public static int octaveFromAbsP(int absPitch) {
        int ret = 0;
        if (absPitch <= 11 && absPitch >= 0) {
            ret = 0;
        } else if (absPitch > 11 && absPitch <= 23) {
            ret = 1;
        } else if (absPitch > 23 && absPitch <= 35) {
            ret = 2;
        } else if (absPitch > 35 && absPitch <= 47) {
            ret = 3;
        } else if (absPitch > 47 && absPitch <= 59) {
            ret = 4;
        } else if (absPitch > 59 && absPitch <= 71) {
            ret = 5;
        } else if (absPitch > 71 && absPitch <= 83) {
            ret = 6;
        } else if (absPitch > 83 && absPitch <= 95) {
            ret = 7;
        } else if (absPitch > 95 && absPitch <= 107) {
            ret = 8;
        } else if (absPitch > 107 && absPitch <= 119) {
            ret = 9;
        } else if (absPitch > 119 && absPitch <= 127) {
            ret = 10;
        } else if (absPitch > 127 || absPitch < 0) {
            throw new IllegalArgumentException("That is not a valid absolute pitch value");
        }
        return ret;
    }

    /**
     * Returns the element from the Pitch enum is associated with the given integer.
     *
     * @param i any integer over the domain [0,11]
     * @return Pitch from given int
     */
    public static Pitch getPitchFromInt(int i) {
        switch (i) {
            case 0:
                return C;
            case 1:
                return Cs;
            case 2:
                return D;
            case 3:
                return Ds;
            case 4:
                return E;
            case 5:
                return F;
            case 6:
                return Fs;
            case 7:
                return G;
            case 8:
                return Gs;
            case 9:
                return A;
            case 10:
                return As;
            case 11:
                return B;
            default:
                throw new IllegalArgumentException("Int must be between 0 and 11 inclusive");
        }
    }
}

