package scene;

import lighting.AmbientLight;
import geometries.Geometries;
import primitives.Color;

/**
 * The Scene class represents a 3D scene containing geometries, ambient light,
 * and a background color. The class is a plain data structure (PDS) with public
 * fields.
 */
public class Scene {
	/** The name of the scene */
	public final String name;
	/** The background color of the scene, initialized to black */
	public Color background = Color.BLACK;
	/** The ambient light in the scene, initialized to none */
	public AmbientLight ambientLight = AmbientLight.NONE;
	/** The geometries in the scene, initialized to an empty set */
	public Geometries geometries = new Geometries();

	/**
	 * Constructor that initializes the scene with a given name.
	 *
	 * @param name The name of the scene.
	 */
	public Scene(String name) {
		this.name = name;
	}

	/**
	 * Sets the background color of the scene.
	 *
	 * @param background The background color.
	 * @return The Scene object itself (for method chaining).
	 */
	public Scene setBackground(Color background) {
		this.background = background;
		return this;
	}

	/**
	 * Sets the ambient light of the scene.
	 *
	 * @param ambientLight The ambient light.
	 * @return The Scene object itself (for method chaining).
	 */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}

	/**
	 * Sets the geometries of the scene.
	 *
	 * @param geometries The geometries.
	 * @return The Scene object itself (for method chaining).
	 */
	public Scene setGeometries(Geometries geometries) {
		this.geometries = geometries;
		return this;
	}
}
