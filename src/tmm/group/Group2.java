package tmm.group;

import tmm.segment.*;

public abstract class Group2 extends Group {

    protected int j1, j2;
    protected Segment s1, s2;

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

    @Override
    public int getGroupClass() {
        return 2;
    }
}
