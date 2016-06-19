package cs3500.music.view;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Factory View Creation
 */
public class FactoryViewTest {

    @Test
    public void create() throws Exception {
        IMusicPieceView console = FactoryView.create("console");
        IMusicPieceView midi = FactoryView.create("midi");
        IMusicPieceView visual = FactoryView.create("visual");

        Assert.assertNotNull(console);
        Assert.assertNotNull(midi);
        Assert.assertNotNull(visual);

        Assert.assertNotSame(console, midi);
        Assert.assertNotSame(console, visual);
        Assert.assertNotSame(midi, visual);

        Assert.assertTrue(console instanceof ConsoleViewFrame);
        Assert.assertTrue(midi instanceof MidiViewImpl);
        Assert.assertTrue(visual instanceof GuiViewFrame);
    }

}