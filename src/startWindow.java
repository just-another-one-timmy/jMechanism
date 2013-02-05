
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.Timer;

import tmm.XML.XMLWorker;
import tmm.compmanager.CompManager;
import tmm.group.GroupManager;
import tmm.visual.J2dVisualizer;

public final class startWindow extends JFrame {

    private CompManager cm = new CompManager();
    private GroupManager gm = new GroupManager(cm);
    private J2dVisualizer vis = new J2dVisualizer(cm);
    private static final Logger logger = Logger.getLogger(
            startWindow.class.getName());

    public void buildMechanizm(String name) throws Exception {

        new XMLWorker(cm, gm).loadFromFile(name);

        try {
            gm.calcNextStep();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Exception when calling calcNextStep()"
                    + " first time.", exception);
        }

        new Timer(10, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gm.makeStep();
                try {
                    gm.calcNextStep();
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Exception when calling "
                            + "calcNextStep()", ex);
                }
            }
        }).start();
    }

    public startWindow(String name) throws Exception {
        viewPanel vp = new viewPanel();
        vis.setUseNewCode(true);
        vis.setGraphics((Graphics2D) vp.getGraphics());
        vp.setVisualizer(vis);
        vis.setScalesAndTranslations(400.0, 400.0, 150, 200);
        add(vp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("jMechanizm: " + name);
        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        String name = "./xmls/T_TTT.xml";
        if (args.length > 0) {
            // Here should be good arg-parsing, now just taking filename.
            name = args[0];
        }
        startWindow s = new startWindow(name);
        s.buildMechanizm(name);
    }
}
