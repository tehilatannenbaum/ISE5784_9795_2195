package primitives;

import static java.lang.Math.sqrt;

/**
 * Represents a vector in 3D space, extending the Point class.
 */
public class Vector extends Point {

	/**
	 * Constructs a new Vector with the specified coordinates.
	 * 
	 * @param d1 The x-coordinate of the vector
	 * @param d2 The y-coordinate of the vector
	 * @param d3 The z-coordinate of the vector
	 * @throws IllegalArgumentException if the vector is zero (all coordinates are
	 *                                  zero)
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Vector can't be zero");
	}

	/**
	 * Constructs a new Vector from a Double3 object representing its coordinates.
	 * 
	 * @param d The Double3 object containing the coordinates of the vector
	 * @throws IllegalArgumentException if the vector is zero (all coordinates are
	 *                                  zero)
	 */
	public Vector(Double3 d) {
		super(d);
		if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Vector can't be zero");
	}

	/**
	 * Adds another vector to this vector, producing a new vector.
	 * 
	 * @param vec The vector to add
	 * @return A new vector that is the result of adding the specified vector to
	 *         this vector
	 */
	public Vector add(Vector vec) {
		return new Vector(this.xyz.add(vec.xyz));
	}

	/**
	 * Scales this vector by a scalar value, producing a new vector.
	 * 
	 * @param scalar The scalar value to scale the vector by
	 * @return A new vector that is the result of scaling this vector by the
	 *         specified scalar value
	 */
	public Vector scale(double scalar) {
		return new Vector(this.xyz.d1 * scalar, this.xyz.d2 * scalar, this.xyz.d3 * scalar);
	}

	/**
	 * Computes the dot product of this vector with another vector.
	 * 
	 * @param vec The vector to compute the dot product with
	 * @return The dot product of this vector with the specified vector
	 */
	public double dotProduct(Vector vec) {
		if (vec != null)
			return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + this.xyz.d3 * vec.xyz.d3;
		else
			return 0.0;
	}

	/**
	 * Computes the cross product of this vector with another vector.
	 * 
	 * @param vec The vector to compute the cross product with
	 * @return A new vector that is the result of the cross product between this
	 *         vector and the specified vector
	 */
	public Vector crossProduct(Vector vec) {
		return new Vector( //
				(this.xyz.d2 * vec.xyz.d3) - (this.xyz.d3 * vec.xyz.d2), //
				(this.xyz.d3 * vec.xyz.d1) - (this.xyz.d1 * vec.xyz.d3), //
				(this.xyz.d1 * vec.xyz.d2) - (this.xyz.d2 * vec.xyz.d1));
	}

	/**
	 * Computes the squared length of this vector.
	 * 
	 * @return The squared length of this vector
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * Computes the length of this vector.
	 * 
	 * @return The length of this vector
	 */
	public double length() {
		return sqrt(this.lengthSquared());
	}

	/**
	 * Normalizes this vector (converts it to a unit vector), producing a new
	 * vector.
	 * 
	 * @return A new vector that is the normalized version of this vector
	 */
	public Vector normalize() {
		double length = length();
		return new Vector(xyz.d1 / length, xyz.d2 / length, xyz.d3 / length);
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return obj instanceof Vector && super.equals(obj);
	}

	
	@Override
	public String toString() {
		return "v" + super.toString();
	}
}
