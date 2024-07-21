package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;
import java.util.List;

/**
 * Represents a tube in three-dimensional space. A tube is a type of radial
 * geometry with an axis defined by a ray and a radius.
 */
public class Tube extends RadialGeometry {
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

	@Override
	public Vector getNormal(Point p) {
		// Calculate the projection of p-head on the direction vector dir
		Vector headToP = p.subtract(axis.getHead());
		double length = axis.getDirection().dotProduct(headToP);
		// Calculate the point O on the axis closest to point p
		Point closestPoint = axis.getPoint(length);
		// Calculate the normal vector from O to p and normalize it
		return p.subtract(closestPoint).normalize();
	}

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		return null;
		// :TODO implement function
	}
}
