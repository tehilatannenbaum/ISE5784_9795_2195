package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Represents a cylinder in 3D space.
 * A cylinder is defined by its axis, radius, and height.
 * Inherits from the Tube class, representing a tube with zero thickness.
 */
public class Cylinder extends Tube {

    /** The height of the cylinder */
    private final double height;

    /**
     * Constructs a new Cylinder object with the specified axis, radius, and height.
     * @param axis The axis of the cylinder
     * @param radius The radius of the cylinder
     * @param height The height of the cylinder
     */
    protected Cylinder(Ray axis, double radius, double height) {
        super(axis, radius);
        this.height = height;
    }

    /**
     * Computes and returns the normal vector to the cylinder at a given point.
     * @param p The point on the cylinder's surface for which to compute the normal
     * @return The normal vector to the cylinder at the specified point (currently returns null)
     */
    public Vector getNormal(Point p) {
        return null; // Not implemented yet
    }
}
