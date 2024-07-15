package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a directional light source in the scene. A directional
 * light source has a fixed direction and intensity, and it affects all objects
 * equally regardless of their position.
 */
public class DirectionalLight extends Light implements LightSource {

	/**
	 * The direction vector of the directional light source.
	 */
	private final Vector direction;

	/**
	 * Constructor to initialize the directional light with a given intensity and
	 * direction.
	 *
	 * @param intensity The intensity of the light.
	 * @param direction The direction of the light.
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	@Override
	public Color getIntensity(Point p) {
		return getIntensity();
	}

	@Override
	public Vector getL(Point p) {
		return direction;
	}

	@Override
	public double getDistance(Point p) {
		return Double.POSITIVE_INFINITY;
	}

}
