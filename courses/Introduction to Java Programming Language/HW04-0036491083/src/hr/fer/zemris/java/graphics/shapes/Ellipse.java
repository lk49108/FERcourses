package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This class implements ellipses.
 * Each ellipse is specified by its x, y coordinates
 * of its center and by its horizontal and vertical radius.
 * Ellipses with any radius smaller than 1 are not allowed.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class Ellipse extends GeometricShape{

	/**
	 * x coordinate of center of this ellipse.
	 */
	protected int x;
	
	/**
	 * y coordinate of center of this ellipse.
	 */
	protected int y;
	
	/**
	 * horizontal radius of this ellipse.
	 */
	protected int horRadius;
	
	/**
	 * vertical radius of this ellipse.
	 */
	protected int verRadius;
	
	/**
	 * This constructor creates an instance of this class(creates a new ellipse)
	 * according to parameters provided in it.
	 * @param x x coordinate of center of ellipse.
	 * @param y y coordinate of center of ellipse.
	 * @param horRadius Horizontal radius of ellipse.
	 * @param verRadius Vertical radius of ellipse.
	 * @throws IllegalArgumentException if any radius parameter is smaller than 1.
	 */
	public Ellipse(int x, int y, int horRadius, int verRadius) {
		if(horRadius < 1){
			throw new IllegalArgumentException("Ellipse must not have any radius smaller than 1.");
		}
		this.x = x;
		this.y = y;
		this.horRadius = horRadius;
		this.verRadius = verRadius;
	}

	@Override
	public void draw(BWRaster r){
		for(int y = this.y - this.verRadius + 1; y < this.y + this.verRadius; y++){
			for(int x = this.x - this.horRadius + 1; x < this.x + this.horRadius ; x++){
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
		if((x - this.x) * (x - this.x) * (this.verRadius - 1) * (this.verRadius - 1)  + (y - this.y) * (y - this.y) *(this.horRadius - 1) * (this.horRadius - 1) <= (this.horRadius- 1) * (this.horRadius - 1) * (this.verRadius - 1) * (this.verRadius - 1)){	
			return true;
		}
		return false;
	}
	
	/**
	 * Getter for x instance variable.
	 * @return x coordinate of center of this ellipse.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Setter for x instance variable.
	 * @param x x coordinate to which center of this ellipse is set.
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Getter for y instance variable.
	 * @return y coordinate of center of this ellipse.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Setter for y instance variable.
	 * @param y y coordinate to which center of this ellipse is set.
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Getter for horRadius instance variable.
	 * @return Horizontal radius of this ellipse.
	 */
	public int getHorRadius() {
		return horRadius;
	}

	/**
	 * Setter for horRadius instance variable.
	 * @param horRadius Horizontal radius to which is adjusted 
	 * horizontal radius of this ellipse.
	 * @throws IllegalArgumentException if horRadius is less than 1.
	 */
	public void setHorRadius(int horRadius) {
		if(horRadius < 1){
			throw new IllegalArgumentException("Ellipses must not have any radius smaler than 1.");
		}
		this.horRadius = horRadius;
	}

	/**
	 * Getter for verRadius instance variable.
	 * @return Vertical radius of this ellipse.
	 */
	public int getVerRadius() {
		return verRadius;
	}

	/**
	 * Setter for verRadius instance variable.
	 * @param verRadius Vertical radius to which is adjusted 
	 * vertical radius of this ellipse.
	 * @throws IllegalArgumentException if horRadius is less than 1.
	 */
	public void setVerRadius(int verRadius) {
		if(verRadius < 1){
			throw new IllegalArgumentException("Ellipses must not have any radius smaler than 1.");
		}
		this.verRadius = verRadius;
	}
	
	
}
