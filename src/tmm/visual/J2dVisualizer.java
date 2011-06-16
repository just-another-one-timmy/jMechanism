/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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
        x = x * scaleX + translateX;
        y = y * scaleY + translateY;
        Shape circle = new Ellipse2D.Double(x-3, y-3, 5, 5);
        g.setStroke(new BasicStroke(1));
        g.setColor(Color.YELLOW);
        g.draw(circle);
    }

    @Override
    protected void drawLine(double x1, double y1, double x2, double y2, int thickness, int color) {
        x1 = x1 * scaleX + translateX;
        x2 = x2 * scaleX + translateX;
        y1 = y1 * scaleY + translateY;
        y2 = y2 * scaleY + translateY;
        Shape line = new Line2D.Double(x1, y1, x2, y2);
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLUE);
        g.draw(line);
    }

    public J2dVisualizer(CompManager c) {
        super(c);
    }
}
