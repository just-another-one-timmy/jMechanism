/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

import java.util.logging.Logger;
import tmm.connector.*;
import tmm.force.*;

/**
 *
 * @author jtimv
 */
public class KPairTurn extends KPair {
    private static final Logger logger = Logger.getLogger(KPairTurn.class.getName());

    private ConnectorTurn c1, c2;

    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_TURN;
    }

    @Override
    public ConnectorTurn getC1() {
        return c1;
    }

    @Override
    public ConnectorTurn getC2() {
        return c2;
    }

    public KPairTurn(String name, ConnectorTurn c1, ConnectorTurn c2) throws Exception {
        
        if (c1==null){
            logger.severe("c1==null in KPairTurn constructor");
            throw new Exception("c1==null in KPairTurn constructor");
        }
        if (c2==null){
            logger.severe("c2==null in KPairTurn constructor");
            throw new Exception("c1==null in KPairTurn constructor");
        }
        if (name==null){
            logger.severe("name==null in KPairTurn constructor");            
            throw new Exception("c1==null in KPairTurn constructor");
        }
        
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
