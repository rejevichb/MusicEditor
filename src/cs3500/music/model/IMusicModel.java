package cs3500.music.model;

/*
Jameson O'Connor
Brendan Rejevich
CS3500 Object Oriented Design
 */

import java.util.List;

/**
 * To represent a Music Model, and all the operations that a MusicModel must have.
 */
public interface IMusicModel {


    /**
     * Adds a note to the list of notes for this model object.
     *
     * @param n the note to add
     * @throws IllegalArgumentException if the start beat is negative.
     */
    void addNote(Note n) throws IllegalArgumentException;


    /**
     * Removes the given note from the MusicModel's list of notes
     *
     * @param n the note to remove
     * @throws IllegalArgumentException if the Beat does not exist in the list
     */
    void removeNote(Note n) throws IllegalArgumentException;


    /**
     * Replaces the note find with the note replace.
     *
     * @param find    the note to find
     * @param replace the note to replace with
     * @throws IllegalArgumentException if the note to be found does not exist in the list.
     */
    void editNote(Note find, Note replace) throws IllegalArgumentException;

    /**
     * Copies all the notes from the given IMusicModel into this IMusicModel, leaving the given
     * music model unchanged.
     *
     * @param m the MusicPieceModel to be copied into this MusicPieceModel.
     */
    void combineMusic(IMusicModel m);

    /**
     * Copies all the notes from the given IMusicModel into this IMusicModel, leaving the given
     * music model unchanged. The total number of beats for this model will increase by the number
     * of beats in the given IMusicModel.
     *
     * @param m the MusicPieceModel to be appended to the end of this MusicPieceModel.
     */
    void appendMusic(IMusicModel m);


    /**
     * User uses this method to see the state of the of the music editor.  Along the left side, the
     * column, of all numbers, represents every beat in the song.  On the top of the model, is the
     * pitches.  The pitches are standard and limited to C, C♯, D, D♯, E, F, F♯, G, G♯, A, A♯, and
     * B.  Each of these pitches is then followed by a number which is a representation of the
     * octave that these pitches are in.  The octaves span from 1 - 10.  Under the pitches and to
     * the right of the beat column the notes are represented.  Each note is represented by it's
     * start "X" and it's beat duration which is represented by "|".
     *
     * @return String: that is a representation model of the music editor.
     */
    String getVisualRepresentation() throws IllegalStateException;

    /**
     * Returns the list of notes that are in this MusicPiece.
     *
     * @return list of notes
     */
    List<Note> getNotes();


    /**
     * Returns a list of notes from this MusicPiece that have a start beat or sustained beat at the
     * given beat.
     *
     * @param b beat
     * @return List of notes at the given beat
     */
    List<Note> getNotesAtBeat(int b);

    /**
     * Returns the total number of beats in this IMusicModel
     *
     * @return number of  beats in this IMusicModel
     */
    int getTotNumBeats();

    /**
     * Sets the tempo for this model
     *
     * @param tempo integer to represent tempo
     */
    void setTempo(int tempo);

    /**
     * Gets the tempo for this model
     *
     * @return defensive copy of this model's tempo
     */
    int getTempo();
}

