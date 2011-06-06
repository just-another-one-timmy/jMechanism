/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jmechanizm;

import tmm.compmanager.CompManager;
import tmm.segment.Segment;

/**
 *
 * @author jtimv
 */
public class JMechanizm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Here we are");
        Segment s = new Segment("name").setMass(10).setRotInertia(20);
        CompManager cm = new CompManager();
        cm.addSegment("f", Math.PI).setRotInertia(1);
    }
}
