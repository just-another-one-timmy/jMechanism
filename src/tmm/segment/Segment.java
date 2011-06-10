/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.segment;

import java.util.*;
import tmm.connector.*;
import tmm.force.*;
import tmm.tf.*;

/**
 *
 * @author jtimv
 */
public class Segment {

    private ConnectorTurn cmass, cpolus;
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

    public ConnectorTurn getCMass() {
        return cmass;
    }

    public Segment setCMass(ConnectorTurn ct) {
        this.cmass = ct;
        return this;
    }

    public ConnectorTurn getCPolus() {
        return cpolus;
    }

    public void setCPolus(ConnectorTurn c) {
        this.cpolus = c;
    }

    public Segment setMass(double mass) {
        this.mass = mass;
        return this;
    }

    /**
     * Задает момент вращательной инерции, возвращает сегмент
     */
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

    public List<Connector> getConnectors() {
        return connectors;
    }

    public List<Force> getForces() {
        return forces;
    }

    public void addConnector(Connector c) {
        connectors.add(c);
    }

    public void deleteConnector(Connector c) {
        connectors.remove(c);
    }

    public void deleteForce(Force f) {
        forces.remove(f);
    }
}
