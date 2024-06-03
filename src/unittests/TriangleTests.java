/**
 * 
 */
package unittests;

import static org.junit.jupiter.api.Assertions.*;

import geometries.Triangle;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;


import org.junit.jupiter.api.Test;

/**
 * @author Tehila
 *
 */
class TriangleTests {
	{

	// Test valid triangle
    try {
        new Triangle(new Point(0, 0, 0), new Point(1, 0, 0), new Point(0, 1, 0));
    } catch (IllegalArgumentException e) 
    {
        fail("Failed constructing a correct triangle");
    }
}
}
	

//check if really not need to implement 
//:TODO
