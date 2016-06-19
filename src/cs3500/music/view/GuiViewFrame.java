package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicPieceModel;

// Possibly of interest for handling mouse events

/**
 * A skeleton Frame (i.e., a window) in Swing
 */
public class GuiViewFrame extends javax.swing.JFrame implements IMusicPieceView {


    private ConcreteGuiViewPanel displayPanel;   // You may want to refine this to a subtype of JPanel


    /**
     * Creates new GuiView
     */
    public GuiViewFrame() {
        super();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Music Viewer v1.1        Authors: Jameson O'Connor, Brendan Rejevich");


        this.displayPanel = new ConcreteGuiViewPanel();
        this.displayPanel.setPreferredSize(new Dimension(1500, 0));
        JScrollPane scrolls = new JScrollPane(displayPanel);

        this.add(scrolls);
        //setVisible(true);


        this.pack();
    }

    @Override
    public void initialize() {
        this.setSize(new Dimension(500, 500));

        //displayPanel.revalidate();
        displayPanel.repaint();

        this.setVisible(true);


    }

    @Override
    public void setModelToView(IMusicModel m) {
        IMusicModel defense = new MusicPieceModel(m);


        this.displayPanel.model = defense;
        this.displayPanel.notes = defense.getNotes();
        //this.displayPanel.setPreferredSize(new Dimension(defense.getTotNumBeats(), 0));

    }

//    @Override
//    public Dimension getPreferredSize() {
//        return new Dimension(900, 650);
//    }


//    @Override
//    public void paint(Graphics g) {
//
//        this.displayPanel.paintComponent(g);
//
//    }



}



