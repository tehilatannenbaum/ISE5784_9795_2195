package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Tube extends RadialGeometry{
	protected final Ray axis;
	
	protected Tube(Ray _axis, double radius) {
		super(radius);
		axis = _axis;
	}
	
	public Vector getNormal(Point p) {
		return null;
	}
	
	
}





	