/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

import tmm.connector.*;

/**
 *
 * @author jtimv
 */
public class KPairSlide extends KPair {

    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_SLIDE;
    }

    public KPairSlide(String name, ConnectorSlide c1, ConnectorSlide c2) {
    }
}
