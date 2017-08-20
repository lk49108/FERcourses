package hr.fer.zemris.java.raytracer;

import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * This class is used to provide recursive 
 * action of pixel coloring. It implements {@link RecursiveAction}
 * interface. "Real" job which threads do is done calling method
 * {@link #computeDirect()}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class WorkingJobRecursive extends RecursiveAction {

	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

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
	 * Represents number of pixels (in height) which
	 * will be used to render a picture.
	 */
	int height;
	
	
	/**
	 * Represents number of pixels (in width) that
	 * will be used to render a picture.
	 */
	int width;
	
	 /**
	 * Represents scene which will be shown.
	 */
	private Scene scene;
	
	/**
	 * Represents {@link Point3D} vector
	 * of screen corner.
	 */
	private Point3D screenCorner;

	/**
	 * i unit vector, i.e. x-axis unit vector.
	 */
	private Point3D xAxis;

	/**
	 * Represents horizontal size of a scene(screen), in unit vectors.
	 */
	private double horizontal;

	/**
	 * Represents vertical size of a scene(screen), in unit vectors.
	 */
	private double vertical;

	/**
	 * j unit vector, i.e. y-axis unit vector.
	 */
	private Point3D yAxis;

	/**
	 * {@link Point3D} vector pointing to observers(users) eye.
	 */
	private Point3D eye;
	
	/**
	 * Array containing red part of a pixel's color.
	 */
	short[] red;
	
	/**
	 * Array containing green part of a pixel's color.
	 */
	short[] green;
	
	/**
	 * Array containing blue part of a pixel's color.
	 */
	short[] blue;
	
	/**
	 * Static variable which determines how many y-lines of a screen will be processed by specific {@link Thread}.
	 */
	private static final int THRESHOLD = 16;
	
	/**
	 * Constructor which creates instance of {@link WorkingJobRecursive}.
	 * @param yMin see {@link #yMin}.
	 * @param yMax see {@link #yMax}.
	 * @param height see {@link #height}.
	 * @param width see {@link #width}.
	 * @param scene see {@link #scene}.
	 * @param screenCorner see {@link #screenCorner}.
	 * @param xAxis see {@link #xAxis}.
	 * @param yAxis see {@link #yAxis}.
	 * @param horizontal see {@link #horizontal}.
	 * @param vertical see {@link #vertical}.
	 * @param eye see {@link #eye}.
	 * @param red see {@link #red}.
	 * @param green see {@link #green}.
	 * @param blue see {@link #blue}.
	 */
	public WorkingJobRecursive(int yMin, int yMax, int height, int width, 
			Scene scene, Point3D screenCorner, Point3D xAxis, 
			Point3D yAxis, double horizontal, double vertical, Point3D eye,
			short[] red, short[] green, short[] blue){
		this.yMin = yMin;
		this.yMax = yMax;
		this.height = height;
		this.width = width;
		this.scene = scene;
		this.screenCorner= screenCorner;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.eye = eye;
		this.blue = blue;
		this.red = red;
		this.green = green;
	}
	
	@Override
	protected void compute() {
		if(yMax - yMin + 1 <= THRESHOLD){
			computeDirect();
			return;
		}
		invokeAll(
				new WorkingJobRecursive(yMin, yMin + (yMax - yMin)/2, height, width, 
						scene, screenCorner, xAxis, 
						yAxis, horizontal, vertical, eye, red, green, blue),
				new WorkingJobRecursive(yMin + (yMax - yMin) / 2 + 1, yMax, height, width, 
						scene, screenCorner, xAxis, 
						yAxis, horizontal, vertical, eye, red, green, blue)
		);
	}

	/**
	 * Through this method thread does "real" job which is to be done, i.e.
	 * specifies colors of pixels which will render picture of a provided {@link #scene}.
	 */
	private void computeDirect() {
		int offset = yMin * width;
		short[] rgb = new short[3];
		for(int y = yMin; y <= yMax; y++){
			for(int x = 0; x < width; x++){
				Point3D screenPoint = screenCorner.add(
						xAxis.scalarMultiply(
								(x * horizontal) / (width - 1)));
				screenPoint = screenPoint.sub(
						yAxis.scalarMultiply((vertical * y) / (height - 1)));

				Ray ray = Ray.fromPoints(eye, screenPoint);
				
				CasterUtil.tracer(scene, ray, rgb);
				
				red[offset] = rgb[0] > 255 ? 255 : rgb[0];
				green[offset] = rgb[1] > 255 ? 255 : rgb[1];
				blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
				offset++;
			}
		}
	}

}
