package geometries;

import primitives.Point;
import primitives.Vector;

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
	protected Sphere(Point p, double radius) {
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
		return null; // Currently not implemented
	}
}
