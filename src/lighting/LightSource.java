package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Interface for light sources in the scene. Light sources provide light
 * intensity and direction for a given point in space.
 */
public interface LightSource {

	/**
	 * Get the intensity of the light source at a given point.
	 *
	 * @param p The point in space.
	 * @return The intensity of the light source as a Color.
	 */
	public Color getIntensity(Point p);

	/**
	 * Get the direction of the light source from a given point.
	 *
	 * @param p The point in space.
	 * @return The direction of the light source as a Vector.
	 */
	public Vector getL(Point p);
	
	/**
     * Get the distance from the light source to a given point.
     *
     * @param point The point in space.
     * @return The distance to the light source.
     */
    double getDistance(Point point);

}
