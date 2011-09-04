package tmm.visual;

import processing.core.*;

public class ProcessingV extends Visualizer {

    private PApplet parent = null;
    private float sc = 250;
    private float tr = 200;

    public ProcessingV(PApplet parent) {
        this.parent = parent;
        parent.fill(0);
        parent.stroke(126);
        parent.smooth();
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        parent.line(tr + (float) x1 * sc, tr + (float) y1 * sc, tr + (float) x2 * sc, tr + (float) y2 * sc);
    }

    @Override
    public void drawCircle(double x, double y, double radius) {
        parent.ellipse(tr + (float) x * sc, tr + (float) y * sc, 10, 10);
    }
}
