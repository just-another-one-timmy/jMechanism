/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

/**
 *
 * @author jtimv
 */
public abstract class Group {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void calcTF0();

    public abstract void calcTF1();

    public abstract void calcTF2();

    public final void calcTF() {
        calcTF0();
        calcTF1();
        calcTF2();
    }
}
