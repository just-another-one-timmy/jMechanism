package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

public class GroupTST extends Group2 {

    private KPairTurn A, C;
    private KPairSlide B;
    private double h1, h2;
    private ConnectorTurn s1cA, s2cC;
    private ConnectorSlide s1cB, s2cB;
    private double lac, lac_, lac__, b, b_, b__;
    private TFTurn AC = new TFTurn(), beta=new TFTurn();

    @Override
    public void calcTF0() throws Exception {
        if (!s1cA.getLinear().getX().isCalculated(0)) {
            throw new Exception("GroupTST: s1cA.getLinear().getX().isCalculated(0) == false");
        }
        if (!s1cA.getLinear().getY().isCalculated(0)) {
            throw new Exception("GroupTST: s1cA.getLinear().getY().isCalculated(0) == false");
        }
        if (!s2cC.getLinear().getX().isCalculated(0)) {
            throw new Exception("GroupTST: s2cC.getLinear().getX().isCalculated(0) == false");
        }
        if (!s2cC.getLinear().getY().isCalculated(0)) {
            throw new Exception("GroupTST: s2cC.getLinear().getY().isCalculated(0) == false");
        }


        double sin_b;
        double xA = s1cA.getLinear().getX().getValue(0);
        double yA = s1cA.getLinear().getY().getValue(0);
        double xC = s2cC.getLinear().getX().getValue(0);
        double yC = s2cC.getLinear().getY().getValue(0);

        lac = Math.sqrt(Math.pow(xA - xC, 2.0) + Math.pow(yA - yC, 2));
        // sin beta
        sin_b = (h1 + h2) / lac;
        b = Math.asin(sin_b); 
        AC.getPhi().setValue(Math.atan2(yA - yC, xA - xC), 0);
        B.getAngle().getPhi().setValue(AC.getPhi().getValue(0) - b, 0);

        s1.getTFTurn().getPhi().setValue(B.getAngle().getPhi().getValue(0) - s1cB.getAlpha(), 0);
        s2.getTFTurn().getPhi().setValue(B.getAngle().getPhi().getValue(0) - s2cB.getAlpha(), 0);
        calcTF0Segment(s1, s1cA);
        calcTF0Segment(s2, s2cC);
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupTST(KPairTurn K1, KPairSlide K2, KPairTurn K3, Segment s1, Segment s2) throws Exception {
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
            throw new Exception("GroupTST: KPair A is not connected to s1");
        }

        ConnectorSlide cs1 = B.getC1(), cs2 = B.getC2();
        if (cs1.getSegment() == s1) {
            s1cB = cs1;
        } else if (cs2.getSegment() == s1) {
            s1cB = cs2;
        } else {
            throw new Exception("GroupTST: KPair B is not connected to s1");
        }

        if (cs1.getSegment() == s2) {
            s2cB = cs1;
        } else if (cs2.getSegment() == s2) {
            s2cB = cs2;
        } else {
            throw new Exception("GroupTST: KPair B is not connected to s2");
        }
        ct1 = C.getC1();
        ct2 = C.getC2();

        if (ct1.getSegment() == s2) {
            s2cC = ct1;

        } else if (ct2.getSegment() == s2) {
            s2cC = ct2;
        } else {
            throw new Exception("GroupTTS: KPair C is not connected to s2");
        }

        h1 = -getHeight(s1cA, s1cB);
        h2 = getHeight(s2cC, s2cB);
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_TST;
    }
}
