package tmm.group;

import tmm.connector.*;
import tmm.kpair.*;
import tmm.segment.*;

public class GroupT extends Group1 {

    private ConnectorTurn s0c0, s1c0;
    private KPairTurn O;
    
    @Override
    public void calcTF0() throws Exception {
        s0.getTFTurn().getPhi().setTF0(0.0);
        s0.getCPolus().getLinear().getX().setTF0(0.0);
        s0.getCPolus().getLinear().getY().setTF0(0.0);
        calcTF0Segment(s0, s0.getCPolus());
        
        s1.getTFTurn().getPhi().setTF0(GC);
        calcTF0Segment(s1, s1c0);
    }

    @Override
    public void calcTF1() {
    }

    @Override
    public void calcTF2() {
    }

    public GroupT(KPairTurn K, Segment s0, Segment s1) {
        O = K;
        this.s0 = s0;
        this.s1 = s1;
        ConnectorTurn c1 = O.getC1(),
                      c2 = O.getC2();
        if (c1.getSegment() == s1){
            s0c0 = c2;
            s1c0 = c1;
        }
        if (c2.getSegment() == s1){
            s0c0 = c1;
            s1c0 = c2;
        }
    }

    @Override
    public void calcReaction() {
    }

    @Override
    public GroupType getType() {
        return GroupType.GROUP_TYPE_T;
    }
}
