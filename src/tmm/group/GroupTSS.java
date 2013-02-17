package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

public class GroupTSS extends Group2 {

    private double h, alpha, tgN, tgM, tgN_, tgM_, tgN__, tgM__, roK, phiK,
            xK, yK, xK_, yK_, xK__, yK__;
    private ConnectorTurn s1cA;
    private ConnectorSlide s1cB, s2cB, s2cC, s0cC;
    private TFLinear k;
    private KPairTurn A;
    private KPairSlide B, C;

    @Override
    public void calcTF0() throws Exception {
        if (!s1cA.getLinear().getX().isCalculatedTF0()) {
            throw new Exception("GroupTSS: s1cA.getLinear().getX().isCalculatedTF0() == false");
        }
        if (!s1cA.getLinear().getY().isCalculatedTF0()) {
            throw new Exception("GroupTSS: s1cA.getLinear().getY().isCalculatedTF0() == false");
        }
        if (!s0cC.getTurn().getPhi().isCalculatedTF0()) {
            throw new Exception("GroupTSS: s0cC.getTurn().getPhi().isCalculatedTF0() == false");
        }
        if (!s0cC.getLinear0().getX().isCalculatedTF0()) {
            throw new Exception("GroupTSS: s0cC.getLinear0().getX().isCalculatedTF0() == false");
        }
        if (!s0cC.getLinear0().getY().isCalculatedTF0()) {
            throw new Exception("GroupTSS: s0cC.getLinear0().getY().isCalculatedTF0() == false");
        }

        double xA = s1cA.getLinear().getX().getTF0();
        double yA = s1cA.getLinear().getY().getTF0();
        double phiN = s0cC.getTurn().getPhi().getTF0();
        double xN = s0cC.getLinear0().getX().getTF0();
        double yN = s0cC.getLinear0().getY().getTF0();
        double phi2 = phiN - s2cC.getAlpha();

        s2.getTFTurn().getPhi().setTF0(phi2 - s2cC.getAlpha());
        s1.getTFTurn().getPhi().setTF0(phi2 + s2cB.getAlpha() - s1cB.getAlpha());
        calcTF0Segment(s1, s1cA);

        double phiM = s2cB.getTurn().getPhi().getTF0();
        double xM = s2cB.getLinear0().getX().getTF0();
        double yM = s2cB.getLinear0().getY().getTF0();

        if ((Math.abs(Math.cos(phiN)) < epsilon)
                && (Math.abs(Math.cos(phiM)) < epsilon)) {
            // both are vertical
            throw new Exception("GroupTSS: both external axises are vertical and parallel");
        }
        if (Math.abs(Math.cos(phiN)) > epsilon) {
            // tan != inf (not vertical)
            tgN = Math.tan(phiN);
        }
        if (Math.abs(Math.cos(phiM)) > epsilon) {
            // tan != inf (not vertical)
            tgM = Math.tan(phiM);
        }
        if (Math.abs(Math.cos(phiN)) < epsilon) {
            // tan == inf (vertical)
            // I don't know formula for this case
            xK = xN;
            throw new Exception("GroupTSS: CalcTF0() I don't know formula for this case");
        } else if (Math.abs(Math.cos(phiM)) < epsilon) {
            // tan == inf (vertical)
            xK = xA + h;
            yK = yN + (xK - xN) * tgN;
        } else if (Math.abs(tgM - tgN) > epsilon) {
            xK = (xM * tgM - xN * tgN
                    + yN - yM)
                    / (tgM - tgN);
            yK = (yN + xK * tgN - xN * tgN);
        } else {
            // axises are parallel 
            throw new Exception("GroupTSS: external axises are parallel");
        }

        s2.getCPolus().getLinear().getX().setTF0(xK - roK * Math.cos(phi2 + phiK));
        s2.getCPolus().getLinear().getY().setTF0(yK - roK * Math.sin(phi2 + phiK));

        calcTF0Segment(s2, s2.getCPolus());
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupTSS(KPairTurn K1, KPairSlide K2, KPairSlide K3, Segment s1, Segment s2) throws Exception {
        A = K1;
        B = K2;
        C = K3;
        this.s1 = s1;
        this.s2 = s2;

        ConnectorTurn ct1 = A.getC1(), ct2 = A.getC2();

        if (ct1.getSegment() == s1) {
            s1cA = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cA = ct2;
        } else {
            throw new Exception("GroupTSS: KPair A is not connected to s1");
        }

        ConnectorSlide cs1 = B.getC1(), cs2 = B.getC2();

        if (cs1.getSegment() == s1) {
            s1cB = cs1;
        } else if (cs2.getSegment() == s1) {
            s1cB = cs2;
        } else {
            throw new Exception("GroupTSS: KPair B is not connected to s1");
        }

        if (cs1.getSegment() == s2) {
            s2cB = cs1;
        } else if (cs2.getSegment() == s2) {
            s2cB = cs2;
        } else {
            throw new Exception("GroupTSS: KPair B is not connected to s2");
        }

        cs1 = C.getC1();
        cs2 = C.getC2();

        if (cs1.getSegment() == s2) {
            s2cC = cs1;
            s0cC = cs2;
        } else if (cs2.getSegment() == s2) {
            s2cC = cs2;
            s0cC = cs1;
        } else {
            throw new Exception("GroupTTS: KPair C is not connected to s2");
        }
        alpha = getAngleSS(s2cB, s2cC);
        h = getHeight(s1cA, s1cB);

        // TODO: WTF?!
        // вычисляем полярные координаты точки К
        // - пересечение двух SlidingConnectors
        roK = 0;
        phiK = 0;
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_TSS;
    }
}
