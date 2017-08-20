package hr.fer.zemris.java.graphics.raster;

/**
 * This interface is used as a core for raster devices.
 * Letters BW stand for Black-and-White raster.
 * This is an abstraction for all raster devices 
 * of fixed width and height for which each pixel can be painted with only
 * two colors: black (when pixel is turned off) and 
 * white (when pixel is turned on). The coordinate system for 
 * raster has (0,0) at the top-left corner of raster;
 * positive x-axis is to the right and positive y-axis is toward the bottom.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface BWRaster {
	
	/**
	 * This method returns width of used raster.
	 * @return Width of used raster.
	 */
	int getWidth();
	
	/**
	 * This method returns height of used raster.
	 * @return Height of used raster.
	 */
	int getHeight();
	
	/**
	 * This method turns off all all pixels in raster.
	 */
	void clear();
	
	/**
	 * Working of this method is closely 
	 * controlled with flipping mode of raster.
	 * If flipping mode of raster is disabled, then the call
	 * of this method turns on the pixel at specified 
	 * location(if location is valid). 
	 * If flipping mode is enabled, then the call of this
	 *  method flips the pixel at the specified location
	 *  (if it was turned on, it must be turned off, and if it 
	 *  was turned off, it must be turned on).
	 * @param x x coordinate of pixel on which this method is executed.
	 * @param y y coordinate of pixel on which this method is executed.
	 * @throws IllegalArgumentException if (x, y) is invalid with respect to 
	 * raster dimensions.
	 */
	void turnOn(int x, int y);
	
	/**
	 * This method turns off pixel at a given location if given location is valid. 
	 * @param x x coordinate of pixel on which this method is executed.
	 * @param y y coordinate of pixel on which this method is executed.
	 * @throws IllegalArgumentException if (x, y) is invalid with respect to 
	 * raster dimensions.
	 */
	void turnOff(int x, int y);
	
	/**
	 * This method control the flipping mode which is initially disabled.
	 */
	void enableFlipMode();
	
	/**
	 * This method control the flipping mode which is initially disabled
	 */
	void disableFlipMode();
	
	/**
	 * This method checks if the pixel at the given location is turned on 
	 * and accordingly returns appropriate value.
	 * @param x x coordinate of pixel on which this method is executed.
	 * @param y  y coordinate of pixel on which this method is executed.
	 * @return  Returns true if the pixel at the given location
	 * is turned on, otherwise returns false.
	 * @throws IllegalArgumentException if (x, y) is invalid with respect to 
	 * raster dimensions.
	 */
	boolean isTurnedOn(int x, int y);
}
