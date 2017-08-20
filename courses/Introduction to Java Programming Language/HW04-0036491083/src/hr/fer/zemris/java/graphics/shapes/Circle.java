package hr.fer.zemris.java.graphics.shapes;

/**
 * This class implements circles.
 * Each circle is specified by its x, y coordinates
 * of its center and by its radius.
 * Circles with radius smaller than 1 are not allowed.
 * Circle with radius 1 would be rendered as a single turned-on 
 * pixel in circle's center. Circle with radius 2 would be rendered
 * to with one additional pixel to the right and left (and top and bottom).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Circle extends Ellipse{
	
	/**
	 * This constructor creates an instance of this class(it
	 * creates a new circle).  
	 * @param x x coordinate of the center of circle that is being created.
	 * @param y y coordinate of the center of circle that is being created. 
	 * @param radius Radius of newly created circle.
	 * @throws IllegalArgumentException If radius parameter is smaller than 1.
	 */
	public Circle(int x, int y, int radius){
		super(x, y, radius, radius);
	}
	
	@Override
	public void setHorRadius(int radius) {
		if(horRadius < 1){
			throw new IllegalArgumentException("Ellipses must not have any radius smaler than 1.");
		}
		this.horRadius = radius;;
		this.verRadius = radius;
	}
	
	@Override
	public void setVerRadius(int radius) {
		if(verRadius < 1){
			throw new IllegalArgumentException("Ellipses must not have any radius smaler than 1.");
		}
		this.verRadius = radius;
		this.horRadius = radius;
	}
	
	
}
