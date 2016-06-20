package cs3500.music.view;

import java.awt.*;

/**
 * Created by brendanrejevich on 6/20/16.
 */
public class ComboConcreteGuiViewPanel extends ConcreteGuiViewPanel {


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        graphics.setColor(Color.RED);
//        graphics.drawLine(time * MEASURE_OFFSET, 0, 1 * MEASURE_OFFSET, BOX_OFFSET * (super.absolutePitchHi - super.absolutePitchLo + 1));
    }

}
