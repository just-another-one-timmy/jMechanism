/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

/**
 *
 * @author jtimv
 */
public class Group2 extends Group {

    protected int j1, j2;

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

    public void setJ1(int j1) {
        this.j1 = j1;
    }

    public void setJ2(int j2) {
        this.j2 = j2;
    }

    public int getJ1() {
        return j1;
    }

    public int getJ2() {
        return j2;
    }
}
