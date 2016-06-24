package cs3500.music.view;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Collections;

import javax.swing.*;

import cs3500.music.controller.MusicController;
import cs3500.music.model.Note;

//TODO

/**
 *
 */
public class ConcreteGuiViewPanel extends JPanel {

    protected java.util.List<Note> notes;
    protected int totalNumBeats;
    protected int tempo;
    private int BOX_OFFSET = 20;
    private int MEASURE_OFFSET = BOX_OFFSET * 4;
    private int absolutePitchLo;  // lowest pitch in lowest octave.
    private int absolutePitchHi;   // highest pitch in highest octave.
    long timeVariable = 0;
    Note remove;

    public ConcreteGuiViewPanel() {
        super();

    }

    private int getAbsoluteLo() {
        int returnVal = 127;
        for (Note n : this.notes) {
            //Set the pitchLo
            if (n.getAbsPitch() < returnVal) {
                returnVal = n.getAbsPitch();
            }
        }
        return returnVal;
    }

    private int getAbsoluteHi() {
        int returnVal = 0;
        for (Note n : this.notes) {
            //Set the pitchHi
            if (n.getAbsPitch() > returnVal) {
                returnVal = n.getAbsPitch();
            }
        }
        return returnVal;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D graphics = (Graphics2D) g;

        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.

        Collections.sort(this.notes);


        //drawing pitch labels
        for (int i = absolutePitchHi; i >= absolutePitchLo; i--) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Cambria", Font.BOLD, 12));
            if (i >= absolutePitchLo) {
                String display = new String(Note.getPitchFromInt(Note.pitchFromAbs(i)).toString()
                        + Note.octaveFromAbsP(i));
                graphics.drawString(display, 10, ((Math.abs(i - absolutePitchHi) * BOX_OFFSET)
                        + 65));
            }
        }

        //drawing beat labels
        for (int j = 0; j < this.totalNumBeats + 1; j++) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("TimesRoman", Font.BOLD, 13));
            if (j % 16 == 0) {
                graphics.drawString(j + "", (j * BOX_OFFSET) + 30, 30);
            }
        }

        graphics.translate(40, 40);


        //rows
        for (int i = absolutePitchLo; i <= absolutePitchHi + 1; i++) {
            graphics.setStroke(new BasicStroke(1.5f));
            if (i % 12 == 0) {
                graphics.setStroke(new BasicStroke(2.65f));
            }
            graphics.drawLine(0, (i - absolutePitchLo) * BOX_OFFSET,
                    (totalNumBeats + completeMeasure()) * BOX_OFFSET,
                    (i - absolutePitchLo) * BOX_OFFSET);
        }

        Collections.sort(this.notes, Collections.reverseOrder());


        //draw all the notes on this layout
        for (int beat = 0; beat < totalNumBeats + 1; beat++) {
            for (int absPitch = absolutePitchHi; absPitch >= absolutePitchLo; absPitch--) {
                for (Note n : notes) {
                    if (n.getStartBeat() < beat
                            && (n.getStartBeat() + n.getDuration()) >= beat
                            && n.getAbsPitch() == absPitch) {
                        graphics.setColor(Color.green);
                        graphics.fillRect(beat * BOX_OFFSET, (Math.abs(absPitch - absolutePitchHi))
                                * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                    } else if (n.getStartBeat() == beat && n.getAbsPitch() == absPitch) {
                        graphics.setColor(Color.BLACK);
                        graphics.fillRect(beat * BOX_OFFSET, (Math.abs(absPitch - absolutePitchHi))
                                * BOX_OFFSET, BOX_OFFSET, BOX_OFFSET);
                    }
                }
            }
        }

        //columns
        for (int j = 0; j <= (this.totalNumBeats + completeMeasure()) / 4; j++) {
            graphics.setColor(Color.BLACK);
            graphics.drawLine(j * MEASURE_OFFSET, 0, (j) * MEASURE_OFFSET, BOX_OFFSET *
                    (absolutePitchHi - absolutePitchLo + 1));
        }


        graphics.setColor(Color.RED);
        graphics.drawLine((int) timeVariable, 0, (int) timeVariable,
                BOX_OFFSET * (absolutePitchHi - absolutePitchLo + 1));
    }


    /**
     * this method is used as a inverse mod so that the measure always end on a full beat measure
     * for the gui view.  This is a helper for paintComponent and make sure that every note is in a
     * beat measure.
     *
     * @return int representing the number of beats to complete the measure that the last beat is in
     */
    private int completeMeasure() {
        int end = this.totalNumBeats % 4;
        return 4 - end;
    }


    protected void removeNote(MouseListener mouseListener) {
        MusicController.MouseHandler mHandler = (MusicController.MouseHandler) mouseListener;

        absolutePitchLo = getAbsoluteLo();  // lowest pitch in lowest octave.
        absolutePitchHi = getAbsoluteHi();   // highest pitch in highest octave.
        System.out.println(mHandler.getX() + " + " + mHandler.getY());


        int xSub = mHandler.getX() % 20;
        int xPoint = ((mHandler.getX() - xSub) - 40) / 20;

        int ySub = mHandler.getY() % 20;
        int yPoint = ((mHandler.getY() - ySub) - 40) / 20;
        yPoint = absolutePitchHi - yPoint;


        for (Note n : this.notes) {  //
            // xPoint <= 5  && n.getStartBeat() - xPoint >= -5
            if (n.getStartBeat() == xPoint && n.getAbsPitch() == yPoint) {
                remove = n;
            }
        }

    }

    public Note getRemovedNote() {
        return remove;
    }

}

