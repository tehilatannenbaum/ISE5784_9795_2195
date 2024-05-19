package geometries;

/**
 * Represents a radial geometry in three-dimensional space.
 * A radial geometry is defined by its radius.
 */
public class RadialGeometry {

    /** The radius of the radial geometry */
    private final double radius;

    /**
     * Constructs a radial geometry with the given radius.
     * @param _radius The radius of the radial geometry
     */
    protected RadialGeometry(double _radius) {
        this.radius = _radius;
    }
}
