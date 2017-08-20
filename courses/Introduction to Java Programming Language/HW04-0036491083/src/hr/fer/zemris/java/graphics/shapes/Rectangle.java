package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This class implements rectangles.
 * Each rectangle is specified by its x, y coordinates
 * of its top-left corner and by its width and height.
 * Rectangles of width or height equal
 * to zero are not allowed (and they would be invisible).
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Rectangle extends GeometricShape{

	/**
	 * x coordinate of top-left corner of this rectangle.
	 */
	protected int x;
	
	/**
	 * y coordinate of top-left corner of this rectangle.
	 */
	protected int y;
	
	/**
	 * Width of this rectangle.
	 */
	protected int width;
	
	/**
	 * Height of this rectangle.
	 */
	protected int height;
	
	/**
	 * This constructor creates an instance of a
	 * Rectangle. 
	 * @param x x coordinate of top-left corner of this rectangle.
	 * @param y y coordinate of top-left corner of this rectangle.
	 * @param width Width of rectangle.
	 * @param height Height of a rectangle.
	 * @throws IllegalArgumentException if width or height that are provided is less than 1.
	 */
	public Rectangle(int x, int y, int width, int height){
		if(width < 1 || height < 1){
			throw new IllegalArgumentException("Rectangles with height or width less than 1 are not allowed.");
		}
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(BWRaster r){
		for(int y = this.y; y < this.y + this.height; y++){
			for(int x = this.x; x < this.x + this.width; x++){
				if(this.containsPoint(x, y)){
					if(x >= 0 && y >= 0){
						r.turnOn(x, y);
					}
				}
			}
		}
	}
	
	@Override
	public boolean containsPoint(int x, int y){
		if(x >= this.x + this.width){
			return false;
		}
		if(x < this.x){
			return false;
		}
		if(y < this.y){
			return false;
		}
		if(y >= this.y + this.height){
			return false;
		}
		return true;
	}
	
	/**
	 * Getter for x instance variable of this rectangle.
	 * @return x instance variable.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x instance variable of this rectangle.
	 * @param x x coordinate of top-left corner of this rectangle.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for y instance variable of this rectangle.
	 * @return y instance variable.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for x instance variable of this rectangle.
	 * @param y y coordinate of top-left corner of this rectangle.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter for width instance variable of this rectangle.
	 * @return width instance variable.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Setter for width instance variable of this rectangle.
	 * @param width Width to which this rectangle is set.
	 * @throws IllegalArgumentException if width provided is less than 1.
	 */
	public void setWidth(int width) {
		if(width < 1){
			throw new IllegalArgumentException("Rectangles with width less than 1 are not allowed.");
		}
		this.width = width;
	}

	/**
	 * Getter for height instance variable of this rectangle.
	 * @return height instance variable.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter for height instance variable of this rectangle.
	 * @param height Height to which this rectangle is set.
	 * @throws IllegalArgumentException provided if height is less than 1.
	 */
	public void setHeight(int height){
		if(height < 1){
			throw new IllegalArgumentException("Rectangles with height less than 1 are not allowed.");
		}
		this.height = height;
	}
}
