package primitives;
import static java.lang.Math.abs; 
import static java.lang.Math.sqrt; 

public class Point {
	protected final Double3 xyz;
	
	public Point(double d1, double d2, double d3) {
		xyz = new Double3(d1,d2,d3);
	}
	
	Point(Double3 d){
		xyz = d;
	}

	
	public Vector subtract(Point p) {
	    return new Vector(this.xyz.subtract(p.xyz));
	}
	
	
	
    public Point add(Vector vec) { 
        return new Point(this.xyz.add(vec.xyz));
        
    }
		   
	public double distanceSquared(Point p) {
		return ((this.xyz.d1-p.xyz.d1)*(this.xyz.d1-p.xyz.d1) +
				(this.xyz.d2-p.xyz.d2)*(this.xyz.d2-p.xyz.d2) +
				(this.xyz.d3-p.xyz.d3)*(this.xyz.d3-p.xyz.d3));
	}
	
	public double distance(Point p) {
		return sqrt(this.distanceSquared(p));
	}

 
	  

	@Override
	 public boolean equals(Object obj) {
	 if (this == obj) return true;
	 return (obj instanceof Point other)
	 && this.xyz.equals(other.xyz);	
	 }

	 @Override
	   public String toString() { return xyz.toString(); }

}
