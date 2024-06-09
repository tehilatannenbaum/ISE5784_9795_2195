package geometries;

import primitives.*;
import primitives.Util.*;

import java.util.List;


/**
 * Represents a triangle in three-dimensional space. A triangle is a type of
 * polygon with three vertices.
 */
public class Triangle extends Polygon implements Intersectable{

	/**
	 * Constructs a triangle with three vertices. The vertices are specified in
	 * counter-clockwise order.
	 * 
	 * @param p1 The first vertex of the triangle
	 * @param p2 The second vertex of the triangle
	 * @param p3 The third vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}
	
	/**
	 * Finds the intersections between the ray and the triangle.
	 *
	 * @param ray The ray to find intersections with.
	 * @return A list of points representing the intersections, or null if there are no intersections.
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		
	    List<Point> intersections = plane.findIntersections(ray);
	    if (intersections == null || intersections.isEmpty()) {
	        // If there are no intersections with the plane, return null
	        return null;
	    }

	    // Check if the intersection point is inside the triangle
	   Vector v1= vertices.get(0).subtract(ray.getHead());
	   Vector v2= vertices.get(1).subtract(ray.getHead());
	   
	   Vector n1 = v1.crossProduct(v2);
	   double result1 = Util.alignZero(ray.getDirection().dotProduct(n1));
	   if(result1==0) {
		   return null;
	   }
	   
	   
	   Vector v3= vertices.get(2).subtract(ray.getHead());
	   Vector n2 = v2.crossProduct( v3);
	   double result2 = Util.alignZero(ray.getDirection().dotProduct(n2));
	   if(result1 * result2 <=0) {
		   return null;
	   }
	    
	   Vector n3 = v3.crossProduct( v1);
	   double result3 = Util.alignZero(ray.getDirection().dotProduct(n3));
	   if(result3 * result1 <=0) {
		   return null;
	   }
	    

	    return intersections;
	}

	
}
	
	
	