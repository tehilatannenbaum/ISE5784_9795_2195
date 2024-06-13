package unittests.primitives;

import primitives.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

}
