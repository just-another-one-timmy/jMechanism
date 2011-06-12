/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import tmm.segment.*;


/**
 *
 * @author jtimv
 */
public abstract class Group1 extends Group {

    protected double GC;
    protected Segment s0, s1;

    public double getGC() {
        return GC;
    }

    public void setGC(double gc) {
        GC = gc;
    }

    @Override
    public int getGroupClass() {
        return 1;
    }
}
