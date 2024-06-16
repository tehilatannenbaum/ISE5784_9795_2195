/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;
import primitives.Double3;
import org.junit.jupiter.api.Test;

/**
 * * Unit tests for primitives.Vector class
 * 
 * 
 */
class VectorTests {
	/**
	 * Tolerance for floating-point comparisons.
	 */
	private final double DELTA = 0.000001;

	/**
	 * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}. This
	 * method tests the creation of a Vector
	 */
	@Test
	public void testConstructor() {

		// ============ Equivalence Partitions Tests ==============

		// test actor based on coordinates
		assertDoesNotThrow(() -> new Vector(1, 2, 3), "Failed constructing a correct vector");

		// TC: test actor based on type Double3
		assertDoesNotThrow(() -> new Vector(new Double3(4, 5, 6)), "Failed constructing a correct vector");

		// =============== Boundary Values Tests ==================

		// TC: illegal ZERO vector, based on coordinates
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"Failed - Zero vector constructed, by coords");

		// TC: illegal ZERO vector, based on Double3
		assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0, 0, 0)),
				"Failed - Zero vector constructed, by Double3");

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}. Tests the addition
	 * of two vectors.
	 */
	@Test
	void testAdd() {
		Vector vec = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============

		// generic add test
		assertEquals(new Vector(5, 7, 9), vec.add(new Vector(4, 5, 6)),
				"ERROR: Vector + Vector does not work correctly");
		// assertEquals(expected, actual, message)

		// =============== Boundary Values Tests ==================

		// opposite add vector test

		assertThrows(IllegalArgumentException.class, () -> vec.add(new Vector(-1, -2, -3)),
				"ERROR: Vector + -itself does not work correctly");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}. Tests the subtract
	 * of two vectors.
	 */
	@Test
	void testSubtract() {
		Vector vec = new Vector(4, 5, 6);

		// ============ Equivalence Partitions Tests ==============

		// generic subtract test
		assertEquals(new Vector(3, 3, 3), vec.subtract(new Vector(1, 2, 3)),
				"ERROR: Vector - Vector does not work correctly");
		// assertEquals(expected, actual, message)

		// =============== Boundary Values Tests ==================

		// opposite subtract vector test
		assertThrows(IllegalArgumentException.class, () -> vec.subtract(new Vector(4, 5, 6)),
				"ERROR: Vector - itself does not work correctly");

		// assertEquals(expected, actual, message)

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}. Tests scaling of a
	 * vector.
	 */
	@Test
	void testScale() {

		Vector vec = new Vector(1, 2, 7);

		// ============ Equivalence Partitions Tests ==============

		// generic scale test
		assertEquals(new Vector(4, 8, 28), vec.scale(4), "ERROR: scale wrong value");
		// assertEquals(expected, actual, message)

		// =============== Boundary Values Tests ==================

		// scale to zero
		assertThrows(IllegalArgumentException.class, () -> vec.scale(0), "ERROR: scale wrong value");
		// assertThrows(expectedType, executable, message)

	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 * Tests the dot product of two vectors.
	 */
	@Test
	void testDotProduct() {
		Vector vec = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============
		// generic scale test

		assertEquals(32, vec.dotProduct(new Vector(4, 5, 6)), "ERROR: dotProduct() wrong value");

		// =============== Boundary Values Tests ==================

		// Dot product with a zero vector
		assertThrows(IllegalArgumentException.class, () -> vec.dotProduct(new Vector(0, 0, 0)),
				"ERROR: dotProduct() with zero vector is not zero");

		// Dot product of orthogonal vectors
		assertEquals(0, vec.dotProduct(new Vector(18, 0, -6)),
				"ERROR: dotProduct() for orthogonal vectors is not zero");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 * Tests the cross product of two vectors.
	 */
	@Test
	void testCrossProduct() {
		Vector vec1 = new Vector(1, 2, 3);
		Vector vec2 = new Vector(0, 3, -2);
		Vector vr = vec1.crossProduct(vec2);

		// ============ Equivalence Partitions Tests ==============

		// Generic cross product test
		double expectedLength = vec1.length() * vec2.length();
		assertEquals(expectedLength, vr.length(), DELTA, "ERROR: crossProduct() wrong result length");

		// Check if the cross product result is orthogonal to v1
		assertEquals(0, vr.dotProduct(vec1), DELTA,
				"ERROR: crossProduct() result is not orthogonal to its left operand");

		// Check if the cross product result is orthogonal to v2
		assertEquals(0, vr.dotProduct(vec2), DELTA,
				"ERROR: crossProduct() result is not orthogonal to its right operand");

		// =============== Boundary Values Tests ==================

		// Parallel vectors cross product test (result zero vector)

		assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(new Vector(2, 4, 6)),
				"crossProduct() for parallel vectors does not throw an exception");

		// Opposite parallel vectors cross product test (result zero vector)
		assertThrows(IllegalArgumentException.class, () -> vec1.crossProduct(new Vector(-2, -4, -6)),
				"crossProduct() for opposite parallel vectors does not throw an exception");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}. Tests the
	 * calculation of the squared length of a vector.
	 */
	@Test
	void testLengthSquared() {
		Vector vec = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============

		// generic length test
		assertEquals(14, vec.lengthSquared(), "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		Vector vec = new Vector(1, 2, 3);

		// ============ Equivalence Partitions Tests ==============

		// generic length test
		assertEquals(Math.sqrt(14), vec.length(), "ERROR: lengthSquared() wrong value");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}. Tests the calculation
	 * of the length of a vector.
	 */
	@Test
	void testNormalize() {
		Vector vec = new Vector(1, 2, 3);
		Vector u = vec.normalize();

		// ============ Equivalence Partitions Tests ==============

		// generic normalize vector(3 tests)

		// confirm unit vector
		assertEquals(1, u.length(), DELTA, "ERROR: the normalized vector is not a unit vector");

		// cross product between parallel vectors returns zero vector
		assertThrows(IllegalArgumentException.class, () -> vec.crossProduct(u),
				"ERROR: the normalized vector is not parallel to the original one");

		// Dot product between parallel vectors equals the product of their lengths
		assertEquals(vec.dotProduct(u), vec.length(), DELTA,
				"ERROR: the normalized vector is not parallel to the original one");
	}
}
