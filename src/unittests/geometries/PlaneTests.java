package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;
import geometries.Plane;

/**
 * This class represents a set of JUnit tests for the Plane class. It includes
 * tests for the constructor, getNormal method, and the findIntersections
 * method.
 */
class PlaneTests {

	/**
	 * Tolerance for floating-point comparisons.
	 */
	private final double DELTA = 0.000001;

	/**
	 * Tests the constructor of the Plane class. Ensures that the constructor throws
	 * an exception for invalid points.
	 */
	@Test
	public void testConstructor() {

		// =============== Boundary Values Tests ==================

		// points converge
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(3, 7, 9)),
				"illegally constructed with converged points");

		// points align
		assertThrows(IllegalArgumentException.class,
				() -> new Plane(new Point(0, 0, 1), new Point(0, 0, 5), new Point(0, 0, 9)),
				"illegally constructed with aligned points");

	}

	/**
	 * Tests the getNormal method of the Plane class. Ensures that the normal vector
	 * is a unit vector and orthogonal to the plane.
	 */
	@Test
	void testGetNormalPoint() {

		Plane pl = new Plane(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0));

		// ============ Equivalence Partitions Tests ==============

		// test unit normal
		assertEquals(1, pl.getNormal().length(), DELTA, "Plane's normal is not a unit vector");

		// test orthogonal, 2 steps
		// the new Vectors are in the plane
		assertEquals(0, pl.getNormal().dotProduct(new Vector(1, -1, 0)), DELTA, "Plane's normal is not orthogonal 1");

		assertEquals(0, pl.getNormal().dotProduct(new Vector(0, -1, 1)), DELTA, "Plane's normal is not orthogonal 2");

	}

	/**
	 * Tests the findIntersections method of the Plane class. Ensures that the
	 * intersections between a ray and the plane are computed correctly.
	 */
	@Test
	public void testFindIntersections() {
		Plane plane = new Plane(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

		Point p001 = new Point(0, 0, 1);
		Point p0001 = new Point(0, 0, -1);
		Point p05050 = new Point(0.5, 0.5, 0);
		Point p111 = new Point(1, 1, 1);
		Point p1001 = new Point(1, 0, -1);
		Point p0333 = new Point(0.33333333333333326, 0.33333333333333326, 0.33333333333333326);

		Vector v123 = new Vector(1, 2, 3);
		Vector v111 = new Vector(1, 1, 1);
		Vector v001 = new Vector(0, 0, 1);
		Vector v0002 = new Vector(0, 0, -2);
		Vector v0111 = new Vector(-1, 1, 1);
		Vector v0101 = new Vector(-1, 0, 1);
		Vector v222 = new Vector(2, 2, 2);

		// ============ Equivalence Partitions Tests ==============
		// **** Group: Ray not orthogonal and not parallel the plane
		// TC01: Ray not orthogonal and not parallel the plane (1 point)
		final var result1 = plane.findIntersections(new Ray(p0001, v001));
		final var exp1 = List.of(p001);

		assertEquals(1, result1.size(), "Wrong number of points");
		assertEquals(exp1, result1, "Ray crosses the plane");

		// TC02: Ray not orthogonal and not parallel the plane (0 point)
		assertNull(plane.findIntersections(new Ray(p0001, v0002)), "Ray does not cross the plane");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray parallel to the plane
		// TC03: Ray parallel to the plane and on the plane (0 point)
		assertNull(plane.findIntersections(new Ray(p05050, v0111)), "Ray is parallel and on the plane");
		// TC04: Ray parallel to the plane and not on the plane (0 point)
		assertNull(plane.findIntersections(new Ray(p1001, v0101)), "Ray is parallel and not on the plane");

		// **** Group: Ray orthogonal to the plane
		// TC05: Ray orthogonal to the plane and starts before plane (1 point)
		final var result2 = plane.findIntersections(new Ray(new Point(-1, -1, -1), v111));
		final var exp2 = List.of(p0333);

		assertEquals(1, result2.size(), "Wrong number of points");
		assertEquals(exp2, result2, "Ray crosses the plane and orthogonal to the plane");

		// TC06: Ray orthogonal to the plane and starts on plane (0 point)
		assertNull(plane.findIntersections(new Ray(p0333, v111)), "Ray orthogonal to the plane and starts on plane");

		// TC07: Ray orthogonal to the plane and starts after plane (0 point)
		assertNull(plane.findIntersections(new Ray(p111, v222)), "Ray orthogonal to the plane and starts after plane");

		// **** Group: Ray not orthogonal and not parallel the plane
		// TC08: Ray not orthogonal and not parallel the plane, ray starts on ray
		// presented point(0 point)
		assertNull(plane.findIntersections(new Ray(p001, v111)),
				"Ray not orthogonal and not parallel the plane and starts on presented point");

		// TC09: Ray not orthogonal and not parallel the plane, ray starts plane(0
		// point)
		assertNull(plane.findIntersections(new Ray(p0333, v123)),
				"Ray not orthogonal and not parallel the plane and starts on plane");

	}

}
