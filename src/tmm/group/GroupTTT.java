/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

/**
 *
 * @author jtimv
 */
public class GroupTTT extends Group2 {

    private KPairTurn A, B, C;
    private ConnectorTurn s1cA, s1cB, s2cB, s2cC;
    private double lab, lcb;
    private TFTurn AB = new TFTurn(), CB = new TFTurn();

    @Override
    public void calcTF0() throws Exception {
        double lac, b1, beta, alfa;

        if (!s1cA.getLinear().getX().isCalculated(0)) {
            throw new Exception("GroupTTT: s1cA.getLinear().x not calculated");
        }
        if (!s1cA.getLinear().getY().isCalculated(0)) {
            throw new Exception("GroupTTT: s1cA.getLinear().y not calculated");
        }
        if (!s2cC.getLinear().getX().isCalculated(0)) {
            throw new Exception("GroupTTT: s2cC.getLinear().x not calculated");
        }
        if (!s2cC.getLinear().getY().isCalculated(0)) {
            throw new Exception("GroupTTT: s2cC.getLinear().y not calculated");
        }

        double xA = A.getLinear().getX().getValue(0);
        double yA = A.getLinear().getY().getValue(0);
        double xC = C.getLinear().getX().getValue(0);
        double yC = C.getLinear().getY().getValue(0);

        lac = Math.sqrt(Math.pow(xA - xC, 2.0) + Math.pow(yA - yC, 2.0));
        b1 = (Math.pow(lab, 2.0) + Math.pow(lac, 2.0) - Math.pow(lcb, 2.0))
                / (2.0 * lab * lac);  //cos beta
        if (Math.abs(b1) <= 1.0) //  Cbopku HET
        {
            beta = Math.acos(b1); //beta=atan2(sqrt(1-b1*b1),b1);
        } else {
            throw new Exception("GroupTTT: fabs (cos( beta))  > 1");
        }

        // Cbopku HET
        alfa = Math.atan2(yC - yA, xC - xA);
        AB.getPhi().setValue(alfa + (double) j1 * beta, 0);
        CB.getPhi().setValue(Math.atan2(lab * Math.sin(AB.getPhi().getValue(0)) - yC + yA,
                lab * Math.cos(AB.getPhi().getValue(0)) - xC + xA), 0);

        s1.getTFTurn().getPhi().setValue(AB.getPhi().getValue(0) - getAngleTT(s1cA, s1cB), 0);
        s2.getTFTurn().getPhi().setValue(CB.getPhi().getValue(0) - getAngleTT(s2cC, s2cB), 0);

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
            throw new Exception("GroupTTT: KPair A not connected to s1");
        }

        ct1 = B.getC1();
        ct2 = B.getC2();
        if (ct1.getSegment() == s1) {
            s1cB = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cB = ct2;
        } else {
            throw new Exception("GroupTTT: KPair B not connected to s1");
        }

        if (ct1.getSegment() == s2) {
            s2cB = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cB = ct2;
        } else {
            throw new Exception("GroupTTT: KPair B not connected to s2");
        }

        ct1 = C.getC1();
        ct2 = C.getC2();

        if (ct1.getSegment() == s2) {
            s2cC = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cC = ct2;
        } else {
            throw new Exception("GroupTTT: KPair C not connected to s2");
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
