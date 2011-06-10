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
public class KPairTurn  extends KPair {

    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_TURN;
    }
    
    public KPairTurn(String name, ConnectorTurn c1, ConnectorTurn c2){
        //
    }
}
