package geometries;

import java.awt.Color;
import primitives.Point;
import primitives.Vector;

/**
 * Abstract class representing a geometric shape in 3D space.
 * This class extends the {@link Intersectable} class and provides
 * additional properties and methods common to all geometric shapes.
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
     * Gets the emission color of the geometry.
     * 
     * @return the emission color
     */
    Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     * 
     * @param color the new emission color
     */
    void setEmission(Color color) {
        this.emission = color;
    }
}
