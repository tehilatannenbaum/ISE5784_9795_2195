package renderer;

import java.util.List;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * Abstract class RayTracerBase, serving as a base class for ray tracing
 * algorithms.
 */
public abstract class RayTracerBase {
	/**
	 * The scene to be rendered.
	 */
	protected final Scene scene;

	/**
	 * Constructor for RayTracerBase, initializing with the given scene.
	 * 
	 * @param scene The scene to be rendered.
	 */
	public RayTracerBase(Scene scene) {
		this.scene = scene;
	}

	/**
	 * The method is responsible for tracing a ray and computing the color of the
	 * closest object it intersects.
	 * 
	 * @param ray The ray to be traced.
	 * @return The color of the closest object intersected by the ray.
	 */
	public abstract Color traceRay(Ray ray);

	/**
	 * Traces a list of rays and returns the resulting color.
	 * 
	 * @param rays the rays to be traced
	 * @return the resulting color
	 */
	public abstract Color traceRays(List<Ray> rays);
}
