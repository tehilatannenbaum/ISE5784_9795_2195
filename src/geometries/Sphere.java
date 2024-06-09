package geometries;

import primitives.Point;

import primitives.Vector;
import primitives.Ray;
import primitives.Util;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;


/**
 * Represents a sphere in three-dimensional space. A sphere is a type of radial
 * geometry with a center point and a radius.
 */
public class Sphere extends RadialGeometry implements Intersectable{

	/** The center point of the sphere */
	private final Point center;

	/**
	 * Constructs a sphere with the given center point and radius.
	 * 
	 * @param p      The center point of the sphere
	 * @param radius The radius of the sphere
	 */
	public Sphere(Point p, double radius) {
		super(radius); // Call the constructor of the superclass RadialGeometry
		center = p;
	}

	/**
	 * Computes the normal vector to the sphere at a given point.
	 * 
	 * @param p The point on the surface of the sphere
	 * @return The normal vector to the sphere at the given point
	 */
	@Override
	public Vector getNormal(Point p) {
        // Calculate the vector from the center to the point
        Vector normalVector = p.subtract(center);
        // Normalize the vector to get the normal
        return normalVector.normalize();
        
    }
	
	   /**
     * Finds the intersections of a ray with the sphere.
     * 
     * @param ray The ray to intersect with the sphere
     * @return A list of intersection points, or null if there are no intersections
     */
	
	 @Override
	    public List<Point> findIntersections(Ray ray) {
	        Point p0 = ray.getHead();  
	        Vector v = ray.getDirection(); 
	        double radius = this.getRadius();

	        // If the ray starts at the center of the sphere, there's only one intersection point
	        if (p0.equals(center)) {
	            return List.of(center.add(v.scale(radius)));
	        }
	        // Vector from ray origin to sphere center
	        Vector u = center.subtract(p0); 
	        
	        double tm = alignZero(v.dotProduct(u)); // Projection of u onto ray direction
	        double dSquared = alignZero(u.lengthSquared() - tm * tm); // Squared distance from sphere center to ray
	        double thSquared = alignZero(radius * radius - dSquared); // Squared distance from the closest approach to intersection point

	       
	       // There's no intersection
	        double d = alignZero(Math.sqrt(dSquared)); 
	        if(d >= radius) {
	        	return null;
	        }
	        

	        double th = alignZero(Math.sqrt(thSquared)); // Distance from the closest approach to intersection point
	        // Distance from ray origin to the first and second intersection point
	        double t1 = alignZero(tm - th); 
	        double t2 = alignZero(tm + th); 

	        // If both t1 and t2 are positive, there are two intersection points
	        if (t1 > 0 && t2 > 0) {
	            Point p1 = ray.getPoint(t1);
	            Point p2 = ray.getPoint(t2);
	            return List.of(p1, p2);
	        }

	        // If t1 is positive, there's one intersection point
	        if (t1 > 0) {
	            return List.of(ray.getPoint(t1));
	        }

	        // If t2 is positive, there's one intersection point
	        if (t2 > 0) {
	            return List.of(ray.getPoint(t2));
	        }

	        // If both are negative, there's no intersection
	        return null;
	    }
    
        
        
    
}

