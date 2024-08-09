package unittests.renderer;

import renderer.Camera;
import renderer.ImageWriter;
import renderer.SimpleRayTracer;
import primitives.Color;
import primitives.Point;
import primitives.Vector;
import scene.Scene;
import geometries.Sphere;
import geometries.Plane;

/**
 * A test class to demonstrate depth of field effects in rendering.
 * 
 * <p>
 * This class sets up a scene with various geometries, configures a camera with
 * depth of field parameters, and renders the scene to an image file.
 * </p>
 */
public class DepthOfFieldTest {

	/**
	 * Main method to execute the depth of field test.
	 * 
	 * <p>
	 * This method initializes the scene, configures the camera with focus distance,
	 * aperture, and other parameters, adds geometries to the scene, and renders the
	 * image to a file.
	 * </p>
	 * 
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		// Create a new scene
		Scene scene = new Scene("Test Scene");

		// Define the camera parameters
		Camera camera = Camera.getBuilder().setLocation(new Point(0, 0, 0))
				.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setVpSize(100, 100) // Example size
				.setVpDistance(100) // Example distance
				.setFocusDistance(150) // Focus distance
				.setAperture(2) // Aperture size
				.setNumOfRays(20) // Number of rays for sampling
				.setImageWriter(new ImageWriter("depth_of_field_test", 500, 500))
				.setRayTracer(new SimpleRayTracer(scene)) // Implement a suitable RayTracerBase
				.build();

		// Add objects to the scene
		scene.geometries.add(new Sphere(new Point(0, 0, -200), 50).setEmission(new Color(255, 0, 0)));
		scene.geometries.add(new Plane(new Point(0, -50, 0), new Vector(0, 1, 0)).setEmission(new Color(0, 255, 0)));
		scene.geometries.add(new Sphere(new Point(0, 0, -300), 30).setEmission(new Color(0, 0, 255)));

		// Render the scene
		camera.renderImage();
		camera.writeToImage();
	}
}
