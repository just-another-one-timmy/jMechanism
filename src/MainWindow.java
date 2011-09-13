
import java.util.logging.*;
import processing.core.*;
import tmm.XML.XMLWorker;
import tmm.compmanager.*;
import tmm.group.*;
import tmm.visual.*;

public class MainWindow extends PApplet {

    private static CompManager cm = null;
    private static GroupManager gm = null;
    private static Visualizer vis = null;
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private static final String DEFAULT_MECHANISM = "xmls/swastika.xml";
    private static PGraphics pg = null;
    
    private void buildMechanism(String[] args) throws Exception {
        cm = new CompManager();
        gm = new GroupManager(cm);
        new XMLWorker(cm, gm).loadFromFile(args.length > 0 ? args[0] : DEFAULT_MECHANISM);
        gm.calcNextStep();
    }

    @Override
    public void setup() {
        try {
            size(800, 800);
            background(0);
            pg = createGraphics(800, 800, P2D);
            pg.smooth();
            vis = new ProcessingV(pg);
            vis.setCompManager(cm);
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw() {
        try {
            background(0);
            vis.draw();
            image(pg, 0, 0);
            gm.makeStep();
            gm.calcNextStep();
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void start(String[] args) throws Exception {
        buildMechanism(args);
        PApplet.main(new String[]{"--present", "MainWindow"});
    }

    public static void main(String[] args) {
        try {
            new MainWindow().start(args);
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
