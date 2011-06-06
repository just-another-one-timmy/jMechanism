/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import tmm.kpair.KPair;
import tmm.kpair.KPairSlide;
import tmm.kpair.KPairTurn;
import tmm.kpair.KPairType;
import tmm.segment.Segment;

/**
 *
 * @author jtimv
 */
public class GroupBuilder {

    public static Group createGroup(String name, KPair k, Segment s1, Segment s2) {
        Group res = null;
        switch (k.getType()) {
            case KPAIR_TYPE_SLIDE:
                ;
                break;
            case KPAIR_TYPE_TURN:
                ;
                break;
        }
        res.setName(name);
        return res;
    }

    public static GroupS createGroup(KPairSlide k, Segment s1, Segment s2) {
        return new GroupS(k, s1, s2);
    }

    public static GroupT createGroup(KPairTurn k, Segment s1, Segment s2) {
        return new GroupT(k, s1, s2);
    }

    public static Group createGroup(String name, KPair k1, KPair k2, KPair k3, Segment s1, Segment s2) {
        Group res = null;
        // STS
        if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            res = createGroup((KPairSlide) k1, (KPairTurn) k2, (KPairSlide) k3, s1, s2);
        } else // TSS
        if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            res = createGroup((KPairTurn) k1, (KPairSlide) k2, (KPairSlide) k3, s1, s2);
        } else // SST 
        if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            res = createGroup((KPairSlide) k1, (KPairSlide) k2, (KPairTurn) k3, s1, s2);
        } else // TST 
        if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            res = createGroup((KPairTurn) k1, (KPairSlide) k2, (KPairTurn) k3, s1, s2);
        } else // TTS 
        if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            res = createGroup((KPairTurn) k1, (KPairTurn) k2, (KPairSlide) k3, s1, s2);
        } else // STT
        if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            res = createGroup((KPairSlide) k1, (KPairTurn) k2, (KPairTurn) k3, s1, s2);
        } else // TTT 
        if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            res = createGroup((KPairTurn) k1, (KPairTurn) k2, (KPairTurn) k3, s1, s2);
        }
        res.setName(name);
        return res;
    }

    //STS
    public static Group createGroup(KPairSlide k1, KPairTurn k2, KPairSlide k3, Segment s1, Segment s2) {
        return null;
    }

    //TSS
    public static Group createGroup(KPairTurn k1, KPairSlide k2, KPairSlide k3, Segment s1, Segment s2) {
        return null;
    }

    public static Group createGroup(KPairSlide k1, KPairSlide k2, KPairTurn k3, Segment s1, Segment s2) {
        return null;
    }

    public static Group createGroup(KPairTurn k1, KPairSlide k2, KPairTurn k3, Segment s1, Segment s2) {
        return null;
    }

    public static Group createGroup(KPairTurn k1, KPairTurn k2, KPairSlide k3, Segment s1, Segment s2) {
        return null;
    }

    public static Group createGroup(KPairSlide k1, KPairTurn k2, KPairTurn k3, Segment s1, Segment s2) {
        return null;
    }

    public static Group createGroup(KPairTurn k1, KPairTurn k2, KPairTurn k3, Segment s1, Segment s2) {
        return null;
    }
}
