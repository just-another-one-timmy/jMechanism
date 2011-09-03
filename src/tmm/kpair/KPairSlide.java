package tmm.kpair;

import java.util.logging.Logger;
import tmm.connector.*;
import tmm.force.*;
import tmm.tf.*;

public class KPairSlide extends KPair {
    private static final Logger logger = Logger.getLogger(KPairTurn.class.getName());

    private ConnectorSlide c1, c2;
    private TFTurn angle = new TFTurn();

    @Override
    public KPairType getType() {
        return KPairType.KPAIR_TYPE_SLIDE;
    }

    @Override
    public ConnectorSlide getC1() {
        return c1;
    }

    @Override
    public ConnectorSlide getC2() {
        return c2;
    }

    public KPairSlide(String name, ConnectorSlide c1, ConnectorSlide c2) throws Exception {
        
        if (c1==null){
            logger.severe("c1==null in KPairSlide constructor");
            throw new Exception("c1==null in KPairSlide constructor");
        }
        if (c2==null){
            logger.severe("c2==null in KPairSlide constructor");
            throw new Exception("c1==null in KPairSlide constructor");
        }
        if (name==null){
            logger.severe("name==null in KPairSlide constructor");            
            throw new Exception("c1==null in KPairSlide constructor");
        }

        this.name = name;
        this.c1 = c1;
        this.c2 = c2;
        this.R1 = new Force("R_" + c2.getSegment().getName() + "_" + c1.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c1);
        R1.setTFLinear(linear).setTFTurn(angle);

        this.R2 = new Force("R_" + c1.getSegment().getName() + "_" + c2.getSegment().getName(), ForceType.FORCE_TYPE_REACTION, c2);
        R1.setTFLinear(linear).setTFTurn(angle);

        c1.setTurn(angle);
        c2.setTurn(angle);
    }

    public TFTurn getAngle() {
        return angle;
    }
}
