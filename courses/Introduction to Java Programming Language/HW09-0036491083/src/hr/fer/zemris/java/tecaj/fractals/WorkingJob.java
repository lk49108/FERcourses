package hr.fer.zemris.java.tecaj.fractals;

import java.util.concurrent.Callable;

/**
 * As the name of this class say, it represents
 * some working job for thread.
 * This work is obtained in method {@link #call()}.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class WorkingJob implements Callable<Void> {

	/**
	 * Defines a minimum of real part
	 * of a complex space that will be shown on 
	 * desktop.
	 */
	double reMin;
	
	/**
	 * Defines a maximum of real part
	 * of a complex space that will be shown on 
	 * desktop.
	 */
	double reMax;
	
	/**
	 * Defines a minimum of imaginary part
	 * of a complex space that will be shown on 
	 * desktop.
	 */
	double imMin;
	
	/**
	 * Defines a maximum of imaginary part
	 * of a complex space that will be shown on 
	 * desktop.
	 */
	double imMax;
	
	
	/**
	 * Represents number of pixels (in width) that
	 * will be used to render a picture.
	 */
	int width;
	
	/**
	 * Represents number of pixels (in height) which
	 * will be used to render a picture.
	 */
	int height;
	
	/**
	 * Determines which part of picture will be rendered by thread instance 
	 * which holds specific value of this instance variable. Works in pair with 
	 * {@link #yMax}.
	 */
	int yMin;
	
	/**
	 * Determine which part of picture will be rendered by thread instance 
	 * which holds specific value of this instance variable.
	 * {@link #yMin}.
	 */
	int yMax;
	
	/**
	 * Reference to short values array which will hold data
	 * specifying what type of color will be used to color each pixel.
	 * This array is filled up by a thread which processes {@link #call()} method. 
	 */
	short[] data;

	/**
	 * @param reMin initializes {@link #reMin} instance variable.
	 * @param reMax initializes {@link #reMax} instance variable.
	 * @param imMin initializes {@link #imMin} instance variable.
	 * @param imMax initializes {@link #imMax} instance variable.
	 * @param width initializes {@link #width} instance variable.
	 * @param height initializes {@link #height} instance variable.
	 * @param yMin initializes {@link #yMin} instance variable.
	 * @param yMax initializes {@link #yMax} instance variable.
	 * @param data initializes {@link #data} instance variable.
	 */
	public WorkingJob(double reMin, double reMax, double imMin,
			double imMax, int width, int height, int yMin, int yMax, 
			short[] data) {
		super();
		this.reMin = reMin;
		this.reMax = reMax;
		this.imMin = imMin;
		this.imMax = imMax;
		this.width = width;
		this.height = height;
		this.yMin = yMin;
		this.yMax = yMax;
		this.data = data;
	}
	
	@Override
	public Void call() throws Exception {

		Newton_Raphson.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data);

		return null;
	}

	
}
