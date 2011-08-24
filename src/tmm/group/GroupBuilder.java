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
                res = createGroup((KPairSlide) k, s1, s2);
                break;
            case KPAIR_TYPE_TURN:
                res = createGroup((KPairTurn) k, s1, s2);
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

    public static Group createGroup(String name, KPair k1, KPair k2, KPair k3, Segment s1, Segment s2) throws Exception {
        Group res = null;

        if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            // STS
            res = createGroup((KPairSlide) k1, (KPairTurn) k2, (KPairSlide) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            // TSS
            res = createGroup((KPairTurn) k1, (KPairSlide) k2, (KPairSlide) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            // SST 
            res = createGroup((KPairSlide) k1, (KPairSlide) k2, (KPairTurn) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            // TST 
            res = createGroup((KPairTurn) k1, (KPairSlide) k2, (KPairTurn) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_SLIDE) {
            // TTS 
            res = createGroup((KPairTurn) k1, (KPairTurn) k2, (KPairSlide) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_SLIDE
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            // STT
            res = createGroup((KPairSlide) k1, (KPairTurn) k2, (KPairTurn) k3, s1, s2);
        } else if (k1.getType() == KPairType.KPAIR_TYPE_TURN
                && k2.getType() == KPairType.KPAIR_TYPE_TURN
                && k3.getType() == KPairType.KPAIR_TYPE_TURN) {
            // TTT 
            res = createGroup((KPairTurn) k1, (KPairTurn) k2, (KPairTurn) k3, s1, s2);
        }
        res.setName(name);
        return res;
    }

    //STS
    public static Group createGroup(KPairSlide k1, KPairTurn k2, KPairSlide k3, Segment s1, Segment s2) throws Exception {
        return new GroupSTS(k1, k2, k3, s1, s2);
    }

    //TSS
    public static Group createGroup(KPairTurn k1, KPairSlide k2, KPairSlide k3, Segment s1, Segment s2) throws Exception {
        return new GroupTSS(k1, k2, k3, s1, s2);
    }

    public static Group createGroup(KPairSlide k1, KPairSlide k2, KPairTurn k3, Segment s1, Segment s2) throws Exception {
        return new GroupTSS(k3, k2, k1, s2, s1);
    }

    public static Group createGroup(KPairTurn k1, KPairSlide k2, KPairTurn k3, Segment s1, Segment s2) throws Exception {
        return new GroupTST(k1, k2, k3, s1, s2);
    }

    public static Group createGroup(KPairTurn k1, KPairTurn k2, KPairSlide k3, Segment s1, Segment s2) throws Exception {
        return new GroupTTS(k1, k2, k3, s1, s2);
    }

    public static Group createGroup(KPairSlide k1, KPairTurn k2, KPairTurn k3, Segment s1, Segment s2) throws Exception {
        return new GroupTTS(k3, k2, k1, s2, s1);
    }

    public static Group createGroup(KPairTurn k1, KPairTurn k2, KPairTurn k3, Segment s1, Segment s2) throws Exception {
        return new GroupTTT(k1, k2, k3, s1, s2);
    }
}
