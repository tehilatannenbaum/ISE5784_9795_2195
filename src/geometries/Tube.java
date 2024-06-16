package geometries;

import static primitives.Util.*;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

/**
 * Represents a tube in three-dimensional space. A tube is a type of radial
 * geometry with an axis defined by a ray and a radius.
 */
public class Tube extends RadialGeometry implements Intersectable {
	/**
	 * The axis of the tube, defined by a ray.
	 */
	protected final Ray axis;

	/**
	 * Constructs a tube with a given axis defined by a ray and a radius.
	 * 
	 * @param axis   The axis of the tube
	 * @param radius The radius of the tube
	 */
	public Tube(Ray axis, double radius) {
		super(radius); // Call the constructor of the superclass RadialGeometry
		this.axis = axis;
	}

	/**
	 * Returns the normal vector to the tube at a given point. Since a tube is a
	 * cylindrical surface, the normal vector is undefined.
	 * 
	 * @param p The point on the tube
	 * @return null, since the normal vector to a tube is undefined
	 */
	@Override
	public Vector getNormal(Point p) {
		Point head = axis.getHead();
		Vector dir = axis.getDirection();
		// Calculate the projection of p-head on the direction vector dir
		Vector headToP = p.subtract(head);
		double length = dir.dotProduct(headToP);
		// Calculate the point O on the axis closest to point p
		Point closestPoint = isZero(length) ? head : head.add(dir.scale(length));
		// Calculate the normal vector from O to p and normalize it
		return p.subtract(closestPoint).normalize();
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
		// :TODO add javadoc, implement function
	}
}
