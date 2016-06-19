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


    private final ConcreteGuiViewPanel displayPanel;   // You may want to refine this to a subtype of JPanel

    /**
     * Creates new GuiView
     */
    public GuiViewFrame() {
        super();
        this.displayPanel = new ConcreteGuiViewPanel();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Music Viewer v1.1        Authors: Jameson O'Connor, Brendan Rejevich");

        JScrollPane scrollPane = new JScrollPane(displayPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollPane.createHorizontalScrollBar();
        //scrollPane.setViewportView(displayPanel);
        scrollPane.setSize(getPreferredSize());
        this.add(scrollPane);
        this.setSize(new Dimension(900, 750));

        //this.pack();
    }

    @Override
    public void initialize() {
        //BorderLayout bdl = new BorderLayout();
        //this.setLayout(bdl);

        this.setVisible(true);
    }

    @Override
    public void setModelToView(IMusicModel m) {
        IMusicModel defence = new MusicPieceModel(m);
        this.displayPanel.model = defence;
        this.displayPanel.notes = defence.getNotes();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 650);
    }


    @Override
    public void paint(Graphics g) {
        this.displayPanel.paintComponent(g);
    }
}



