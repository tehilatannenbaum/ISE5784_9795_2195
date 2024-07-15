package primitives;

import java.util.List;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

/**
 * Represents a ray in 3D space, defined by a starting point (head) and a
 * direction vector.
 */
public class Ray {
	/** The starting point (head) of the ray */
	private final Point head;
	/** The direction vector of the ray */
	private final Vector direction;

	/**
	 * Offset size for the origin of shadow rays.
	 **/
	private static final double DELTA = 0.1;

	/**
	 * Returns the point at a distance t along the ray.
	 * 
	 * @param t The distance along the ray
	 * @return The point at distance t along the ray
	 */
	public Point getPoint(double t) {
		// p(t) = head + t * direction
		return isZero(t) ? head : head.add(direction.scale(t));
	}

	/**
	 * Returns the head point of this Ray object.
	 *
	 * @return The head point.
	 */
	public Point getHead() {
		return head;
	}

	/**
	 * Returns the direction vector of this Ray object.
	 *
	 * @return The direction vector.
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * Constructs a new Ray with the given starting point and direction vector.
	 * 
	 * @param p         The starting point (head) of the ray
	 * @param direction The direction vector of the ray
	 */
	public Ray(Point p, Vector direction) {
		// Normalize the direction vector to ensure it represents a unit vector
		head = p;
		this.direction = direction.normalize();
	}

	/**
	 * Constructs a new Ray with the given starting point and direction vector and
	 * Normal vector.
	 * 
	 * @param normal    to move by
	 * @param point     the starting point before moving
	 * @param direction the direction of the ray
	 */
	public Ray(Point point, Vector direction, Vector normal) {
		this.direction = direction.normalize();
		double dotProduct = alignZero(this.direction.dotProduct(normal));
		this.head = dotProduct == 0 ? point : point.add(normal.scale(dotProduct < 0 ? -DELTA : DELTA));
	}

	/**
	 * Checks whether this Ray object is equal to another object. Two rays are
	 * considered equal if they have the same head and direction.
	 * 
	 * @param obj The object to compare with this Ray
	 * @return true if the given object is equal to this Ray, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	/**
	 * Returns a string representation of this Ray object. The string contains the
	 * head point and the direction vector of the ray.
	 * 
	 * @return A string representation of this Ray
	 */
	@Override
	public String toString() {
		return head + "->" + direction;
	}

	/**
	 * Finds the closest GeoPoint to the ray's head among a list of GeoPoints.
	 * 
	 * @param intersections List of GeoPoints to check
	 * @return The closest GeoPoint, or null if the list is empty or null
	 */
	public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections) {
		if (intersections == null || intersections.isEmpty())
			return null;

		GeoPoint closestGeoPoint = null;
		double minDistance = Double.POSITIVE_INFINITY;

		// Iterate through each point in the list
		for (GeoPoint point : intersections) {
			double distance = point.point.distance(head);

			// Update the closest point if the current point is closer
			if (distance < minDistance) {
				minDistance = distance;
				closestGeoPoint = point;
			}
		}

		return closestGeoPoint;
	}

	/**
	 * Finds the closest Point to the ray's head among a list of Points.
	 * 
	 * @param points List of Points to check
	 * @return The closest Point, or null if the list is empty or null
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
	}
}
