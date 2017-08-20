package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This program renders image containing two spheres
 * in front and other in back using phong's model.
 * Parallelization is used while doing so.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class RayCasterParallel {
	
	/**
	 * Program starts its execution from this method.
	 * @param args Command-line arguments, not used here.
	 */
	public static void main(String[] args) {
			RayTracerViewer.show(getIRayTracerProducer(),
			new Point3D(10,0,0),
			new Point3D(0,0,0),
			new Point3D(0,0,10),
			20, 20);
		}
		
	/**
	 * This method is used for returning {@link IRayTracerProducer} which is 
	 * capable to create scene snapshots by using ray-tracing technique.
	 * @return {@link IRayTracerProducer} implementation object.
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
		
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
						double horizontal, double vertical, int width, int height,
						long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];
				
				Point3D[] vectors = CasterUtil.setVectors(eye, view, viewUp, horizontal, vertical);
				
				Point3D xAxis = vectors[0];
				Point3D yAxis = vectors[1];
				Point3D zAxis = vectors[2];
				Point3D screenCorner = vectors[3];
				
								
				Scene scene = RayTracerViewer.createPredefinedScene();
								
				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new WorkingJobRecursive(0, height - 1, height, width, 
						scene, screenCorner, xAxis, 
						yAxis, horizontal, vertical, eye, red, green, blue));
				pool.shutdown();
				
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
			
		};
	}
}
