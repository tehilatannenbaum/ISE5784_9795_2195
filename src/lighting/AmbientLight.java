package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents the ambient light in a scene. The ambient
 * light is a global light source that affects all objects equally.
 */
public class AmbientLight {
	/** The intensity of the ambient light */
	private final Color intensity;

	/** A constant representing no ambient light */
	public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

	/**
	 * Constructor to initialize ambient light with given color and attenuation
	 * factors.
	 *
	 * @param Ia The original light intensity as a Color.
	 * @param Ka The attenuation factors as a Double3.
	 */
	public AmbientLight(Color Ia, Double3 Ka) {
		this.intensity = Ia.scale(Ka);
	}

	/**
	 * Constructor to initialize ambient light with given color and a single
	 * attenuation factor.
	 *
	 * @param Ia The original light intensity as a Color.
	 * @param Ka The attenuation factor as a double.
	 */
	public AmbientLight(Color Ia, double Ka) {
		this.intensity = Ia.scale(Ka);
	}

	/**
	 * Get the intensity of the ambient light.
	 *
	 * @return The ambient light intensity as a Color.
	 */
	public Color getIntensity() {
		return intensity;
	}
}
