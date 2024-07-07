package geometries;

import java.util.List;
import primitives.Point;
import primitives.Ray;

/**
 * Abstract class representing geometrical objects that can be intersected by
 * rays.
 */
public abstract class Intersectable {

	/**
	 * Converts a list of GeoPoints to a list of Points representing intersection
	 * points with a given ray.
	 * 
	 * @param ray The ray to find intersections with.
	 * @return A list of Points representing intersection points with the ray, or
	 *         null if no intersections are found.
	 */
	public List<Point> findIntersections(Ray ray) {
		var geoList = findGeoIntersections(ray);
		return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
	}

	/**
	 * Finds all intersections of the given ray with the intersectable objects,
	 * including the geometry.
	 *
	 * @param ray the ray to intersect with the objects
	 * @return a list of GeoPoint intersection points
	 */
	public final List<GeoPoint> findGeoIntersections(Ray ray) {
		return findGeoIntersectionsHelper(ray);
	}

	/**
	 * Finds geometric intersection points between the intersectable object and a
	 * given ray. To be implemented by subclasses.
	 *
	 * @param ray The ray to intersect with.
	 * @return A list of geometric intersection points with the ray. Returns null if
	 *         there are no intersections.
	 */
	protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

	/**
	 * Inner static class representing a geometric point, combining a geometry and a
	 * point.
	 */
	public static class GeoPoint {
		/** The geometry part of the GeoPoint */
		public Geometry geometry;
		/** The point part of the GeoPoint */
		public Point point;

		/**
		 * Constructs a GeoPoint with the given geometry and point.
		 *
		 * @param geometry the geometry part of the GeoPoint.
		 * @param point    the point part of the GeoPoint.
		 */
		public GeoPoint(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;
		}

		/**
		 * Compares this GeoPoint to the specified object. The result is true if and
		 * only if the argument is not null and is a GeoPoint object that represents the
		 * same geometry and point as this object.
		 *
		 * @param obj the object to compare this GeoPoint against.
		 * @return true if the given object represents a GeoPoint equivalent to this
		 *         GeoPoint, false otherwise.
		 */
		@Override
		public boolean equals(Object obj) {
			// Check if it's the same object
			if (this == obj)
				return true;
			// Check if the object is null or not the same class
			if (obj == null || getClass() != obj.getClass())
				return false;
			GeoPoint other = (GeoPoint) obj;
			// Check if the geometry and point are the same
			return this.geometry == other.geometry && this.point.equals(other.point);
		}

		@Override
		public String toString() {
			return "GeoPoint [" + geometry + "," + point + "]";

		}

	}
}
