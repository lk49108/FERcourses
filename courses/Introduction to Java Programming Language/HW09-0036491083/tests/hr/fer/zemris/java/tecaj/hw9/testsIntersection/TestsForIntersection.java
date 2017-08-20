package hr.fer.zemris.java.tecaj.hw9.testsIntersection;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Sphere;

/**
 * JUnit test for intersection
 * of specific {@link Ray} and {@link Sphere}.
 * @author Leonardo Kokot
 * @version 1.0
 */
public class TestsForIntersection {

	
    /**
     * Constant used for checking if two double values
     * are equal.
     */
    private static final double DIFERRENCE = 1E-5;
    
    
    /**
     * Test for ray-sphere intersection.
     */
    @Test
    public void closestIntersection_test1() {
            Ray ray = Ray.fromPoints(new Point3D(0, 0, 10), new Point3D(0, 0, 0));
            Sphere sphere = new Sphere(new Point3D(0, 0, 0), 1, 1, 1, 1, 0.5,
                            0.5, 0.5, 10);
            RayIntersection intersection = sphere.findClosestRayIntersection(ray);
           
            assertEquals(0, intersection.getPoint().x, DIFERRENCE);
            assertEquals(0, intersection.getPoint().y, DIFERRENCE);
            assertEquals(1.0, intersection.getPoint().z, DIFERRENCE);
            
            assertTrue(intersection.getKdr() == 1);
            assertTrue(intersection.getKdg() == 1);
            assertTrue(intersection.getKdb() == 1);
            assertTrue(intersection.getKrr() == 0.5);
            assertTrue(intersection.getKrg() == 0.5);
            assertTrue(intersection.getKrb() == 0.5);
           
    }

   
}
