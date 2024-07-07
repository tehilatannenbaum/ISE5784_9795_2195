package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.List;

/**
 * Represents a triangle in three-dimensional space. A triangle is a type of
 * polygon with three vertices.
 */
public class Triangle extends Polygon {

	/**
	 * Constructs a triangle with three vertices. The vertices are specified in
	 * counter-clockwise order.
	 * 
	 * @param p1 The first vertex of the triangle
	 * @param p2 The second vertex of the triangle
	 * @param p3 The third vertex of the triangle
	 */
	public Triangle(Point p1, Point p2, Point p3) {
		super(p1, p2, p3);
	}

	/**
	 * Finds the intersections between the ray and the triangle.
	 *
	 * @param ray The ray to find intersections with.
	 * @return A list of points representing the intersections, or null if there are
	 *         no intersections.
	 */
	@Override
	protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

		var rayHead = ray.getHead();
		var rayDirection = ray.getDirection();

		var intersections = plane.findIntersections(ray);
		if (intersections == null)
			// If there are no intersections with the plane, return null
			return null;

		// Check if the intersection point is inside the triangle
		Vector v1 = vertices.get(0).subtract(rayHead);
		Vector v2 = vertices.get(1).subtract(rayHead);

		Vector n1 = v1.crossProduct(v2).normalize();
		double result1 = alignZero(rayDirection.dotProduct(n1));
		if (result1 == 0)
			return null;

		Vector v3 = vertices.get(2).subtract(rayHead);
		Vector n2 = v2.crossProduct(v3).normalize();
		double result2 = alignZero(rayDirection.dotProduct(n2));
		if (result1 * result2 <= 0)
			return null;

		Vector n3 = v3.crossProduct(v1).normalize();
		double result3 = alignZero(rayDirection.dotProduct(n3));
		if (result3 * result1 <= 0)
			return null;

		return List.of(new GeoPoint(this, intersections.getFirst()));
	}

}
