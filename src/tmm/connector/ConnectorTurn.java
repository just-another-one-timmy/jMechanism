/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.connector;

import tmm.segment.Segment;
import tmm.tf.TFLinear;

/**
 *
 * @author jtimv
 */
public class ConnectorTurn extends Connector {

    private TFLinear linear;

    public ConnectorTurn() {
    }

    public ConnectorTurn(Segment s, String name, TFLinear linear) {
        super(s, name);
        this.linear = linear;
    }

    public TFLinear getLinear() {
        return linear;
    }

    public void setLinear(TFLinear linear) {
        this.linear = linear;
    }

    @Override
    public ConnectorType getType() {
        return ConnectorType.CONNECTOR_TYPE_TURN;
    }

    @Override
    public void clear() {
        linear.clear();
    }
}
