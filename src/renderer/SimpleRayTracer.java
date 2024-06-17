package renderer;

import scene.Scene;
import primitives.*;
import java.util.List;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase. This class is
 * responsible for initializing the ray tracer with a given scene.
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * Constructor for SimpleRayTracer, initializing with the given scene.
	 * 
	 * @param scene The scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Trace a ray in the scene and calculate the color at the intersection point.
	 * 
	 * @param ray The ray to trace.
	 * @return The color at the intersection point.
	 */
	@Override
	public Color traceRay(Ray ray) {
		// Find intersections between the ray and the geometries in the scene
		List<Point> intersections = scene.geometries.findIntersections(ray);

		// If no intersections found, return the background color of the scene
		if (intersections == null) {
			return scene.background;
		}

		// Find the closest intersection point to the ray's origin
		Point closestPoint = ray.findClosestPoint(intersections);

		// Calculate the color at the intersection point
		return calcColor(closestPoint);
	}

	/**
	 * Calculate the color at a given intersection point.
	 * 
	 * @param point The intersection point.
	 * @return The color at the intersection point.
	 */
	private Color calcColor(Point point) {
		// For now, return the ambient light color of the scene
		return scene.ambientLight.getIntensity();
	}
}
