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
 * Used to draw lines in {@link JVDraw}.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Line extends GeometricalObject {

	/**
	 * {@link JTextField} instance used in program execution. Describes one of the points of line.
	 */
	 private JTextField sxCorTF;
	   
	 /**
      * {@link JTextField} instance used in program execution. Describes one of the points of line.
	  */
	 private JTextField syCorTF;
	  
	 /**
      * {@link JTextField} instance used in program execution. Describes one of the points of line.
	  */
	 private JTextField exCorTF;
	 
	 /**
	  * {@link JTextField} instance used in program execution. Describes one of the points of line.
	  */
	 private JTextField eyCorTF;
	 
	 /**
	  * {@link JTextField} instance used in program execution. Describes one of the points of line.
	  */
	 private JTextField colorTF;
	
	/**
     * Color used for line rendering.
     */
    private Color color;

	
	/**
	 * First x-coordinate of this circle.
	 */
    private int x1Cor;
    /**
	 * First y-coordinate of this circle.
	 */
    private int y1Cor;
    
    /**
	 * Second x-coordinate of this circle.
	 */
    private int x2Cor;
    
    /**
	 * Second y-coordinate of this circle.
	 */
    private int y2Cor;
    
    
    /**
     * Creates a new drawable line
     * 
     * @param sxCor starting x-coor
     * @param syCor starting y-coor
     * @param exCor ending x-coor
     * @param eyCor ending y-coor
     * @param color color of the line
     */
    public Line(int sxCor, int syCor, int exCor, int eyCor, Color color) {
        this.x1Cor = sxCor;
        this.y1Cor = syCor;
        this.x2Cor = exCor;
        this.y2Cor = eyCor;
        this.color = color;
    }

    /**
     * Reads from text fields to update the line.
     */
    @Override
    public void updateObject(JComponent comp) {
    	try {
        x1Cor = Util.getInt(sxCorTF.getText());
        y1Cor = Util.getInt(syCorTF.getText());
        x2Cor = Util.getInt(exCorTF.getText());
        y2Cor = Util.getInt(eyCorTF.getText());
        color = Util.getColor(colorTF.getText());
    	} catch (IllegalArgumentException ex){
    		JOptionPane.showMessageDialog(this, "Wrong parameters.", "Warning", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
        comp.repaint();
    }

    /**
     * Changes the endpoint of the line.
     */
    @Override
    public void changeEndPoint(Point ending) {
        x2Cor = ending.x;
        y2Cor = ending.y;
    }

    /**
     * Returns the bounds of this line.
     */
    @Override
    public Rectangle getBounds() {
        Point p = new Point(Math.min(x1Cor, x2Cor), Math.min(y1Cor, y2Cor));
        Dimension d = new Dimension(Math.abs(x1Cor - x2Cor), Math.abs(y1Cor - y2Cor));
        return new Rectangle(p, d);
    }

    @Override
    public void drawShifted(Graphics g, int x, int y) {
        drawLine(g, color, x1Cor - x, y1Cor - y, x2Cor - x, y2Cor - y);
    }

    /**
     * Draws a colored line.
     */
    @Override
    public void paintComponent(Graphics g) {
        drawLine(g, color, x1Cor, y1Cor, x2Cor, y2Cor);

    }

    /**
     * Draws a line in given graphics with given color.
     * 
     * @param g graphics used
     * @param c color of the line
     * @param sx starting x
     * @param sy starting y
     * @param ex ending x
     * @param ey ending y
     */
    private void drawLine(Graphics g, Color c, int sx, int sy, int ex, int ey) {
        g.setColor(c);
        g.drawLine(sx, sy, ex, ey);
    }

    
    @Override
    public JPanel createMessage() {

        JPanel main = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new GridLayout(5, 1));
        JPanel right = new JPanel(new GridLayout(5, 1));

        main.add(left, BorderLayout.LINE_START);
        main.add(right, BorderLayout.CENTER);

        left.add(new JLabel("Start x-coor: "));
        left.add(new JLabel("Start y-coor: "));
        left.add(new JLabel("End x-coor: "));
        left.add(new JLabel("End x-coor: "));
        left.add(new JLabel("Color (r,g,b): "));

        sxCorTF = new JTextField();
        syCorTF = new JTextField();
        exCorTF = new JTextField();
        eyCorTF = new JTextField();
        colorTF = new JTextField();

        right.add(sxCorTF);
        right.add(syCorTF);
        right.add(exCorTF);
        right.add(eyCorTF);
        right.add(colorTF);

        return main;
    }

    @Override
    public String toString() {
        return "LINE " + x1Cor + " " + y1Cor + " " + x2Cor + " " + y2Cor + " " + color.getRed() + " "
                + color.getGreen() + " " + color.getBlue();
    }
}