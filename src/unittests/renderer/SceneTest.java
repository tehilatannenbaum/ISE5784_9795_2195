package unittests.renderer;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 */
public class SceneTest {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");

	/**
	 * A builder for the Camera object, initialized with a direction and a simple
	 * ray tracer for the scene. The direction of the camera is set to face along
	 * the negative z-axis with the up vector along the positive y-axis. A
	 */
	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0)).setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a snowman */
	@Test
	public void snowman() {

		// Snowman Body
		scene.geometries.add(new Sphere(new Point(0, -60, -100), 50d).setEmission(new Color(169, 169, 169)) // Bottom
																											// sphere
				.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 10, -100), 35d).setEmission(new Color(169, 169, 169)) // Middle sphere
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 65, -100), 25d).setEmission(new Color(169, 169, 169)) // Top sphere
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)));

		// Snowman Eyes
		scene.geometries.add(new Sphere(new Point(-10, 70, -75), 2d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(10, 70, -75), 2d).setEmission(new Color(0, 0, 0)));

		// Snowman Nose
		scene.geometries.add(new Triangle(new Point(0, 65, -50), new Point(0, 65, -130), new Point(25, 65, -90))
				.setEmission(new Color(255, 165, 0))
				.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		// Snowman Buttons
		scene.geometries.add(
				new Sphere(new Point(0, -20, -63), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 0, -65), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 20, -62), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)));

		// Ice Lake
		scene.geometries.add(new Plane(new Point(-200, -110, -200), new Point(1, -110, -200), new Point(200, -110, 200))
				.setEmission(new Color(173, 216, 230))
				.setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(300).setKr(new Double3(0.7, 0.7, 0.7))));

		// Snowman Smile
		scene.geometries.add(new Sphere(new Point(-12, 55, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(-8, 52, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(-4, 50, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(0, 49, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(4, 50, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(8, 52, -80), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(12, 55, -80), 1d).setEmission(new Color(0, 0, 0)));

		// Lights
		scene.lights.add(new SpotLight(new Color(700, 700, 1000), new Point(200, 200, 500), new Vector(-1, -1, -2))
				.setKl(0.0004).setKq(0.0000006));

		// Sunlight light source
		scene.lights.add(new DirectionalLight(new Color(255, 200, 0), new Vector(0.5, -0.5, -0.5)));

		// Snow
		for (int i = 0; i < 100; i++) {
			double x = -200 + Math.random() * 400;
			double y = -50 + Math.random() * 200;
			double z = -200 + Math.random() * 200;
			scene.geometries.add(new Sphere(new Point(x, y, z), 1d).setEmission(new Color(255, 255, 255))
					.setMaterial(new Material().setKt(new Double3(0.5))));
		}

		/**
		 * 
		 * // Add one patch of grass along the edge of the lake double patchX = -100;
		 * double patchZ = -100; for (int j = 0; j < 20; j++) { double x = patchX +
		 * Math.random() * 10 - 5; double z = patchZ + Math.random() * 10 - 5; double
		 * height = 5 + Math.random() * 5; scene.geometries.add(new Triangle(new
		 * Point(x, -110, z), new Point(x + 1, -110, z + 1), new Point(x, -110 + height,
		 * z + 1)).setEmission(new Color(34, 139, 34)) // Green color for grass
		 * .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50))); }
		 * 
		 * // Add pebbles scene.geometries.add( new Sphere(new Point(-150, -110, -150),
		 * 2d).setEmission(new Color(105, 105, 105)) .setMaterial(new
		 * Material().setKd(0.5).setKs(0.5).setShininess(50)), new Sphere(new
		 * Point(-100, -110, -150), 2d).setEmission(new Color(105, 105, 105))
		 * .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)), new
		 * Sphere(new Point(-50, -110, -150), 2d).setEmission(new Color(105, 105, 105))
		 * .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)), new
		 * Sphere(new Point(0, -110, -150), 2d).setEmission(new Color(105, 105, 105))
		 * .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(50)));
		 **/

		// Trees
		scene.geometries.add(new Triangle(new Point(50, 27, -249), new Point(72.5, 62.5, -249), new Point(95, 35, -249))
				.setEmission(new Color(0, 100, 0)).setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(50, 27, -249), new Point(72.5, 62.5, -249), new Point(78.86, 29.4, -205))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(50, 27, -249), new Point(95, 35, -249), new Point(78.86, 29.4, -205))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(45, 12, -249), new Point(72.5, 49, -249), new Point(100, 22, -249))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(45, 12, -249), new Point(72.5, 49, -249), new Point(73.46, 11.3, -205))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(45, 12, -249), new Point(100, 22, -249), new Point(73.46, 11.3, -205))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(40, -3, -249), new Point(72.5, 34, -249), new Point(105, 7, -249))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(40, -3, -249), new Point(72.5, 34, -249), new Point(88.06, 2.8, -211))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(40, -3, -249), new Point(105, 7, -249), new Point(88.06, 2.8, -211))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(35, -18, -249), new Point(72.5, 19, -249), new Point(111, -8, -249))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(35, -18, -249), new Point(72.5, 19, -249), new Point(67, -14, -211))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(72.5, 19, -249), new Point(67, -14, -211), new Point(111, -8, -249))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		scene.setBackground(new Color(135, 206, 235));

		ImageWriter imageWriter = new ImageWriter("snowman", 800, 1000);

		cameraBuilder.setLocation(new Point(0, 0, 500)).setVpDistance(1000).setVpSize(500, 500)
				.setImageWriter(imageWriter).build().renderImage().writeToImage();
	}

}
