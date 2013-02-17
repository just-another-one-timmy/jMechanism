package tmm.visual;

import java.util.logging.Level;
import java.util.logging.Logger;
import tmm.compmanager.*;
import tmm.connector.Connector;
import tmm.kpair.*;
import tmm.tf.*;

public abstract class Visualizer {

    protected boolean debugMode = true;
    protected boolean drawSegments = true;
    protected boolean drawKPairs = true;
    protected CompManager cm = null;
    private double R = 3, A = .1, B = .2, L = 1.5;
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

    public final void draw() throws Exception {
        beforeDraw();
        drawKPairs();
        afterDraw();
    }

    void drawLineForConnector(KPair k, Connector c) {
        if (!c.getSegment().getName().equals("Ground")) {
            setColor(COLOR_SEGMENT);
            try {
                drawLine(k.getLinear().getX().getTF0(),
                        k.getLinear().getY().getTF0(),
                        k.getC1().getSegment().getCPolus().getLinear().getX().getTF0(),
                        k.getC1().getSegment().getCPolus().getLinear().getY().getTF0());
            } catch (Exception e) {
                Logger.getLogger(Visualizer.class.getName()).log(Level.SEVERE, null, e);
            }
        }

    }

    /* Abstraction level = mechanism
     * for all methods below
     */
    void drawKPairs() throws Exception {
        // Draw all KPairSlide.
        for (KPair k : cm.getKPairs()) {
            if (k.getType() == KPairType.KPAIR_TYPE_SLIDE) {
                drawLineForConnector(k, k.getC1());
                drawLineForConnector(k, k.getC2());
                drawKPairSlide((KPairSlide) k);
            }
        }

        // Draw all KPairTurn.
        for (KPair k : cm.getKPairs()) {
            if (k.getType() == KPairType.KPAIR_TYPE_TURN) {
                drawLineForConnector(k, k.getC1());
                drawLineForConnector(k, k.getC2());
                drawKPairTurn((KPairTurn) k);
            }
        }
    }

    private void drawGroundTurn(KPair k) throws Exception {
        double xTop = k.getLinear().getX().getTF0(),
                yTop = k.getLinear().getY().getTF0();
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

    private void drawGroundSlideIn(KPairSlide k) throws Exception {
        setColor(0x0000FF);
        setBgColor(0x222222);
        drawRotatedRect(k.getC1().getLinear0().getX().getTF0(),
                k.getC1().getLinear0().getY().getTF0(),
                A, B, k.getAngle().getPhi().getTF0());
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
        drawCircle(k.getLinear().getX().getTF0(), k.getLinear().getY().getTF0(), R);
    }

    private void drawLinePol(KPairSlide k, TFTurn angle) throws Exception {
        double phi = angle.getPhi().getTF0();
        double xCenter = k.getC1().getLinear0().getX().getTF0(),
                yCenter = k.getC1().getLinear0().getY().getTF0();
        double xRight = xCenter + .5 * L * Math.cos(phi),
                yRight = yCenter + .5 * L * Math.sin(phi);
        double xLeft = xCenter - .5 * L * Math.cos(phi),
                yLeft = yCenter - .5 * L * Math.sin(phi);
        setColor(COLOR_SEGMENT);
        drawLine(xLeft, yLeft, xRight, yRight);
    }

    private void drawRectPol(KPairSlide k, TFTurn angle, double A, double B) throws Exception {
        setColor(COLOR_KPAIR_SLIDE_RECT_STROKE);
        setBgColor(COLOR_KPAIR_SLIDE_RECT_FILL);
        drawRotatedRect(k.getC1().getLinear0().getX().getTF0(),
                k.getC1().getLinear0().getY().getTF0(),
                A, B, angle.getPhi().getTF0());
    }

    private void drawGroundSlideOut(KPairSlide k) throws Exception {
        setColor(0xFF0000);
        setBgColor(0x222222);
        drawRotatedRect(k.getC1().getLinear0().getX().getTF0(),
                k.getC1().getLinear0().getY().getTF0(),
                A, B, k.getAngle().getPhi().getTF0());
    }

    public abstract void drawLine(double x1, double y1, double x2, double y2);

    public abstract void drawCircle(double x, double y, double radius) throws Exception;

    public abstract void setColor(int color);

    public abstract void setBgColor(int bgColor) throws Exception;

    public abstract void drawPolygon(double[] points) throws Exception;

    public abstract void drawRotatedRect(double xc, double yc, double w, double h, double phi) throws Exception;

    public abstract void beforeDraw();

    public abstract void afterDraw();

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
