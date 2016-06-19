package cs3500.music.view;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jameson on 6/19/16.
 */
public class ConsoleViewFrameTest {

    private Note c4 = new Note(Pitch.C, 4, 10, 5);
    private Note cSharp4 = new Note(Pitch.Cs, 4, 2, 3);
    private Note dSharp4 = new Note(Pitch.Ds, 4, 5, 3);
    private Note dSharp4B = new Note(Pitch.Ds, 4, 7, 4);
    private Note dSharp4C = new Note(Pitch.Ds, 4, 10, 3);
    private Note e4 = new Note(Pitch.E, 4, 3, 5);
    private Note fSharp4 = new Note(Pitch.Fs, 4, 7, 4);
    private Note gSharp4 = new Note(Pitch.Gs, 4, 8, 6);
    private Note a4 = new Note(Pitch.A, 4, 5, 5);
    private Note aSharp4 = new Note(Pitch.As, 4, 3, 5);
    private Note b4 = new Note(Pitch.B, 4, 12, 4);


    @Test
    public void initialize() throws Exception {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        IMusicPieceView cvTest = new ConsoleViewFrame();

        IMusicModel test = new MusicPieceModel();
        test.addNote(cSharp4);

        cvTest.setModelToView(test);

        cvTest.initialize();

        String stringExpected = "   C#4\n" +
                "0       \n" +
                "1       \n" +
                "2   X   \n" +
                "3   |   \n" +
                "4   |   \n" +
                "5   |   \n" +
                "6       \n";

        assertEquals(stringExpected, outContent.toString());

        test.addNote(c4);
        test.addNote(dSharp4);
        test.addNote(dSharp4B);
        test.addNote(dSharp4C);

        cvTest.setModelToView(test);

        cvTest.initialize();

        String stringExpected2 = "   C#4\n" +
                "0       \n" +
                "1       \n" +
                "2   X   \n" +
                "3   |   \n" +
                "4   |   \n" +
                "5   |   \n" +
                "6       \n" +
                "    C4   C#4    D4   D#4\n" +
                "0                         \n" +
                "1                         \n" +
                "2         X               \n" +
                "3         |               \n" +
                "4         |               \n" +
                "5         |           X   \n" +
                "6                     |   \n" +
                "7                     X   \n" +
                "8                     |   \n" +
                "9                     |   \n" +
                "10  X                 X   \n" +
                "11  |                 |   \n" +
                "12  |                 |   \n" +
                "13  |                 |   \n" +
                "14  |                     \n" +
                "15  |                     \n" +
                "16                        \n";

        assertEquals(stringExpected2, outContent.toString());

        test.addNote(e4);
        test.addNote(fSharp4);
        test.addNote(gSharp4);
        test.addNote(a4);
        test.addNote(aSharp4);
        test.addNote(b4);

        assertEquals(stringExpected2, outContent.toString());

        cvTest.initialize();

        String stringExpected3 = "   C#4\n" +
                "0       \n" +
                "1       \n" +
                "2   X   \n" +
                "3   |   \n" +
                "4   |   \n" +
                "5   |   \n" +
                "6       \n" +
                "    C4   C#4    D4   D#4\n" +
                "0                         \n" +
                "1                         \n" +
                "2         X               \n" +
                "3         |               \n" +
                "4         |               \n" +
                "5         |           X   \n" +
                "6                     |   \n" +
                "7                     X   \n" +
                "8                     |   \n" +
                "9                     |   \n" +
                "10  X                 X   \n" +
                "11  |                 |   \n" +
                "12  |                 |   \n" +
                "13  |                 |   \n" +
                "14  |                     \n" +
                "15  |                     \n" +
                "16                        \n" +
                "    C4   C#4    D4   D#4    E4    F4   F#4    G4   G#4    A4   A#4    B4\n" +
                "0                                                                         \n" +
                "1                                                                         \n" +
                "2         X                                                               \n" +
                "3         |                 X                                   X         \n" +
                "4         |                 |                                   |         \n" +
                "5         |           X     |                             X     |         \n" +
                "6                     |     |                             |     |         \n" +
                "7                     X     |           X                 |     |         \n" +
                "8                     |     |           |           X     |     |         \n" +
                "9                     |                 |           |     |               \n" +
                "10  X                 X                 |           |     |               \n" +
                "11  |                 |                 |           |                     \n" +
                "12  |                 |                             |                 X   \n" +
                "13  |                 |                             |                 |   \n" +
                "14  |                                               |                 |   \n" +
                "15  |                                                                 |   \n" +
                "16                                                                    |   \n";

        assertEquals(stringExpected3, outContent.toString());


    }

}