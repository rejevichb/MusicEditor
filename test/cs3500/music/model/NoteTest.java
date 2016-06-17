package cs3500.music.model;

import static org.junit.Assert.*;

import org.junit.Test;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static org.junit.Assert.*;

/**
 * Tests for the Note class
 */
public class NoteTest {

    private Note dSharp1 = new Note(Pitch.Ds, 1, 5, 3);
    private Note c4 = new Note(Pitch.C, 4, 10, 5);
    private Note cSharp4 = new Note(Pitch.Cs, 4, 2, 3);
    private Note dSharp4 = new Note(Pitch.Ds, 4, 5, 3);
    private Note dSharp4Copy = new Note(Pitch.Ds, 4, 5, 3);
    private Note dSharp4B = new Note(Pitch.Ds, 4, 7, 4);
    private Note dSharp4C = new Note(Pitch.Ds, 4, 10, 3);
    private Note e4 = new Note(Pitch.E, 4, 3, 5);
    private Note e4Copy = new Note(Pitch.E, 4, 3, 5);
    private Note fSharp4 = new Note(Pitch.Fs, 4, 7, 4);
    private Note gSharp4 = new Note(Pitch.Gs, 4, 8, 6);
    private Note a4 = new Note(Pitch.A, 4, 5, 5);
    private Note c5 = new Note(Pitch.C, 5, 10, 5);
    private Note gSharp10 = new Note(Pitch.Gs, 10, 8, 6);
    private Note b10 = new Note(Pitch.B, 10, 12, 4);


    @Test
    public void equals() throws Exception {

        assertEquals(false, cSharp4.equals(dSharp4));
        assertEquals(true, dSharp4.equals(dSharp4Copy));
        assertEquals(true, e4Copy.equals(e4));
        assertEquals(false, c4.equals(c5));
    }


    @Test
    public void toStringTest() {
        assertEquals(" C4 10-15", c4.toString());
        assertEquals(" C5 10-15", c5.toString());
        assertEquals("D#4 7-11", dSharp4B.toString());
        assertEquals("D#4 10-13", dSharp4C.toString());
        assertEquals("D#4 5-8", dSharp4.toString());
    }

    @Test
    public void getAbsolutePitch() throws Exception {

        assertEquals(36, c4.getAbsPitch());
        assertEquals(48, c5.getAbsPitch());
        assertEquals(12, c5.getAbsPitch() - c4.getAbsPitch());

        assertEquals(39,dSharp4.getAbsPitch());
        assertEquals(39,dSharp4B.getAbsPitch());
        assertEquals(39,dSharp4C.getAbsPitch());

        assertEquals(116,gSharp10.getAbsPitch());
        assertEquals(119,b10.getAbsPitch());
    }


    @Test
    public void getDuration() throws Exception {
        assertEquals(5, a4.getDuration());
        assertEquals(6, gSharp10.getDuration());
        assertEquals(4, b10.getDuration());
        assertEquals(3, dSharp4.getDuration());


    }

    @Test
    public void getStartBeat() throws Exception {
        assertEquals(5, a4.getStartBeat());
        assertEquals(8, gSharp10.getStartBeat());
        assertEquals(12, b10.getStartBeat());
        assertEquals(5, dSharp4.getStartBeat());
    }


}