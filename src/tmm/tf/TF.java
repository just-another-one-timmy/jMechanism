package tmm.tf;

// TODO: здесь проверить, может я зря написал с константой TF_COUNT
public class TF {

    public static final int TF_COUNT = 3;
    private double values[];
    private boolean calculated[];

    public TF() {
        values = new double[TF_COUNT];
        calculated = new boolean[TF_COUNT];
    }

    public boolean isCalculated(int i) {
        return calculated[i];
    }

    public void setValue(double value, int i) {
        values[i] = value;
        calculated[i] = true;
    }

    public double getValue(int i) throws Exception {
        try {
            if (!calculated[i]) {
                throw new Exception("values[" + i + "] is not calculated yet!");
            }
        } catch (Exception e) {
        }
        return values[i];
    }

    public void clear() {
        for (int i = 0; i < calculated.length; i++) {
            calculated[i] = false;
        }
    }
}
