/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.connector;

import tmm.segment.Segment;
import tmm.tf.TFLinear;
import tmm.tf.TFTurn;

/**
 *
 * @author jtimv
 */
public class ConnectorSlide extends Connector {

    private double alpha;
    private TFLinear linear0;
    private TFTurn turn;

    private ConnectorSlide(Segment s, String name, TFLinear linear0, TFTurn turn) {
        super(s, name);
        this.linear0 = linear0;
        this.turn = turn;
    }

    public ConnectorSlide(Segment s, String name) {
        super(s, name);
        linear0 = new TFLinear();
    }

    @Override
    public ConnectorType getType() {
        return ConnectorType.CONNECTOR_TYPE_SLIDE;
    }

    public Connector setAlpha(double alpha) {
        this.alpha = alpha;
        return this;
    }

    public double getAlpha() {
        return alpha;
    }

    public TFLinear getLinear0() {
        return linear0;
    }

    public TFTurn getTurn() {
        return turn;
    }

    public ConnectorSlide setTurn(TFTurn t) {
        this.turn = t;
        return this;
    }

    @Override
    public void clear() {
        linear0.clear();
        turn.clear();
    }
}
