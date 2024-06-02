/**
 * 
 */
package unittests;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;


/**
 * * * Unit tests for primitives.Point class
 * 
 * @author Tehila and Maayan
 *
 */
class PointTests {
	private final double DELTA = 0.000001;


	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}
	 * Tests the subtract of two points.
	 */
	@Test
	void testSubtract() {
		Point p = new Point(4,5,6);

		// ============ Equivalence Partitions Tests ==============

		// generic subtract test
		assertEquals(
				new Point(3,3,3), 
				p.subtract(new Point(1,2,3)),
				"ERROR: Point - Point does not work correctly");
		// assertEquals(expected, actual, message)

		// =============== Boundary Values Tests ==================

		// opposite subtract vector test
		assertThrows(
				IllegalArgumentException.class, 
				() -> p.subtract(p), 
				"ERROR: Point - itself does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 * Tests the add of vector and point.
	 */
	@Test
	void testAdd() {
		Point p = new Point(4,5,6);
		// ============ Equivalence Partitions Tests ==============

	    // generic subtract test
		assertEquals(
				new Point(5,7,9), 
				p.add(new Vector(1,2,3)),
				"ERROR: point + vector does not work correctly");
		
		 // =============== Boundary Values Tests ==================

	    // Adding a zero vector (result should be the original point)
		assertEquals(
				Point.ZERO, 
				p.add(new Vector(-1,-2,-3)),
				"ERROR: point + vector = does not work correctlycenter of coordinates does not work correctly");
		
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 * This method tests the calculation of the squared distance between two points.
	 */
	@Test
	void testDistanceSquared() {
		Point  p1 = new Point(1, 2, 3);
	    Point p2 = new Point(2,0,1);

	 // ============ Equivalence Partitions Tests ==============

	 // generic distance squared test
	 assertEquals(
			 9,
	         p1.distanceSquared(p2),
	         DELTA,
	         "ERROR: squared distance between points is wrong");

	 // opposite to generic distance squared test
	 assertEquals(
			 9,
	         p2.distanceSquared(p1),
	         DELTA,
	         "ERROR: squared distance between points is wrong");

	 // =============== Boundary Values Tests ==================

	 // distance from self test should be 0
	 assertEquals(
	          0,
	          p1.distanceSquared(p1),
	          DELTA,
	          "ERROR: point squared distance to itself is not zero");
	    }




	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 * This method tests the calculation of the distance between two points.
	 */
	@Test
	void testDistance() {
		 Point p1 = new Point(1, 2, 3);
		 Point p2 = new Point(4, 6, 8);

		 // ============ Equivalence Partitions Tests ==============
		 
		 // generic distance  test
		 assertEquals(
		            Math.sqrt(50),
		            p1.distance(p2),
		            DELTA,
		            "ERROR: distance between points is wrong");
		 
		 // opposite to generic distance test
		 assertEquals(
		            Math.sqrt(50),
		            p2.distance(p1),
		            DELTA,
		            "ERROR: distance between points is wrong");
		 
		 
		 // =============== Boundary Values Tests ==================
		 
		 //distance from a point to itself should be 0
		 assertEquals(
		            0,
		            p1.distance(p1),
		            DELTA,
		            "ERROR: point distance to itself is not zero");

		

	}

}






