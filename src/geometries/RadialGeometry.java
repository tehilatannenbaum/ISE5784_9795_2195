package geometries;

/**
 * Represents a radial geometry in three-dimensional space. A radial geometry is
 * defined by its radius.
 */
public abstract class RadialGeometry implements Geometry {

	/** The radius of the radial geometry */
	public final double radius;
	/** The squared radius of the radial geometry */
	public final double radiusSquared;

	/**
	 * Constructs a radial geometry with the given radius.
	 * 
	 * @param radius The radius of the radial geometry
	 */
	protected RadialGeometry(double radius) {
		this.radius = radius;
		radiusSquared = radius * radius;
	}

	/**
	 * Returns the radius of this object.
	 *
	 * @return the radius.
	 */
	protected double getRadius() {
		return radius;
	}

}
