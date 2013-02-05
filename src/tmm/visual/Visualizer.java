package tmm.visual;

import java.util.logging.Level;
import java.util.logging.Logger;
import tmm.compmanager.CompManager;
import tmm.connector.Connector;
import tmm.connector.ConnectorSlide;
import tmm.connector.ConnectorTurn;
import tmm.kpair.KPair;
import tmm.kpair.KPairSlide;
import tmm.kpair.KPairTurn;
import tmm.segment.Segment;
import tmm.tf.TFTurn;

public abstract class Visualizer {

    protected boolean debugMode;
    protected CompManager cm;
    double scaleX = 1.0, scaleY, translateX = 0, translateY = 0;
    double h;
    private static final Logger logger = Logger.getLogger(Visualizer.class.getName());
    //const R=3;a=10;b=20;l=40;
    private static double R = 3, A = 10, B = 20, L = 40;
    boolean useNewCode = false;

    public void setUseNewCode(boolean x) {
        useNewCode = x;
    }

    public Visualizer(CompManager c) {
        cm = c;
    }

    private void drawConnectorTurn(ConnectorTurn ct, double pol_x, double pol_y, String sname, boolean drawText, String text) throws Exception {
        if (!ct.getLinear().getX().isCalculated(0)) {
            //    System.out.println("X not calcul " + ct.getName());
        }
        if (!ct.getLinear().getY().isCalculated(0)) {
            //     System.out.println("Y not calcul " + ct.getName());
        }
        double x = ct.getLinear().getX().getValue(0);
        double y = ct.getLinear().getY().getValue(0);
        //System.out.println("Drawing " + ct.getName() + " at: " + x + "  " + y);
        drawCircle(x, h - y, 5, 0xFFBB22, 2, 0xFFBB22);
        if (!sname.equals("Ground")) {
            drawLine(pol_x, h - pol_y, x, h - y, 2, 0xFFFFFF);
        }
        if (drawText) {
            drawText(x, h - y, text);
        }
    }

    private void drawConnectorSlide(ConnectorSlide cs) throws Exception {
        double x = cs.getLinear0().getX().getValue(0);
        double y = cs.getLinear0().getY().getValue(0);

        logger.log(Level.FINE, "Drawing {0} at: {1}  {2}", new Object[]{cs.getName(), x, y});

        double phi = cs.getTurn().getPhi().getValue(0);
        double len = .3;
        double delta_x = len * Math.cos(phi);
        double delta_y = len * Math.sin(phi);
        drawLine(x - delta_x, h - (y - delta_y), x + delta_x, h - (y + delta_y), 1, 0x00FF00);

        double rh = .1, rw = .2;
        double x1 = x - rw / 2, x2 = x + rw / 2;
        double y1 = y + rh / 2, y2 = y - rh / 2;
        //sf::Shape rect = sf::Shape::Rectangle(-rw/2,-rh/2,+rw/2,+rh/2,sf::Color::Blue, 2, sf::Color::Blue);
        //rect.Rotate(phi*180/pi);
        //rect.SetPosition(x, h-y);
        //rect.EnableFill(false);
        //w->Draw(rect);
        //drawRect(x1, h - y, x2, y2);

        /*
        drawSquare(x, h - y, 5, 0x00FF00);
        
        if (!sname.equals("Ground")) {
        drawLine(pol_x, h - pol_y, x, h - y, 1, 0x00BBFF);
        }
        if (drawText) {
        drawText(x, h - y, text);
        }
         */
    }

    private void drawConnector(Connector c, double pol_x, double pol_y,
            String sname, boolean drawText, String text) throws Exception {
        switch (c.getType()) {
            case CONNECTOR_TYPE_TURN: {
                drawConnectorTurn((ConnectorTurn) c, pol_x, pol_y, sname,
                        drawText, text);
                break;
            }
            case CONNECTOR_TYPE_SLIDE: {
                drawConnectorSlide((ConnectorSlide) c);
                break;
            }
        }
    }

    private void drawKPair(KPair k) throws Exception {
        Connector c = k.getC1();
        Segment s = c.getSegment();
        double pol_x = s.getCPolus().getLinear().getX().getValue(0);
        double pol_y = s.getCPolus().getLinear().getY().getValue(0);
        drawConnector(c, pol_x, pol_y, s.getName(), true, k.getName());
    }

    public void draw() throws Exception {
        if (useNewCode) {
            drawNew();
            return;
        }
        if (cm.isBusy()) {
            return;
        }
        for (Segment s : cm.getSegments()) {
            double pol_x = s.getCPolus().getLinear().getX().getValue(0);
            double pol_y = s.getCPolus().getLinear().getY().getValue(0);

            for (Connector c : s.getConnectors()) {
                try {
                    drawConnector(c, pol_x, pol_y, s.getName(), false, "");
                } catch (Exception e) {
                    logger.log(Level.SEVERE,
                            "Visualizer: problems with connector {0} : {1}",
                            new String[]{c.getName(), e.getMessage()});
                }
            }
        }
        for (KPair k : cm.getKPairs()) {
            drawKPair(k);
        }
    }

    public void drawNew() throws Exception {
        if (!cm.isBusy()) {
            drawSegments();
            drawKPairs();
        }
    }

    /* for(segments)
     * if (segment!=ground)
     *      line(CMass,polus);
     */
    void drawSegments() throws Exception {
        for (Segment s : cm.getSegments()) {
            // Hm..? Doesn't look good for me. :(
            if (!s.getName().equals("Ground")) {
                if (s.getCMass() != null) {
                    drawLineBetweenConnectors(s.getCMass(), s.getCPolus());
                }
            }
        }
    }

    /*
     * for (kpairs)
     * {
     * if (kpair.c1.segment != ground)
     *      line(kpair,kpair.c1.segment.polus);
     * if (kpair.c2.segment != ground)
    line(kpair,kpair.c2.segment.polus);
     * switch(kpair.type)
     *  {
     *      case TURN :
     *          if ((kpair.c1.segment == ground)
     *          ||   kpair.c2.segment == ground))
     *          drawGroundTurn(kpair);  // 
     *          circle(kpair,R);  // кружок
     *           
     *      case SLIDE:
     *      alfa = kpair.getAngle();
     *      if (kpair.c1.segment == ground)
     *              drawGroundSlideIn(kpair);    
     *        else 
     *              line_pol(kpair,alfa,l); // худая палка
     *
     *          if (kpair.c2.segment == ground)
     *              drawGroundSlideOut(kpair);   
     *         esle 
     *              rect_pol(kpair,alfa,a,b);    // толстая палка
     * 
     *      }
     * }
     */
    void drawKPairs() throws Exception {
        for (KPair k : cm.getKPairs()) {
            // Temporary to get started.
            // drawKPair(k);
            if (!k.getC1().getSegment().getName().equals("Ground")) {
                //
            }
            if (!k.getC2().getSegment().getName().equals("Ground")) {
                //
            }
            switch (k.getType()) {
                case KPAIR_TYPE_TURN: {
                    if (k.getC1().getSegment().getName().equals("Ground")
                            || k.getC2().getSegment().getName().equals("Ground")) {
                        // Draw triangle.
                        drawGroundTurn((KPairTurn) k);
                    }
                    circleForKPair((KPairTurn) k, R);
                    break;
                }
                case KPAIR_TYPE_SLIDE: {
                    Connector c = k.getC1();
                    drawConnectorSlide((ConnectorSlide) c);
                    if (k.getC1().getSegment().getName().equals("Ground")) {
                        drawGroundSlideIn((KPairSlide) k);
                    } else {
                        linePol(k, ((KPairSlide) k).getAngle(), L);
                    }
                    if (k.getC2().getSegment().getName().equals("Ground")) {
                        drawGroundSlideIn((KPairSlide) k);
                    } else {
                        rectPol((KPairSlide) k, ((KPairSlide) k).getAngle(), A, B);
                    }
                    break;
                }
            }
        }
    }

    public final void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    public final boolean getDebugMode() {
        return debugMode;
    }

    public final void setCompManager(CompManager c) {
        cm = c;
    }

    public final void setScalesAndTranslations(double scaleX, double scaleY,
            double translateX, double translateY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    void drawLinePol(double ro1, double phi1, double ro2, double phi2) {
        //
    }

    void drawLinePolAngle(double ro, double phi, double alpha, double l) {
        //
    }

    /**
     * Draws triangle.
     */
    void drawGroundTurn(KPairTurn k) throws Exception {
        // Here should be a triangle with equal sides.
        // Let me do the math later.
        double x = k.getC1().getLinear().getX().getValue(0);
        double y = k.getC1().getLinear().getY().getValue(0);
        drawLine(x - .05, y, x + .05, y, 1, 0x00FF00);
        drawLine(x - .05, y, x, y - .05, 1, 0x00FF00);
        drawLine(x + .05, y, x, y - .05, 1, 0x00FF00);
    }

    /**
     * Draws circle.
     * @param k KPair for which circle should be drawn.
     * @param R radius.
     */
    void circleForKPair(KPairTurn k, double R) throws Exception {
        double x = k.getC1().getLinear().getX().getValue(0);
        double y = k.getC1().getLinear().getY().getValue(0);
        drawCircle(x, h - y, 5, 0xFFBB22, 2, 0xFFBB22);
    }

    void drawLineBetweenConnectors(Connector c1, Connector c2) throws Exception {
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        switch (c1.getType()) {
            case CONNECTOR_TYPE_SLIDE: {
                x1 = ((ConnectorSlide) c1).getLinear0().getX().getValue(0);
                y1 = ((ConnectorSlide) c1).getLinear0().getY().getValue(0);
                break;
            }
            case CONNECTOR_TYPE_TURN: {
                x1 = ((ConnectorTurn) c1).getLinear().getX().getValue(0);
                y1 = ((ConnectorTurn) c1).getLinear().getY().getValue(0);
                break;
            }
        }
        switch (c2.getType()) {
            case CONNECTOR_TYPE_SLIDE: {
                x2 = ((ConnectorSlide) c2).getLinear0().getX().getValue(0);
                y2 = ((ConnectorSlide) c2).getLinear0().getY().getValue(0);
                break;
            }
            case CONNECTOR_TYPE_TURN: {
                x2 = ((ConnectorTurn) c2).getLinear().getX().getValue(0);
                y2 = ((ConnectorTurn) c2).getLinear().getY().getValue(0);
                break;
            }
        }
        drawLine(x1, y1, x2, y2, 2, 0x00FF00);
    }

    void drawGroundSlideIn(KPairSlide k) throws Exception {
        double x = k.getC1().getLinear0().getX().getValue(0);
        double y = k.getC1().getLinear0().getX().getValue(1);
        drawSquare(x, h - y, 5, 0xAAFFCC);
    }

    protected abstract void drawCircle(double x, double y, int radius,
            int color, int outline, int outlineColor);

    protected abstract void drawLine(double x1, double y1, double x2,
            double y2, int thickness, int color);

    protected abstract void drawText(double x, double y, String text);

    protected abstract void drawSquare(double x, double y, int side, int color);

    private void linePol(KPair k, TFTurn angle, double L) {
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void rectPol(KPairSlide k, TFTurn angle, double A, double B) throws Exception {
        // This should also rotate the rectangle, but for now it doesn't.
        double x = k.getC1().getLinear0().getX().getValue(0);
        double y = k.getC1().getLinear0().getX().getValue(1);
        drawSquare(x, h - y, 5, 0x00FF00);
    }
}

/*
 * 
 * 
 * line_pol рисует палку наклоненную на угол alfa, и проходящую через точку с координатами 
 * kpair
circle and rect закрашивают область внутри себя.
 */

/*  todo
 * еще на будущее надо предусмотреть возможность прорисовать 
 * одну группу или одно звено, или одну скорость/ускорение/силу . т.е. возможно нужно создать какие-то множества элементов
 * типа Visible. это для генератора отчетов. 
 * 
 */

/* drawGroundTurn(kpair)
 * {
 * 
 *   o
 *  /_\
 * ////
 * }

 *drawGroundSlideIn(kpair);
 * {
 * ________________
 * ////
 * }
drawGroundSlideOut(kpair);
 *{
 * ////////
 * --------
 * --------
 * ////////
 *}
 */
