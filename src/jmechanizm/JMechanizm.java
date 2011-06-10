/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmechanizm;

import tmm.compmanager.CompManager;
import tmm.group.GroupManager;

/**
 *
 * @author ArcSin
 */
public class JMechanizm {

    CompManager cm = new CompManager();
    GroupManager gm = new GroupManager(cm);

    void createMechanizm() {
        cm.addSegment("Ground", 10000, 0);

        cm.addConnectorSlideDescartes("s0c1", "Ground", 0.0, -0.150, Math.PI / 2);
        cm.addConnectorTurnDescartes("s0c2", "Ground", 0.20, 0.10);
        cm.addConnectorTurnDescartes("s0c3", "Ground", 0.20, 0.10);

        cm.addSegment("s1");
        cm.addConnectorTurnDescartes("s1c2", "s1", 0.0, 0.0);
        cm.addConnectorSlide("s1c1", "s1", 0.0, 0.0, 0);
        cm.addConnectorSlide("s1c3", "s1", 0.0, 0.0, Math.PI / 2);
        cm.addConnectorTurnDescartes("s1c4", "s1", 0.0, 0.0);

        cm.addSegment("s2");
        cm.addConnectorTurn("s2c1", "s2", 0, 0.0);
        cm.addConnectorTurn("s2c2", "s2", 0.3, 0.0);
        cm.addConnectorTurn("s2c3", "s2", 0.3, 0.0);


        cm.addSegment("s3");
        cm.addConnectorTurn("s3c1", "s3", 0, 0.0);
        cm.addConnectorTurn("s3c2", "s3", 0.3, 0.0);


        cm.addSegment("s4");
        cm.addConnectorTurn("s4c1", "s4", 0, 0);
        cm.addConnectorTurn("s4c2", "s4", 0.3, 0);
        cm.addConnectorTurn("s4c3", "s4", 0.3, 0);


        cm.addSegment("s5");
        cm.addConnectorTurn("s5c1", "s5", 0.0, 0);
        cm.addConnectorTurn("s5c2", "s5", 0.3, 0);

        cm.addSegment("s6");
        cm.addConnectorTurn("s6c1", "s6", 0, 0);
        cm.addConnectorSlide("s6c2", "s6", 0, 0, 0);

        cm.addSegment("s7");
        cm.addConnectorTurn("s7c1", "s7", 0, 0);
        cm.addConnectorSlide("s7c2", "s7", 0, 0, 0);
        cm.addConnectorSlide("s7c3", "s7", 0, 0, 0);

        cm.addSegment("s8");
        cm.addConnectorTurn("s8c1", "s8", 0, 0);
        cm.addConnectorSlide("s8c2", "s8", 0, 0, 0);

        cm.addSegment("s9");
        cm.addConnectorTurn("s9c1", "s9", 0, 0);
        cm.addConnectorSlide("s9c2", "s9", 0, 0, 0);


        cm.addKPairSlide("O", "s0c1", "s1c1");
        cm.addKPairTurn("A", "s1c2", "s2c1");
        cm.addKPairTurn("B", "s2c2", "s3c1");
        cm.addKPairTurn("S", "s3c2", "s0c2");

        cm.addKPairTurn("E", "s1c4", "s4c1");
        cm.addKPairTurn("D", "s4c2", "s5c1");
        cm.addKPairTurn("C", "s5c2", "s0c3");

        cm.addKPairSlide("G", "s6c2", "s7c2");
        cm.addKPairTurn("F", "s2c3", "s6c1");
        cm.addKPairTurn("H", "s4c3", "s7c1");

        cm.addKPairSlide("L", "s1c3", "s9c2");
        cm.addKPairTurn("M", "s9c1", "s8c1");
        cm.addKPairSlide("N", "s8c2", "s7c3");
        gm.setStepSize(0.005);
        gm.setGeneralCoordinateMinimum(0);
        gm.setGeneratlCoordinateMaximum(Math.PI * 2);
        gm.setJump(true);
        gm.buildEdgesSets();
        gm.addFirstGroupByName("O", "Ground", "s1");
        gm.analyze();
    }

    public static void main(String[] args) {
        new JMechanizm().createMechanizm();
    }
}
