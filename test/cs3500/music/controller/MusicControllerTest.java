package cs3500.music.controller;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.Pitch;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.CompositionBuilderImpl;
import cs3500.music.view.ConsoleViewFrame;
import cs3500.music.view.FactoryView;
import cs3500.music.view.IMusicPieceView;


import static junit.framework.TestCase.assertEquals;


/**
 * Created by brendanrejevich on 6/25/16.
 */
public class MusicControllerTest {


    IMusicPieceView view = new ConsoleViewFrame();


    @Test
    public void testModelToView() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        CompositionBuilder<IMusicModel> builder = new CompositionBuilderImpl();
        //adding 4 notes
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i, i + 5, 1, i + 60, 64);
        }
        //adding 4 notes
        for (int i = 0; i <= 9; i += 3) {
            builder.addNote(i + 10, i + 15, 1, i + 60, 64);
        }
        builder.setTempo(20000);
        IMusicModel model = builder.build();
        IMusicPieceView console = FactoryView.create("console");

        MusicController testController = new MusicController(model, console);

        testController.modelToView();

        console.initialize();

        assertEquals("    C5   C#5    D5   D#5    E5    F5   F#5    G5   G#5    A5\n" +
                        "0   X                                                         \n" +
                        "1   |                                                         \n" +
                        "2   |                                                         \n" +
                        "3   |                 X                                       \n" +
                        "4   |                 |                                       \n" +
                        "5   |                 |                                       \n" +
                        "6                     |                 X                     \n" +
                        "7                     |                 |                     \n" +
                        "8                     |                 |                     \n" +
                        "9                                       |                 X   \n" +
                        "10  X                                   |                 |   \n" +
                        "11  |                                   |                 |   \n" +
                        "12  |                                                     |   \n" +
                        "13  |                 X                                   |   \n" +
                        "14  |                 |                                   |   \n" +
                        "15  |                 |                                       \n" +
                        "16                    |                 X                     \n" +
                        "17                    |                 |                     \n" +
                        "18                    |                 |                     \n" +
                        "19                                      |                 X   \n" +
                        "20                                      |                 |   \n" +
                        "21                                      |                 |   \n" +
                        "22                                                        |   \n" +
                        "23                                                        |   \n" +
                        "24                                                        |   \n" +
                        "25                                                            \n",
                outContent.toString());

        Note c4 = new Note(Pitch.C, 4, 10, 5);
        Note cSharp4 = new Note(Pitch.Cs, 4, 2, 3);
        Note dSharp4 = new Note(Pitch.Ds, 4, 5, 3);
        Note dSharp4B = new Note(Pitch.Ds, 4, 7, 4);
        Note dSharp4C = new Note(Pitch.Ds, 4, 10, 3);

        model.addNote(c4);
        model.addNote(cSharp4);
        model.addNote(dSharp4);
        model.addNote(dSharp4B);
        model.addNote(dSharp4C);

        testController.modelToView();


        console.initialize();

        assertEquals("    C5   C#5    D5   D#5    E5    F5   F#5    G5   G#5    A5\n" +
                        "0   X                                                         \n" +
                        "1   |                                                         \n" +
                        "2   |                                                         \n" +
                        "3   |                 X                                       \n" +
                        "4   |                 |                                       \n" +
                        "5   |                 |                                       \n" +
                        "6                     |                 X                     \n" +
                        "7                     |                 |                     \n" +
                        "8                     |                 |                     \n" +
                        "9                                       |                 X   \n" +
                        "10  X                                   |                 |   \n" +
                        "11  |                                   |                 |   \n" +
                        "12  |                                                     |   \n" +
                        "13  |                 X                                   |   \n" +
                        "14  |                 |                                   |   \n" +
                        "15  |                 |                                       \n" +
                        "16                    |                 X                     \n" +
                        "17                    |                 |                     \n" +
                        "18                    |                 |                     \n" +
                        "19                                      |                 X   \n" +
                        "20                                      |                 |   \n" +
                        "21                                      |                 |   \n" +
                        "22                                                        |   \n" +
                        "23                                                        |   \n" +
                        "24                                                        |   \n" +
                        "25                                                            \n" +
                        "    C4   C#4    D4   D#4    E4    F4   F#4    G4   G#4    A4   A#4   " +
                        " B4  " +
                        "  C5   C#5    D5   D#5    E5    F5   F#5    G5   G#5    A5\n" +
                        "0                                                             " +
                        "              X                                               " +
                        "          \n" +

                        "1                                                             " +
                        "              |                                                       " +
                        "  \n" +
                        "2         X                                                     " +
                        "            |                                                        " +
                        " \n" +
                        "3         |                                                     " +
                        "            |                 X                                      " +
                        " \n" +
                        "4         |                                                         " +
                        "        |                 |                                       \n" +
                        "5         |           X                                             " +
                        "        |                 |                                       \n" +
                        "6                     |                                             " +
                        "                          |                 X                     \n" +
                        "7                     X                                             " +
                        "                          |                 |                     \n" +
                        "8                     |                                             " +
                        "                          |                 |                     \n" +
                        "9                     |                                             " +
                        "                                            |                 X   \n" +
                        "10  X                 X                                             " +
                        "        X                                   |                 |   \n" +
                        "11  |                 |                                             " +
                        "        |                                   |                 |   \n" +
                        "12  |                 |                                             " +
                        "        |                                                     |   \n" +
                        "13  |                 |                                             " +
                        "        |                 X                                   |   \n" +
                        "14  |                                                                 " +
                        "      |                 |                                   |   \n" +
                        "15  |                                                               " +
                        "        |                 |                                       \n" +
                        "16                                                                " +
                        "                            |                 X                     \n" +
                        "17                                                                " +
                        "                            |                 |                     \n" +
                        "18                                                                " +
                        "                            |                 |                     \n" +
                        "19                                                                " +
                        "                                              |                 X   \n" +
                        "20                                                                   " +
                        "                                           |                 |   \n" +
                        "21                                                                  " +
                        "                                            |                 |   \n" +
                        "22                                                                  " +
                        "                                                              |   \n" +
                        "23                                                                  " +
                        "                                                              |   \n" +
                        "24                                                                  " +
                        "                                                              |   \n" +
                        "25                                                                  " +
                        "                                                                  \n",
                outContent.toString());


    }

}