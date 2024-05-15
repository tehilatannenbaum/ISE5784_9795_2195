package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;


public class Cylinder extends Tube{
	private final double hight;
	
	protected Cylinder(Ray _axis, double _radius, double _hight) {
		super(_axis, _radius);
		this.hight = _hight;
	}
	
	public Vector getNormal(Point p) {
		return null;
	}
}
