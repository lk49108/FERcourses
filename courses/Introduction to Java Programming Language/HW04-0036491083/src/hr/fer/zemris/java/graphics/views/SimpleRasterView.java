package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This class implements RasterView interface.
 * It is used for producing picture of raster.
 * It produces that picture by printing it out.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class SimpleRasterView implements RasterView{
	
	/**
	 *  This instance variable is used for storing Character which
	 *  will represent pixel that is
	 *  turned on.
	 */
	private final char turnedOnSign;
	
	/**
	 *  This instance variable is used for storing Character which
	 *  will represent pixel that is
	 *  turned off.
	 */
	private final char turnedOffSign;
	
	/**
	 * This constructor creates an instance of this class and 
	 * it also allows user to select which Character will represent
	 * pixels that are turned on and which will represent pixels that are turned off
	 * on a raster.
	 * @param turnedOn Character representation of turned on pixel. 
	 * @param turnedOff Character representation of turned off pixel. 
	 */
	public SimpleRasterView(char turnedOn, char turnedOff){
		this.turnedOnSign = turnedOn;
		this.turnedOffSign = turnedOff;
	}
	
	/**
	 * This is a default constructor which creates an instance of this class. It also
	 * delegates the call to first
	 * constructor and provides '*' and '.' as required characters.
	 */
	public SimpleRasterView(){
		this('*', '.');
	}
	
	@Override
	public Object produce(BWRaster raster){
		for(int y = 0; y < raster.getHeight(); y++){
			for(int x = 0; x < raster.getWidth(); x++){
				if(raster.isTurnedOn(x, y)){
					System.out.printf("%c ", turnedOnSign);
				}
				else{
					System.out.printf("%c ", turnedOffSign);
				}
			}
			System.out.println();
		}
		
		return null;
	}
}
