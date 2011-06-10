/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

import tmm.connector.*;
import tmm.force.*;

/**
 *
 * @author jtimv
 */
public class KPairTurn extends KPair {
    
    private ConnectorTurn c1, c2;
    
    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_TURN;
    }
    
    public KPairTurn(String name, ConnectorTurn c1, ConnectorTurn c2) {
        this.name = name;
        this.c1 = c1;
        this.c2 = c2;
        
        R1 = new Force("R_" + c2.getSegment().getName() + "_" + c1.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c1);
        R1.setTFLinear(linear);
        
        R2 = new Force("R_" + c1.getSegment().getName() + "_" + c2.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c2);
        R2.setTFLinear(linear);
        
        c1.setLinear(linear);
        c2.setLinear(linear);
    }
}
