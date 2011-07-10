
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import tmm.visual.*;

public class viewPanel extends JPanel implements ActionListener {

    J2dVisualizer v;

    public void setVisualizer(J2dVisualizer v) {
        this.v = v;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setBackground(Color.BLACK);
        g.clearRect(0, 0, this.getWidth(), this.getHeight());
        try {
            v.setGraphics((Graphics2D) g);
            v.draw();
        } catch (Exception ex) {
            Logger.getLogger(viewPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public viewPanel() {
        setDoubleBuffered(true);
        new Timer(10, this).start();
    }
}
