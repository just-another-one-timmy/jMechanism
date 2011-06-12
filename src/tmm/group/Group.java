/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import sun.security.util.PropertyExpander.ExpandException;
import tmm.connector.*;
import tmm.segment.*;

/**
 *
 * @author jtimv
 */
//TODO: реализовал методы, но не все. Дописать.
public abstract class Group {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void calcTF0Segment(Segment s, ConnectorTurn c) throws Exception {
        if (s.getTFTurn().getPhi().isCalculated(0)) {
            throw new Exception("calcTF0Segment: s.getTFTurn().getPhi().isCalculated(0)");
        }
        double x = c.getLinear().getX().getValue(0),
                y = c.getLinear().getY().getValue(0),
                ro = c.getRo(),
                phi0 = s.getTFTurn().getPhi().getValue(0),
                phi = c.getPhi();

        if (!s.getCPolus().getLinear().getX().isCalculated(0)) {
            s.getCPolus().getLinear().getX().setValue(x - ro * Math.cos(phi0 + phi), 0);
        }
        if (!s.getCPolus().getLinear().getY().isCalculated(0)) {
            s.getCPolus().getLinear().getY().setValue(y - ro * Math.cos(phi0 + phi), 0);
        }

        double pol_x = s.getCPolus().getLinear().getX().getValue(0),
                pol_y = s.getCPolus().getLinear().getY().getValue(0);

        for (Connector ci : s.getConnectors()) {
            switch (ci.getType()) {
                case CONNECTOR_TYPE_SLIDE: {
                    ConnectorSlide cs = (ConnectorSlide) ci;
                    double roc = cs.getRo(),
                            phic = cs.getPhi();
                    if (!cs.getLinear0().getX().isCalculated(0)) {
                        cs.getLinear0().getX().setValue(pol_x + roc * Math.cos(phi0 + phic), 0);
                    }

                    if (!cs.getLinear0().getY().isCalculated(0)) {
                        cs.getLinear0().getY().setValue(pol_y + roc * Math.sin(phi0 + phic), 0);
                    }

                    if (cs.getTurn() == null) {
                        throw new Exception("CalcTF0Segment: ConnectorSliding doesn't have TurnTF");
                    }

                    double phi0c = s.getTFTurn().getPhi().getValue(0);
                    cs.getTurn().getPhi().setValue(phi0c + cs.getAlpha(), 0);

                    break;
                }
                case CONNECTOR_TYPE_TURN: {
                    ConnectorTurn ct = (ConnectorTurn) ci;
                    if (ct.getLinear() == null) {
                        throw new Exception("CalcTF0Segment: ConnectorTurn [" + ct.getName() + "] doesn't have LinearTF!");
                    }
                    double roc = ct.getRo();
                    double phic = c.getPhi();

                    if (!ct.getLinear().getX().isCalculated(0)) {
                        ct.getLinear().getX().setValue(pol_x + roc * Math.cos(phi0 + phic), 0);
                    }
                    if (!ct.getLinear().getY().isCalculated(0)) {
                        ct.getLinear().getY().setValue(pol_y + roc * Math.sin(phi0 + phic), 0);
                    }
                    break;
                }
            }
        }
    }

    public void calcTF1Segment(Segment s, ConnectorTurn c) {
        //TODO: Group.calcTF1Segment
    }

    public void calcTF2Segment(Segment s, ConnectorTurn c) {
        //TODO: Group.calcTF2Segment
    }

    public double getDist(ConnectorTurn c1, ConnectorTurn c2) {
        return Math.sqrt(Math.pow(c1.getRo(), 2) + Math.pow(c2.getRo(), 2) - 2.0 * c1.getRo() * c2.getRo() * Math.cos(c1.getPhi() - c2.getPhi()));
    }

    public double getHeight(ConnectorTurn c1, ConnectorSlide c2) {
        double res = point2LineDistanceP(c1.getRo(), c1.getPhi(), c2.getRo(), c2.getPhi(), c2.getAlpha());
        return res;
    }

    public double getAngleSS(ConnectorSlide c1, ConnectorSlide c2) {
        return c1.getAlpha() - c2.getAlpha();
    }

    public double getAngleTT(ConnectorTurn c1, ConnectorTurn c2) {
        double x1 = c1.getRo() * Math.cos(c1.getPhi());
        double y1 = c1.getRo() * Math.sin(c1.getPhi());
        double x2 = c2.getRo() * Math.cos(c2.getPhi());
        double y2 = c2.getRo() * Math.sin(c2.getPhi());

        return Math.atan2(y2 - y1, x2 - x1);
    }

    public double point2LineDistance(double x1, double y1, double A, double B, double C) {
        double res;
        double num = Math.abs(A * x1 + B * y1 + C);
        double denum = Math.sqrt(A * A + B * B);
        res = num / denum;
        if (C < 0) {
            res = -res;
        }
        return res;
    }

    double point2LineDistanceP(double ro1, double phi1, double ro2, double phi2, double alpha) {
        double x1 = ro1 * Math.cos(phi1);
        double y1 = ro1 * Math.sin(phi1);
        double x2 = ro2 * Math.cos(phi2);
        double y2 = ro2 * Math.sin(phi2);

        double A = Math.tan(alpha);
        double B = -1;
        double C = y2 - x2 * Math.tan(alpha);

        double res = point2LineDistance(x1, y1, A, B, C);
        return res;
    }

    public abstract void calcTF0() throws Exception;

    public abstract void calcTF1();

    public abstract void calcTF2();

    public abstract void calcReaction();

    public abstract GroupType getType();

    public abstract int getGroupClass();

    public final void calcTF() throws Exception {
        calcTF0();
        calcTF1();
        calcTF2();
    }
}
