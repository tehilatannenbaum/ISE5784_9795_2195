package geometries;

import primitives.Point;
import primitives.*;

/**
 * Abstract class representing a geometric shape in 3D space. This class extends
 * the {@link Intersectable} class and provides additional properties and
 * methods common to all geometric shapes.
 */
public abstract class Geometry extends Intersectable {

	/**
	 * Gets the normal vector to the geometry at a given point.
	 * 
	 * @param p the point at which to get the normal vector
	 * @return the normal vector at the given point
	 */
	abstract public Vector getNormal(Point p);

	/**
	 * The emission color of the geometry.
	 */
	protected Color emission = Color.BLACK;

	/**
	 * The material of the geometry, initialized to a default material.
	 */
	private Material material = new Material();

	/**
	 * Gets the emission color of the geometry.
	 * 
	 * @return the emission color
	 */
	public Color getEmission() {
		return emission;
	}

	/**
	 * Sets the emission color of the geometry.
	 * 
	 * @param color the emission color to set
	 * @return the geometry instance with the updated emission color
	 */
	public Geometry setEmission(Color color) {
		this.emission = color;
		return this;
	}

	/**
	 * Gets the material of the geometry.
	 * 
	 * @return the material of the geometry.
	 */
	public Material getMaterial() {
		return material;
	}

	/**
	 * Sets the material of the geometry.
	 * 
	 * @param material the material to set.
	 * @return the geometry itself (for method chaining).
	 */
	public Geometry setMaterial(Material material) {
		this.material = material;
		return this;
	}

}
