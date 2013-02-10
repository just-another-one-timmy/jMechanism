package tmm.visual;

import processing.core.*;

public class ProcessingV extends Visualizer {

    private PGraphics graphics = null;
    // scale
    private float sc = 250;
    // translation
    private float tr = 200;

    public ProcessingV(PGraphics parent) {
        this.graphics = parent;
        parent.smooth();
        parent.colorMode(PApplet.RGB, 255);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        graphics.line(tr + (float) x1 * sc, tr + (float) y1 * sc, tr + (float) x2 * sc, tr + (float) y2 * sc);
    }

    @Override
    public void drawCircle(double x, double y, double radius) {
        graphics.ellipse(tr + (float) x * sc, tr + (float) y * sc, 10, 10);
    }

    @Override
    public void setColor(int color) {
        graphics.stroke((color & 0xFF0000) / 0xFFFF,
                (color & 0x00FF00) / 0xFF,
                color & 0x0000FF);
    }

    @Override
    public void setBgColor(int bgColor) {
        graphics.fill((bgColor & 0xFF0000) / 0xFFFF,
                (bgColor & 0x00FF00) / 0xFF,
                bgColor & 0x0000FF);
    }

    @Override
    public void drawPolygon(double[] points) throws Exception {
        if (points.length % 2 != 0) {
            throw new Exception("We are expecting even number of points for drawing polygon!");
        }
        graphics.beginShape();
        int i = 0;
        while (i < points.length) {
            graphics.vertex((float) points[i] * sc + tr, (float) points[i + 1] * sc + tr);
            i += 2;
        }
        graphics.endShape(PApplet.CLOSE);
    }

    @Override
    public void drawRotatedRect(double xc, double yc, double w, double h, double phi) throws Exception {
        graphics.rectMode(processing.core.PConstants.CENTER);
        graphics.translate(tr, tr);
        graphics.rotate((float) phi);
        graphics.rect(sc * (float) xc, sc * (float) yc, (float) w * sc, (float) h * sc);
        graphics.rotate((float) -phi);
        graphics.translate(-tr, -tr);
    }

    @Override
    public void beforeDraw() {
        graphics.beginDraw();
        graphics.background(0);
    }

    @Override
    public void afterDraw() {
        graphics.endDraw();
    }
}
