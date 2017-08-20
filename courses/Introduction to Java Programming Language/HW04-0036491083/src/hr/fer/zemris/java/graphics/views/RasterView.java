package hr.fer.zemris.java.graphics.views;
import hr.fer.zemris.java.graphics.raster.BWRaster;

/**
 * This is single method interface.
 * Classes which implements this interface
 * are responsible for visualization of created images.
 * @author Leonardo Kokot
 * @version 1.0
 */
public interface RasterView {

	/**This is the only method in this
	 * interface. This method produces image of geometric shapes.
	 * @param raster Reference to BWRaster instance.
	 * @return String or null reference. If null reference is 
	 * returned then this method prints out produced picture by itself.
	 */
	public Object produce(BWRaster raster);
	
}
