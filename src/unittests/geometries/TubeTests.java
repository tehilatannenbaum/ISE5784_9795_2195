package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Tube;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Tube class. This class contains test methods for verifying
 * the correctness of the Tube's methods.
 */
class TubeTests {

	/**
	 * 
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 * 
	 * This method tests the correctness of the Tube's getNormal method.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Point on the tube surface (not edge case)
		Tube tube = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 0, 0)), 1);
		assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 0)), "tube normal is not correct");

		// =============== Boundary Values Tests ==================

		// TC02: Point on the surface where P - P0 is orthogonal to the direction
		assertEquals(new Vector(0, 1, 0), tube.getNormal(new Point(0, 1, 0)),
				"tube at orthogonal point normal is not correct");
	}

}
