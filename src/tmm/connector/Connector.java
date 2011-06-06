/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.connector;

import tmm.segment.Segment;

/**
 *
 * @author jtimv
 */
public abstract class Connector {

    private Segment s;
    private double ro, phi;
    private String name;

    public Connector(){
    }
    
    public Connector(Segment s, String name) {
        this.s = s;
        this.name = name;
    }

    public Connector setSegment(Segment s) {
        this.s = s;
        return this;
    }

    public Segment getSegment() {
        return s;
    }

    public Connector setRo(double ro) {
        this.ro = ro;
        return this;
    }

    public double getRo() {
        return ro;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getPhi() {
        return phi;
    }

    public Connector setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public abstract ConnectorType getType();
}
