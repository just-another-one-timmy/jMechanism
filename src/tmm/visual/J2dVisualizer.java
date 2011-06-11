/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmm.visual;

import java.awt.*;
import tmm.compmanager.CompManager;

/**
 *
 * @author jtimv
 */
public class J2dVisualizer extends Visualizer {

    private Graphics2D gr;

    @Override
    public void draw() {
        gr.drawString(Integer.toString((int) System.currentTimeMillis()), 260, 365);
    }

    public J2dVisualizer(CompManager c) {
        super(c);
        this.cm = c;
    }

    public void setGraphics(Graphics2D gr) {
        this.gr = gr;
    }
}
