/**
 * 
 */
package hr.fer.zemris.java.gui.charts;

/**
 * This class has two read-only properties, 
 * x and y, both of type int.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class XYValue {

	/**
	 * First read-only property.
	 */
	private int x;
	
	/**
	 * Second read-only property.
	 */
	private int y;

	/**
	 * Constructor which creates instance of 
	 * this class and initializes instance variables..
	 * @param x integer which is joined to {@link #x} read-only property.
	 * @param y integer which is joined to {@link #y} read-only property.
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for {@link #x} read-only property.
	 * @return {@link #x}.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for {@link #y} read-only property.
	 * @return {@link #y}.
	 */
	public int getY() {
		return y;
	}

	
	
	
}
