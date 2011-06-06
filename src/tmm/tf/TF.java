/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.tf;

/**
 *
 * @author jtimv
 */
public class TF {

    public static final int TF_COUNT = 3;
    private double values[];
    private boolean calculated[];

    public TF() {
        values = new double[TF_COUNT];
        calculated = new boolean[TF_COUNT];
    }

    boolean isCalculated(int i) {
        return calculated[i];
    }

    void setValue(double value, int i) {
        values[i] = value;
        calculated[i] = true;
    }

    double getValue(int i) throws Exception {
        if (!calculated[i]) {
            throw new Exception("values[" + i + "] is not calculated yet!");
        }
        return values[i];
    }

    public void clear() {
        for (int i = 0; i < calculated.length; i++) {
            calculated[i] = false;
        }
    }
}
