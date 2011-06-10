/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.kpair;

import tmm.force.*;

/**
 *
 * @author jtimv
 */
public abstract class KPair {

    private Force R1, R2;

    public abstract KPairType getType();

    public Force getR1() {
        return R1;
    }

    public Force getR2() {
        return R2;
    }
}
