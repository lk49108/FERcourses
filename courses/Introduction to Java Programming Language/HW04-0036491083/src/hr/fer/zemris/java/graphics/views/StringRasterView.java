package hr.fer.zemris.java.graphics.views;

import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This class implements RasterView interface.
 * It is used for producing picture of raster.
 * It produces that picture by creating new String using method produce which 
 * represents that picture and then it returns that same picture.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class StringRasterView implements RasterView{

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
	 * This instance variable is a representation of raster as an array of strings.
	 * It is initialized through method produce.
	 */
	private char[] stringRepr;
	
	/**
	 * This constructor creates an instance of this class and 
	 * it also allows user to select which Character will represent
	 * pixels that are turned on and which will represent pixels that are turned off
	 * on a raster.
	 * @param turnedOn Character representation of turned on pixel. 
	 * @param turnedOff Character representation of turned off pixel. 
	 */
	public StringRasterView(char turnedOn, char turnedOff){
		this.turnedOnSign = turnedOn;
		this.turnedOffSign = turnedOff;
	}
	
	/**
	 * This is a default constructor which creates an instance of this class. It also
	 * delegates the call to first
	 * constructor and provides '*' and '.' as required characters.
	 */
	public StringRasterView(){
		this('*', '.');
	}
	
	@Override
	public Object produce(BWRaster raster){
		stringRepr = new char[raster.getHeight() * (raster.getWidth() + 1) - 1];
		for(int y = 0; y < raster.getHeight(); y++){
			for(int x = 0; x < raster.getWidth() + 1; x++){
				//At the end of the last row we do not need to add '\n' Character.
				if(x == raster.getWidth() && y == raster.getHeight() - 1){
					break;
				}
				if(x == raster.getWidth()){
					stringRepr[y * (raster.getWidth() + 1) + x] = '\n';
				}
				else if(raster.isTurnedOn(x, y)){
					stringRepr[y * (raster.getWidth() + 1) + x] = turnedOnSign;
				}
				else{
					stringRepr[y * (raster.getWidth() + 1) + x] = turnedOffSign;
				}
			}
		}
		return new String(stringRepr);
	}
}
