
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import tmm.visual.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jtimv
 */
public class viewPanel extends JPanel implements ActionListener {
    
    double tq;
    J2dVisualizer v;
    
    public void setVisualizer(J2dVisualizer v) {
        this.v = v;
    }
    
    @Override
    public void paint(Graphics g) {
        try {
            v.setGraphics((Graphics2D)g);
            v.draw();
            g.dispose();
        } catch (Exception ex) {
            Logger.getLogger(viewPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        tq++;
        repaint();
    }
    
    public viewPanel() {
        setDoubleBuffered(true);
        new Timer(10, this).start();
    }
}
