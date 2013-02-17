package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;

public class GroupSTS extends Group2 {

    private double h1, h2;
    private ConnectorSlide s1cA, s0cA, s2cC, s0cC;
    private ConnectorTurn s1cB, s2cB;
    private KPairSlide A, C;
    private KPairTurn B;
    double tgN, tgM,
            tgN_, tgM_,
            tgN__, tgM__;

    @Override
    public void calcTF0() throws Exception {
        if (!s0cA.getTurn().getPhi().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cA.getTurn().getPhi().isCalculatedTF0() == false");
        }
        if (!s0cC.getTurn().getPhi().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cC.getTurn().getPhi().isCalculatedTF0() == false");
        }

        if (!s0cA.getLinear0().getX().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cA.getLinear0().getX().isCalculatedTF0() == false");
        }
        if (!s0cA.getLinear0().getY().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cA.getLinear0().getY().isCalcualted(0) == false");
        }


        if (!s0cC.getLinear0().getX().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cC.getLinear0().getX().isCalculatedTF0() == false");
        }
        if (!s0cC.getLinear0().getY().isTF0Calculated()) {
            throw new Exception("GroupSTS: s0cC.getLinear0().getY().isCalculatedTF0() == false");
        }

        double phiN = s0cA.getTurn().getPhi().getTF0();
        double phiM = s0cC.getTurn().getPhi().getTF0();
        double xN = s0cA.getLinear0().getX().getTF0();
        double yN = s0cA.getLinear0().getY().getTF0();
        double xM = s0cC.getLinear0().getX().getTF0();
        double yM = s0cC.getLinear0().getY().getTF0();
        double xB = 0, yB = 0;

        if ((Math.abs(Math.cos(phiN)) < epsilon)
                && (Math.abs(Math.cos(phiM)) < epsilon)) {
            // both are vertical
            throw new Exception("GroupSTS: both external axises are vertical and parallel");
        }

        if (Math.abs(Math.cos(phiN)) > epsilon) {
            // tan != inf  not vertical
            tgN = Math.tan(phiN);
        }
        if (Math.abs(Math.cos(phiM)) > epsilon) {
            // tan != inf  not vertical
            tgM = Math.tan(phiM);
        }

        if (Math.abs(Math.cos(phiN)) < epsilon) {
            // tan == inf  vertical
            xB = xN - h1;
            yB = h2 / Math.cos(phiM) + tgM * xB
                    + yM - tgM * xM;
        } else if (Math.abs(Math.cos(phiM)) < epsilon) {
            // tan == inf  vertical
            xB = xM - h2;
            yB = h1 / Math.cos(phiN) + tgN * xB
                    + yN - tgN * xN;
        } else if (Math.abs(tgM - tgN) > epsilon) {

            xB = (h1 / Math.cos(phiN) - h2 / Math.cos(phiM)
                    - tgN * xN + tgM * xM
                    + yN - yM)
                    / (tgM - tgN);

            yB = tgN * xB - tgN * xN + yN + h1 / Math.cos(phiN);
        } else {
            // both are parallel
            throw new Exception("GroupSTS: external axises are parallel");
        }

        B.getLinear().getX().setTF0(xB);
        B.getLinear().getY().setTF0(yB);
        s1.getTFTurn().getPhi().setTF0(phiN - s1cA.getAlpha());
        s2.getTFTurn().getPhi().setTF0(phiM - s2cC.getAlpha());
        calcTF0Segment(s1, s1cB);
        calcTF0Segment(s2, s2cB);
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupSTS(KPairSlide K1, KPairTurn K2, KPairSlide K3, Segment s1, Segment s2) throws Exception {
        A = K1;
        B = K2;
        C = K3;
        this.s1 = s1;
        this.s2 = s2;

        ConnectorSlide cs1 = A.getC1(), cs2 = A.getC2();
        if (cs1.getSegment() == s1) {
            s1cA = cs1;
            s0cA = cs2;
        } else if (cs2.getSegment() == s1) {
            s1cA = cs2;
            s0cA = cs1;
        } else {
            throw new Exception("GroupSTS: KPair A is not connected to s1");
        }

        ConnectorTurn ct1 = B.getC1(), ct2 = B.getC2();
        if (ct1.getSegment() == s1) {
            s1cB = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cB = ct2;
        } else {
            throw new Exception("GroupSTS: KPair B is not connected to s1");
        }

        if (ct1.getSegment() == s2) {
            s2cB = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cB = ct2;
        } else {
            throw new Exception("GroupSTS: KPair B is not connected to s2");
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
            throw new Exception("GroupSTS: KPair C is not connected to s2");
        }

        h1 = getHeight(s1cB, s1cA);
        h2 = getHeight(s2cB, s2cC);
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_STS;
    }
}
