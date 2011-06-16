/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import tmm.compmanager.*;
import tmm.connector.*;
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

    public Visualizer(CompManager c) {
        cm = c;
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
                    switch (c.getType()) {
                        case CONNECTOR_TYPE_TURN: {
                            ConnectorTurn ct = (ConnectorTurn) c;
                            if (!ct.getLinear().getX().isCalculated(0)) {
                                //    System.out.println("X not calcul " + ct.getName());
                            }
                            if (!ct.getLinear().getY().isCalculated(0)) {
                                //     System.out.println("Y not calcul " + ct.getName());
                            }

                            double x = ct.getLinear().getX().getValue(0);
                            double y = ct.getLinear().getY().getValue(0);

                            System.out.println("Drawing " + ct.getName() + " at: " + x + "  " + y);

                            drawCircle(x, h - y, 5, 0xFFBB22, 2, 0xFFBB22);

                            if (!s.getName().equals("Ground")) {
                                drawLine(pol_x, h - pol_y, x, h - y, 2, 0xFFFFFF);
                            }

                            break;
                        }

                        case CONNECTOR_TYPE_SLIDE: {
                            ConnectorSlide cs = (ConnectorSlide) c;
                            double x = cs.getLinear0().getX().getValue(0);
                            double y = cs.getLinear0().getY().getValue(0);

                            double phi = cs.getTurn().getPhi().getValue(0);
                            double len = 50;
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
                            drawRect(x1, y1, x2, y2);

                            if (!s.getName().equals("Ground")) {
                                drawLine(pol_x, h - pol_y, x, h - y, 1, 0x00BBFF);
                            }

                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Visualizer: problems with connector " + c.getName());
                    System.out.println(e.getMessage());
                    e.printStackTrace();
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

    public final void setScalesAndTranslations(double scaleX, double scaleY, double translateX, double translateY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
    }

    protected abstract void drawCircle(double x, double y, int radius, int color, int outline, int outlineColor);

    protected abstract void drawLine(double x1, double y1, double x2, double y2, int thickness, int color);

    protected void drawRect(double x1, double y1, double x2, double y2) {
        //
    }
}
