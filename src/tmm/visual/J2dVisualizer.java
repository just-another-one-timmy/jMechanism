/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import tmm.compmanager.CompManager;

/**
 *
 * @author jtimv
 */
public class J2dVisualizer extends Visualizer {

    @Override
    public void draw() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public J2dVisualizer(CompManager c) {
        super(c);
        this.cm = c;
    }
}
