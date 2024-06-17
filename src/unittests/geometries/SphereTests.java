
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Sphere;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Sphere class.
 */
class SphereTests {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}. This
	 * test checks if the normal computation at a point on the sphere's surface is
	 * correct.
	 */
	@Test
	void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Point on the surface of the sphere
		Sphere sphere = new Sphere(new Point(0, 0, 0), 1);

		assertEquals(new Vector(1, 0, 0), sphere.getNormal(new Point(1, 0, 0)), "normal to sphere is not correct");
	}

	/**
	 * A reference point (1, 0, 0) used for various calculations and tests.
	 */
	private final Point p100 = new Point(1, 0, 0);

	/**
	 * A vector (0, 0, 1) used for various calculations and tests.
	 */
	private final Vector v001 = new Vector(0, 0, 1);

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 * This test checks various scenarios where a ray intersects with a sphere.
	 */

	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(p100, 1d);
		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp = List.of(gp1, gp2);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector v111 = new Vector(1, 1, 1);
		final Vector v200 = new Vector(2, 0, 0);
		final Vector v300 = new Vector(3, 0, 0);
		final Vector v010 = new Vector(0, 1, 0);
		final Vector v301 = new Vector(3, 0, 1);
		final Vector v0111 = new Vector(-1, 1, 1);
		final Vector v030 = new Vector(0, 3, 0);
		final Vector v020 = new Vector(0, 2, 0);
		final Vector v01050 = new Vector(-1, 0.5, 0);

		final Point p01 = new Point(-1, 0, 0);
		final Point p200 = new Point(2, 0, 0);
		final Point p1500 = new Point(1.5, 0, 0);
		final Point p2500 = new Point(2.5, 0, 0);
		final Point p0510 = new Point(0.5, 1, 0);
		final Point p210 = new Point(2, 1, 0);
		final Point p110 = new Point(1, 1, 0);
		final Point p300 = new Point(3, 0, 0);
		final Point p1020 = new Point(1, -2, 0);
		final Point p1010 = new Point(1, -1, 0);
		final Point p1050 = new Point(1, 0.5, 0);
		final Point p04080 = new Point(0.4, 0.8, 0);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p01, v111)), "Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		final var result1 = sphere.findIntersections(new Ray(p01, v310)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p01))).toList();

		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp, result1, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		var result2 = sphere.findIntersections(new Ray(p100, v010));
		final var exp2 = List.of(new Point(1, 1, 0));

		assertEquals(1, result2.size(), "Wrong number of points");
		assertEquals(exp2, result2, "Ray from inside sphere");

		// TC04: Ray starts after the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p01, v111)), "Ray from after sphere");

		// =============== Boundary Values Tests ==================
		// **** Group: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 points)
		final var result3 = sphere.findIntersections(new Ray(p200, v0111));
		final var exp3 = List.of(new Point(1.333333333333333, 0.6666666666666669, 0.6666666666666669));

		assertEquals(1, result3.size(), "Wrong number of points");
		assertEquals(exp3, result3, "Ray from sphere and goes inside");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(p200, v310)), "Ray from sphere and goes outside");

		// **** Group: Ray's line goes through the center
		// TC13: Ray starts before the sphere (2 points)
		final var result4 = sphere.findIntersections(new Ray(p1020, v030)).stream()
				.sorted(Comparator.comparingDouble(p -> p.distance(p1020))).toList();
		final var exp4 = List.of(p1010, p110);

		assertEquals(2, result4.size(), "Wrong number of points");
		assertEquals(exp4, result4, "Ray starts before the sphere, goes through center");

		// TC14: Ray starts at sphere and goes inside (1 points)
		final var result5 = sphere.findIntersections(new Ray(p1010, v020));
		final var exp5 = List.of(p110);

		assertEquals(1, result5.size(), "Wrong number of points");
		assertEquals(exp5, result5, "Ray starts at sphere and goes inside, goes through center");

		// TC15: Ray starts inside (1 points)
		final var result6 = sphere.findIntersections(new Ray(p1050, v01050));
		final var exp6 = List.of(p04080);

		assertEquals(1, result5.size(), "Wrong number of points");
		assertEquals(exp6, result6, "Ray starts inside sphere , goes through center");

		// TC16: Ray starts at the center (1 points)
		final var result7 = sphere.findIntersections(new Ray(p100, v200));
		final var exp7 = List.of(p200);

		assertEquals(1, result7.size(), "Wrong number of points");
		assertEquals(exp7, result7, "Ray starts at center of sphere and goes out");

		// TC17: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(p200, v300)),
				"Ray from sphere and goes outside, aligned with center");

		// TC18: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p2500, v300)),
				"Ray after sphere and goes outside, aligned with center");

		// **** Group: Ray's line is tangent to the sphere (all tests 0 points)
		// TC19: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(p210, v010)), "Ray tangent to sphere and starts before the point");

		// TC20: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(p110, v010)), "Ray tangent to sphere and starts at the point");

		// TC21: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(p0510, v010)), "Ray tangent to sphere and starts after the point");

		// **** Group: Special cases
		// TC22: Ray's line is outside, ray is orthogonal to sphere's center line,
		// starts outside (0 points)
		assertNull(sphere.findIntersections(new Ray(p300, v301)),
				"Ray's line is outside, ray is orthogonal to sphere's center line, starts outside");

		// TC23: Ray's line is outside, ray is orthogonal to sphere's center line,
		// starts inside (1 points)
		final var result8 = sphere.findIntersections(new Ray(p1500, v001));
		final var exp8 = List.of(new Point(1.5, 0, 0.8660254037844386));

		assertEquals(1, result8.size(), "Wrong number of points");
		assertEquals(exp8, result8, "Ray's line is outside, ray is orthogonal to sphere's center line, starts inside");
	}

}
