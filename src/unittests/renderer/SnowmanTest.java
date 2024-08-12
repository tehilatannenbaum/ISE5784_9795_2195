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
public class SnowmanTest {
	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");

	/**
	 * A builder for the Camera object, initialized with a direction and a simple
	 * ray tracer for the scene. The direction of the camera is set to face along
	 * the negative z-axis with the up vector along the positive y-axis. A
	 */

	private final Camera.Builder cameraBuilder = Camera.getBuilder()
			.setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))

			// MP1 depth of field
			.setNumOfRays(8).setAperture(3).setFocusDistance(800)

			// MP2 Adaptive Super Sampling
			.setAdaptiveGrid(2)

			.setRayTracer(new SimpleRayTracer(scene));

	/** Produce a picture of a snowman */
	@Test
	public void snowman() {
		// Snowman Body
		scene.geometries.add(new Sphere(new Point(0, -60, 400), 50d).setEmission(new Color(169, 169, 169)) // Bottom
																											// sphere
				.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 10, 400), 35d).setEmission(new Color(169, 169, 169)) // Middle sphere
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 65, 400), 25d).setEmission(new Color(169, 169, 169)) // Top sphere
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)));

		// Snowman Eyes
		scene.geometries.add(new Sphere(new Point(-10, 70, -75 + 500), 2d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(10, 70, -75 + 500), 2d).setEmission(new Color(0, 0, 0)));

		// Snowman Nose
		scene.geometries.add(
				new Triangle(new Point(0, 65, -50 + 500), new Point(0, 65, -130 + 500), new Point(25, 65, -90 + 500))
						.setEmission(new Color(255, 165, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		// Snowman Buttons
		scene.geometries.add(
				new Sphere(new Point(0, -20, -63 + 500), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 0, -65 + 500), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)),
				new Sphere(new Point(0, 20, -62 + 500), 3d).setEmission(new Color(139, 69, 19))
						.setMaterial(new Material().setKd(0.4).setKs(0.6).setShininess(300)));

		// Ice Lake
		scene.geometries.add(new Plane(new Point(-200, -110, -200), new Point(1, -110, -200), new Point(200, -110, 200))
				.setEmission(new Color(173, 216, 230))
				.setMaterial(new Material().setKd(0.1).setKs(0.9).setShininess(300).setKr(new Double3(0.7, 0.7, 0.7))));

		// Snowman Smile
		scene.geometries.add(new Sphere(new Point(-12, 55, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(-8, 52, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(-4, 50, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(0, 49, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(4, 50, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(8, 52, -80 + 500), 1d).setEmission(new Color(0, 0, 0)),
				new Sphere(new Point(12, 55, -80 + 500), 1d).setEmission(new Color(0, 0, 0)));

		// Snowflakes
		int numSnowflakes = 100;
		for (int i = 0; i < numSnowflakes; i++) {
			double x = Math.random() * 400 - 200;
			double y = Math.random() * 200 - 100;
			double z = Math.random() * 400 - 200;
			double radius = 0.5 + Math.random();

			scene.geometries.add(new Sphere(new Point(x, y, z), radius).setEmission(new Color(255, 255, 255))
					.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10)));
		}

		// Snowman Lights
		scene.lights.add(new SpotLight(new Color(500, 500, 800), new Point(200, 200, 500), new Vector(-1, -1, -1.5))
				.setKl(0.0005).setKq(0.0000005));

		// Sunlight Directional Light Source
		scene.lights.add(new DirectionalLight(new Color(255, 200, 100), new Vector(0.5, -0.5, -0.5)));

		// Point Light to enhance illumination around the snowman
		scene.lights.add(new PointLight(new Color(400, 400, 600), new Point(-100, 20, 300)).setKl(0.001).setKq(0.0001));

		// Ambient Light to provide overall illumination
		AmbientLight ambientLight = new AmbientLight(new Color(100, 100, 150), 0.2);
		scene.setAmbientLight(ambientLight);

		// Additional Spot Light to add emphasis
		scene.lights.add(new SpotLight(new Color(600, 600, 900), new Point(100, 100, 600), new Vector(-1, -1, -1))
				.setKl(0.0005).setKq(0.0000005));

		// Trees
		// First tree
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

		// Second tree
		scene.geometries.add(new Triangle(new Point(-100, 15, -200), new Point(-80, 50, -200), new Point(-50, 20, -200))
				.setEmission(new Color(0, 100, 0)).setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-100, 15, -200), new Point(-80, 50, -200), new Point(-90, 20, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-100, 15, -200), new Point(-50, 20, -200), new Point(-90, 20, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-105, -5, -200), new Point(-80, 20, -200), new Point(-45, -10, -200))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-105, -5, -200), new Point(-80, 20, -200), new Point(-65, -12, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-105, -5, -200), new Point(-45, -10, -200), new Point(-65, -12, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-110, -25, -200), new Point(-80, -5, -200), new Point(-40, -25, -200))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-110, -25, -200), new Point(-80, -5, -200), new Point(-60, -30, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-110, -25, -200), new Point(-40, -25, -200), new Point(-60, -30, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-115, -45, -200), new Point(-80, -25, -200), new Point(-30, -45, -200))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-115, -45, -200), new Point(-80, -25, -200), new Point(-50, -50, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-115, -45, -200), new Point(-30, -45, -200), new Point(-50, -50, -160))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		// Third tree
		scene.geometries.add(new Triangle(new Point(150, 30, -300), new Point(180, 70, -300), new Point(210, 40, -300))
				.setEmission(new Color(0, 100, 0)).setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(150, 30, -300), new Point(180, 70, -300), new Point(165, 40, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(150, 30, -300), new Point(210, 40, -300), new Point(165, 40, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(145, 10, -300), new Point(180, 60, -300), new Point(220, 15, -300))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(145, 10, -300), new Point(180, 60, -300), new Point(160, 10, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(145, 10, -300), new Point(220, 15, -300), new Point(160, 10, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(140, -10, -300), new Point(180, 40, -300), new Point(230, -10, -300))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(140, -10, -300), new Point(180, 40, -300), new Point(170, -15, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(140, -10, -300), new Point(230, -10, -300), new Point(170, -15, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(135, -30, -300), new Point(180, 20, -300), new Point(240, -30, -300))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(135, -30, -300), new Point(180, 20, -300), new Point(175, -35, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(135, -30, -300), new Point(240, -30, -300), new Point(175, -35, -260))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		// Fourth tree
		scene.geometries.add(
				new Triangle(new Point(-250, 30, -350), new Point(-220, 70, -350), new Point(-190, 40, -350))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-250, 30, -350), new Point(-220, 70, -350), new Point(-235, 40, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-250, 30, -350), new Point(-190, 40, -350), new Point(-235, 40, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-255, 10, -350), new Point(-220, 60, -350), new Point(-180, 15, -350))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-255, 10, -350), new Point(-220, 60, -350), new Point(-230, 10, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-255, 10, -350), new Point(-180, 15, -350), new Point(-230, 10, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-260, -10, -350), new Point(-220, 30, -350), new Point(-170, -10, -350))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-260, -10, -350), new Point(-220, 30, -350), new Point(-235, -15, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-260, -10, -350), new Point(-170, -10, -350), new Point(-235, -15, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-265, -30, -350), new Point(-220, 10, -350), new Point(-160, -30, -350))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-265, -30, -350), new Point(-220, 10, -350), new Point(-240, -35, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),
				new Triangle(new Point(-265, -30, -350), new Point(-160, -30, -350), new Point(-240, -35, -310))
						.setEmission(new Color(0, 100, 0))
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)));

		scene.setBackground(new Color(135, 206, 235));

		ImageWriter imageWriter = new ImageWriter("snowmanfocal", 1000, 1000);

		cameraBuilder.setLocation(new Point(0, 0, 1200)).setVpDistance(1000).setVpSize(500, 500)
				.setImageWriter(imageWriter).setDebugPrint(0.5).setMultithreading(5).build().renderImage()
				.writeToImage();

	}

}