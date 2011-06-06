/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.segment;

import java.util.*;
import tmm.connector.Connector;
import tmm.force.Force;
import tmm.tf.TFTurn;

/**
 *
 * @author jtimv
 */
public class Segment{

    private Connector cmass, cpolus;
    private List<Connector> connectors;
    private List<Force> forces;
    private String name;
    private double mass, rotInertia;
    private TFTurn turn;

    public Segment() {
        connectors = new LinkedList<Connector>();
        forces = new LinkedList<Force>();
    }

    public Segment(String name) {
        this();
        this.name = name;
    }

    public Connector getCMass() {
        return cmass;
    }

    public Connector getCPolus() {
        return cpolus;
    }

    public Segment setMass(double mass) {
        this.mass = mass;
        return this;
    }

    public Segment setRotInertia(double rotInertia) {
        this.rotInertia = rotInertia;
        return this;
    }

    public double getMass() {
        return mass;
    }

    public double getRotInertia() {
        return rotInertia;
    }

    public String getName() {
        return name;
    }

    public TFTurn getTFTurn() {
        return turn;
    }

    // TODO: сделать здесь unmodifiable iterator, но пока что пусть будет так. Да и вообще подумать, нужно ли сегменту знать свои коннекторы?
    ListIterator<Connector> getConnectorIterator() {
        return connectors.listIterator();
    }
}
