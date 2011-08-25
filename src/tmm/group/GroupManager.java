package tmm.group;

import java.util.*;
import tmm.compmanager.*;
import tmm.connector.*;
import tmm.kpair.KPair;
import tmm.segment.*;

/**
 *
 * @author jtimv
 */
public class GroupManager {

    private double stepSize, minGC, maxGC;
    private boolean jump, moveForward;
    private CompManager cm;
    private Set<Segment> blackVertexes, newBlackVertexes; // to escape ConcurrentModificationException
    private Set<KPair> blackEdges;
    private Map<Segment, Set<KPair>> edges;
    private List<Group> groups;
    private boolean doSelfTest = true; // Do we want to check segments lengths and angles?

    private void addSecondGroup(KPair k1, KPair k2, KPair k3, Segment s1, Segment s2) throws Exception {
        groups.add(GroupBuilder.createGroup("(" + s1.getName() + "," + s2.getName() + ")", k1, k2, k3, s1, s2));
    }

    private void addBlackEdge(KPair k) {
        blackEdges.add(k);
    }

    private void addBlackVertex(Segment s) {
        blackVertexes.add(s);
    }

    private void findPath(List<Segment> path, int len) throws Exception {
        if (len == -1) {
            processPath(path);
        } else {
            Segment s = path.get(path.size() - 1);
            boolean findBlack = (len == 0);
            for (Segment si : cm.getSegments()) {
                boolean inPath = path.contains(si);
                if (!inPath && (blackVertexes.contains(si) == findBlack)) {
                    if (!commonEdges(s, si).isEmpty()) {
                        path.add(si);
                        findPath(path, len - 1);
                        path.remove(path.size() - 1);
                    }
                }
            }
        }
    }

    private Set<KPair> commonEdges(Segment s1, Segment s2) {
        Set<KPair> res = new HashSet<KPair>(edges.get(s1));
        res.retainAll(edges.get(s2));
        return res;
    }

    private void processPath(List<Segment> path) throws Exception {
        int len = path.size();
        KPair k[] = new KPair[len - 1];
        for (int i = 1; i < len; i++) {
            k[i - 1] = commonEdges(path.get(i), path.get(i - 1)).iterator().next();
            blackEdges.add(k[i - 1]);
            newBlackVertexes.add(path.get(i - 1));
        }
        newBlackVertexes.add(path.get(len - 1));

        if (len == 4) {
            addSecondGroup(k[0], k[1], k[2], path.get(1), path.get(2));
        } else {
            throw new Exception("Can't process group!");
        }
    }

    public GroupManager(CompManager cm) {
        this.cm = cm;
        minGC = maxGC = 0;
        jump = moveForward = true;
        edges = new HashMap<Segment, Set<KPair>>();
        groups = new LinkedList<Group>();
        blackEdges = new HashSet<KPair>();
        blackVertexes = new HashSet<Segment>();
        newBlackVertexes = new HashSet<Segment>();
    }

    public void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }

    public double getStepSize() {
        return stepSize;
    }

    public void setMinGC(double val) {
        minGC = val;
    }

    public void setMaxGC(double val) {
        maxGC = val;
    }

    public double getMinGC() {
        return minGC;
    }

    public double getMaxGC() {
        return maxGC;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean getJump() {
        return jump;
    }

    private void addToEdges(Segment s, KPair k) {
        Set<KPair> kps = edges.get(s);
        if (edges.get(s) == null) {
            kps = new HashSet<KPair>();
            edges.put(s, kps);
        }
        kps.add(k);
    }

    private void buildEdgesSets() {
        for (KPair k : cm.getKPairs()) {
            addToEdges(k.getC1().getSegment(), k);
            addToEdges(k.getC2().getSegment(), k);
        }
    }

    public void calcNextStep() throws Exception {
        for (Group g : groups) {
            g.calcTF0();
        }
        for (Group g : groups) {
            g.calcTF1();
        }
        for (Group g : groups) {
            g.calcTF2();
        }
        if (doSelfTest) {
            testDistances();
        }
    }

    public void makeStep() {
        if (groups.isEmpty()) {
            return;
        }
        cm.setBusy(true);
        Group1 g = (Group1) groups.get(0);
        double gc = g.getGC();
        gc += moveForward ? stepSize : -stepSize;
        cm.clear();
        if (moveForward && gc > maxGC) {
            if (jump) {
                gc = minGC;
            } else {
                moveForward = false;
            }
        } else if (!moveForward && gc < minGC) {
            moveForward = true;
        }
        g.setGC(gc);
        cm.setBusy(false);
    }

    public void addFirstGroupByName(String kp1name, String segment1name, String segment2name) {
        Segment s1 = cm.getSegment(segment1name),
                s2 = cm.getSegment(segment2name);
        KPair firstK = cm.getKPair(kp1name);
        groups.add(GroupBuilder.createGroup("(" + s1.getName() + "," + s2.getName() + ")", firstK, s1, s2));
        addBlackEdge(firstK);
        addBlackVertex(s1);
        addBlackVertex(s2);
    }

    public void addSecondGroupByName(String kp1name, String kp2name, String kp3name, String segment1name, String segment2name, int... mjs) throws Exception {
        int mj1 = 0, mj2 = 0;
        if (mjs != null) {
            if (mjs.length > 0) {
                mj1 = mjs[0];
            }
            if (mjs.length > 1) {
                mj2 = mjs[1];
            }
        }
        Segment s1 = cm.getSegment(segment1name),
                s2 = cm.getSegment(segment2name);
        KPair k1 = cm.getKPair(kp1name),
                k2 = cm.getKPair(kp2name),
                k3 = cm.getKPair(kp3name);
        String gname = "(" + s1.getName() + "," + s2.getName() + ")";
        Group2 g = (Group2) GroupBuilder.createGroup(gname, k1, k2, k3, s1, s2);
        g.setJ1(mj1);
        g.setJ2(mj2);
        groups.add(g);
    }

    public void addGround(String groundName) {
        addBlackVertex(cm.getSegment(groundName));
    }

    public void analyze() throws Exception {
        buildEdgesSets();
        int edgesCount = cm.getKPairsCount(),
                vertexesCount = cm.getSegmentsCount();
        while ((blackVertexes.size() != vertexesCount) && (blackEdges.size() != edgesCount)) {
            List<Segment> path = new LinkedList<Segment>();
            for (Segment s : blackVertexes) {
                path.add(s);
                findPath(path, 2);
                path.remove(path.size() - 1);
            }
            if (!newBlackVertexes.isEmpty()) {
                blackVertexes.addAll(newBlackVertexes);
                newBlackVertexes.clear();
            }
        }
    }

    public List<Group> getGroups() {
        return groups;
    }

    private double getDistDescartes(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    private double getConnectorsDist(Connector c1, Connector c2) {
        double ro1 = c1.getRo(), phi1 = c1.getPhi(),
                ro2 = c2.getRo(), phi2 = c2.getPhi();
        double x1 = ro1 * Math.cos(phi1),
                y1 = ro1 * Math.sin(phi1),
                x2 = ro2 * Math.cos(phi2),
                y2 = ro2 * Math.sin(phi2);
        return getDistDescartes(x1, y1, x2, y2);
    }

    private double getConnectorsDescartesX(Connector c) throws Exception {
        if (c.getType() == ConnectorType.CONNECTOR_TYPE_SLIDE) {
            ConnectorSlide cs = (ConnectorSlide) c;
            return cs.getLinear0().getX().getValue(0);
        }
        if (c.getType() == ConnectorType.CONNECTOR_TYPE_TURN) {
            ConnectorTurn ct = (ConnectorTurn) c;
            return ct.getLinear().getX().getValue(0);
        }
        return 0;
    }

    private double getConnectorsDescartesY(Connector c) throws Exception {
        if (c.getType() == ConnectorType.CONNECTOR_TYPE_SLIDE) {
            ConnectorSlide cs = (ConnectorSlide) c;
            return cs.getLinear0().getY().getValue(0);
        }
        if (c.getType() == ConnectorType.CONNECTOR_TYPE_TURN) {
            ConnectorTurn ct = (ConnectorTurn) c;
            return ct.getLinear().getY().getValue(0);
        }
        return 0;
    }

    private double getConnectorsDistDescartes(Connector c1, Connector c2) throws Exception {
        double x1 = getConnectorsDescartesX(c1),
                y1 = getConnectorsDescartesY(c1),
                x2 = getConnectorsDescartesX(c2),
                y2 = getConnectorsDescartesY(c2);
        return getDistDescartes(x1, y1, x2, y2);
    }

    public void testDistances() throws Exception {
        for (Segment s : cm.getSegments()) {
            for (Connector c1 : s.getConnectors()) {
                for (Connector c2 : s.getConnectors()) {
                    double distP = getConnectorsDist(c1, c2);
                    double distD = getConnectorsDistDescartes(c1, c2);
                    if (Math.abs(distP - distD) > Group.epsilon) {
                        throw new Exception("testDistances fails on " + c1.getName() + " - " + c2.getName()+" : distP = " + Double.toString(distP)+" distD = "+Double.toString(distD)+" difference = "+Double.toString(Math.abs(distP - distD)));
                    }
                }
            }
        }
    }
}


/* TODO
 * c1 c2 s
 * ro1=c1.getRo();
 * phi1=c1.getPhi();
 * 
 * ro2=c2.getRo();
 * phi2=c2.getPhi();
 * 
 * 
 * etalon=DistPol(ro1,phi1,ro2,phi2);
 * 
 * x1 = c1.getLinear().getX().getValue(0);
 * y1 = c1.getLinear().getY().getValue(0);
 * x2 = c2.getLinear().getX().getValue(0);
 * y2 = c2.getLinear().getY().getValue(0);
 * 
 *  
 * current=DistDec(x1,y1,x2,y2);
 * Equal(etalon,current);
 * 
 * TEST Angles
 * 
 * etalon = phi2-phi1;
 * a=sqrt((x1-xP)^2+(y1-yP)^2); // длины векторов
 * b=sqrt((x2-xP)^2+(y2-yP)^2);
 * current= acos((x1-xP)*(x2-xP)+
 *               (y1-yP)*(y2-yP))/
 *                  (|a|*|b|)); // из скаларного произведения
 * 
 * 
 * 
 */