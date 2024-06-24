package primitives;

import static java.lang.Math.sqrt;

/**
 * Represents a point in 3D space.
 */

public class Point {
	/** The coordinates of the point */
	protected final Double3 xyz;

	/** The origin point (0, 0, 0) */
	public static final Point ZERO = new Point(Double3.ZERO);

	/**
	 * Constructs a new Point with the given coordinates.
	 * 
	 * @param d1 The x-coordinate
	 * @param d2 The y-coordinate
	 * @param d3 The z-coordinate
	 */
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1, d2, d3);
	}

	/**
	 * Constructs a new Point with the coordinates from the given Double3 object.
	 * 
	 * @param d The Double3 object containing the coordinates
	 */
	Point(Double3 d) {
		xyz = d;
	}

	/**
	 * Calculates the vector from this point to the given point.
	 * 
	 * @param p The point to subtract from this point
	 * @return The vector from this point to the given point
	 */
	public Vector subtract(Point p) {
		return new Vector(this.xyz.subtract(p.xyz));
	}

	/**
	 * Adds the given vector to this point, producing a new point.
	 * 
	 * @param vec The vector to add to this point
	 * @return A new point resulting from adding the vector to this point
	 */
	public Point add(Vector vec) {
		return new Point(this.xyz.add(vec.xyz));
	}

	/**
	 * Calculates the squared distance between this point and the given point.
	 * 
	 * @param p The other point
	 * @return The squared distance between this point and the given point
	 */
	public double distanceSquared(Point p) {
		double dx = this.xyz.d1 - p.xyz.d1;
		double dy = this.xyz.d2 - p.xyz.d2;
		double dz = this.xyz.d3 - p.xyz.d3;

		return dx * dx + dy * dy + dz * dz;
	}

	/**
	 * Calculates the distance between this point and the given point.
	 * 
	 * @param p The other point
	 * @return The distance between this point and the given point
	 */
	public double distance(Point p) {
		return sqrt(this.distanceSquared(p));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Point other) && this.xyz.equals(other.xyz);
	}

	@Override
	public String toString() {
		return "" + xyz;
	}
}
