package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents graphical object Sphere.
 * It extends {@link GraphicalObject} and implements its 
 * abstract method findClosestRayIntersection(Ray ray) method.
 * 
 * @author Leonardo Kokot
 * @version 1.0
 *
 */
public class Sphere extends GraphicalObject {
	
	/**
	 * This instance variable holds vector pointing to
	 * center of this Sphere. Vector is represented
	 * by {@link Point3D} object.
	 */
	 private Point3D center;
	
	/**
	 * This instance variable holds value of sphere's radius
	 * which must be positive.
	 */
	private double radius;
	
	/**
	 * This instance variable holds value of kdr, i.e.
	 * diffuse component for red color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double kdr;

	/**
	 * This instance variable holds value of kdg, i.e.
	 * diffuse component for green color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double kdg;
	
	/**
	 * This instance variable holds value of kdb, i.e.
	 * diffuse component for blue color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double kdb;
	
	/**
	 * This instance variable holds value of krr, i.e.
	 * reflective component for red color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double krr;
	
	/**
	 * This instance variable holds value of krg, i.e.
	 * reflective component for green color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double krg;
	
	/**
	 * This instance variable holds value of krb, i.e.
	 * reflective component for blue color; used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double krb;
	
	/**
	 * This instance variable holds value of coefficient <code>n</code>
	 * used as a part of reflective component used in
	 * lightning model to calculate point color.
	 * Legal values are [0.0,1.0].
	 */
	private double krn;
	
	/**
	 * This constructor creates instance of {@link Sphere} and initializes all of
	 * its instance variables.
	 * @param center Center of {@link Sphere}.
	 * @param radius Radius of {@link Sphere}.
	 * @param kdr see {@link #kdr}.
	 * @param kdg see {@link #kdg}.
	 * @param kdb see {@link #kdb}.
	 * @param krr see {@link #krr}.
	 * @param krg see {@link #krg}.
	 * @param krb see {@link #krb}.
	 * @param krn see {@link #krn}.
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
			if(center == null){
				throw new IllegalArgumentException("Center of sphere vector must not"
						+ " be null reference.");
			}
			this.center = center;
			if(radius <= 0){
				throw new IllegalArgumentException("Illegal value for Sphere radius: "
						+ radius + ".\nRadius should be positive integer.");
			}
			this.radius = radius;
			if(kdr < 0 || kdr > 1 || kdg < 0 || kdg > 1 || kdb < 0 || kdb > 1){
				throw new IllegalArgumentException("Diffuse component set: "
						+ kdr + ", " + kdg + ", " + kdb + " is illegal.");
			}
			this.kdr = kdr;
			this.kdg = kdg;
			this.kdb = kdb;
			if(krr < 0 || krr > 1 || krg < 0 || krg > 1 || krb < 0 || krb > 1){
				throw new IllegalArgumentException("Reflective component set: "
						+ krr + ", " + krg + ", " + krb + " is illegal.");
			}	
			this.krr = krr;
			this.krg = krg;
			this.krb = krb;
			if(krn < 0){
				throw new IllegalArgumentException("Coefficient n used in reflective"
						+ " component is illegal.");
			}
			this.krn = krn;	
			}
	
	
		@Override
	    public RayIntersection findClosestRayIntersection(Ray ray) {
			double determinant = calculateDeterminant(ray);
			//Intersection do not exist
			if(determinant < 0){
				return null;
			}
			Point3D startMinusSphCenter = ray.start.difference(ray.start, this.center);
			//We assume that ray is not traveling in both 
			//directions from its starting point, but only in one
			//specified by direction vector.
			double lambda = Math.abs(-ray.direction.scalarProduct(startMinusSphCenter) - Math.sqrt(determinant));
			double lambda2 = Math.abs(-ray.direction.scalarProduct(startMinusSphCenter) + Math.sqrt(determinant));
			if(lambda < lambda2){
				return new SphereRayIntersection(ray.start.add(ray.direction.scalarMultiply(lambda)), 
						lambda, true);
			} 
			return new SphereRayIntersection(ray.start.add(ray.direction.scalarMultiply(lambda2)), 
					lambda2, true);
		}


		/**
		 * Method used for calculation of determinant of
		 * expression used in calculation for finding closest intersection.
		 * @param ray {@link Ray} which is checked for having
		 * a intersection with this {@link Sphere}.
		 * @return
		 */
		private double calculateDeterminant(Ray ray) {
			Point3D startMinusSphCenter = ray.start.difference(ray.start, this.center);
			double b2 = Math.pow(startMinusSphCenter.scalarProduct(ray.direction), 2);
			double ac = startMinusSphCenter.scalarProduct(startMinusSphCenter) - this.radius * this.radius; 
			return b2 - ac;
		}
		
		/**
		 * This private class extends {@link RayIntersection} class.
		 * Instance of this class represents intersection of
		 * {@link Sphere} and specific {@link Ray}.
		 * 
		 * @author Leonardo Kokot
		 * @version 1.0
		 */
		private class SphereRayIntersection extends RayIntersection {

			/**
			 * Constructor which creates instance of {@link SphereRayIntersection}.
			 * @param point Point in coordinate system in at which this intersection is situated.
			 * @param distance Distance from starting point of array to intersection point.
			 * @param outer Boolean parameter which initializes outer instance variable and
			 * which if is set to true indicates that {@link SphereRayIntersection} is outer, i.e.
			 * {@link Ray} travels from outside of {@link Sphere} into {@link Sphere}.
			 */
		    private SphereRayIntersection(Point3D point, double distance, boolean outer) {
				super(point, distance, outer);
			}

			@Override
			public Point3D getNormal() {
				return this.getPoint().difference(this.getPoint(), center).normalize();
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKrn() {
				return krn;
			}
			
		}
}
