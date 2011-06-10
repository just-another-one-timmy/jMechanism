/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// TODO: переписать Force, пока - только заглушки
package tmm.force;

import tmm.connector.Connector;
import tmm.tf.*;

/**
 *
 * @author jtimv
 */
public class Force {

    private String name;
    private ForceType type;
    private double fx, fy, torque;
    private boolean calculatedX, calculatedY, calculatedTorque;
    private Connector c;

    public Force(String name, ForceType type, Connector c) {
        this.name = name;
        this.type = type;
        this.c = c;
        calculatedX = calculatedY = calculatedTorque = false;
        c.getSegment().getForces().add(this);
    }

    public Force setTFLinear(TFLinear t) {
        return this;
    }

    public Force setTFTurn(TFTurn t) {
        return this;
    }

    public String getName() {
        return name;
    }

    public ForceType getType() {
        return type;
    }

    public Force setForce(double fx, double fy, double torque) {
        this.fx = fx;
        this.fy = fy;
        this.torque = torque;
        calculatedX = calculatedY = calculatedTorque = true;
        return this;
    }

    public Force setX(double fx) {
        this.fx = fx;
        calculatedX = true;
        return this;
    }

    public Force setY(double fy) {
        this.fy = fy;
        calculatedY = true;
        return this;
    }

    public Force setTorque(double torque) {
        this.fx = torque;
        calculatedTorque = true;
        return this;
    }

    public Connector getConnector() {
        return c;
    }

    public double getForceX() throws Exception {
        if (!calculatedX) {
            throw new Exception("fx is not calculated yet");
        }
        return fx;
    }

    public double getForceY() throws Exception {
        if (!calculatedY) {
            throw new Exception("fy is not calculated yet");
        }
        return fy;
    }

    public double getForceTorque() throws Exception {
        if (!calculatedTorque) {
            throw new Exception("torque is not calculated yet");
        }
        return torque;
    }

    public boolean isXCalculated() {
        return calculatedX;
    }

    public boolean isYCalculated() {
        return calculatedY;
    }

    public boolean isTorqueCalculated() {
        return calculatedTorque;
    }

    public void clear() {
        if (type == ForceType.FORCE_TYPE_GRAVITY || type == ForceType.FORCE_TYPE_TECHNO) {
            return;
        }
        calculatedX = calculatedY = calculatedTorque = false;
    }
}
