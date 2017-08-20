package hr.fer.zemris.java.hw16.components;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw16.util.Util;

/**
 * Used to draw circles in {@link JVDraw}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FCircle extends GeometricalObject {

	/**
	 * {@link JTextField} instance used in program execution. Describes one of the points of circle.
	 */
    private JTextField xTF;
	 
    /**
	 * {@link JTextField} instance used in program execution. Describes one of the points of circle.
	 */
    private JTextField yTF;
	 
    /**
	 * {@link JTextField} instance used in program execution. Describes one of the points of circle.
	 */
	private JTextField radiusTF;
	   
	/**
	 * {@link JTextField} instance used in program execution. Describes one of the points of circle.
	 */
	private JTextField fColorTF;
	   
	/**
	 * {@link JTextField} instance used in program execution. Describes one of the points of circle.
	 */
	private JTextField bColorTF;
	
	/**
	 * X-coordinate of center of this circle.
	 */
    private int x;
    
    /**
	 * Y-coordinate of center of this circle.
	 */
    private int y;
    
    /**
     * Radius of this circle.
     */
    private int radius;
    
    /**
     * Foreground color of this circle.
     */
    private Color fColor;
    
    /**
     * Background color of this circle.
     */
    private Color bColor;

    /**
     * Creates a new drawable circle
     * 
     * @param x x-coor of the center
     * @param y y-coor of the center
     * @param fColor color of the border
     * @param bColor color of the inner circle
     */
    public FCircle(int x, int y, int radius, Color fColor, Color bColor) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.fColor = fColor;
        this.bColor = bColor;
    }

    /**
     * Draws a circle with a colored border.
     */
    @Override
    public void paintComponent(Graphics g) {
        drawCircle(g, fColor, bColor, x, y, radius);
    }

    @Override
    public void drawShifted(Graphics g, int x, int y) {
        drawCircle(g, fColor, bColor, this.x - x, this.y - y, radius);
    }

    /**
     * Draws a filled circle.
     * 
     * @param g used graphics
     * @param f foreground color
     * @param b background color
     * @param x center x
     * @param y center y
     * @param radius radius
     */
    private void drawCircle(Graphics g, Color f, Color b, int x, int y, int radius) {
        g.setColor(f);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
        g.setColor(b);
        g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public Rectangle getBounds() {
        Point p = new Point(x - radius, y - radius);
        Dimension d = new Dimension(2 * radius, 2 * radius);
        return new Rectangle(p, d);
    }

    @Override
    public JPanel createMessage() {
        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(5, 1));
        JPanel right = new JPanel(new GridLayout(5, 1));

        main.add(left, BorderLayout.LINE_START);
        main.add(right, BorderLayout.CENTER);

        left.add(new JLabel("Center x-coor "));
        left.add(new JLabel("Start y-coor: "));
        left.add(new JLabel("Radius: "));
        left.add(new JLabel("Foregnd color: "));
        left.add(new JLabel("Backgnd color: "));

        xTF = new JTextField();
        yTF = new JTextField();
        radiusTF = new JTextField();
        fColorTF = new JTextField();
        bColorTF = new JTextField();

        right.add(xTF);
        right.add(yTF);
        right.add(radiusTF);
        right.add(fColorTF);
        right.add(bColorTF);

        return main;
    }

    @Override
    public void updateObject(final JComponent comp) {
    	try {
    		 x = Util.getInt(xTF.getText());
    	        y = Util.getInt(yTF.getText());
    	        radius = Util.getInt(radiusTF.getText());
    	        fColor = Util.getColor(fColorTF.getText());
    	        bColor = Util.getColor(bColorTF.getText());
    	} catch (IllegalArgumentException ex){
    		JOptionPane.showMessageDialog(this, "Wrong parameters.", "Warning", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
       
        comp.repaint();
    }

    @Override
    public void changeEndPoint(Point ending) {
        radius = (int) new Point(x, y).distance(ending);
    }


    @Override
    public String toString() {
        return "FCIRCLE " + x + " " + y + " " + radius + " " + fColor.getRed() + " " + fColor.getGreen() + " "
                + fColor.getBlue() + " " + bColor.getRed() + " " + bColor.getGreen() + " " + bColor.getBlue();
    }
}