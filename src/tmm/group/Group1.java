/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

/**
 *
 * @author jtimv
 */
public class Group1 extends Group {

    protected double GC;

    @Override
    public void calcTF0() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void calcTF1() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void calcTF2() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void calcReaction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GroupType getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getGC() {
        return GC;
    }

    public void setGC(double gc) {
        GC = gc;
    }
}
