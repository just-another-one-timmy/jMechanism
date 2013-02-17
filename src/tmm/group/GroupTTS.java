package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

public class GroupTTS extends Group2 {

    private double lab, h;
    private TFTurn AB = new TFTurn(), NC = new TFTurn();
    private ConnectorTurn s1cA, s1cB, s2cB;
    private ConnectorSlide s2cC, s0cC;
    private TF yN0 = new TF(), tang = new TF();
    private double tg, tg_, tg__;
    private KPairTurn A, B;
    private KPairSlide C;

    @Override
    public void calcTF0() throws Exception {
        double b1, r1, r2, q0, p;


        if (!s0cC.getTurn().getPhi().isCalculatedTF0()) {
            throw new Exception("GroupTTS: s0cC.getTurn().getPhi().isCalculatedTF0() == false");
        }
        if (!s0cC.getLinear0().getX().isCalculatedTF0()) {
            throw new Exception("GroupTTS: s0cC.getLinear0().getX().isCalculatedTF0() == false");
        }
        if (!s0cC.getLinear0().getY().isCalculatedTF0()) {
            throw new Exception("GroupTTS: s0cC.getLinear0().getY().isCalculatedTF0() == false");
        }

        if (!s1cA.getLinear().getX().isCalculatedTF0()) {
            throw new Exception("GroupTTS: s1cA.getLinear().getX().isCalculatedTF0() == false");
        }
        if (!s1cA.getLinear().getY().isCalculatedTF0()) {
            throw new Exception("GroupTTS: s1cA.getLinear().getY().isCalculatedTF0() == false");
        }

        double phiN = s0cC.getTurn().getPhi().getTF0();
        double xN = s0cC.getLinear0().getX().getTF0();
        double yN = s0cC.getLinear0().getY().getTF0();

        double xA = A.getLinear().getX().getTF0();
        double yA = A.getLinear().getY().getTF0();

        double xB;
        double yB;
        
        if (Math.abs(Math.cos(phiN)) < epsilon) {
            // if axis is vertical
            xB = (xN - h);
            b1 = Math.pow(lab, 2.0) - Math.pow(xB - xA, 2.0);
            if (b1 < 0.0) {
                // impossible to build mechanism
                throw new Exception("GroupTTS: b1<0 ");
            }
            yB = (yA + (double) j1 * Math.sqrt(b1));
        } else {
            tg = Math.tan(phiN);
            double cosPhiN = Math.cos(phiN);
            double hOverCos = h/cosPhiN;
            yN0.setTF0(yN + hOverCos);
            r1 = lab - tg * xN + yN0.getTF0() - yA;
            r2 = lab + tg * xN - yN0.getTF0() + yA;

            p = 0.5 * (2.0 * xA - r1 * tg + r2 * tg);
            q0 = Math.pow(xA, 2.0) - r1 * r2;
            b1 = Math.pow(p, 2.0) - q0 / Math.pow(Math.cos(phiN), 2.0);

            if (b1 < 0.0) {
                // impossible to build mechanism
                throw new Exception("GroupTTS: b1<0 ");
            }

            xB = ((p + j1 * Math.sqrt(b1)) * Math.pow(Math.cos(phiN), 2.0));
            yB = (tg * (xB - xN) + yN0.getTF0());
        }
        B.getLinear().getX().setTF0(xB);
        B.getLinear().getY().setTF0(yB);

        tang.setTF0(tg);
        AB.getPhi().setTF0(Math.atan2(-yB + yA, -xB + xA));
        s1.getTFTurn().getPhi().setTF0(AB.getPhi().getTF0() - getAngleTT(s1cB, s1cA));
        s2.getTFTurn().getPhi().setTF0(phiN - s2cC.getAlpha());
        calcTF0Segment(s1, s1cA);
        calcTF0Segment(s2, s2cB);
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupTTS(KPairTurn K1, KPairTurn K2, KPairSlide K3, Segment s1, Segment s2) throws Exception {
        A = K1;
        B = K2;
        C = K3;
        this.s1 = s1;
        this.s2 = s2;

        ConnectorTurn ct1,ct2;
        ct1 = A.getC1();
        ct2 = A.getC2();
        if (ct1.getSegment() == s1) {
            s1cA = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cA = ct2;
        } else {
            throw new Exception("GroupTTS: KPair "+A.getName()+" is not connected to segment "+s1.getName());
        }

        ct1 = B.getC1();
        ct2 = B.getC2();
        if (ct1.getSegment() == s1) {
            s1cB = ct1;
        } else if (ct2.getSegment() == s1) {
            s1cB = ct2;
        } else {
            throw new Exception("GroupTTS: KPair "+B.getName()+" is not connected to segment "+s1.getName());
        }

        if (ct1.getSegment() == s2) {
            s2cB = ct1;
        } else if (ct2.getSegment() == s2) {
            s2cB = ct2;
        } else {
            throw new Exception("GroupTTS: KPair "+B.getName()+" is not connected to segment "+s2.getName());
        }

        ConnectorSlide cs1 = C.getC1(), cs2 = C.getC2();
        if (cs1.getSegment() == s2) {
            s2cC = cs1;
            s0cC = cs2;
        } else if (cs2.getSegment() == s2) {
            s2cC = cs2;
            s0cC = cs1;
        } else {
            throw new Exception("GroupTTS: KPair "+C.getName()+" is not connected to segment "+s2.getName());
        }

        lab = getDist(s1cA, s1cB);
        h = getHeight(s2cB, s2cC);
        // h=0;
        j1 = 1;
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_TTS;
    }
}
