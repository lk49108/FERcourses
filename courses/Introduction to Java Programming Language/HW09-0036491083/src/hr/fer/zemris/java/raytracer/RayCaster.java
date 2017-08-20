package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This program renders image containing two spheres
 * in front and other in back using phong's model.
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class RayCaster {

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
				
				short[] rgb = new short[3];
				int offset = 0;
				
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
				
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
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			
		};
	}
}
