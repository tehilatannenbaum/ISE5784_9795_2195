package geometries;

import static primitives.Util.*;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

/**
 * Represents a plane in 3D space. A plane is defined by either three points or
 * a point and a normal vector.
 */
public class Plane implements Geometry, Intersectable {

	/** A point on the plane */
	private final Point p;

	/** The normal vector to the plane */
	private final Vector normal;

	/**
	 * Constructs a plane using three points on it.
	 * 
	 * @param p1 The first point on the plane
	 * @param p2 The second point on the plane
	 * @param p3 The third point on the plane
	 * 
	 * @throws IllegalArgumentException if there are convergent points or the points
	 *                                  are co-lineear
	 */
	public Plane(Point p1, Point p2, Point p3) {
		normal = (p2.subtract(p1)).crossProduct(p3.subtract(p1)).normalize();
		this.p = p1;
	}

	/**
	 * Constructs a plane using a reference point and a normal vector.
	 * 
	 * @param p   The reference point on the plane
	 * @param vec The normal vector to the plane
	 */
	public Plane(Point p, Vector vec) {
		this.p = p;
		// Normalize the vector to ensure it's a unit vector
		this.normal = vec.normalize();
	}

	/**
	 * Gets the normal vector to the plane at a given point.
	 * 
	 * @param p The point on the plane
	 * @return The normal vector to the plane
	 */
	@Override
	public Vector getNormal(Point p) {
		return normal;
	}

	/**
	 * Gets the normal vector to the plane.
	 * 
	 * @return The normal vector to the plane
	 */
	public Vector getNormal() {
		return normal;
	}

	/**
	 * Finds the intersection points of a ray with the plane.
	 * 
	 * @param ray The ray to find intersections with
	 * @return A list of intersection points, or null if there are no intersections
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		Point p0 = ray.getHead();
		Vector v = ray.getDirection();

		// Calculate nv
		double nv = normal.dotProduct(v);
		// If nv is zero, the ray is parallel to the plane (no intersections)
		if (isZero(nv))
			return null;

		Vector pMinusP0;
		try {
			pMinusP0 = p.subtract(p0);
		} catch (IllegalArgumentException ignore) {
			return null;
		}
		// Calculate n * (p - p0) / nv
		double t = alignZero(normal.dotProduct(pMinusP0) / nv);

		// If t is negative, the intersection point is behind the ray's origin (no
		// intersection) and Return the intersection point as a list otherwise
		return t <= 0 ? null : List.of(ray.getPoint(t));
	}
}
