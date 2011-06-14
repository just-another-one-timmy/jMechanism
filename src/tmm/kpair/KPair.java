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
public abstract class KPair {

    protected Force R1, R2;
    protected String name;
    protected TFLinear linear = new TFLinear();

    public abstract KPairType getType();

    public final Force getR1() {
        return R1;
    }

    public final Force getR2() {
        return R2;
    }
    
    public final String getName(){
        return name;
    }

    public abstract Connector getC1();

    public abstract Connector getC2();
    
    public final TFLinear getLinear(){
        return linear;
    }
}
