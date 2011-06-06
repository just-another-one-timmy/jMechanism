/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.tf;

/**
 *
 * @author jtimv
 */
public class TFLinear {

    private TF x, y;

    public TFLinear() {
        x = new TF();   
        y = new TF();
    }

    public void clear() {
        x.clear();
        y.clear();
    }

    public TF getX() {
        return x;
    }

    public TF getY() {
        return y;
    }
}
