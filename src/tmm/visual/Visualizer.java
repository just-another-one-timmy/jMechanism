package tmm.visual;

import tmm.compmanager.*;
import tmm.kpair.*;
import tmm.tf.*;

public abstract class Visualizer {

    protected boolean debugMode = true;
    protected boolean drawSegments = true;
    protected boolean drawKPairs = true;
    protected CompManager cm = null;
    private double R = 3, A = 10, B = 20, L = 40;

    public void draw() throws Exception {
        drawKPairs();
    }

    /* Abstraction level = mechanism 
     * for all methods below
     */
    void drawKPairs() throws Exception {
        for (KPair k : cm.getKPairs()) {
            if (!k.getC1().getSegment().getName().equals("Ground")) {
                drawLine(k.getLinear().getX().getValue(0),
                        k.getLinear().getY().getValue(0),
                        k.getC1().getSegment().getCPolus().getLinear().getX().getValue(0),
                        k.getC1().getSegment().getCPolus().getLinear().getY().getValue(0));
            }
            if (!k.getC2().getSegment().getName().equals("Ground")) {
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

    private void drawGroundTurn(KPair k) {
    }

    private void drawGroundSlideIn(KPair k) {
    }

    private void drawKPairTurn(KPairTurn k) throws Exception {
        if (k.getC1().getSegment().getName().equals("Ground")
                || k.getC2().getSegment().getName().equals("Ground")) {
            // triangle
            drawGroundTurn(k);
        }
        drawCircleForKPair(k);  // circle  
    }

    private void drawKPairSlide(KPairSlide k) {
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

    private void drawLinePol(KPairSlide k, TFTurn angle) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void drawRectPol(KPairSlide k, TFTurn angle, double A, double B) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    private void drawGroundSlideOut(KPairSlide k) {
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    public abstract void drawLine(double x1, double y1, double x2, double y2);

    public abstract void drawCircle(double x, double y, double radius);

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
