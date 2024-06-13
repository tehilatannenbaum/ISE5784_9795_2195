/**
 * This class represents a set of JUnit tests for the Triangle class.
 * It includes tests for the getNormal method and the findIntersections method.
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import java.util.List;
import java.util.Comparator;
import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;


import org.junit.jupiter.api.Test;


class TriangleTests {
	private static final double DELTA = 0.000001;


	{

	// Test valid triangle
    try {
        new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
    } catch (IllegalArgumentException e) 
    {
        fail("Failed constructing a correct triangle");}
    
    }  
	
	
    /**
     * Tests the getNormal method of the Triangle class.
     * This method checks if the normal vector of a triangle is computed correctly.
     */
	@Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test - create a triangle with three points
        Point p1 = new Point(0, 0, 0);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        assertNotNull(triangle);

        // Ensure there are no exceptions
        assertDoesNotThrow(() -> triangle.getNormal(p1), "");

        // Generate the test result
        Vector result = triangle.getNormal(p1);

        // Ensure |result| = 1
        assertEquals(1, result.length(), DELTA,
                "Triangle's normal is not a unit vector");

        // Ensure the result is orthogonal to all the edges
        Point[] pts = {p1, p2, p3};
        for (int i = 0; i < 3; ++i) {
            assertEquals(0d, result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1])), DELTA,
                    "Triangle's normal is not orthogonal to one of the edges");
        }
    }
    
	


	  /**
     * Tests the findIntersections method of the Triangle class.
     * This method checks if the intersections between a ray and a triangle are computed correctly.
     */
	 @Test
	 public void testFindIntersections() {
			 
			 Triangle triangle = new Triangle(new Point(0,0,1),new Point(1,0,0),new Point(0,1,0));
		        
		        Vector v0111 = new Vector(-1,1,1);
		        Vector v0331 = new Vector(0.33,0.33,1);
		        Vector v03031 = new Vector(-3,-3,1);
		        Vector v101 = new Vector(1,0,1);
		        Vector v133 = new Vector(1.33,-0.33,0);
		        Vector v0001 = new Vector(0, 0, -1);


		        Point p0500 = new Point(0.5,0,0);
		        Point p111 = new Point(1,1,1);
		        Point p05051 = new Point(0.5,0.5,1);
		        Point p1115 = new Point(1,1,1.5);
		        Point p110 = new Point(1,1,0);
		        Point p0333 = new Point(0.33333333333333326,0.33333333333333326,0.33333333333333326);
				Point p0331 = new Point(0.33333333333333326,0.33333333333333326,1);


		      
			 
			// ============ Equivalence Partitions Tests ==============
			 // TC01: Point inside triangle (1 points)				
				 final var result1 = triangle.findIntersections(new Ray(p0331, v0001));
				 final var exp1 = List.of(p0333);
				 
				 assertEquals(1, result1.size(), "Wrong number of points");
				 assertEquals(exp1, result1, "Point inside triangle");
		        
		     // TC02: Point outside triangle (0 points)
				 assertNull(triangle.findIntersections(new Ray(p111, v0111)), 
						 "Point outside triangle, infront of side");
				   
			 // TC03: Point outside triangle (0 points)
				 assertNull(triangle.findIntersections(new Ray(p1115, v03031)), 
						 "Point outside triangle, infront of vertex");

				 
		     // =============== Boundary Values Tests ==================
		     // TC04: Point on triangle side (0 points)
				 assertNull(triangle.findIntersections(new Ray(p0500, v101)), 
						 "Point on triangle side");

		      // TC05: Point on triangle vertex (0 points)
				 assertNull(triangle.findIntersections(new Ray(p05051, v0331)), 
						 "Point on triangle vertex");
			  
			// TC06: Point on continuation of triangle side (0 points)
				 assertNull(triangle.findIntersections(new Ray(p110, v133)), 
						 "Point on continuation of triangle side");

				 

	 }
	

}
	

