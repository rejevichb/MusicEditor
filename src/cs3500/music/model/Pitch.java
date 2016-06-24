package cs3500.music.model;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

/**
 * Created by brendanrejevich on 6/7/16. Represents the possible types of a Pitch, and their
 * associated int values.
 */
public enum Pitch {

    C(0), Cs(1), D(2), Ds(3), E(4), F(5), Fs(6), G(7), Gs(8), A(9), As(10), B(11);

    private int value;

    Pitch(int value) {
        this.value = value;
    }

    public int getIntValue() {
        int ret = this.value;
        return ret;
    }

    @Override
    public String toString() {
        switch (value) {
            case 0:
                return " C";
            case 1:
                return "C#";
            case 2:
                return " D";
            case 3:
                return "D#";
            case 4:
                return " E";
            case 5:
                return " F";
            case 6:
                return "F#";
            case 7:
                return " G";
            case 8:
                return "G#";
            case 9:
                return " A";
            case 10:
                return "A#";
            case 11:
                return " B";
            default:
                throw new IllegalArgumentException("Int must be between 0 and 11 inclusive");
        }
    }
}

