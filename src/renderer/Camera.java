package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import primitives.Color;
import java.util.*;

import static primitives.Util.*;

/**
 * Represents a camera in 3D space. The camera is used to generate rays through
 * a view plane for rendering scenes.
 */
public class Camera implements Cloneable {

	// Existing fields
	/**
	 * Camera's position in 3D space.
	 */
	private Point p0;

	/**
	 * Camera's view direction.
	 */
	private Vector vTo;

	/**
	 * Camera's up direction.
	 */
	private Vector vUp;

	/**
	 * Camera's right direction.
	 */
	private Vector vRight;

	/**
	 * Height of the view plane. Default is 0.
	 */
	private double height = 0;

	/**
	 * Width of the view plane. Default is 0.
	 */
	private double width = 0;

	/**
	 * Distance to the view plane. Default is 0.
	 */
	private double distance = 0;

	// New fields for Depth of Field and Anti-aliasing
	/**
	 * Focus distance for depth of field. Default is 0.
	 */
	private double focusDistance = 0;

	/**
	 * Aperture size for depth of field. Default is 0.
	 */
	private double aperture = 0;

	/**
	 * Number of rays per pixel for anti-aliasing. Default is 0.
	 */
	private int numOfRays;

	/**
	 * Image writer for saving the rendered image.
	 */
	private ImageWriter imageWriter;

	/**
	 * Ray tracer for rendering the scene.
	 */
	private RayTracerBase rayTracer;

	/**
	 * Random generator for operations requiring randomness.
	 */
	private static final Random random = new Random();

	/**
	 * Private constructor to prevent direct instantiation of the {@link Camera} class.
	 * 
	 * <p>This constructor is used only within the {@link Builder} class to create an instance of the {@link Camera} class.
	 * It ensures that the camera is properly configured through the builder pattern before being used.</p>
	 */
	private Camera() {
	}

	/**
	 * Creates and returns a new builder instance for Camera.
	 * 
	 * @return a new Camera.Builder instance
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	// Getters

	/**
	 * Returns the aperture size of the camera.
	 * 
	 * @return aperture size
	 */
	public double getAperture() {
		return aperture;
	}

	/**
	 * Returns the focus distance of the camera.
	 * 
	 * @return focus distance
	 */
	public double getFocusDistance() {
		return focusDistance;
	}

	/**
	 * Returns the position of the camera (p0).
	 * 
	 * @return camera position
	 */
	public Point getP0() {
		return p0;
	}

	/**
	 * Returns the number of rays used for anti-aliasing and depth of field.
	 * 
	 * @return number of rays
	 */
	public int getNumOfRays() {
		return numOfRays;
	}

	// Setters

	/**
	 * Sets the aperture size of the camera.
	 * 
	 * @param aperture aperture size
	 * @return this Camera instance for chaining
	 */
	public Camera setAperture(double aperture) {
		if (aperture < 0)
			throw new IllegalArgumentException("Focus aperture cannot be negative");
		this.aperture = aperture;
		return this;
	}

	/**
	 * Sets the focus distance of the camera.
	 * 
	 * @param focusDistance focus distance
	 * @return this Camera instance for chaining
	 */
	public Camera setFocusDistance(double focusDistance) {
		if (focusDistance < 0)
			throw new IllegalArgumentException("Focus distance cannot be negative");
		this.focusDistance = focusDistance;
		return this;
	}

	/**
	 * Sets the number of rays used for anti-aliasing and depth of field.
	 * 
	 * @param numOfRays number of rays
	 * @return this Camera instance for chaining
	 */
	public Camera setNumOfRays(int numOfRays) {
		this.numOfRays = numOfRays;
		return this;
	}

	/**
	 * Sets the image writer for the camera.
	 * 
	 * @param imageWriter the image writer
	 * @return this Camera instance for chaining
	 */
	public Camera setImageWriter(ImageWriter imageWriter) {
		this.imageWriter = imageWriter;
		return this;
	}

	/**
	 * Sets the ray tracer for the camera.
	 * 
	 * @param rayTracer the ray tracer
	 * @return this Camera instance for chaining
	 */
	public Camera setRayTracer(RayTracerBase rayTracer) {
		this.rayTracer = rayTracer;
		return this;
	}

	// Other methods

	/**
	 * Constructs a ray through the specified pixel (j, i) in the view plane.
	 * 
	 * @param nX the number of pixels in the x direction
	 * @param nY the number of pixels in the y direction
	 * @param j  the x-coordinate of the pixel
	 * @param i  the y-coordinate of the pixel
	 * @return a ray from the camera through the specified pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pij = getPij(nX, nY, j, i);
		return new Ray(getP0(), pij.subtract(getP0()));
	}

	/**
	 * Returns the point pij in the view plane corresponding to the specified pixel
	 * (j, i).
	 * 
	 * @param nX the number of pixels in the x direction
	 * @param nY the number of pixels in the y direction
	 * @param j  the x-coordinate of the pixel
	 * @param i  the y-coordinate of the pixel
	 * @return the point pij in the view plane
	 */
	public Point getPij(int nX, int nY, int j, int i) {
		double rY = height / nY;
		double rX = width / nX;

		double yi = -(i - (nY - 1) / 2d) * rY;
		double xj = (j - (nX - 1) / 2d) * rX;

		Point pij = getP0().add(vTo.scale(distance));

		if (!isZero(xj))
			pij = pij.add(vRight.scale(xj));
		if (!isZero(yi))
			pij = pij.add(vUp.scale(yi));
		return pij;
	}

	/**
	 * Prints a grid on the image with the specified interval and color.
	 * 
	 * @param interval the interval between grid lines
	 * @param color    the color of the grid lines
	 * @return this Camera instance for chaining
	 */
	public Camera printGrid(int interval, Color color) {
		for (int i = 0; i < imageWriter.getNy(); i++) {
			for (int j = 0; j < imageWriter.getNx(); j++) {
				if (i % interval == 0 || j % interval == 0) {
					imageWriter.writePixel(j, i, color);
				}
			}
		}
		return this;
	}

	/**
	 * Writes the rendered image to the output file.
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	/**
	 * Renders the image by casting rays through each pixel in the view plane.
	 * 
	 * @return this Camera instance for chaining
	 */
	public Camera renderImage() {
		int resolutionX = imageWriter.getNx(); // Horizontal resolution
		int resolutionY = imageWriter.getNy(); // Vertical resolution

		for (int y = 0; y < resolutionY; y++) {
			for (int x = 0; x < resolutionX; x++) {
				castRays(resolutionX, resolutionY, x, y);
			}
		}
		return this;
	}

	/**
	 * Casts rays through the specified pixel (pixelX, pixelY) to determine its
	 * color.
	 * 
	 * @param nX     the number of pixels in the x direction
	 * @param nY     the number of pixels in the y direction
	 * @param pixelX the x-coordinate of the pixel
	 * @param pixelY the y-coordinate of the pixel
	 */
	private void castRays(int nX, int nY, int pixelX, int pixelY) {
		Color color = Color.BLACK;
		Ray ray = constructRay(nX, nY, pixelX, pixelY);

		if (numOfRays > 1 && !isZero(aperture) && !isZero(focusDistance)) {
			double cosA = vTo.dotProduct(ray.getDirection());
			double dij = focusDistance / cosA;
			Point focalPoint = p0.add(ray.getDirection().scale(dij));
			List<Point> apperturePoints = constructBeamPoints(aperture, numOfRays);
			for (var sourcePoint : apperturePoints) {
				Ray secondaryRay = new Ray(sourcePoint, focalPoint.subtract(sourcePoint));
				color = color.add(rayTracer.traceRay(secondaryRay));
			}
			color = color.reduce(apperturePoints.size());

		} else
			color = rayTracer.traceRay(ray);

		imageWriter.writePixel(pixelX, pixelY, color);

	}

	/**
	 * Constructs a list of random points within the aperture for depth of field
	 * effects.
	 * 
	 * @param radius   the radius of the aperture
	 * @param gridSize the number of points to generate
	 * @return a list of random points within the aperture
	 */
	private List<Point> constructBeamPoints(double radius, int gridSize) {
		List<Point> beamPoints = new LinkedList<>();
		double windowSize = radius * 2;
		double gridStep = windowSize / gridSize;
		Point p = p0;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				p = p0;
				p = p.add(vRight.scale(-radius + i * gridStep + random.nextDouble() * gridStep));
				p = p.add(vUp.scale(-radius + j * gridStep + random.nextDouble() * gridStep));
				if (p0.distance(p) <= radius)
					beamPoints.add(p);
			}
		}

		return beamPoints;
	}

	/**
	 * A builder class for constructing {@link Camera} instances.
	 * <p>
	 * The Builder pattern is used to create immutable Camera objects with complex
	 * configurations.
	 * </p>
	 */
	public static class Builder {

		/**
		 * The {@link Camera} instance used for rendering the scene.
		 * 
		 * <p>This field holds the configuration of the camera that will be used to generate rays for rendering the scene.
		 * It is initialized in the builder pattern and configured with various parameters such as location, direction,
		 * view plane size, distance, focus distance, aperture, and image writer.</p>
		 */
		private final Camera camera = new Camera();

		/**
		 * Sets the location of the camera.
		 * 
		 * @param location the location of the camera
		 * @return this Builder instance for chaining
		 */
		public Builder setLocation(Point location) {
			if (location == null)
				throw new IllegalArgumentException("location cannot be null");
			camera.p0 = location;
			return this;
		}

		/**
		 * Sets the direction vectors of the camera.
		 * 
		 * @param vTo the direction vector towards the view plane
		 * @param vUp the direction vector upwards
		 * @return this Builder instance for chaining
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (vTo == null || vUp == null)
				throw new IllegalArgumentException("Direction vectors cannot be null");
			if (!isZero(vTo.dotProduct(vUp)))
				throw new IllegalArgumentException("vTo and vUp must be orthogonal");
			camera.vTo = vTo.normalize();
			camera.vUp = vUp.normalize();
			camera.vRight = vTo.crossProduct(vUp).normalize();
			return this;
		}

		/**
		 * Sets the size of the view plane.
		 * 
		 * @param width  the width of the view plane
		 * @param height the height of the view plane
		 * @return this Builder instance for chaining
		 */
		public Builder setVpSize(double width, double height) {
			if (alignZero(width) <= 0 || alignZero(height) <= 0)
				throw new IllegalArgumentException("View plane dimensions must be positive");
			camera.width = width;
			camera.height = height;
			return this;
		}

		/**
		 * Sets the distance of the view plane from the camera.
		 * 
		 * @param distance the distance to the view plane
		 * @return this Builder instance for chaining
		 */
		public Builder setVpDistance(double distance) {
			if (distance <= 0)
				throw new IllegalArgumentException("View plane distance must be positive");
			camera.distance = distance;
			return this;
		}

		/**
		 * Sets the focus distance of the camera.
		 * 
		 * @param focusDistance the focus distance
		 * @return this Builder instance for chaining
		 */
		public Builder setFocusDistance(double focusDistance) {
			if (focusDistance < 0)
				throw new IllegalArgumentException("Focus distance cannot be negative");
			camera.focusDistance = focusDistance;
			return this;
		}

		/**
		 * Sets the aperture size of the camera.
		 * 
		 * @param aperture the aperture size
		 * @return this Builder instance for chaining
		 */
		public Builder setAperture(double aperture) {
			if (aperture < 0)
				throw new IllegalArgumentException("Focus aperture cannot be negative");
			this.camera.aperture = aperture;
			return this;
		}

		/**
		 * Sets the number of rays used for anti-aliasing and depth of field.
		 * 
		 * @param numOfRays the number of rays
		 * @return this Builder instance for chaining
		 */
		public Builder setNumOfRays(int numOfRays) {
			this.camera.numOfRays = numOfRays;
			return this;
		}

		/**
		 * Sets the image writer for the camera.
		 * 
		 * @param imageWriter the image writer
		 * @return this Builder instance for chaining
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {
			this.camera.imageWriter = imageWriter;
			return this;
		}

		/**
		 * Sets the ray tracer for the camera.
		 * 
		 * @param rayTracer the ray tracer
		 * @return this Builder instance for chaining
		 */
		public Builder setRayTracer(RayTracerBase rayTracer) {
			this.camera.rayTracer = rayTracer;
			return this;
		}

		/**
		 * Builds and returns the Camera instance.
		 * 
		 * @return a Camera instance
		 * @throws MissingResourceException if any required fields are missing
		 */
		public Camera build() {
			if (camera.getP0() == null || camera.vTo == null || camera.vUp == null || camera.vRight == null
					|| !isZero(camera.vTo.dotProduct(camera.vUp)) || camera.width <= 0 || camera.height <= 0
					|| camera.distance <= 0)
				throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "");

			if (camera.imageWriter == null || camera.rayTracer == null)
				throw new MissingResourceException("Missing ImageWriter or RayTracer", Camera.class.getName(), "");

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new AssertionError();
			}
		}
	}
}