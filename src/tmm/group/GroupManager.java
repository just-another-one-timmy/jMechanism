/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.group;

import java.util.*;
import tmm.compmanager.*;
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
    
    public void buildEdgesSets() {
        for (KPair k : cm.getKPairs()) {
            addToEdges(k.getC1().getSegment(), k);
            addToEdges(k.getC2().getSegment(), k);
        }
    }
    
    public void calcNextStep() throws Exception {
        // TODO: переделать gruop, чтобы можно было писать так:
        // for (int tf=0; tf<=2; tf++){
        //     for (Group g: gruops){
        //         g.calcTF(tf);
        //     }
        // }
        for (Group g : groups) {
            g.calcTF0();
        }
        for (Group g : groups) {
            g.calcTF1();
        }
        for (Group g : groups) {
            g.calcTF2();
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
        g.setJ1(mj2);
        groups.add(g);
    }
    
    public void addGround(String groundName) {
        addBlackVertex(cm.getSegment(groundName));
    }
    
    public void analyze() throws Exception {
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
}
