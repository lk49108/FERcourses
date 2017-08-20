package hr.fer.zemris.java.graphics.shapes;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This is an abstract class with two methods.
 * It is used as a root class for all geometric shapes.
 * @author Leonardo Kokot
 * @version 1.0
 */
public abstract class GeometricShape {

	 /**
	  * Method which is used for drawing
      * geometric shape on a raster.
	  * @param r Reference to an instance of BWRaster,
	  * in which pixel are contained.
	  */
	public void draw(BWRaster r){
		for(int y = 0; y < r.getHeight(); y++){
			for(int x = 0; x < r.getWidth(); x++){
				if(this.containsPoint(x, y)){
					if(x >= 0 && y >= 0){
						r.turnOn(x, y);
					}
				}
			}
		}
	}
	
	/**
	 * Method which is returns true if a given pixel(x, y) is contained in
	 * a specified geometric shape, and it 
	 * returns false otherwise.
	 * @param x X coordinate of pixel on which this method is executed.
	 * @param y Y coordinate of pixel on which this method is executed.
	 */
	public abstract boolean containsPoint(int x, int y);
}
