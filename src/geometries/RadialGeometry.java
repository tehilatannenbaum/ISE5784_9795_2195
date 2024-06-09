package geometries;

/**
 * Represents a radial geometry in three-dimensional space. A radial geometry is
 * defined by its radius.
 */
public abstract class RadialGeometry implements Geometry {

	/** The radius of the radial geometry */
	private final double radius;
	/** The squared radius of the radial geometry */
	private final double radiusSquared;

	/**
	 * Constructs a radial geometry with the given radius.
	 * 
	 * @param radius The radius of the radial geometry
	 */
	protected RadialGeometry(double radius) {
		this.radius = radius;
		radiusSquared = radius * radius;
	}
	
	protected double getRadius() {
		return radius;
	}
}
