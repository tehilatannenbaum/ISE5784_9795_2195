package unittests.renderer;

import org.junit.jupiter.api.Test;
import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test cases for scenes with different configurations.
 */
public class SceneTest {

	/** Scene for testing */
	private final Scene scene = new Scene("Concentric Rings Scene");

	/**
	 * Test case for rendering a scene with concentric rings and a sphere.
	 */
	@Test
	public void renderConcentricRingsScene() {
		// Define geometries for concentric rings using thin cylinders
		for (int i = 0; i < 8; i++) {
			double radius = 30 + i * 15;
			double thickness = 1;
			double height = 5;
			Geometry ring = new Cylinder(new Ray(new Point(0, 0, -100), new Vector(0, 0, 1)), radius + thickness,
					height).setEmission(new Color(255, 140, 0))
					.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
			scene.geometries.add(ring);
		}

		// Define a sphere
		Geometry sphere = new Sphere(new Point(0, 0, -100), 20d).setEmission(new Color(255, 204, 0))
				.setMaterial(new Material().setKd(0.6).setKs(0.6).setShininess(100));

		// Add geometries to the scene
		scene.geometries.add(sphere);

		// Define a point light
		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point(0, 0, -50)).setKl(0.00001).setKq(0.000001));

		// Setup camera
		Camera.Builder cameraBuilder = Camera.getBuilder().setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
				.setRayTracer(new SimpleRayTracer(scene)).setLocation(new Point(0, 0, 100)).setVpDistance(100)
				.setVpSize(500, 500).setImageWriter(new ImageWriter("concentricRingsScene", 1000, 1000)); // Larger
																											// image
																											// size

		// Render and save the image
		cameraBuilder.build().renderImage().writeToImage();
	}
}
