/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.Vector;
import primitives.Point;
import geometries.Plane;

/**
 * Unit tests for geometries.Plane class
 * 
 * @author Tehila and Maayan
 */
class PlaneTests {
	
	private final double DELTA = 0.000001;
	
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	public void testConstructor() {
		
        // =============== Boundary Values Tests ==================

        // points converge
        assertThrows(
               IllegalArgumentException.class,
                ()-> new Plane(new Point(0,0,1), new Point(0,0,1),new Point(3,7,9)),
                "illegally constructed with converged points"
        );

        // points align
        assertThrows(
                IllegalArgumentException.class,
                ()-> new Plane( new Point(0,0,1),new Point(0,0,5),new Point(0,0,9)),
                "illegally constructed with aligned points");

	}
	

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormalPoint() {
		
        Plane pl = new Plane(new Point(0,0,1), new Point(1,0,0), new Point(0,1,0));

        // ============ Equivalence Partitions Tests ==============
        
        
        // test unit normal
        assertEquals(
                1,
                pl.getNormal().length(),
                DELTA,
                "Plane's normal is not a unit vector");

        // test orthogonal, 2 steps
        // the new Vectors are in the plane
        assertEquals(
                0,
                pl.getNormal().dotProduct(new Vector(1,-1,0)),
                DELTA,
                "Plane's normal is not orthogonal 1");

        assertEquals(
                0,
                pl.getNormal().dotProduct(new Vector(0,-1,1)),
                DELTA,
                "Plane's normal is not orthogonal 2");

	}

}
