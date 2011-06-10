/*
 * С этим вроде закончил :)
 * 
 */
package tmm.compmanager;

import java.util.*;
import tmm.connector.*;
import tmm.force.*;
import tmm.kpair.*;
import tmm.segment.*;
import tmm.tf.*;

/**
 *
 * @author jtimv
 */
public class CompManager {

    private Map<String, Segment> segments;
    private Map<String, KPair> kpairs;
    private Map<String, Force> forces;
    private Map<String, Connector> connectors;
    private double gravity;

    /**
     * Creates new segment and adds it into internal map.
     * Also adds connector (turn) named as segment's name + "cP".
     * For this connector creates new TFLinear.
     * @param name Name of the segment
     * @param params mass, rotational inertia (optionally)
     * @return created segment
     */
    public Segment addSegment(String name, double... params) {
        Segment res = new Segment(name);
        if (params.length >= 1) {
            res.setMass(params[0]);
        }
        if (params.length >= 2) {
            res.setRotInertia(params[1]);
        }

        segments.put(name, res);

        res.setCPolus(this.addConnectorTurn(name + "cP", name, 0, 0));
        res.getCPolus().setLinear(new TFLinear());
        return res;
    }

    public CompManager() {
        segments = new HashMap<String, Segment>();
        kpairs = new HashMap<String, KPair>();
        forces = new HashMap<String, Force>();
        connectors = new HashMap<String, Connector>();
    }

    public CompManager deleteSegment(String name) {
        Segment s = segments.get(name);
        if (s != null) {
            for (Connector c : s.getConnectors()) {
                connectors.remove(c.getName());
            }
            segments.remove(name);
        }
        return this;
    }

    public Segment getSegment(String name) {
        return segments.get(name);
    }

    public ConnectorTurn addCMass(String segmentName, double ro, double phi) {
        String cname = segmentName + "cS";
        ConnectorTurn res = this.addConnectorTurn(cname, segmentName, ro, phi);
        segments.get(segmentName).setCMass(res);

        Force f = new Force("G_" + segmentName, ForceType.FORCE_TYPE_GRAVITY, res);
        f.setForce(0, gravity * segments.get(segmentName).getMass(), 0);

        forces.put(f.getName(), f);
        res.setLinear(new TFLinear());
        f.setTFLinear(res.getLinear());

        return res;
    }

    public KPairTurn addKPairTurn(String name, String c1name, String c2name) {
        KPairTurn res = new KPairTurn(name, (ConnectorTurn) connectors.get(c1name), (ConnectorTurn) connectors.get(c2name));
        kpairs.put(name, res);
        forces.put(res.getR1().getName(), res.getR1());
        forces.put(res.getR2().getName(), res.getR2());
        return res;
    }

    public KPairSlide addKPairSlide(String name, String c1name, String c2name) {
        KPairSlide res = new KPairSlide(name, (ConnectorSlide) connectors.get(c1name),
                (ConnectorSlide) connectors.get(c2name));
        kpairs.put(name, res);
        forces.put(res.getR1().getName(), res.getR1());
        forces.put(res.getR2().getName(), res.getR2());
        return res;
    }

    public KPair getKPair(String name) {
        return kpairs.get(name);
    }

    public CompManager deleteKPair(String name) {
        KPair k = kpairs.get(name);
        forces.remove(k.getR1().getName());
        forces.remove(k.getR2().getName());
        kpairs.remove(name);
        return this;
    }

    public ConnectorTurn addConnectorTurn(String name, String segmentName, double ro, double phi) {
        ConnectorTurn res = new ConnectorTurn();
        res.setName(name);
        connectors.put(name, res);
        Segment s = segments.get(segmentName);
        s.addConnector(res);
        res.setRo(ro);
        res.setPhi(phi);
        res.setSegment(s);
        return res;
    }

    public ConnectorSlide addConnectorSlide(String name, String segmentName, double ro, double phi, double alpha) {
        Segment s = segments.get(segmentName);
        ConnectorSlide res = new ConnectorSlide(s, name);
        s.addConnector(res);
        connectors.put(name, res);
        res.setTurn(new TFTurn()).setAlpha(alpha).setRo(ro).setPhi(phi);
        return res;
    }

    public ConnectorTurn addConnectorTurnDescartes(String name, String segmentName, double x, double y) {
        double ro = Math.sqrt(Math.pow(x, 2.) + Math.pow(y, 2.));
        double phi = Math.atan2(y, x);
        return this.addConnectorTurn(name, segmentName, ro, phi);
    }

    public ConnectorSlide addConnectorSlideDescartes(String name, String segmentName, double x, double y, double alpha) {
        double ro = Math.sqrt(Math.pow(x, 2.) + Math.pow(y, 2.));
        double phi = Math.atan2(y, x);
        return this.addConnectorSlide(name, segmentName, ro, phi, alpha);
    }

    public Connector getConnector(String name) {
        return connectors.get(name);
    }

    public CompManager deleteConnector(String name) {
        Connector c = connectors.get(name);
        Segment s = c.getSegment();
        s.getConnectors().remove(c);
        connectors.remove(name);
        return this;
    }

    public Force addTechnoForce(String name, String connectorName, double fx, double fy, double torque) {
        ConnectorTurn c = (ConnectorTurn) connectors.get(connectorName);
        Force res = new Force(name, ForceType.FORCE_TYPE_TECHNO, c);
        res.setForce(fx, fy, torque);
        forces.put(res.getName(), res);
        res.setTFLinear(c.getLinear());
        res.setTFTurn(c.getSegment().getTFTurn());
        return res;
    }

    public Force getForce(String name) {
        return forces.get(name);
    }

    public CompManager delTechnoForce(String name) {
        Force f = forces.get(name);
        f.getConnector().getSegment().getForces().remove(f);
        forces.remove(f.getName());
        return this;
    }

    /**
     * Set ALL to uncalculated state
     * @return this
     */
    public CompManager clear() {
        for (Connector c : connectors.values()) {
            c.clear();
        }
        for (Segment s : segments.values()) {
            for (Force f : s.getForces()) {
                f.clear();
            }
        }
        return this;
    }
}
