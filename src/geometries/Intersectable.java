package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import java.util.Objects;

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
	public final List<Point> findIntersections(Ray ray) {
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

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			return (obj instanceof GeoPoint && Objects.equals(this.geometry, ((GeoPoint) obj).geometry)
					&& this.point.equals(((GeoPoint) obj).point));

		}

		@Override
		public String toString() {
			return "GeoPoint{" + "geometry=" + this.geometry + ", point=" + this.point + '}';
		}

	}
}
