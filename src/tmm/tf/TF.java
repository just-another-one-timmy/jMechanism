package tmm.tf;

public class TF {

    private double TF0,TF1,TF2;
    private boolean calculatedTF0,calculatedTF1,calculatedTF2;

    public TF() {
        calculatedTF0 = false;
        calculatedTF1 = false;
        calculatedTF2 = false;
    }

    public boolean isTF0Calculated() {
        return calculatedTF0;
    }
    
    public boolean isTF1Calculated() {
        return calculatedTF1;
    }

    public boolean isTF2Calculated() {
        return calculatedTF2;
    }
    
    public void setTF0(double TransferFunction0) {
        TF0 = TransferFunction0;
        calculatedTF0 = true;
    }
    
    public void setTF1(double TransferFunction1) {
        TF1 = TransferFunction1;
        calculatedTF1 = true;
    }

    public void setTF2(double TransferFunction2) {
        TF2 = TransferFunction2;
        calculatedTF2 = true;
    }
    
    public double getTF0() throws Exception {
        try {
            if (!calculatedTF0) {
                throw new Exception("TF0 is not calculated yet!");
            }
        } catch (Exception e) {
        }
        return TF0;
    }
    
    public double getTF1() throws Exception {
        try {
            if (!calculatedTF1) {
                throw new Exception("TF1 is not calculated yet!");
            }
        } catch (Exception e) {
        }
        return TF1;
    }
    
        public double getTF2() throws Exception {
        try {
            if (!calculatedTF2) {
                throw new Exception("TF2 is not calculated yet!");
            }
        } catch (Exception e) {
        }
        return TF2;
    }
    
    public void clear() {
        calculatedTF0 = false;
        calculatedTF1 = false;
        calculatedTF2 = false;
        
    }
}