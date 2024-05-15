package geometries;
import primitives.Point;
import primitives.Vector;

public class Plane {
	private final Point p;
	private final Vector vector;
	
	protected Plane(Point p1, Point p2, Point p3) {
		vector = null;
		p = p1;
	}
	
	protected Plane(Point _p, Vector vec) {
		p = _p;
		vector = vec.normalize();
	}
	
	public Vector getNormal(Point p) {
		return vector.normalize();
	}

	public Vector getNormal() {
		return vector.normalize();
	}
}
