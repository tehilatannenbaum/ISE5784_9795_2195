package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import primitives.Util;
import java.util.List;

/**
 * Represents a cylinder in 3D space. A cylinder is defined by its axis, radius,
 * and height. Inherits from the Tube class, representing a tube with zero
 * thickness.
 */
public class Cylinder extends Tube implements Intersectable{

	/** The height of the cylinder */
	@SuppressWarnings("unused")
	private final double height;

	/**
	 * Constructs a new Cylinder object with the specified axis, radius, and height.
	 * 
	 * @param axis   The axis of the cylinder
	 * @param radius The radius of the cylinder
	 * @param height The height of the cylinder
	 */
	public Cylinder(Ray axis, double radius, double height) {
		super(axis, radius);
		this.height = height;
	}

	/**
	 * Computes and returns the normal vector to the cylinder at a given point.
	 * 
	 * @param p The point on the cylinder's surface for which to compute the normal
	 * @return The normal vector to the cylinder at the specified point (currently
	 *         returns null)
	 */
	@Override
	public Vector getNormal(Point p) {
	Point head = axis.getHead();
	        Vector dir = axis.getDirection();
	        // Calculate projection of p-head on the direction vector
	        if(p.equals(head))
	        	return dir;
	        double length = dir.dotProduct(p.subtract(head));
	        // Check if the point is on the bottom base
	        if (Util.isZero(length)) {
	            return dir.scale(-1);
	        }
	        // Check if the point is on the top base
	        if (Util.isZero(length - height)) {
	            return dir;
	        }
	        // The point is on the lateral surface
	        Point closestPoint = head.add(dir.scale(length)); // The projection point on the axis
	        return p.subtract(closestPoint).normalize();
	}
	
	 @Override
	    public List<Point> findIntersections(Ray ray) {
		 return null;
		 //:TODO add javadoc, implement function
	 }
}
