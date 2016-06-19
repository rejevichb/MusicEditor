package cs3500.music.view;

/**
 * Factory class for creating different implementations of IMusicPieceView based on an input
 * string.
 */
public class FactoryView {

    public static IMusicPieceView create(String v) {
        IMusicPieceView out;
        if (v.equalsIgnoreCase("console")) {
            out = new ConsoleViewFrame();
        } else if (v.equalsIgnoreCase("midi")) {
            out = new MidiViewImpl();
        }
        else if (v.equalsIgnoreCase("visual")) {
            out = new GuiViewFrame();
        } else {
            throw new IllegalArgumentException("Not a valid view type!");
        }
        return out;
    }

}
