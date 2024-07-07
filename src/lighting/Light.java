package lighting;

import primitives.Color;

/**
 * Abstract class representing a light source. Provides the basic properties and
 * methods for all types of light sources.
 */
abstract class Light {
	/**
	 * The intensity of the light source.
	 */
	protected Color intensity;

	/**
	 * Constructor to initialize the light intensity.
	 *
	 * @param intensity the intensity of the light source
	 */
	protected Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Getter for the intensity of the light source.
	 *
	 * @return the intensity of the light source
	 */
	public Color getIntensity() {
		return intensity;
	}

}
