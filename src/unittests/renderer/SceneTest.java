package unittests.renderer;

import static java.awt.Color.*;

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
    private final Scene scene = new Scene("Complex Effects Scene");

    /** Camera builder for the tests with triangles */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Test case for rendering a scene with multiple geometries and different effects.
     */
    @Test
    public void renderComplexEffectsScene() {
        // Add geometries
        scene.geometries.add(
                new Sphere(new Point(0, 0, -300), 100d).setEmission(new Color(255, 0, 255))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Triangle(new Point(-150, -150, -200), new Point(150, -150, -200), new Point(0, 150, -200))
                        .setEmission(new Color(0, 0, 255))
                        .setMaterial(new Material().setKd(0.5).setKs(0.3).setShininess(60).setKt(0.5)),
                new Cylinder(new Ray(new Point(200, -100, -400), new Vector(0, 1, 0)), 60, 300)
                        .setEmission(new Color(25, 50, 75))
                        .setMaterial(new Material().setKd(0.5).setKs(0.4).setShininess(80).setKr(0.3)),
                new Plane(new Point(0, -200, 0), new Vector(0, 1, 0))
                        .setEmission(new Color(150, 150, 150))
                        .setMaterial(new Material().setKd(0.6).setKs(0.3).setShininess(30)));

        // Add lights
        scene.lights.add(new SpotLight(new Color(1000, 600, 0), new Point(-200, -200, 300), new Vector(1, 1, -2))
                .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(new DirectionalLight(new Color(500, 300, 300), new Vector(-1, -1, -1)));

        // Setup camera
        cameraBuilder.setLocation(new Point(0, 100, 500)).setVpDistance(1000).setVpSize(300, 300)
                .setImageWriter(new ImageWriter("complexScene", 1000, 1000)).build().renderImage().writeToImage();
    }
}
