/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import tmm.compmanager.*;

/**
 *
 * @author jtimv
 */
public class J2dVisualizer extends Visualizer {

    private Graphics2D g;

    public void setGraphics(Graphics2D g) {
        this.g = g;
    }

    @Override
    protected void drawCircle(double x, double y, int radius, int color, int outline, int outlineColor) {
        Shape circle = new Ellipse2D.Double(x+10, y+10, radius, radius);
        g.draw(circle);
    }

    @Override
    protected void drawLine(double x1, double y1, double x2, double y2, int thickness, int color) {
    }

    public J2dVisualizer(CompManager c) {
        super(c);
    }
}
