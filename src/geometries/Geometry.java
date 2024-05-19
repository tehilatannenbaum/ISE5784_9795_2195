package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * Represents a geometric shape in 3D space.
 * This interface defines methods for obtaining the normal vector at a given point on the shape.
 */
public interface Geometry {

    /**
     * Computes and returns the normal vector to the geometry at a given point.
     * @param p The point on the geometry's surface for which to compute the normal
     * @return The normal vector to the geometry at the specified point
     */
    Vector getNormal(Point p);
}
