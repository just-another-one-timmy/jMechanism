/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.XML;

import java.io.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import tmm.compmanager.*;
import tmm.group.*;

/**
 *
 * @author jtimv
 */
public class XMLWorker extends DefaultHandler {
    
    private static final String ELEMENT_NAME_MECHANISM = "Mechanism",
            ELEMENT_NAME_SEGMENT = "Segment",
            ELEMENT_NAME_CONNECTOR_TURN = "ConnectorTurn",
            ELEMENT_NAME_CONNECTOR_SLIDE = "ConnectorSliding",
            ELEMENT_NAME_KPAIRS = "KPairs",
            ELEMENT_NAME_KPAIR_TURN = "KPTurn",
            ELEMENT_NAME_KPAIR_SLIDE = "KPSliding",
            ELEMENT_NAME_GROUPS = "Groups",
            ELEMENT_NAME_GROUP1 = "Group1",
            ELEMENT_NAME_GROUP2 = "Group2",
            ATTR_NAME_STEP_SIZE = "step_size",
            ATTR_NAME_MIN = "min",
            ATTR_NAME_MAX = "max",
            ATTR_NAME_JUMP = "jump",
            ATTR_NAME_MASS = "mass",
            ATTR_NAME_ROT_INERTIA = "rot_inertia",
            ATTR_NAME_NAME = "name",
            ATTR_NAME_RO = "ro",
            ATTR_NAME_PHI = "phi",
            ATTR_NAME_ALPHA = "alpha",
            ATTR_NAME_C1_NAME = "c1_name",
            ATTR_NAME_C2_NAME = "c2_name",
            ATTR_NAME_KPAIR_NAME = "kp_name",
            ATTR_NAME_S1_NAME = "s1_name",
            ATTR_NAME_S2_NAME = "s2_name",
            ATTR_NAME_NEED_ANALYZE = "need_analyze";
    CompManager cm;
    GroupManager gm;
    String lastSegmentName;
    boolean needAnalyzeGroups;
    
    public XMLWorker(CompManager cm, GroupManager gm) {
        this.cm = cm;
        this.gm = gm;
    }
    
    @Override
    public void startDocument() {
    }
    
    @Override
    public void endDocument() {
    }
    
    @Override
    public void startElement(String uri, String name, String qName, Attributes atts) {
        if (ELEMENT_NAME_MECHANISM.equals(qName)) {
            processElementMechanism(atts);
            return;
        }
        if (ELEMENT_NAME_SEGMENT.equals(qName)) {
            processElementSegment(atts);
            return;
        }
        if (ELEMENT_NAME_CONNECTOR_TURN.equals(qName)) {
            processElementConnectorTurn(atts);
            return;
        }
        if (ELEMENT_NAME_CONNECTOR_SLIDE.equals(qName)) {
            processElementConnectorSlide(atts);
            return;
        }
        if (ELEMENT_NAME_KPAIRS.equals(qName)) {
            processElementKPairs(atts);
            return;
        }
        if (ELEMENT_NAME_KPAIR_TURN.equals(qName) || ELEMENT_NAME_KPAIR_SLIDE.equals(qName)) {
            processElementKPair(atts, qName);
            return;
        }
        if (ELEMENT_NAME_GROUPS.equals(qName)) {
            processElementGroupsStart(atts);
            return;
        }
        if (ELEMENT_NAME_GROUP1.equals(qName)) {
            processElementGroup1(atts);
            return;
        }
    }
    
    @Override
    public void endElement(String uri, String name, String qName) {
        if (ELEMENT_NAME_GROUPS.equals(qName)) {
            processElementGroupsEnd();
            return;
        }
    }
    
    public void loadFromFile(String filename) throws ParserConfigurationException, SAXException, IOException {
        cm.setBusy(true);
        SAXParserFactory.newInstance().newSAXParser().parse(filename, new XMLWorker(cm, gm));
        cm.createMissingTFs();
        cm.setBusy(false);
    }
    
    private void processElementMechanism(Attributes atts) {
        gm.setStepSize(getV(ATTR_NAME_STEP_SIZE, atts));
        gm.setMinGC(getV(ATTR_NAME_MIN, atts));
        gm.setMaxGC(getDoubleValueEx(ATTR_NAME_MAX, atts, Double.MAX_VALUE));
        gm.setJump(getV(ATTR_NAME_JUMP, atts, false));
    }
    
    private void processElementSegment(Attributes atts) {
        String name = getV(ATTR_NAME_NAME, atts, "");
        double mass = getV(ATTR_NAME_MASS, atts),
                rotInertia = getV(ATTR_NAME_ROT_INERTIA, atts);
        cm.addSegment(name, mass, rotInertia);
        lastSegmentName = name;
    }
    
    private void processElementConnectorTurn(Attributes atts) {
        String name = getV(ATTR_NAME_NAME, atts, "");
        double ro = getV(ATTR_NAME_RO, atts),
                phi = getV(ATTR_NAME_PHI, atts);
        cm.addConnectorTurn(name, lastSegmentName, ro, phi);
    }
    
    private void processElementConnectorSlide(Attributes atts) {
        String name = getV(ATTR_NAME_NAME, atts, "");
        double ro = getV(ATTR_NAME_RO, atts),
                phi = getV(ATTR_NAME_PHI, atts),
                alpha = getV(ATTR_NAME_ALPHA, atts);
        cm.addConnectorSlide(name, lastSegmentName, ro, phi, alpha);
    }
    
    private void processElementKPairs(Attributes atts) {
    }
    
    private void processElementKPair(Attributes atts, String elementName) {
        String name = getV(ATTR_NAME_NAME, atts, ""),
                c1_name = getV(ATTR_NAME_C1_NAME, atts, ""),
                C2_name = getV(ATTR_NAME_C2_NAME, atts, "");
        if (elementName.equals(ELEMENT_NAME_KPAIR_TURN)) {
            cm.addKPairTurn(name, c1_name, C2_name);
        }
        if (elementName.equals(ELEMENT_NAME_KPAIR_SLIDE)) {
            cm.addKPairSlide(name, c1_name, C2_name);
        }
    }
    
    private void processElementGroupsStart(Attributes atts) {
        needAnalyzeGroups = getV(ATTR_NAME_NEED_ANALYZE, atts, false);
    }
    
    private void processElementGroupsEnd() {
        if (needAnalyzeGroups) {
            try {
                gm.analyze();
            } catch (Exception ex) {
            }
        }
    }
    
    private void processElementGroup1(Attributes atts) {
        String kpairName = getV(ATTR_NAME_KPAIR_NAME, atts, ""),
                s1Name = getV(ATTR_NAME_S1_NAME, atts, ""),
                s2Name = getV(ATTR_NAME_S2_NAME, atts, "");
        gm.addFirstGroupByName(kpairName, s1Name, s2Name);
    }

    //-----------------------------------------------------------------------
    // Helper functions. 
    //-----------------------------------------------------------------------
    private double getDoubleValueEx(String attName, Attributes atts, double defaultValue) {
        String doubleValue = atts.getValue(attName);
        if (doubleValue != null) {
            return Double.parseDouble(doubleValue);
        }
        return defaultValue;
    }
    
    private String getStringValueEx(String attName, Attributes atts, String defaultValue) {
        String stringValue = atts.getValue(attName);
        if (stringValue != null) {
            return stringValue;
        }
        return defaultValue;
    }
    
    private boolean getBooleanValueEx(String attName, Attributes atts, boolean defaultValue) {
        String booleanValue = atts.getValue(attName);
        if (booleanValue != null) {
            return Boolean.parseBoolean(booleanValue);
        }
        return defaultValue;
    }
    
    double getV(String attName, Attributes atts) {
        return getDoubleValueEx(attName, atts, 0.0);
    }
    
    String getV(String attName, Attributes atts, String x) {
        return getStringValueEx(attName, atts, "");
    }
    
    boolean getV(String attName, Attributes atts, boolean x) {
        return getBooleanValueEx(attName, atts, x);
    }
}