
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;


/**
 * The SpotLight class represents a light source that has a specific position and direction,
 * creating a beam of light that narrows as it moves away from the source. 
 * It extends the PointLight class, inheriting its position and color properties.
 */
public class SpotLight extends PointLight {
	/**
	 * The direction vector of the spot light. This vector indicates the direction in which the light is directed.
	 */
	private Vector dir;

	/**
	 * The narrowness of the beam of the spot light. A higher value results in a narrower beam.
	 * Default value is 1.
	 */
	private double NarrowBeam = 1;

	/**
	 * Constructs a SpotLight with the specified color, position, and direction.
	 *
	 * @param color     the color of the light
	 * @param position  the position of the light source
	 * @param direction the direction in which the light is directed
	 */
	public SpotLight(Color color, Point position, Vector direction) {
		super(color, position);
		this.dir = direction.normalize();
	}

	/**
	 * Sets the constant attenuation factor for the spot light.
	 * This method overrides the PointLight's setKc method to allow for method chaining with SpotLight.
	 *
	 * @param kc the new constant attenuation factor
	 * @return the current SpotLight instance with the updated constant attenuation factor
	 */
	public SpotLight setKc(double kc) {
		super.setKc(kc);
		return this;
	}

	/**
	 * Sets the linear attenuation factor for the spot light.
	 * This method overrides the PointLight's setKl method to allow for method chaining with SpotLight.
	 *
	 * @param kl the new linear attenuation factor
	 * @return the current SpotLight instance with the updated linear attenuation factor
	 */
	public SpotLight setKl(double kl) {
		super.setKl(kl);
		return this;
	}

	/**
	 * Sets the quadratic attenuation factor for the spot light.
	 * This method overrides the PointLight's setKq method to allow for method chaining with SpotLight.
	 *
	 * @param kq the new quadratic attenuation factor
	 * @return the current SpotLight instance with the updated quadratic attenuation factor
	 */
	public SpotLight setKq(double kq) {
		super.setKq(kq);
		return this;
	}

	/**
	 * Sets the narrowness of the beam for the spot light.
	 * A higher value results in a narrower beam.
	 *
	 * @param NarrowBeam the new narrowness value for the beam
	 * @return the current SpotLight instance with the updated narrow beam value
	 */
	public SpotLight setNarrowBeam(double NarrowBeam) {
		this.NarrowBeam = NarrowBeam;
		return this;
	}

	@Override
	public Color getIntensity(Point p) {
		return NarrowBeam != 1 ? super.getIntensity(p).scale(Math.pow(Math.max(0, dir.dotProduct(getL(p))), NarrowBeam))
				: super.getIntensity(p).scale(Math.max(0, dir.dotProduct(getL(p))));
	}
}
