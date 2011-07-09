
import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.*;
import tmm.XML.*;
import tmm.compmanager.*;
import tmm.group.*;
import tmm.visual.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jtimv
 */
public final class startWindow extends JFrame {

    private CompManager cm = new CompManager();
    private GroupManager gm = new GroupManager(cm);
    private J2dVisualizer vis = new J2dVisualizer(cm);
    private Timer t;
    
    private final Logger logger = Logger.getLogger(startWindow.class.getName());

    public void buildMechanizm(String name) throws Exception {

        new XMLWorker(cm, gm).loadFromFile("xmls\\" + name + ".xml");

        try {
            gm.calcNextStep();
        } catch (Exception exception) {
        }

        t = new Timer(25, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gm.makeStep();
                try {
                    gm.calcNextStep();
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Can't calcNextStep()!", ex);
                }
            }
        });
        t.start();
    }

    public startWindow(String name) throws Exception {
        viewPanel vp = new viewPanel();
        vis.setGraphics((Graphics2D) vp.getGraphics());
        vp.setVisualizer(vis);
        vis.setScalesAndTranslations(400.0, 400.0, 150, 200);
        add(vp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("jMechanizm: "+name);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        String[] names = {"T_TTS_simple"};
        for (String x : names) {
            startWindow s = new startWindow(x);
            s.buildMechanizm(x);
        }
    }
}
