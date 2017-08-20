package hr.fer.zemris.java.graphics.raster;

/**
 * This class implement BWRaster Interface.
 * It keeps all of its data in computer memory.
 * On creation of new objects of this class it is
 * expected that all pixels will be initially turned off(it is made like that 
 * by default). This class provides a single constructor which accepts
 * raster width and height.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class BWRasterMem implements BWRaster{
	
	/**
	 * This is an instance variable.
	 * It is a reference to two dimensional boolean array.
	 * It is initialized by constructor. 
	 * It is initialized to false (meaning that every pixel is
	 * turned off).
	 */
	private final boolean[][] raster;
	
	/**
	 * This instance variable represents height of this raster.
	 * It is initialized by constructor. 
	 */
	private final int height;
	
	/**
	 * This instance variable represents width of this raster.
	 * It is initialized by constructor. 
	 */
	private final int width;
	
	/**
	 * This is instance variable which holds value of flipping mode
	 * of this raster. Initially, it is initialized by constructor 
	 * to be DISABLED.
	 */
	 FlippingMode flipMode;
	
	/**
	 * Constructor which creates an instance of this class with appropriate
	 * raster dimensions. It initializes all instance variables as described in
	 * their javaDOC.
	 * @param width Width of this newly created raster.
	 * @param height Height of this newly created raster.
	 * @throws IllegalArgumentException if height or width are less than 1.
	 */
	public BWRasterMem(int width,int height){
		if(width < 1 || height < 1){
			throw new IllegalArgumentException();
		}
		raster = new boolean[width][height];
		this.height = height;
		this.width = width;
		flipMode = FlippingMode.DISABLED;
	}
	
	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void clear() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				raster[i][j] = false;
			}
		}
	}

	@Override
	public void turnOn(int x, int y) {
		if(flipMode == FlippingMode.ENABLED) {
			raster[x][y] = !raster[x][y];
		}
		else{
			raster[x][y] = true;
		}
	}

	@Override
	public void turnOff(int x, int y) {
		raster[x][y] = false;
	}

	@Override
	public void enableFlipMode() {
		flipMode = FlippingMode.ENABLED;
	}

	@Override
	public void disableFlipMode() {
		flipMode = FlippingMode.DISABLED;
	}

	@Override
	public boolean isTurnedOn(int x, int y) {
		if(raster[x][y] == true){
			return true;
		}
		return false;
	}
	
	
}
