/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

import tmm.connector.*;
import tmm.force.*;
import tmm.tf.*;

/**
 *
 * @author jtimv
 */
public class KPairSlide extends KPair {

    private ConnectorSlide c1, c2;
    private TFTurn angle = new TFTurn();

    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_SLIDE;
    }

    public KPairSlide(String name, ConnectorSlide c1, ConnectorSlide c2) {
        this.c1 = c1;
        this.c2 = c2;
        this.R1 = new Force("R_" + c2.getSegment().getName() + "_" + c1.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c1);
        R1.setTFLinear(linear).setTFTurn(angle);

        this.R2 = new Force("R_" + c1.getSegment().getName() + "_" + c2.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c2);
        R1.setTFLinear(linear).setTFTurn(angle);

        c1.setTurn(angle);
        c2.setTurn(angle);
    }
}
