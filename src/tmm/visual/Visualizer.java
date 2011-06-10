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
public abstract class Visualizer {

    protected boolean debugMode;
    protected CompManager cm;

    public Visualizer(CompManager c) {
        cm = c;
    }

    public abstract void draw();

    public final void setDebugMode(boolean mode) {
        debugMode = mode;
    }

    public final boolean getDebugMode() {
        return debugMode;
    }

    public final void setCompManager(CompManager c) {
        cm = c;
    }
}
