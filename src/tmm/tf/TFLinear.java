package tmm.tf;

public class TFLinear {

    private TF x = new TF(), y = new TF();

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
