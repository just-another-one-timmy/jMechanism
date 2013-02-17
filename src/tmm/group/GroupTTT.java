package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

public class GroupTTT extends Group2 {

    private KPairTurn A, B, C;
    private ConnectorTurn s1cA, s1cB, s2cB, s2cC;
    private double lab, lcb;
    private TFTurn AB = new TFTurn(), CB = new TFTurn();

    @Override
    public void calcTF0() throws Exception {
        double lac, b1, beta, alfa;

        if (!s1cA.getLinear().getX().isTF0Calculated()) {
            throw new Exception("GroupTTT: s1cA.getLinear().getX().isCalculatedTF0() == false");
        }
        if (!s1cA.getLinear().getY().isTF0Calculated()) {
            throw new Exception("GroupTTT: s1cA.getLinear().getY().isCalculatedTF0() == false");
        }
        if (!s2cC.getLinear().getX().isTF0Calculated()) {
            throw new Exception("GroupTTT: s2cC.getLinear().getX().isCalculatedTF0() == false");
        }
        if (!s2cC.getLinear().getY().isTF0Calculated()) {
            throw new Exception("GroupTTT: s2cC.getLinear().getY().isCalculatedTF0() == false");
        }

        double xA = A.getLinear().getX().getTF0();
        double yA = A.getLinear().getY().getTF0();
        double xC = C.getLinear().getX().getTF0();
        double yC = C.getLinear().getY().getTF0();

        lac = Math.sqrt(Math.pow(xA - xC, 2.0) + Math.pow(yA - yC, 2.0));
        // cos beta
        b1 = (Math.pow(lab, 2.0) + Math.pow(lac, 2.0) - Math.pow(lcb, 2.0))
                / (2.0 * lab * lac);
        if (Math.abs(b1) <= 1.0)
        {
            // beta=atan2(sqrt(1-b1*b1), b1)
            beta = Math.acos(b1);
        } else {
            // impossible to build mechanism
            throw new Exception("GroupTTT: fabs (cos( beta))  > 1");
        }

        alfa = Math.atan2(yC - yA, xC - xA);
        AB.getPhi().setTF0(alfa + (double) j1 * beta);
        CB.getPhi().setTF0(Math.atan2(lab * Math.sin(AB.getPhi().getTF0()) - yC + yA,
                lab * Math.cos(AB.getPhi().getTF0()) - xC + xA));

        s1.getTFTurn().getPhi().setTF0(AB.getPhi().getTF0() - getAngleTT(s1cA, s1cB));
        s2.getTFTurn().getPhi().setTF0(CB.getPhi().getTF0() - getAngleTT(s2cC, s2cB));

        calcTF0Segment(s1, s1cA);
        calcTF0Segment(s2, s2cC);
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupTTT(KPairTurn K1, KPairTurn K2, KPairTurn K3, Segment s1, Segment s2) throws Exception {
        A = K1;
        B = K2;
        C = K3;
        this.s1 = s1;
        this.s2 = s2;
        j1 = 1;

        ConnectorTurn ct1 = A.getC1(), ct2 = A.getC2();
        if (ct1.getSegment() == s1) {
            s1cA = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cA = ct2;
        } else {
            throw new Exception("GroupTTT: KPair A is not connected to s1");
        }

        ct1 = B.getC1();
        ct2 = B.getC2();
        if (ct1.getSegment() == s1) {
            s1cB = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cB = ct2;
        } else {
            throw new Exception("GroupTTT: KPair B is not connected to s1");
        }

        if (ct1.getSegment() == s2) {
            s2cB = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cB = ct2;
        } else {
            throw new Exception("GroupTTT: KPair B is not connected to s2");
        }

        ct1 = C.getC1();
        ct2 = C.getC2();

        if (ct1.getSegment() == s2) {
            s2cC = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cC = ct2;
        } else {
            throw new Exception("GroupTTT: KPair C is not connected to s2");
        }

        lab = getDist(s1cA, s1cB);
        lcb = getDist(s2cC, s2cB);
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_TTT;
    }
}
