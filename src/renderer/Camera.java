package renderer;

import primitives.Point;

import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import primitives.Color;
import java.util.MissingResourceException;
import scene.*;

/**
 * Represents a camera in 3D space. The camera is used to generate rays through
 * a view plane for rendering scenes.
 */
public class Camera implements Cloneable {
	/**
	 * The position of the camera.
	 */
	private Point p0;

	/**
	 * The direction vector towards the view plane.
	 */
	private Vector vTo;

	/**
	 * The up direction vector for the camera.
	 */
	private Vector vUp;

	/**
	 * The right direction vector for the camera.
	 */
	private Vector vRight;

	/**
	 * The height of the view plane.
	 */
	private double height = 0;

	/**
	 * The width of the view plane.
	 */
	private double width = 0;

	/**
	 * The distance from the camera to the view plane.
	 */
	private double distance = 0;

	/**
	 * The ImageWriter for the camera.
	 */
	private ImageWriter imageWriter;

	/**
	 * The RayTracer for the camera.
	 */
	private RayTracerBase rayTracer;

	/**
	 * Private default constructor to prevent instantiation. This constructor is
	 * used internally by the Builder class.
	 */
	private Camera() {
	}

	/**
	 * Static method to get a new Builder instance for the Camera.
	 *
	 * @return a new Camera.Builder instance
	 */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
	 * Constructs a ray through a given pixel in the view plane.
	 *
	 * @param nX number of pixels in the x direction
	 * @param nY number of pixels in the y direction
	 * @param j  the column index of the pixel
	 * @param i  the row index of the pixel
	 * @return the constructed ray through the pixel
	 */
	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pCenter = p0.add(vTo.scale(distance));
		double rY = height / nY;
		double rX = width / nX;

		double yi = -(i - (nY - 1) / 2d) * rY;
		double xj = (j - (nX - 1) / 2d) * rX;

		Point pij = pCenter;

		if (!Util.isZero(xj)) {
			pij = pij.add(vRight.scale(xj));
		}

		if (!Util.isZero(yi)) {
			pij = pij.add(vUp.scale(yi));
		}

		return new Ray(p0, pij.subtract(p0));
	}

	/**
	 * Creates and returns a copy of this Camera.
	 *
	 * @return a clone of this Camera
	 * @throws AssertionError if cloning is not supported
	 */
	@Override
	public Camera clone() {
		try {
			return (Camera) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	/**
	 * Returns the point p0 of this object.
	 *
	 * @return the point p0.
	 */
	public Point getPoint() {
		return p0;
	}

	/**
	 * Returns the direction vector vTo of this object.
	 *
	 * @return the direction vector vTo.
	 */
	public Vector getVTo() {
		return vTo;
	}

	/**
	 * Returns the upward direction vector vUp of this object.
	 *
	 * @return the upward direction vector vUp.
	 */
	public Vector getVUp() {
		return vUp;
	}

	/**
	 * Returns the right direction vector vRight of this object.
	 *
	 * @return the right direction vector vRight.
	 */
	public Vector getVRight() {
		return vRight;
	}

	/**
	 * Returns the height of this object.
	 *
	 * @return the height.
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * Returns the width of this object.
	 *
	 * @return the width.
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * Returns the distance of this object.
	 *
	 * @return the distance.
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * Builder class for constructing Camera instances.
	 */
	public static class Builder {
		/**
		 * The camera used for rendering.
		 */
		private final Camera camera = new Camera();

		/**
		 * Sets the position of the camera.
		 *
		 * @param location the position to set
		 * @return the Builder instance
		 */
		public Builder setLocation(Point location) {
			if (location == null) {
				throw new IllegalArgumentException("location cannot be null");
			}
			camera.p0 = location;
			return this;
		}

		/**
		 * Sets the direction vectors of the camera.
		 *
		 * @param vTo the forward direction vector
		 * @param vUp the up direction vector
		 * @return the Builder instance
		 */
		public Builder setDirection(Vector vTo, Vector vUp) {
			if (vTo == null || vUp == null) {
				throw new IllegalArgumentException("Direction vectors cannot be null");
			}
			if (!Util.isZero(vTo.dotProduct(vUp))) {
				throw new IllegalArgumentException("vTo and vUp must be orthogonal");
			}
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
		 * @return the Builder instance
		 */
		public Builder setVpSize(double width, double height) {
			if (width <= 0 || height <= 0) {
				throw new IllegalArgumentException("View plane dimensions must be positive");
			}
			camera.width = width;
			camera.height = height;
			return this;
		}

		/**
		 * Sets the distance between the camera and the view plane.
		 *
		 * @param distance the distance to set
		 * @return the Builder instance
		 */
		public Builder setVpDistance(double distance) {
			if (distance <= 0) {
				throw new IllegalArgumentException("View plane distance must be positive");
			}
			camera.distance = distance;
			return this;
		}

		/**
		 * Sets the ImageWriter for the camera.
		 *
		 * @param imageWriter the ImageWriter to set
		 * @return the Builder instance
		 */
		public Builder setImageWriter(ImageWriter imageWriter) {
			// Set the ImageWriter
			this.camera.imageWriter = imageWriter;
			return this;
		}

		/**
		 * Sets the RayTracer for the camera.
		 *
		 * @param rayTracer the RayTracer to set
		 * @return the Builder instance
		 */
		public Builder setRayTracer(RayTracerBase rayTracer) {
			// Set the RayTracer
			this.camera.rayTracer = rayTracer;
			return this;
		}

		/**
		 * Builds the Camera instance.
		 *
		 * @return the constructed Camera instance
		 * @throws MissingResourceException if any required parameter is missing
		 */
		public Camera build() {
			if (camera.p0 == null || camera.vTo == null || camera.vUp == null || camera.vRight == null
					|| camera.width == 0 || camera.height == 0 || camera.distance == 0) {
				throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "");
			}
			// if (camera.imageWriter == null || camera.rayTracer == null) {
			// throw new MissingResourceException("Missing ImageWriter or RayTracer",
			// Camera.class.getName(), "");
			// }
			camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

			return (Camera) camera.clone();
		}
	}

	/**
	 * Draws a grid pattern on the image.
	 *
	 * @param interval The interval between grid lines
	 * @param color    The color of the grid lines
	 * @return The Camera object with the grid drawn on the image
	 */
	public Camera printGrid(int interval, Color color) {
		// Iterate over the image pixels and draw the grid lines
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
	 * Writes the rendered image to a file using the appropriate method of the image
	 * writer.
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	/**
	 * Renders the image by casting rays through each pixel in the view plane.
	 *
	 * @return The Camera object with the rendered image
	 */
	public Camera renderImage() {
		int resolutionX = imageWriter.getNx(); // Horizontal resolution
		int resolutionY = imageWriter.getNy(); // Vertical resolution

		for (int y = 0; y < resolutionY; y++) {
			for (int x = 0; x < resolutionX; x++) {
				castRay(resolutionX, resolutionY, x, y);
			}
		}
		return this;
	}

	/**
	 * Casts a ray through the center of a pixel and colors the pixel accordingly.
	 * 
	 * @param resolutionX The horizontal resolution (number of pixels in width).
	 * @param resolutionY The vertical resolution (number of pixels in height).
	 * @param pixelX      The x coordinate of the pixel.
	 * @param pixelY      The y coordinate of the pixel.
	 */
	private void castRay(int resolutionX, int resolutionY, int pixelX, int pixelY) {
		// Construct a ray through the center of the pixel
		Ray ray = constructRay(resolutionX, resolutionY, pixelX, pixelY);

		// Trace the ray to find the color
		Color color = rayTracer.traceRay(ray);

		// Write the color to the pixel
		imageWriter.writePixel(pixelX, pixelY, color);
	}

}
