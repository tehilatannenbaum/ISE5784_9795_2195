package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * Represents a tube in three-dimensional space.
 * A tube is a type of radial geometry with an axis defined by a ray and a radius.
 */
public class Tube extends RadialGeometry {
    /**
     * The axis of the tube, defined by a ray.
     */
    protected final Ray axis;

    /**
     * Constructs a tube with a given axis defined by a ray and a radius.
     * @param _axis The axis of the tube
     * @param radius The radius of the tube
     */
    protected Tube(Ray _axis, double radius) {
        super(radius); // Call the constructor of the superclass RadialGeometry
        axis = _axis;
    }

    /**
     * Returns the normal vector to the tube at a given point.
     * Since a tube is a cylindrical surface, the normal vector is undefined.
     * @param p The point on the tube
     * @return null, since the normal vector to a tube is undefined
     */
    public Vector getNormal(Point p) {
        return null;
    }
}
