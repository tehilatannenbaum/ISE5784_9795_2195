package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Class representing a point light source in the scene. A point light source
 * emits light from a specific position and attenuates with distance.
 */
public class PointLight extends Light implements LightSource {

	/**
	 * The position of the point light source in 3D space.
	 */
	private final Point position;

	/**
	 * Constant attenuation
	 */
	private double kc = 1.0;
	/**
	 * Linear attenuation
	 */
	private double kl = 0.0;
	/**
	 * Quadratic attenuation
	 */
	private double kq = 0.0;

	/**
	 * Constructor to initialize the point light with a given intensity and
	 * position.
	 *
	 * @param intensity The intensity of the light.
	 * @param position  The position of the light source.
	 */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}

	/**
	 * Set the constant attenuation factor.
	 *
	 * @param kc The constant attenuation factor.
	 * @return The point light source itself (for chaining).
	 */
	public PointLight setKc(double kc) {
		this.kc = kc;
		return this;
	}

	/**
	 * Set the linear attenuation factor.
	 *
	 * @param kl The linear attenuation factor.
	 * @return The point light source itself (for chaining).
	 */
	public PointLight setKl(double kl) {
		this.kl = kl;
		return this;
	}

	/**
	 * Set the quadratic attenuation factor.
	 *
	 * @param kq The quadratic attenuation factor.
	 * @return The point light source itself (for chaining).
	 */
	public PointLight setKq(double kq) {
		this.kq = kq;
		return this;
	}

	/**
	 * Calculates the intensity of the light at a given point, taking into account
	 * attenuation with distance.
	 *
	 * @param p The point at which to calculate the intensity.
	 * @return The intensity of the light at the given point.
	 */
	@Override
	public Color getIntensity(Point p) {
		double d = position.distance(p);
		return intensity.scale(1 / (kc + kl * d + kq * d * d));
	}

	/**
	 * Calculates the direction of the light from the light source to a given point.
	 *
	 * @param p The point at which to determine the direction.
	 * @return The normalized vector representing the direction from the light
	 *         source to the point.
	 */
	@Override
	public Vector getL(Point p) {
		return p.subtract(position).normalize();
	}

	@Override
	public double getDistance(Point point) {
		return position.distance(point);
	}
}
