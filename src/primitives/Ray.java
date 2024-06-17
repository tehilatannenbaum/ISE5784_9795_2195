
package primitives;

import java.util.List;
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
	 * Returns the point at a distance t along the ray.
	 * 
	 * @param t The distance along the ray
	 * @return The point at distance t along the ray
	 */
	public Point getPoint(double t) {
		return isZero(t) ? head : head.add(direction.scale(t)); // p(t) = head + t * direction
	}

	/**
	 * Returns the head point of this object.
	 *
	 * @return the head point.
	 */
	public Point getHead() {
		return head;
	}

	/**
	 * Returns the direction vector of this object.
	 *
	 * @return the direction vector.
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * Constructs a new Ray with the given starting point and direction vector.
	 * 
	 * @param p   The starting point (head) of the ray
	 * @param vec The direction vector of the ray
	 */
	public Ray(Point p, Vector vec) {
		// Normalize the direction vector to ensure it represents a unit vector
		head = p;
		direction = vec.normalize();
	}

	/**
	 * Checks whether this Ray is equal to another object. Two rays are considered
	 * equal if they have the same head and direction.
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
	 * Returns a string representation of this Ray. The string contains the head
	 * point and the direction vector of the ray.
	 * 
	 * @return A string representation of this Ray
	 */
	@Override
	public String toString() {
		return head + "->" + direction;
	}

	/**
	 * Finds the closest point to the ray's start point from a collection of points.
	 * 
	 * @param points The collection of points.
	 * @return The point closest to the ray's start point, or null if the collection
	 *         is empty.
	 */
	public Point findClosestPoint(List<Point> points) {
		if (points == null || points.isEmpty())
			return null;

		Point closestPoint = null;
		double minDistance = Double.POSITIVE_INFINITY;

		// Iterate through each point in the list
		for (Point point : points) {
			double distance = head.distance(point);

			// Update the closest point if the current point is closer
			if (distance < minDistance) {
				minDistance = distance;
				closestPoint = point;
			}
		}

		return closestPoint;
	}

}
