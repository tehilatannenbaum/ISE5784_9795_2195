package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Interface for geometrical objects that can be intersected by rays.
 */
public abstract class Intersectable {

	/**
	 * Finds intersection points between the intersectable object and a given ray.
	 *
	 * @param ray The ray to intersect with.
	 * @return A list of intersection points with the ray. Returns null if there are
	 *         no intersections.
	 */
	public List<Point> findIntersections(Ray ray);
	
	public static class GeoPoint {
		 public Geometry geometry;
		 public Point point;
		}

}
