/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import java.util.logging.Level;
import java.util.logging.Logger;
import tmm.compmanager.*;
import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;

/**
 *
 * @author jtimv
 */
public abstract class Visualizer {

    protected boolean debugMode;
    protected CompManager cm;
    double scaleX = 1.0, scaleY, translateX = 0, translateY = 0;
    double h;
    private static final Logger logger = Logger.getLogger(Visualizer.class.getName());    

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

    private void drawConnectorSlide(ConnectorSlide cs, double pol_x, double pol_y, String sname, boolean drawText, String text) throws Exception {
        double x = cs.getLinear0().getX().getValue(0);
        double y = cs.getLinear0().getY().getValue(0);

        logger.fine("Drawing " + cs.getName() + " at: " + x + "  " + y);
        
        double phi = cs.getTurn().getPhi().getValue(0);
        double len = 10;
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

        drawSquare(x, h - y, 5, 0x00FF00);
        
        if (!sname.equals("Ground")) {
            drawLine(pol_x, h - pol_y, x, h - y, 1, 0x00BBFF);
        }
        if (drawText) {
            drawText(x, h - y, text);
        }
    }

    private void drawConnector(Connector c, double pol_x, double pol_y, String sname, boolean drawText, String text) throws Exception {
        switch (c.getType()) {
            case CONNECTOR_TYPE_TURN: {
                drawConnectorTurn((ConnectorTurn) c, pol_x, pol_y, sname, drawText, text);
                break;
            }
            case CONNECTOR_TYPE_SLIDE: {
                drawConnectorSlide((ConnectorSlide) c, pol_x, pol_y, sname, drawText, text);
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
                    logger.log(Level.SEVERE, "Visualizer: problems with connector {0} : {1}", new String[]{c.getName(), e.getMessage()});
                }
            }
        }
        for (KPair k : cm.getKPairs()) {
            drawKPair(k);
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

    public final void setScalesAndTranslations(double scaleX, double scaleY, double translateX, double translateY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    protected abstract void drawCircle(double x, double y, int radius, int color, int outline, int outlineColor);

    protected abstract void drawLine(double x1, double y1, double x2, double y2, int thickness, int color);

    protected abstract void drawText(double x, double y, String text);

    protected abstract void drawSquare(double x, double y, int side, int color);
}
