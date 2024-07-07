package lighting;

import primitives.*;

import static primitives.Util.*;

/**
 * Class representing a spot light source in the scene. A spot light source
 * emits light from a specific position and direction with attenuation.
 */
public class SpotLight extends PointLight {

	/**
	 * The direction vector of the spot light.
	 */
	private final Vector direction;

	/**
	 * Constructor to initialize the spot light with a given intensity, position,
	 * and direction.
	 *
	 * @param intensity The intensity of the light.
	 * @param position  The position of the light source.
	 * @param direction The direction of the light.
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	/**
	 * Get the intensity of the light at a given point.
	 *
	 * @param p The point to get the light intensity at.
	 * @return The light intensity at the given point.
	 */
	@Override
	public Color getIntensity(Point p) {
		double projection = alignZero(direction.dotProduct(getL(p)));
		return (projection <= 0) ? Color.BLACK : super.getIntensity(p).scale(projection);
	}

	/**
	 * Get the direction vector from the light source to a given point.
	 *
	 * @param p The point to get the direction vector to.
	 * @return The direction vector from the light source to the given point.
	 */
	@Override
	public Vector getL(Point p) {
		return super.getL(p);
	}
}
