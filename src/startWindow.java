import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.*;
import tmm.XML.*;
import tmm.compmanager.*;
import tmm.group.*;
import tmm.visual.*;

public final class startWindow extends JFrame {

    private CompManager cm = new CompManager();
    private GroupManager gm = new GroupManager(cm);
    private J2dVisualizer vis = new J2dVisualizer(cm);
    private Timer t;
    private static final Logger logger = Logger.getLogger(startWindow.class.getName());
    private int counter=0;

    public void buildMechanizm(String name) throws Exception {

        new XMLWorker(cm, gm).loadFromFile(name);

        try {
            gm.calcNextStep();
        } catch (Exception exception) {
            logger.log(Level.SEVERE, "Can't first calcNextStep()!", exception);
        }

        t = new Timer(50, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                counter = (counter + 1) % 10;
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
        vis.setUseNewCode(false);
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
        String name = "xmls/swastika.xml";
        if (args.length > 0){
            // there should be good arg-parsing, now just checking for filename
            name = args[0];
        }
        startWindow s = new startWindow(name);
        s.buildMechanizm(name);
    }
}
