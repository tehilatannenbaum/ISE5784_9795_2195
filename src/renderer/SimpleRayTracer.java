package renderer;

import scene.Scene;
import primitives.*;

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
		var intersections = scene.geometries.findIntersections(ray);

		// If no intersections found, return the background color of the scene
		// Otherwise - calculate the color at the closest intersection point
		return intersections == null ? scene.background : calcColor(ray.findClosestPoint(intersections));
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
