package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class represents a collection of geometric objects. This class is
 * used to group geometric objects together and apply operations on the entire
 * group. Implements the Intersectable interface based on the Composite design
 * pattern.
 */
public class Geometries implements Intersectable {

	// List of geometric objects
	private final List<Intersectable> geometries = new LinkedList<>();

	// Default constructor - creates an empty list of geometries
	public Geometries() {
	}

	/**
	 * Constructor with parameters. Initializes the geometries list with the given
	 * geometric objects.
	 *
	 * @param geometries Geometric objects to be added to the list
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Adds geometric objects to the geometries list.
	 *
	 * @param geometries Geometric objects to be added
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable geometry : geometries) {
			this.geometries.add(geometry);
		}
	}

	/**
	 * Finds the intersections of the ray with all the geometries in the collection.
	 * 
	 * @param ray The ray to check intersections with.
	 * @return A list of intersection points, or null if no intersections are found.
	 */
	@Override
	public List<Point> findIntersections(Ray ray) {
		List<Point> intersections = null;

		// Iterate through all geometries
		for (Intersectable geometry : geometries) {
			// Find intersections with the current geometry
			List<Point> tempIntersections = geometry.findIntersections(ray);

			// If there are intersections, add them to the main intersections list
			if (tempIntersections != null) {
				if (intersections == null) {
					// If the main intersections list is null, initialize it
					intersections = new LinkedList<>();
				}
				// Add all intersections of the current geometry to the main list
				intersections.addAll(tempIntersections);
			}
		}

		// Return the intersections list, or null if no intersections were found
		return intersections;
	}
}