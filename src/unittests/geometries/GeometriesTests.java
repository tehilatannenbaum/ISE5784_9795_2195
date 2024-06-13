/**

Unit tests for the Geometries class.
This class contains test methods for verifying the correctness of the Geometries' findIntersections method.
*/

package unittests;

import geometries.*;
import primitives.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeometriesTests {
	/**

	Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.

	This method tests various scenarios to ensure the correctness of the findIntersections method.
	*/
    @Test
    void testFindIntersections() {
		// ============ Equivalence Partitions Tests ==============
    	// TC01: Some shapes (but not all) are intersected

    	Geometries geometries = new Geometries(
                new Sphere(new Point(1, 1, 4), 1),
                new Triangle(new Point(0, 1, 2), new Point(1, 0, 2), new Point(2, 1, 0)),
                new Plane(new Point(1, 0, -1), new Point(2, 0, -1), new Point(1, 1, -1))
        );
    	Ray ray = new Ray(new Point(2, 2, 2), new Vector(-1, -1, 1)); 
        assertEquals(2, geometries.findIntersections(ray).size(), "Expected two intersections.");

		 // =============== Boundary Values Tests ==================
        // TC02: Empty collection
        geometries = new Geometries();
        ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
        assertEquals(null, geometries.findIntersections(ray), "Expected no intersections with an empty collection.");
   
        
        // TC03: No shape is intersected
        geometries = new Geometries(
                new Sphere(new Point(5, 5, 5), 1),
                new Triangle(new Point(0, 1, 2), new Point(1, 0, 2), new Point(2, 1, 0)),
                new Plane(new Point(0, 0, 1), new Point(1, 0, 1), new Point(0, 1, 1))
        );
        ray = new Ray(new Point(7, 7, 7), new Vector(1, 7, 7));
        assertEquals(null, geometries.findIntersections(ray), "Expected no intersections.");
        
        
        // TC04: Only one shape is intersected
    	geometries = new Geometries(
                new Sphere(new Point(1, 1, 4), 1),
                new Triangle(new Point(1, 0, 2), new Point(1, -1, 0), new Point(1, 1, 0)),
                new Plane(new Point(0, 0, 1), new Point(1, 0, 1), new Point(0, 1, 1))
        );
          // Ray that intersects only the plane
        ray = new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0)); 
        assertEquals(1, geometries.findIntersections(ray).size(), "Expected one intersection.");


        // TC05: All shapes are intersected
    	  geometries = new Geometries(
    	            new Sphere(new Point(1, 0, 2), 2),
    	            new Triangle(new Point(1, 0, 2),  new Point(1, -1, 0), new Point(1, 1, 0)),
    	            new Plane(new Point(3, 0, 0), new Point(3, 1, 0), new Point(3, 0, 1))
    	    );
          ray = new Ray(new Point(-1, 0, 1), new Vector(1, 0, 0)); 
    	    assertEquals(4, geometries.findIntersections(ray).size(), "Expected three intersections.");
    	}
    

    
}
