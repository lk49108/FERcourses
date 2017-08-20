package her.fer.zemris.java.tests;
 

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
 
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
/**
 * This program demonstrates how to draw text vertically
 * on a graphics context.
 * @author Leonardo Kokot
 */
public class VerticalTextDemo extends JFrame {
     
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VerticalTextDemo() {
        super("smth");
         
        setSize(320, 280);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
     
    public void paint(Graphics g) {
        super.paint(g);
         
        g.drawString("Molim? :D", 100, 100);
         
        Graphics2D g2d = (Graphics2D) g;
        //Pamtimo poziciju
        AffineTransform defaultAt = g2d.getTransform();
         
        AffineTransform at = new AffineTransform();
        
        at.rotate(-Math.PI / 2);
        g2d.setTransform(at);
        g2d.drawString("Alo :D", -100, 40);
         
        AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(1);
        g2d.setTransform(at2);
         
        g2d.drawString("Hej :D", 120, -200);
         
        g2d.setTransform(defaultAt);
         
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	new VerticalTextDemo().setVisible(true);
        });
    }
 
}