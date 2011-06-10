/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

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

    public Force getR1() {
        return R1;
    }

    public Force getR2() {
        return R2;
    }
}
