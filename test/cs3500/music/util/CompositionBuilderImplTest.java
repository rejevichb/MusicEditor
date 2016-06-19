package cs3500.music.util;

import org.junit.Test;

import cs3500.music.model.IMusicModel;

import static org.junit.Assert.*;

/**
 * Created by brendanrejevich on 6/19/16.
 */
public class CompositionBuilderImplTest {
    @Test
    public void build() throws Exception {
        CompositionBuilder<IMusicModel> builder1 = new CompositionBuilderImpl();

        for (int i = 0; i <= 11; i += 3) {
            builder1.addNote(i, i + 5, 1, i + 60, 64);
        }

        for (int i = 0; i <= 11; i += 3) {
            builder1.addNote(i + 10, i + 15, 1, i + 60, 64);
        }

        builder1.setTempo(20000);

        IMusicModel out = builder1.build();

        assertEquals(20000, out.getTempo());
        assertEquals(24, out.getTotNumBeats());
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
                        "25                                                            \n"
                , out.getVisualRepresentation());
    }

    @Test
    public void setTempo() throws Exception {
        CompositionBuilder<IMusicModel> builder1 = new CompositionBuilderImpl();
        CompositionBuilder<IMusicModel> builder2 = new CompositionBuilderImpl();

        builder1.setTempo(1000000);

        for (int i = 0; i <= 11; i += 3) {
            builder1.addNote(i, i + 5, 1, i + 60, 64);
            builder2.addNote(i, i + 5, 1, i + 60, 64);
        }

        for (int i = 0; i <= 11; i += 3) {
            builder1.addNote(i + 10, i + 15, 1, i + 60, 64);
            builder2.addNote(i + 10, i + 15, 1, i + 60, 64);
        }

        builder2.setTempo(205005);

        IMusicModel out1 = builder1.build();
        IMusicModel out2 = builder2.build();

        assertEquals(1000000, out1.getTempo());
        assertEquals(205005, out2.getTempo());
    }

    @Test
    public void addNote() throws Exception {

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

        IMusicModel out = builder.build();

        assertEquals(8, out.getNotes().size());
        assertEquals(24, out.getTotNumBeats());

    }

}