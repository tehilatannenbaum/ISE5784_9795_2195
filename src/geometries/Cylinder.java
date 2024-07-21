package geometries;

import primitives.Point;
import static primitives.Util.*;

import primitives.Vector;
import primitives.Ray;

/**
 * Represents a cylinder in 3D space. A cylinder is defined by its axis, radius,
 * and height. Inherits from the Tube class, representing a tube with zero
 * thickness.
 */
public class Cylinder extends Tube {

	/** The height of the cylinder */
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

	@Override
	public Vector getNormal(Point p) {

		Point head = axis.getHead();
		Vector dir = axis.getDirection();
		// Calculate projection of p-head on the direction vector
		if (p.equals(head))
			return dir;
		double length = dir.dotProduct(p.subtract(head));
		// Check if the point is on the bottom base
		if (isZero(length))
			return dir.scale(-1);
		// Check if the point is on the top base
		if (isZero(length - height))
			return dir;
		// The point is on the lateral surface
		Point closestPoint = axis.getPoint(length); // The projection point on the axis
		return p.subtract(closestPoint).normalize();

	}

}
