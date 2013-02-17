package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;

public class GroupS extends Group1 {

    private ConnectorSlide s0c0, s1c0;
    private KPairSlide O;

    @Override
    public void calcTF0() throws Exception {
        s0.getTFTurn().getPhi().setTF0(0.0);
        s0.getCPolus().getLinear().getX().setTF0(0.0);
        s0.getCPolus().getLinear().getY().setTF0(0.0);

        calcTF0Segment(s0, s0.getCPolus());

        double phi0 = s0c0.getTurn().getPhi().getTF0();
        s1.getTFTurn().getPhi().setTF0(phi0);
        s1c0.getLinear0().getX().setTF0(s0c0.getLinear0().getX().getTF0() + GC * Math.cos(s0c0.getTurn().getPhi().getTF0()));
        s1c0.getLinear0().getY().setTF0(s0c0.getLinear0().getY().getTF0() + GC * Math.sin(s0c0.getTurn().getPhi().getTF0()));
        s1.getCPolus().getLinear().getX().setTF0(s1c0.getLinear0().getX().getTF0()
                - s1c0.getRo() * Math.cos(phi0 + s1c0.getPhi()));
        s1.getCPolus().getLinear().getY().setTF0(s1c0.getLinear0().getY().getTF0()
                - s1c0.getRo() * Math.sin(phi0 + s1c0.getPhi()));

        calcTF0Segment(s1, s1.getCPolus());
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupS(KPairSlide K, Segment s0, Segment s1) {
        O = K;
        this.s0 = s0;
        this.s1 = s1;
        ConnectorSlide c1 = O.getC1(),
                c2 = O.getC2();
        if (c1.getSegment() == s1) {
            s0c0 = c2;
            s1c0 = c1;
        }
        if (c2.getSegment() == s1) {
            s0c0 = c1;
            s1c0 = c2;
        }
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_S;
    }
}
