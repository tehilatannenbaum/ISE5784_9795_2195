package renderer;

import scene.Scene;
import geometries.Intersectable.GeoPoint;

import primitives.*;
import geometries.*;
import lighting.*;

import static primitives.Util.*;

import java.util.List;

import static java.lang.Math.*;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase. This class is
 * responsible for initializing the ray tracer with a given scene.
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * The maximum recursion level for color calculation.
	 */
	private static final int MAX_CALC_COLOR_LEVEL = 10;

	/**
	 * The minimum coefficient threshold for color calculation.
	 */
	private static final double MIN_CALC_COLOR_K = 0.001;

	/**
	 * The initial attenuation factor used in color calculations.
	 */
	private static final Double3 INITIAL_K = Double3.ONE;

	/**
	 * Constructor for SimpleRayTracer, initializing with the given scene.
	 * 
	 * @param scene The scene to be rendered.
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Traces the ray and returns the color of the closest intersection point.
	 * 
	 * @param ray The ray to trace.
	 * @return The color at the intersection point, or the background color if no
	 *         intersection.
	 */
	@Override
	public Color traceRay(Ray ray) {
		GeoPoint closestPoint = findClosestIntersection(ray);
		return closestPoint == null ? scene.background : calcColor(closestPoint, ray);

	}

	/**
	 * Traces a list of rays and calculates the average color resulting from all
	 * rays. For stage 1
	 * 
	 * @param rays the list of rays to be traced
	 * @return the average color resulting from tracing all the rays
	 */
	public Color traceRays(List<Ray> rays) {
		Color averageColor = Color.BLACK;
		for (Ray ray : rays) {
			averageColor = averageColor.add(traceRay(ray));
		}
		return averageColor.reduce(rays.size());
	}

	/**
	 * Calculates the color at a given intersection point with recursion.
	 * 
	 * @param gp    The intersection point.
	 * @param ray   The ray from the camera to the intersection.
	 * @param level The current recursion level.
	 * @param k     The cumulative attenuation factor.
	 * @return The color at the intersection point.
	 */
	private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(gp, ray, k);
		return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
	}

	/**
	 * Calculates the color at a given geometric point in the scene for a specified
	 * ray. The method considers multiple levels of reflection and refraction to
	 * compute the final color. Ambient light intensity from the scene is also added
	 * to the result.
	 *
	 * @param gp  the geometric point for which the color is calculated
	 * @param ray the ray that intersects the geometric point
	 * @return the calculated color at the given geometric point
	 */
	private Color calcColor(GeoPoint gp, Ray ray) {
		return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
	}

	/**
	 * Calculates the effect of different light sources on a point in the scene
	 * according to the Phong model.
	 *
	 * @param geoPoint The point on the geometry in the scene.
	 * @param ray      The ray from the camera to the intersection.
	 * @param k        The attenuation factor for the local effects.
	 * @return The color of the point affected by local light sources.
	 */
	private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
		Geometry geometry = geoPoint.geometry;
		Point point = geoPoint.point;

		Vector n = geometry.getNormal(point);
		Vector v = ray.getDirection();
		double nv = alignZero(n.dotProduct(v));

		if (nv == 0)
			return Color.BLACK;

		Material material = geometry.getMaterial();
		Color color = geometry.getEmission();

		for (LightSource lightSource : scene.lights) {
			Vector l = lightSource.getL(point);
			double nl = alignZero(n.dotProduct(l));
			if (nl * nv > 0) { // sign(nl) == sing(nv)
				Double3 ktr = transparency(geoPoint, l, n, lightSource);
				if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr); // the intensity of the light source
					color = color.add(calcDiffusive(material.kD, nl, iL)
							.add(calcSpecular(material.kS, n, l, v, material.nShininess, iL)));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the diffuse component of the lighting.
	 *
	 * @param kd        The diffuse reflection coefficient.
	 * @param nl        The dot product of the normal and light direction vectors.
	 * @param intensity The intensity of the light.
	 * @return The color representing the diffuse component.
	 */
	private Color calcDiffusive(Double3 kd, double nl, Color intensity) {
		return intensity.scale(kd.scale(abs(nl)));
	}

	/**
	 * Calculates the specular component of the lighting.
	 *
	 * @param ks         The specular reflection coefficient.
	 * @param n          The normal vector at the point of intersection.
	 * @param l          The light direction vector.
	 * @param v          The view direction vector.
	 * @param nShininess The shininess coefficient of the material.
	 * @param intensity  The intensity of the light.
	 * @return The color representing the specular component.
	 */
	private Color calcSpecular(Double3 ks, Vector n, Vector l, Vector v, int nShininess, Color intensity) {
		Vector r = l.subtract(n.scale(l.dotProduct(n)).scale(2)).normalize();
		double minusVR = alignZero(-v.dotProduct(r));
		return minusVR <= 0 ? Color.BLACK : intensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
	}

	/**
	 * Checks if the point is unshaded by any other geometry from the light source.
	 * 
	 * @param gp          The point on the geometry in the scene.
	 * @param lightSource The light source.
	 * @param l           The vector from the point to the light source.
	 * @param n           The normal vector at the point.
	 * @return true if the point is unshaded, false if it is in shadow.
	 */
	@SuppressWarnings("unused")
	@Deprecated(forRemoval = true)
	private boolean unshaded(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {

		// from point to light source
		Vector lightDirection = l.scale(-1);
		Point point = gp.point;
		Ray lightRay = new Ray(point, lightDirection, n);

		var intersections = scene.geometries.findGeoIntersections(lightRay);
		if (intersections == null)
			return true;

		var lightDistance = lightSource.getDistance(point);
		for (GeoPoint intersection : intersections) {
			if (alignZero(intersection.point.distance(point) - lightDistance) <= 0) {
				if (intersection.geometry.getMaterial().kT.equals(Double3.ZERO)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Calculates the transparency factor at a given geometric point in the scene.
	 * 
	 * @param gp          the geometric point at which transparency is being
	 *                    calculated
	 * @param l           the direction vector from the point to the light source
	 * @param n           the normal vector at the geometric point
	 * @param lightSource the light source affecting the transparency
	 * @return the transparency factor at the given geometric point, represented as
	 *         a {@link Double3} value
	 */
	private Double3 transparency(GeoPoint gp, Vector l, Vector n, LightSource lightSource) {

		// from point to light source
		Vector lightDirection = l.scale(-1);
		Point point = gp.point;
		Ray lightRay = new Ray(point, lightDirection, n);

		var intersections = scene.geometries.findGeoIntersections(lightRay);

		Double3 ktr = Double3.ONE;
		if (intersections == null)
			return ktr;

		var lightDistance = lightSource.getDistance(point);
		for (GeoPoint intersection : intersections) {
			if (alignZero(intersection.point.distance(point) - lightDistance) <= 0) {
				ktr = ktr.product(intersection.geometry.getMaterial().kT);
				if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
					return Double3.ZERO;
				}
			}
		}

		return ktr;
	}

	/**
	 * Constructs a reflected ray at a given geometric point.
	 *
	 * @param gp the geometric point where the reflection occurs
	 * @param v  the direction vector of the incoming ray
	 * @param n  the normal vector at the point of intersection
	 * @return the reflected ray, or null if the dot product of the direction vector
	 *         and the normal is zero
	 */
	private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
		double vn = alignZero(v.dotProduct(n));
		if (vn == 0)
			return null;
		Vector r = v.subtract(n.scale(2 * vn));
		return new Ray(gp.point, r, n);
	}

	/**
	 * Constructs the refraction ray for the given intersection point.
	 *
	 * @param gp  The intersection point on the geometry.
	 * @param ray The incident ray.
	 * @param n   The normal vector at the intersection point.
	 * @return The refraction ray.
	 */
	private Ray constructRefractedRay(GeoPoint gp, Ray ray, Vector n) {
		return new Ray(gp.point, ray.getDirection(), n);
	}

	/**
	 * Calculates the global effects (reflection and refraction) for a given
	 * intersection point.
	 * 
	 * @param gp    The intersection point.
	 * @param ray   The ray from the camera to the intersection.
	 * @param level The current recursion level.
	 * @param k     The cumulative attenuation factor.
	 * @return The color contribution from global effects.
	 */
	private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
		Color color = Color.BLACK;
		Vector n = gp.geometry.getNormal(gp.point);
		Material material = gp.geometry.getMaterial();
		Vector v = ray.getDirection();

		Double3 kkr = material.kR.product(k);
		if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
			Ray reflectedRay = constructReflectedRay(gp, v, n);
			GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
			if (reflectedPoint != null) {
				color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(material.kR));
			}
		}

		Double3 kkt = material.kT.product(k);
		if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
			Ray refractedRay = constructRefractedRay(gp, ray, n);
			GeoPoint refractedPoint = findClosestIntersection(refractedRay);
			if (refractedPoint != null) {
				color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(material.kT));
			}
		}

		return color;
	}

	/**
	 * Finds the closest intersection point between a ray and geometries in the
	 * scene.
	 *
	 * @param ray The ray to intersect with the geometries.
	 * @return The closest intersection point as a GeoPoint, or null if no
	 *         intersections exist.
	 */
	private GeoPoint findClosestIntersection(Ray ray) {
		return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
	}

}
