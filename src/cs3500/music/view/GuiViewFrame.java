package cs3500.music.view;

import java.awt.*;

import javax.swing.*;

import cs3500.music.model.IMusicModel;

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
        //this.getContentPane().add(displayPanel);
        this.setTitle("Music Viewer v1.1        Authors: Jameson O'Connor, Brendan Rejevich");
        BorderLayout bdl = new BorderLayout();
        //scrollPane.setLayout(new ScrollPaneLayout());
        this.setLayout(bdl);


        JScrollPane scrollPane = new JScrollPane(displayPanel);
        this.add(scrollPane);
        this.add(displayPanel, bdl.CENTER);
        scrollPane.createHorizontalScrollBar();
        //TODO make sure we are initializing in the right place
        //this.initialize();

        this.pack();
    }

    @Override
    public void initialize() {
        this.setVisible(true);
    }

    @Override
    public void setModelToView(IMusicModel model) {
        this.displayPanel.model = model;
        this.displayPanel.notes = model.getNotes();
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



