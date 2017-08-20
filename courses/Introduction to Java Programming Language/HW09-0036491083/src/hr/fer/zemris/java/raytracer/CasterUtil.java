package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;

/**
 * This class holds static methods used in {@link RayCaster} and
 * {@link RayCasterParallel} for calculation of vectors defining
 * screen, i.e. method {@link CasterUtil#setVectors(Point3D, Point3D, Point3D, double, double)}.
 * It also holds method {@link #tracer(Scene, Ray, short[])} which, using other methods
 * in this class processes each pixel on screen and determine its color through its rgb(which is set to adequate value by method).
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class CasterUtil {
	
	
	/**
	 * Static variable holding value for ambient component of rgb.
	 */
	private static final int AMBIENT_COMPONENT = 15;

	/**
	 * Calculates necessary vectors from other provided as parameters.
	 * Calculates unit vectors for x-axis, y-axis and z-axis and screenCorner(left-upper corner on the screen)
	 * and returns them organized in array.
	 * 
	 * @param eye Vector pointing to humans eye position.
	 * @param view View vector, also used as a source for coordinate system, i.e. its components are(0, 0, 0).
	 * @param viewUp Used for calculation of j unit vector.
	 * @param horizontal Distance, in unit vectors, from one side to other side of screen.
	 * @param vertical Distance, in unit vectors, from bottom to top of the screen.
	 * @return {@link Point3D} array containing, i, j, z unit vectors and screenCorner vector.
	 */
	public static Point3D[] setVectors(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical) {
		Point3D OG = view.difference(view, eye).normalize();
		Point3D vuv = viewUp.normalize();
		double ogvuv = OG.scalarProduct(vuv);
		Point3D yAxis = vuv.difference(vuv, OG.scalarMultiply(ogvuv)).normalize();
		Point3D xAxis = OG.vectorProduct(yAxis).normalize();
		Point3D zAxis = xAxis.vectorProduct(yAxis).normalize();
		
		Point3D help = new Point3D(0, 0, 0);
		Point3D help2 = help.modifySub(xAxis.scalarMultiply(horizontal/2));
		Point3D screenCorner = help2.add(yAxis.scalarMultiply(vertical/2));
		Point3D[] returnArray = new Point3D[4];
		returnArray[0] = xAxis;
		returnArray[1] = yAxis;
		returnArray[2] = zAxis;
		returnArray[3] = screenCorner;
		return returnArray;
	}
	
	/**
	 * Calculates, using helper methods, rgb of pixel on screen specified by intersection of provided
	 * ray and screen objects, if there is no intersection rgb is set to 0 for all components.
	 * @param scene Scene in which objects are placed.
	 * @param ray Ray for which is checked for existence of intersection.
	 * @param rgb rgb of specific pixel.
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) { 

		if(scene.getObjects() == null || scene.getObjects().isEmpty()){
			setRgbtoZero(rgb);
			return;
			}
		if(scene.getLights() == null || scene.getLights().isEmpty()){
			setRgbtoZero(rgb);
			return;
		}
		RayIntersection realIntersection = findIntersection(ray, scene);
		if(realIntersection == null){
			setRgbtoZero(rgb);
			return;
		}
		
		setAmbientComponent(rgb);
		
		Ray fromLightToIntersection = null;
		for(int i = 0; i < scene.getLights().size(); i++){
			LightSource light = scene.getLights().get(i);
			fromLightToIntersection = Ray.fromPoints(light.getPoint(), realIntersection.getPoint());
			RayIntersection unwantedIntersection = findIntersection(fromLightToIntersection, scene);
			if(unwantedIntersection != null && unwantedIntersection.getDistance() + 0.01 < light.getPoint().difference(light.getPoint(), realIntersection.getPoint()).norm()){
				continue;
			}
			modifyColor(rgb, light, realIntersection, ray);
		}
	}

	/**
	 * Method which, based on provided intersection, light source and ray pointing from viewers eye to
	 * specific pixel, updates rgb components of that same pixel which is situated at that intersection on screen.
	 * @param rgb rgb components of specific pixel.
	 * @param light Light source.
	 * @param intersection {@link RayIntersection} instance which is situated at specific pixel's position.
	 * @param ray Ray poitning from human eye to specific pixel which rgb components are updated in this method.
	 */
	private static void modifyColor(short[] rgb, LightSource light,RayIntersection intersection,
			Ray ray) {
	Ray fromPointToSource = Ray.fromPoints(intersection.getPoint(), light.getPoint());
	//skalarni produkt (l*n) = cos(a)...
	double ln = fromPointToSource.direction.normalize().scalarProduct(intersection.getNormal());
	//Not necessary to exist...
	if(ln < 0) ln = 0;
	//r * v...
	//r = 2 * cos(fi) * n - l, cos(fi) = l * n
	Point3D r = intersection.getNormal().scalarMultiply(2 * intersection.getNormal().scalarProduct(fromPointToSource.direction)).sub(fromPointToSource.direction).normalize();  
	//Pogledaj te subove jako dobro
	Point3D v = ray.direction.negate().normalize();
	//sad racunamo to do kraja...
	//r * v skalarni produkt...
	double rv = r.scalarProduct(v);
	rgb[0] = updateRed(rv, ln, light, intersection, rgb[0]);
	rgb[1] = updateGreen(rv, ln, light, intersection, rgb[1]);
	rgb[2] = updateBlue(rv, ln, light, intersection, rgb[2]);
	}

	/**
	 * Updates blue part of pixel's rgb.
	 * @param rv Part of expression.
	 * @param ln Part of expression.
	 * @param light Light for which in influence change of blue part of rgb is calculated.
	 * @param intersection {@link RayIntersection} instance representing part where this pixel is situated.
	 * @param s Current blue component of pixel's rgb.
	 * @return New blue component of pixel's rgb.
	 */
	private static short updateBlue(double rv, double ln, LightSource light, RayIntersection intersection, short s) {
		return (short) (s + light.getB() * (intersection.getKdb() * ln + intersection.getKrb() * Math.pow(rv, intersection.getKrn())));
	}

	/**
	 * Updates green part of pixel's rgb.
	 * @param rv Part of expression.
	 * @param ln Part of expression.
	 * @param light Light for which in influence change of green part of rgb is calculated.
	 * @param intersection {@link RayIntersection} instance representing part where this pixel is situated.
	 * @param s Current green component of pixel's rgb.
	 * @return New green component of pixel's rgb.
	 */
	private static short updateGreen(double rv, double ln, LightSource light, RayIntersection intersection, short s) {
		return (short) (s + light.getG() * (intersection.getKdg() * ln + intersection.getKrg() * Math.pow(rv, intersection.getKrn())));
	}

	/**
	 * Updates red part of pixel's rgb.
	 * @param rv Part of expression.
	 * @param ln Part of expression.
	 * @param light Light for which in influence change of red part of rgb is calculated.
	 * @param intersection {@link RayIntersection} instance representing part where this pixel is situated.
	 * @param s Current red component of pixel's rgb.
	 * @return New red component of pixel's rgb.
	 */
	private static short updateRed(double rv, double ln, LightSource light, RayIntersection intersection, short s) {
		return (short) (s + light.getR() * (intersection.getKdr() * ln + intersection.getKrr() * Math.pow(rv, intersection.getKrn())));
	}


	/**
	 * Method used for finding nearest(first) intersection of provided ray and any object in provided scene.
	 * @param ray {@link Ray} for which it is checked to have intersection with any object in provided {@link Scene}.
	 * @param scene {@link Scene}.
	 * @return {@link RayIntersection} instance representing found intersection. Null is returned if there is no
	 * intersection between provided {@link Ray} and {@link Scene} objects.
	 */
	private static RayIntersection findIntersection(Ray ray, Scene scene) {
		RayIntersection realIntersection = null;
		for(int i = 0; i < scene.getObjects().size(); i++){
			RayIntersection intersection = scene.getObjects().get(i).findClosestRayIntersection(ray);
			if(realIntersection == null){
				realIntersection = intersection;
			} else if(intersection != null && intersection.getDistance() < realIntersection.getDistance()){
				realIntersection = intersection;
			}
		}
		return realIntersection;
	}

	/**
	 * Sets all rgb components to zero.
	 * @param rgb array holding rgb components of pixel's color at specific point.
	 */
	private static void setRgbtoZero(short[] rgb){
		for(int i = 0; i < rgb.length; i++){
			rgb[i] = 0;
		}
	}
	
	/**
	 * Sets ambient components of rgb to value predefined
	 * by {@link #AMBIENT_COMPONENT}.
	 * @param rgb array holding rgb components of pixel's color at specific point.
	 */
	private static void setAmbientComponent(short[] rgb) {
		for(int i = 0; i < rgb.length; i++){
			rgb[i] = AMBIENT_COMPONENT;
		}
	}
}
