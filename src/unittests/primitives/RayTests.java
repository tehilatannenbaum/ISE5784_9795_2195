package unittests.primitives;

import primitives.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Unit tests for the Ray class.
 */
class RayTests {

	/**
	 * Test method for {@link primitives.Ray#getPoint(double)}. This test checks the
	 * calculation of a point on the ray at a positive, negative and zero distance.
	 */
	@Test
	void testGetPoint() {
		// Positive distance
		Ray ray1 = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
		Point expected1 = new Point(3, 1, 1);
		assertEquals(expected1, ray1.getPoint(2), "Expected point at distance 2 from ray origin.");

		// Negative distance
		Ray ray2 = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
		Point expected2 = new Point(-1, 1, 1);
		assertEquals(expected2, ray2.getPoint(-2), "Expected point at distance -2 from ray origin.");

		// Zero distance
		Ray ray3 = new Ray(new Point(1, 1, 1), new Vector(1, 0, 0));
		Point expected3 = new Point(1, 1, 1);
		assertEquals(expected3, ray3.getPoint(0), "Expected point at the origin of the ray.");

	}

	/**
	 * Tests the findClosestPoint method. This test verifies that the
	 * findClosestPoint method correctly identifies the point closest to a given
	 * reference point from a set of points.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray = new Ray(new Point(0, 0, 0), new Vector(1, 0, 0));
		Point p100 = new Point(1, 0, 0);
		Point p0300 = new Point(-3, 0, 0);
		Point p200 = new Point(2, 0, 0);

		// Test with a list containing points
		List<Point> points = new ArrayList<>();
		points.add(p0300);
		points.add(p100);
		points.add(p200);

		// ============ Equivalence Partitions Tests ==============
		// TC01: Middle point should be closest
		assertEquals(p100, ray.findClosestPoint(points));

		// =============== Boundary Values Tests ==================

		// TC02: First point should be closest
		points.clear();
		points.add(p100);
		points.add(p0300);
		points.add(p200);
		assertEquals(p100, ray.findClosestPoint(points));

		// TC03: Last point should be closest
		points.clear();
		points.add(p0300);
		points.add(p200);
		points.add(p100);

		assertEquals(p100, ray.findClosestPoint(points));

		// TC04: Test with empty list
		points.clear();
		assertNull(ray.findClosestPoint(points));
	}

}
