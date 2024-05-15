package primitives;
import static java.lang.Math.abs; 
import static java.lang.Math.sqrt; 

public class Vector extends Point{
	

	public Vector(double d1, double d2, double d3) {
		super(d1,d2,d3); if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Vector can't be zero");
	}
	
	public Vector(Double3 d) {
		super(d); if (xyz.equals(Double3.ZERO))
			throw new IllegalArgumentException("Vector can't be zero");

	}
		
	public Vector add(Vector vec) {
		return new Vector(this.xyz.add(vec.xyz));
		
	}

	
	public Vector scale(double scalar) {
		return new Vector(this.xyz.scale(scalar));
	}
	
	public double dotProduct(Vector vec) {
		return this.xyz.d1 * vec.xyz.d1 + this.xyz.d2 * vec.xyz.d2 + 
				this.xyz.d3 * vec.xyz.d3; 
	}
	
	public Vector crossProduct(Vector vec) {
		return new Vector(((this.xyz.d2 * vec.xyz.d3) - (this.xyz.d3 * vec.xyz.d2)),
				((this.xyz.d3 * vec.xyz.d1) - (this.xyz.d1 * vec.xyz.d3)),
				((this.xyz.d1 * vec.xyz.d2) - (this.xyz.d2 * vec.xyz.d1)) );
	}
		
	public double lengthSquared() {
		return (this.dotProduct(this));
	}
	
	public double length() {
		return sqrt(this.lengthSquared());
	}
	
	
	public Vector normalize() {
	    return new Vector(xyz.d1 / length(), xyz.d2 / length(), xyz.d3 / length());
	}

		
	

	@Override
	 public boolean equals(Object obj) {
		 return super.equals(obj);
	 }
	 
	 @Override
	   public String toString() { return "Vector: " + super.toString(); }


	

}

