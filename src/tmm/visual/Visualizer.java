package tmm.visual;

import tmm.compmanager.*;
import tmm.kpair.*;
import tmm.tf.*;

public abstract class Visualizer {

    protected boolean debugMode = true;
    protected boolean drawSegments = true;
    protected boolean drawKPairs = true;
    protected CompManager cm = null;
    private double R = 3, A = .03, B = .03, L = 0.2;
    protected double GROUND_TRIANGLE_X_DIFF = .075, GROUND_TRIANGLE_Y_DIFF = .1;
    protected double GROUND_TRIANGLE_STRIKE_X_DIFF = -.015,
            GROUND_TRIANGLE_STRIKE_Y_DIFF = .025,
            // MUST BE > 1 
            GROUND_TRIANGLE_STRIKES_COUNT = 5;
    protected static int COLOR_SEGMENT = 0x445566;
    protected static int COLOR_KPAIR_FILL = 0x000010;
    protected static int COLOR_KPAIR_STROKE = 0xFFFF00;
    protected static int COLOR_GROUND_TRIANGLE_STROKE = 0x00FF00;
    protected static int COLOR_GROUND_TRIANGLE_FILL = 0x005500;
    protected static int COLOR_GROUND_TRIANGLE_STRIKES = 0x225500;
    protected static int COLOR_KPAIR_SLIDE_RECT_FILL = 0x550000;
    protected static int COLOR_KPAIR_SLIDE_RECT_STROKE = 0x882233;

    public void draw() throws Exception {
        drawKPairs();
    }

    /* Abstraction level = mechanism 
     * for all methods below
     */
    void drawKPairs() throws Exception {
        for (KPair k : cm.getKPairs()) {
            if (!k.getC1().getSegment().getName().equals("Ground")) {
                setColor(COLOR_SEGMENT);
                drawLine(k.getLinear().getX().getValue(0),
                        k.getLinear().getY().getValue(0),
                        k.getC1().getSegment().getCPolus().getLinear().getX().getValue(0),
                        k.getC1().getSegment().getCPolus().getLinear().getY().getValue(0));
            }
            if (!k.getC2().getSegment().getName().equals("Ground")) {
                setColor(COLOR_SEGMENT);
                drawLine(k.getLinear().getX().getValue(0),
                        k.getLinear().getY().getValue(0),
                        k.getC2().getSegment().getCPolus().getLinear().getX().getValue(0),
                        k.getC2().getSegment().getCPolus().getLinear().getY().getValue(0));
            }
            switch (k.getType()) {
                case KPAIR_TYPE_TURN: {
                    drawKPairTurn((KPairTurn) k);
                    break;
                }
                case KPAIR_TYPE_SLIDE: {
                    drawKPairSlide((KPairSlide) k);
                    break;
                }
            }
        }
    }

    private void drawGroundTurn(KPair k) throws Exception {
        double xTop = k.getLinear().getX().getValue(0),
                yTop = k.getLinear().getY().getValue(0);
        double xRight = xTop + GROUND_TRIANGLE_X_DIFF, yRight = yTop + GROUND_TRIANGLE_Y_DIFF;
        double xLeft = xTop - GROUND_TRIANGLE_X_DIFF, yLeft = yTop + GROUND_TRIANGLE_Y_DIFF;
        setColor(COLOR_GROUND_TRIANGLE_STROKE);
        setBgColor(COLOR_GROUND_TRIANGLE_FILL);
        drawPolygon(new double[]{xTop, yTop, xLeft, yLeft, xRight, yRight});
        double xStep = (xRight - xLeft) * 1.0 / (GROUND_TRIANGLE_STRIKES_COUNT - 1);
        for (int i = 0; i < GROUND_TRIANGLE_STRIKES_COUNT; i++) {
            drawLine(xLeft + i * xStep, yLeft, xLeft + i * xStep + GROUND_TRIANGLE_STRIKE_X_DIFF,
                    yLeft + GROUND_TRIANGLE_STRIKE_Y_DIFF);
        }
    }

    private void drawGroundSlideIn(KPair k) {
    }

    private void drawKPairTurn(KPairTurn k) throws Exception {
        if (k.getC1().getSegment().getName().equals("Ground")
                || k.getC2().getSegment().getName().equals("Ground")) {
            // triangle
            drawGroundTurn(k);
        }
        setColor(COLOR_KPAIR_STROKE);
        setBgColor(COLOR_KPAIR_FILL);
        drawCircleForKPair(k);
    }

    private void drawKPairSlide(KPairSlide k) throws Exception {
        if (k.getC1().getSegment().getName().equals("Ground")) {
            drawGroundSlideIn(k);
        } else {
            drawLinePol(k, ((KPairSlide) k).getAngle());
        }
        if (k.getC2().getSegment().getName().equals("Ground")) {
            drawGroundSlideOut(k);
        } else {
            drawRectPol(k, ((KPairSlide) k).getAngle(), A, B);
        }
    }

    private void drawCircleForKPair(KPairTurn k) throws Exception {
        drawCircle(k.getLinear().getX().getValue(0), k.getLinear().getY().getValue(0), R);
    }

    private void drawLinePol(KPairSlide k, TFTurn angle) throws Exception {
        /*
        double phi = angle.getPhi().getValue(0);
        double xCenter = k.getLinear().getX().getValue(0),
        yCenter = k.getLinear().getY().getValue(0);
        double xRight = k.getLinear().getX().getValue(0) + .5 * L * Math.cos(phi),
        yRight = k.getLinear().getY().getValue(0) + .5 * L * Math.sin(phi);
        double xLeft = k.getLinear().getX().getValue(0) - .5 * L * Math.cos(phi),
        yLeft = k.getLinear().getY().getValue(0) + .5 * L * Math.sin(phi);
        setColor(COLOR_SEGMENT);
        drawLine(xLeft, yLeft, xRight, yRight);
         */
    }

    private void drawRectPol(KPairSlide k, TFTurn angle, double A, double B) throws Exception {
        setColor(COLOR_KPAIR_SLIDE_RECT_STROKE);
        setBgColor(COLOR_KPAIR_SLIDE_RECT_FILL);
        drawRotatedRect(k.getLinear().getX().getValue(0),
                k.getLinear().getY().getValue(0),
                A, B, angle.getPhi().getValue(0));
    }

    private void drawGroundSlideOut(KPairSlide k) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public abstract void drawLine(double x1, double y1, double x2, double y2) throws Exception;

    public abstract void drawCircle(double x, double y, double radius) throws Exception;

    public abstract void setColor(int color) throws Exception;

    public abstract void setBgColor(int bgColor) throws Exception;

    public abstract void drawPolygon(double[] points) throws Exception;

    public abstract void drawRotatedRect(double xc, double yc, double w, double h, double phi) throws Exception;

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public void setDrawSegments(boolean drawSegments) {
        this.drawSegments = drawSegments;
    }

    public void setDrawKPairs(boolean drawKPairs) {
        this.drawKPairs = drawKPairs;
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public boolean getDrawSegments() {
        return drawSegments;
    }

    public boolean getDrawKPairs() {
        return drawKPairs;
    }

    public void setCompManager(CompManager cm) {
        this.cm = cm;
    }
}
