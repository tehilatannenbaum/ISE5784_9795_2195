package renderer;

import scene.Scene;
import geometries.Intersectable.GeoPoint;

import primitives.*;
import geometries.*;
import lighting.*;

import static primitives.Util.*;
import static java.lang.Math.*;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase. This class is
 * responsible for initializing the ray tracer with a given scene.
 */
public class SimpleRayTracer extends RayTracerBase {

    /** 
     * Offset size for the origin of shadow rays. 
     **/
	private static final double DELTA = 0.1;

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
		// Find intersections between the ray and the geometries in the scene
		var intersections = scene.geometries.findGeoIntersections(ray);

		// If no intersections found, return the background color of the scene
		// Otherwise - calculate the color at the closest intersection point
		return intersections == null ? scene.background : calcColor(ray.findClosestGeoPoint(intersections), ray);
	}

	/**
	 * Calculates the color at a given intersection point.
	 * 
	 * @param geoPoint The intersection point.
	 * @param ray      The ray from the camera to the intersection.
	 * @return The color at the intersection point.
	 */
	private Color calcColor(GeoPoint geoPoint, Ray ray) {
		return scene.ambientLight.getIntensity().add(calcLocalEffects(geoPoint, ray));
	}

	/**
	 * Calculates the effect of different light sources on a point in the scene
	 * according to the Phong model.
	 *
	 * @param geoPoint The point on the geometry in the scene.
	 * @param ray      The ray from the camera to the intersection.
	 * @return The color of the point affected by local light sources.
	 */
	private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
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
		    if ((nl * nv > 0) && unshaded(geoPoint,lightSource, l, n)) {
		        Color iL = lightSource.getIntensity(point);
		        color = color.add(iL.scale(calcDiffuse(material, nl).add(calcSpecular(material, n, l, nl, v))));
		    }
		
		}
		return color;
	}

	/**
	 * Calculates the diffuse component of light reflection.
	 *
	 * @param material The material of the geometry.
	 * @param nl       The dot product between the normal vector and the light
	 *                 vector.
	 * @return The color contribution from the diffuse reflection.
	 */
	private Double3 calcDiffuse(Material material, double nl) {
		return material.kD.scale(abs(nl));
	}

	/**
	 * Calculates the specular component of light reflection.
	 *
	 * @param material The material of the geometry.
	 * @param n        The normal vector at the point.
	 * @param l        The light vector.
	 * @param nl       The dot product between the normal vector and the light
	 *                 vector.
	 * @param v        The view vector.
	 * @return The color contribution from the specular reflection.
	 */
	private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
		// Reflectance vector
		Vector r = l.subtract(n.scale(nl * 2));
		// Max between 0 and -v*r
		double max = max(0, v.scale(-1).dotProduct(r));

		return material.kS.scale(pow(max, material.nShininess));
	}
	/**
     * Checks if the point is unshaded by any other geometry from the light source.
     * 
     * @param gp The point on the geometry in the scene.
     * @param lightSource The light source.
     * @param l  The vector from the point to the light source.
     * @param n  The normal vector at the point.
     * @return true if the point is unshaded, false if it is in shadow.
     */
  private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n) {

    	// from point to light source
        Vector lightDirection = l.scale(-1); 
        Point point = gp.point;
        Vector delta = n.scale(n.dotProduct(lightDirection) > 0 ? DELTA : -DELTA);
        Point pointToCheck = point.add(delta);
        Ray lightRay = new Ray(pointToCheck, lightDirection);

        var intersections = scene.geometries.findGeoIntersections(lightRay);

        if (intersections == null || intersections.isEmpty())
        	return true;
        
        var lightDistance = lightSource.getDistance(point);
        for (GeoPoint intersection : intersections) {
        	if (alignZero(intersection.point.distance(point) - lightDistance) <= 0) {
        		return false;
        	}
        }
        
        return true;
    }
	
	}

