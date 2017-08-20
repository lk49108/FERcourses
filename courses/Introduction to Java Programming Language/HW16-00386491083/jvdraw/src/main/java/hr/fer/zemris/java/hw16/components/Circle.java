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
public class Circle extends GeometricalObject {

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
	 private JTextField colorTF;
	
	/**
	 * X-coordinate of the center of circle.
	 */
    private int x;
    
    /**
     * Y-coordinate of the center of circle.
     */
    private int y;
    
    /**
     * Radius of circle.
     */
    private int radius;
    
    /**
     * Color used for circle rendering, its borders at least.
     */
    private Color color;

    /**
     * Creates a new drawable circle
     * 
     * @param x x-coor of the center
     * @param y y-coor of the center
     * @param color color of the border
     */
    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Draws a circle with a colored border.
     */
    @Override
    public void paintComponent(Graphics g) {
        drawCircle(g, color, x, y, radius);
    }

    @Override
    public void drawShifted(Graphics g, int x, int y) {
        drawCircle(g, color, this.x - x, this.y - y, radius);
    }

    @Override
    public void updateObject(final JComponent comp) {
    	try {
    		x = Util.getInt(xTF.getText());
            y = Util.getInt(yTF.getText());
            radius = Util.getInt(radiusTF.getText());
            color = Util.getColor(colorTF.getText());
    	} catch (IllegalArgumentException ex){
    		JOptionPane.showMessageDialog(this, "Wrong parameters.", "Warning", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
        
        comp.repaint();
    }

    /**
     * Draws a circle in the given graphics with given color.
     * 
     * @param g graphics used
     * @param c color used
     * @param x center x
     * @param y center y
     * @param radius radius
     */
    private void drawCircle(Graphics g, Color c, int x, int y, int radius) {
        g.setColor(c);
        g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    @Override
    public Rectangle getBounds() {
        Point p = new Point(x - radius, y - radius);
        Dimension d = new Dimension(2 * radius, 2 * radius);
        return new Rectangle(p, d);
    }

    /**
     * Changes some point on the circle that determines the radius.
     */
    @Override
    public void changeEndPoint(Point ending) {
        radius = (int) new Point(x, y).distance(ending);
    }

    @Override
    public JPanel createMessage() {
        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(4, 1));
        JPanel right = new JPanel(new GridLayout(4, 1));

        main.add(left, BorderLayout.LINE_START);
        main.add(right, BorderLayout.CENTER);

        left.add(new JLabel("Center x-coor "));
        left.add(new JLabel("Start y-coor: "));
        left.add(new JLabel("Radius: "));
        left.add(new JLabel("Color: "));

        xTF = new JTextField();
        yTF = new JTextField();
        radiusTF = new JTextField();
        colorTF = new JTextField();

        right.add(xTF);
        right.add(yTF);
        right.add(radiusTF);
        right.add(colorTF);

        return main;
    }
    
    @Override
    public String toString() {
        return "CIRCLE " + x + " " + y + " " + radius + " " + color.getRed() + " " + color.getGreen() + " "
                + color.getBlue();
    }
}