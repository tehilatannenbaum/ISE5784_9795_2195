package primitives;

/**
 * The Material class represents the material properties of a geometry in the
 * scene. This class is a plain data structure (PDS) with public fields.
 */
public class Material {
	/**
	 * Diffuse coefficien
	 */
	public Double3 kD = Double3.ZERO;
	/**
	 * Specular coefficient
	 */
	public Double3 kS = Double3.ZERO;
	/**
	 * Shininess level
	 */
	public int nShininess = 0;
	/**
	 * Reflectivity coefficient
	 */
	public Double3 kR = Double3.ZERO;
	/**
	 * Transparency coefficient
	 */
	public Double3 kT = Double3.ZERO;

	/**
	 * Sets the diffuse coefficient (kD) with a Double3 value.
	 * 
	 * @param kD The diffuse coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKd(Double3 kD) {
		this.kD = kD;
		return this;
	}

	/**
	 * Sets the diffuse coefficient (kD) with a double value.
	 * 
	 * @param kD The diffuse coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKd(double kD) {
		this.kD = new Double3(kD);
		return this;
	}

	/**
	 * Sets the specular coefficient (kS) with a Double3 value.
	 * 
	 * @param kS The specular coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKs(Double3 kS) {
		this.kS = kS;
		return this;
	}

	/**
	 * Sets the specular coefficient (kS) with a double value.
	 * 
	 * @param kS The specular coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKs(double kS) {
		this.kS = new Double3(kS);
		return this;
	}

	/**
	 * Sets the shininess level (nShininess).
	 * 
	 * @param nShininess The shininess level.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setShininess(int nShininess) {
		this.nShininess = nShininess;
		return this;
	}

	/**
	 * Sets the reflectivity coefficient (kR) with a Double3 value.
	 * 
	 * @param kR The reflectivity coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKr(Double3 kR) {
		this.kR = kR;
		return this;
	}

	/**
	 * Sets the reflectivity coefficient (kR) with a double value.
	 * 
	 * @param kR The reflectivity coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKr(double kR) {
		this.kR = new Double3(kR);
		return this;
	}

	/**
	 * Sets the transparency coefficient (kT) with a Double3 value.
	 * 
	 * @param kT The transparency coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKt(Double3 kT) {
		this.kT = kT;
		return this;
	}

	/**
	 * Sets the transparency coefficient (kT) with a double value.
	 * 
	 * @param kT The transparency coefficient.
	 * @return The Material object itself (for method chaining).
	 */
	public Material setKt(double kT) {
		this.kT = new Double3(kT);
		return this;
	}
}
