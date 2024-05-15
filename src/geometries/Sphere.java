package geometries;
import primitives.Point;
import primitives.Vector;


public class Sphere extends RadialGeometry {
	private final Point center;
	
	protected Sphere(Point p, double radius) {
        super(radius); // Call the constructor of the superclass RadialGeometry
		center = p;
	}
	
	public Vector getNormal(Point p) {
		return null;
	}
	
}
