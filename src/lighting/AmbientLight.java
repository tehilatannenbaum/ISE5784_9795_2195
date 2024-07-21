package lighting;

import primitives.Color;

import primitives.Double3;

/**
 * The AmbientLight class represents the ambient light in a scene. The ambient
 * light is a global light source that affects all objects equally.
 */
public class AmbientLight extends Light {

	/** A constant representing no ambient light */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructor to initialize ambient light with given color and attenuation
	 * factors.
	 *
	 * @param ia The original light intensity as a Color.
	 * @param ka The attenuation factors as a Double3.
	 */
	public AmbientLight(Color ia, Double3 ka) {
		super(ia.scale(ka));
	}

	/**
	 * Constructor to initialize ambient light with given color and attenuation
	 * factors.
	 *
	 * @param ia The original light intensity as a Color.
	 * @param ka The attenuation factors as a Double.
	 */
	public AmbientLight(Color ia, Double ka) {
		super(ia.scale(ka));
	}

}
