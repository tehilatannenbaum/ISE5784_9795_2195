/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;
import geometries.Cylinder;


import org.junit.jupiter.api.Test;

/**
 * Unit tests for geometries.Cylinder class
 * 
 * @author Tehila and Maayan
 *
 */
class CylinderTests {

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		 Cylinder cylinder = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(0, 0, 1)), 1, 2);

	        // ============ Equivalence Partitions Tests ==============
	        
		    // Point on the round surface
	        assertEquals(
	        		new Vector(1, 0, 0), 
	        		cylinder.getNormal(new Point(1, 0, 1)), 
	        		"Bad normal to cylinder on round surface");

	        // Point on the bottom base
	        assertEquals(
	        		new Vector(0, 0, -1), 
	        		cylinder.getNormal(new Point(0.5, 0.5, 0)), 
	        		"Bad normal to cylinder on bottom base");

	        // Point on the top base
	        assertEquals(
	        		new Vector(0, 0, 1), 
	        		cylinder.getNormal(new Point(0.5, 0.5, 2)), 
	        		"Bad normal to cylinder on top base");

	        // =============== Boundary Values Tests ==================

	        // Point on the edge between the round surface and the bottom base
	        Vector normal = cylinder.getNormal(new Point(1, 0, 0));
	        assertTrue(
	        		normal.equals(new Vector(1, 0, 0)) || normal.equals(new Vector(0, 0, -1)), 
	        		"Bad normal to cylinder on edge of bottom base");

	        // Point on the edge between the round surface and the top base
	        normal = cylinder.getNormal(new Point(1, 0, 2));
	        assertTrue(
	        		normal.equals(new Vector(1, 0, 0)) || normal.equals(new Vector(0, 0, 1)), 
	        		"Bad normal to cylinder on edge of top base");

	        // Point in the center of the bottom base
	        assertEquals(
	        		new Vector(0, 0, -1), 
	        		cylinder.getNormal(new Point(0, 0, 0)), 
	        		"Bad normal to cylinder in center of bottom base");

	        // Point in the center of the top base
	        assertEquals(
	        		new Vector(0, 0, 1), 
	        		cylinder.getNormal(new Point(0, 0, 2)), 
	        		"Bad normal to cylinder in center of top base");
	}

}
