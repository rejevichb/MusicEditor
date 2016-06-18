package cs3500.music.view;

import java.awt.*;
import java.util.Collections;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.Note;

/**
 * A dummy view that simply draws a string
 */
public class ConcreteGuiViewPanel extends JPanel {

    IMusicModel model;
    java.util.List<Note> notes;
    int BOX_OFFSET = 20;
    int MEASURE_OFFSET = BOX_OFFSET * 4;


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics = (Graphics2D) g;
        this.notes = model.getNotes();

        Collections.sort(this.notes);

        int absolutePitchLo = 110;  // lowest pitch in lowest octave.
        int absolutePitchHi = 0;   // highest pitch in highest octave.

        for (Note n : notes) {
            //Set the pitchLo
            if (n.getAbsPitch() < absolutePitchLo) {
                absolutePitchLo = n.getAbsPitch();
            }
            //Set the pitchHi
            if (n.getAbsPitch() > absolutePitchHi) {
                absolutePitchHi = n.getAbsPitch();
            }
        }

        //FIXME reverse pitch labels
        //drawing pitch labels
        for (int i = absolutePitchLo; i <= absolutePitchHi + 1; i++) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Cambria", Font.BOLD, 12));
            if (i <= absolutePitchHi) {
                String display = new String(Note.getPitchFromInt(Note.pitchFromAbs(i)).toString() + Note.octaveFromAbsP(i));
                graphics.drawString(display, 10, (((i - absolutePitchLo) * BOX_OFFSET) + 65));
            }
        }

        //drawing beat labels
        for (int j = 0; j < model.getTotNumBeats() + 1; j++) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 13));
            if (j % 16 == 0) {
                graphics.drawString(j + "", (j * BOX_OFFSET) + 30, 40);
            }
        }


        graphics.translate(40, 50);


        //rows
        for (int i = absolutePitchLo; i <= absolutePitchHi + 1; i++) {
            graphics.setStroke(new BasicStroke(1.5f));
            if (i % 12 == 0) {
                graphics.setStroke(new BasicStroke(2.65f));
            }
            graphics.drawLine(0, (i - absolutePitchLo) * BOX_OFFSET,
                    model.getTotNumBeats() * BOX_OFFSET, (i - absolutePitchLo) * BOX_OFFSET);
        }

        Collections.sort(this.notes, Collections.reverseOrder());

        //FIXME reverse pitch from *** from low to high *** to *** from hi to lo ***
        //draw all the notes on this layout
        for (int beat = 0; beat < model.getTotNumBeats() + 1; beat++) {
            for (int absPitch = absolutePitchLo; absPitch <= absolutePitchHi; absPitch++) {

                for (Note n : notes) {
                    if (n.getStartBeat() < beat
                            && (n.getStartBeat() + n.getDuration()) >= beat
                            && n.getAbsPitch() == absPitch) {
                        graphics.setColor(Color.green);
                        //graphics.clearRect(beat * BOX_OFFSET, (absPitch - absolutePitchLo) * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                        graphics.fillRect(beat * BOX_OFFSET, (absPitch - absolutePitchLo) * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                    } else if (n.getStartBeat() == beat && n.getAbsPitch() == absPitch) {
                        graphics.setColor(Color.BLACK);
                        //graphics.clearRect(beat * BOX_OFFSET, (absPitch - absolutePitchLo) * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                        graphics.fillRect(beat * BOX_OFFSET, (absPitch - absolutePitchLo) * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                    }
                }
            }
        }

        //columns
        for (int j = 0; j < (model.getTotNumBeats() / 4) + 1; j++) {
            graphics.setColor(Color.BLACK);
            graphics.drawLine(j * MEASURE_OFFSET, 0, j * MEASURE_OFFSET, BOX_OFFSET * (absolutePitchHi - absolutePitchLo + 1));
        }
    }

}

