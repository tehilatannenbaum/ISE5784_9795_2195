/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Tube;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import org.junit.jupiter.api.Test;

/**
 * * Unit tests for geometries.Tube class
 * 
 * @author Tehila and Maayan
 *
 */
class TubeTests {
	private final double DELTA = 0.000001;

	
	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		  // ============ Equivalence Partitions Tests ==============

	      // Point on the tube surface (not edge case)
	      Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1);
	      assertEquals(
	    		  new Vector(0, 1, 0), 
	    		  tube.getNormal(new Point(0, 1, 0)), 
	    		  "tube normal is not correct");

	      // =============== Boundary Values Tests ==================

	      // Point on the surface where P - P0 is orthogonal to the direction
	      assertEquals(
	    		  new Vector(0, 1, 0), 
	    		  tube.getNormal(new Point(0, 1, 0)), 
	    		  "tube at orthogonal point normal is not correct");
        }

}
