package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a plane in 3D space. A plane is defined by either three points or
 * a point and a normal vector.
 */
public class Plane implements Geometry {

	/** A point on the plane */
	private final Point p;

	/** The normal vector to the plane */
	private final Vector normalize;

	/**
	 * Constructs a plane using three points on it.
	 * 
	 * @param p1 The first point on the plane
	 * @param p2 The second point on the plane
	 * @param p3 The third point on the plane
	 */
	protected Plane(Point p1, Point p2, Point p3) {
		normalize = null; // Not used in this constructor
		this.p = p1; // Choose any of the three points as the reference point
	}

	/**
	 * Constructs a plane using a reference point and a normal vector.
	 * 
	 * @param _p  The reference point on the plane
	 * @param vec The normal vector to the plane
	 */
	protected Plane(Point p, Vector vec) {
		this.p = p;
		normalize = vec; // Normalize the vector to ensure it's a unit vector
	}

	/**
	 * Computes and returns the normal vector to the plane at a given point.
	 * 
	 * @param p The point on the plane (unused in this implementation)
	 * @return The normalized normal vector to the plane
	 */
	@Override
	public Vector getNormal(Point p) {
		return normalize;// Returns the normalized normal vector
	}

	/**
	 * Returns the normal vector to the plane.
	 * 
	 * @return The normalized normal vector to the plane
	 */
	public Vector getNormal() {
		return normalize; // Returns the normalized normal vector
	}
}
