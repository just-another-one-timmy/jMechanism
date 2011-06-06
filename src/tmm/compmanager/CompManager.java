/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.compmanager;

import tmm.connector.Connector;
import tmm.connector.ConnectorSlide;
import tmm.connector.ConnectorTurn;
import tmm.force.Force;
import tmm.kpair.KPair;
import tmm.kpair.KPairSlide;
import tmm.kpair.KPairTurn;
import tmm.segment.Segment;

/**
 *
 * @author jtimv
 */
public class CompManager {

    // TODO: почти все методы - заглушки
    public Segment addSegment(String name, double... params) {
        Segment res = new Segment();
        if (params.length >= 1) {
            res.setMass(params[0]);
        }
        if (params.length >= 2) {
            res.setRotInertia(params[1]);
        }
        return res;
    }

    public CompManager deleteSegment(String name) {
        return this;
    }

    public Segment getSegment(String name) {
        return null;
    }

    public Connector addCMass(String segmentName, double ro, double phi) {
        return null;
    }

    public KPairTurn addKPairTurn(String name, String c1name, String c2name) {
        return null;
    }

    public KPairSlide addKPairSlide(String name, String c1name, String c2name) {
        return null;
    }

    public KPair getKPair(String name) {
        return null;
    }

    public CompManager deleteKPair(String name) {
        return this;
    }

    public ConnectorTurn addConnectorTurn(String name, String segmentName, double ro, double phi) {
        return null;
    }

    public ConnectorSlide addConnectorSlide(String name, String segmentName, double ro, double phi, double alpha) {
        return null;
    }

    public ConnectorTurn addConnectorTurnDescartes(String name, String segmentName, double x, double y) {
        return null;
    }

    public ConnectorSlide addConnectorSlideDescartes(String name, String segmentName, double ro, double phi, double alpha) {
        return null;
    }
    
    public Connector getConnector(String name){
        return null;
    }
    
    public CompManager deleteConnector(String name){
        return this;
    }
    
    public Force addTechnoForce(String name, String connectorName, double fx, double fy, double torque){
        return null;
    }
    
    public Force getForce(String name){
        return null;
    }
    
    public CompManager delTechnoForce(String name){
        return this;
    }
    
    public CompManager clear(){
        return this;
    }
}
