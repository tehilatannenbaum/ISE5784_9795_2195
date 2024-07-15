
package geometries;

import primitives.Point;

import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static primitives.Util.*;

/**
 * Represents a sphere in three-dimensional space. A sphere is a type of radial
 * geometry with a center point and a radius.
 */
public class Sphere extends RadialGeometry {

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
		// Normalize the vector to get the normal
		return p.subtract(center).normalize();

	}

	/**
	 * Finds the intersections of a ray with the sphere.
	 * 
	 * @param ray The ray to intersect with the sphere
	 * @return A list of intersection points, or null if there are no intersections
	 */

	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
		Point p0 = ray.getHead();
		Vector v = ray.getDirection();

		// If the ray starts at the center of the sphere, there's only one intersection
		// point
		if (p0.equals(center)) {
			return List.of(new GeoPoint(this, ray.getPoint(radius)));

		}
		// Vector from ray origin to sphere center
		Vector u = center.subtract(p0);
		// Projection of u onto ray direction
		double tm = alignZero(v.dotProduct(u));
		double dSquared = alignZero(u.lengthSquared() - tm * tm);
		double thSquared = alignZero(radiusSquared - dSquared);
		if (alignZero(thSquared) <= 0)
			return null;

		// There's no intersection
		double d = alignZero(Math.sqrt(dSquared));
		if (d >= radius)
			return null;

		// alignZero, vec lengthSquared, vec subtract, vec dotProduct

		// Distance from the closest approach to intersection point
		double th = Math.sqrt(thSquared);
		// Distance from ray origin to the first and second intersection point, t1 < t2
		double t2 = alignZero(tm + th);
		if (t2 <= 0)
			return null;

		double t1 = alignZero(tm - th);
		return t1 <= 0 ? List.of(new GeoPoint(this, ray.getPoint(t2))) //
				: List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));
	}

}
