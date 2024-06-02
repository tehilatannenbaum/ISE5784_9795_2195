/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Sphere class
 * 
 * @author Tehila and Maayan
 *
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		  // ============ Equivalence Partitions Tests ==============

	      // Point on the surface of the sphere
	      Sphere sphere = new Sphere(new Point(0, 0, 0), 1);
	      
	      assertEquals(
	    		  new Vector(1, 0, 0), 
	    		  sphere.getNormal(new Point(1, 0, 0)), 
	    		  "normal to sphere is not correct");
	}

}
