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
	/** Camera's position in 3D space. */
	private Point p0;

	/** used for initializing the blackboard */
	Point pixelCenter;

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
	
	// super sampling and multi thread
	
	/**
	 *  List of points representing the aperture positions for depth of field effects. 
	 */
	static List<Point> apperturePoints;
	
	/** 
	 * the number of threads, or other code operations 
	 */
	private int threadsCount = 0; // -2 auto, -1 range/stream, 0 no threads, 1+ number of threads

	/** 
	 * progress percentage 
	 */
	private double printInterval = 0; // printing progress percentage interval

	/** 
	 * Manages the pixels and their rendering for the image. 
	*/
	private PixelManager pixelManager;

	/** 
	 * A static instance of the Blackboard used for managing shared resources. 
	 */
	private static Blackboard blackboard;

	/**
	 * max level of recursion for adaptive grid acceleration, 0 means no adaptive
	 * grid is used
	 */
	private static int adaptiveGridDepth = 0;

	/**
	 * Private constructor to prevent direct instantiation of the {@link Camera}
	 * class.
	 * 
	 * This constructor is used only within the {@link Builder} class to create an
	 * instance of the {@link Camera} class. It ensures that the camera is properly
	 * configured through the builder pattern before being used.
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
		this.pixelCenter = pij;
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
	 * a method that checks if 4 colors are identical
	 * 
	 * @param c1
	 * @param c2
	 * @param c3
	 * @param c4
	 * @return true or false
	 */
	private static boolean compareColors(Color c1, Color c2, Color c3, Color c4) {
		return c1.equals(c2) && c2.equals(c3) && c3.equals(c4);
	}

	/**
	 * Writes the rendered image to the output file.
	 */
	public void writeToImage() {
		imageWriter.writeToImage();
	}

	public Camera renderImage() {
		// IMAGE RENDERING
		// Pass a ray from the camera through each pixel in the view plane and set the color
		int nY = this.imageWriter.getNy();
		int nX = this.imageWriter.getNx();
		pixelManager = new PixelManager(nY, nX, printInterval);

		if (threadsCount == 0) {
			for (int i = 0; i < nY; i++) {
				for (int j = 0; j < nX; j++) {
					castRays(nX, nY, i, j);
				}
			}
		} else { 
			var threads = new LinkedList<Thread>(); 
			while (threadsCount-- > 0) 
				threads.add(new Thread(() -> {
					// current pixel(row,col)
					PixelManager.Pixel pixel; 
					// allocate pixel(row,col) in loop until there are no more pixels
					while ((pixel = pixelManager.nextPixel()) != null)
						// cast ray through pixel (and color it – inside castRays)
						castRays(nX, nY, pixel.col(), pixel.row());
				}));
			
			// start all the threads
			for (var thread : threads)
				thread.start();
			// wait until all the threads have finished
			try {
				for (var thread : threads)
					thread.join();
			} catch (InterruptedException ignore) {
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

		if (adaptiveGridDepth != 0 && !isZero(aperture) && !isZero(focusDistance)) // super sampiling
		{
			double cosA = vTo.dotProduct(ray.getDirection());
			double dij = focusDistance / cosA;
			Point focalPoint = p0.add(ray.getDirection().scale(dij));

			int fullGridSize = (int) Math.pow(2, adaptiveGridDepth) + 1;
			blackboard = new Blackboard(fullGridSize, fullGridSize, p0, vRight, vUp, aperture * 2 + 1,
					aperture * 2 + 1);

			Point leftUp = blackboard.gridMethod(0, 0);
			Color leftUpColor = this.rayTracer.traceRay(new Ray(leftUp, focalPoint.subtract(leftUp)));

			Point rightUp = blackboard.gridMethod(0, fullGridSize - 1);
			Color rightUpColor = this.rayTracer.traceRay(new Ray(rightUp, focalPoint.subtract(rightUp)));

			Point leftDown = blackboard.gridMethod(fullGridSize - 1, 0);
			Color leftDownColor = this.rayTracer.traceRay(new Ray(leftDown, focalPoint.subtract(leftDown)));

			Point rightDown = blackboard.gridMethod(fullGridSize - 1, fullGridSize - 1);
			Color rightDownColor = this.rayTracer.traceRay(new Ray(rightDown, focalPoint.subtract(rightDown)));

			color = compareColors(leftDownColor, rightDownColor, leftUpColor, rightUpColor) ? leftDownColor
					: calcColorRec(new Blackboard.SubPixel(fullGridSize / 2, fullGridSize / 2), focalPoint, leftUpColor,
							rightUpColor, leftDownColor, rightDownColor, 1, blackboard);

		} else if (numOfRays > 1 && !isZero(aperture) && !isZero(focusDistance)) {
			double cosA = vTo.dotProduct(ray.getDirection());
			double dij = focusDistance / cosA;
			Point focalPoint = p0.add(ray.getDirection().scale(dij));

			// apperturePoints = constructBeamPoints(aperture, numOfRays);
			blackboard = new Blackboard(numOfRays, numOfRays, p0, vRight, vUp, aperture * 2 + 1, aperture * 2 + 1);
			apperturePoints = blackboard.generateJitterdPoint().points;
			for (var sourcePoint : apperturePoints) {
				Ray secondaryRay = new Ray(sourcePoint, focalPoint.subtract(sourcePoint));
				color = color.add(rayTracer.traceRay(secondaryRay));
			}

			color = color.reduce(numOfRays * numOfRays);
		} else {
			color = rayTracer.traceRay(ray);
		}

		imageWriter.writePixel(pixelX, pixelY, color);
		pixelManager.pixelDone();
	}

	/**
	 * a recursive method that calculates the color of a pixel using adaptive
	 * super-sampling
	 * 
	 * @param pixelCenter    indices of the center pixel
	 * @param focalPoint     the head of the ray to the center of the pixel
	 * @param leftUpColor
	 * @param rightUpColor
	 * @param leftDownColor
	 * @param rightDownColor
	 * @param level          level of recursion. after going past the level the
	 *                       recursion quits
	 * @param blackboard     the blackboard for the pixel
	 * @return the color of the pixel
	 */
	private Color calcColorRec(Blackboard.SubPixel pixelCenter, Point focalPoint, Color leftUpColor, Color rightUpColor,
			Color leftDownColor, Color rightDownColor, int level, Blackboard blackboard) {

		if (level == adaptiveGridDepth)
			return leftUpColor.add(rightUpColor, leftDownColor, rightDownColor).reduce(4);

		int gridStep = (int) (Math.pow(2, adaptiveGridDepth - level));

		Point left = blackboard.gridMethod(pixelCenter.j - gridStep, pixelCenter.i);
		Color leftColor = this.rayTracer.traceRay(new Ray(left, focalPoint.subtract(left)));

		Point up = blackboard.gridMethod(pixelCenter.j, pixelCenter.i - gridStep);
		Color upColor = this.rayTracer.traceRay(new Ray(up, focalPoint.subtract(up)));

		Point down = blackboard.gridMethod(pixelCenter.j, pixelCenter.i + gridStep);
		Color downColor = this.rayTracer.traceRay(new Ray(down, focalPoint.subtract(down)));

		Point right = blackboard.gridMethod(pixelCenter.j + gridStep, pixelCenter.i);
		Color rightColor = this.rayTracer.traceRay(new Ray(right, focalPoint.subtract(right)));

		Point center = blackboard.gridMethod(pixelCenter.j, pixelCenter.i);
		Color centerColor = this.rayTracer.traceRay(new Ray(center, focalPoint.subtract(center)));

		int halfGridStep = gridStep / 2;

		Color leftUpSquareColor = compareColors(leftUpColor, upColor, leftColor, centerColor) ? leftUpColor
				: calcColorRec(new Blackboard.SubPixel(pixelCenter.j - halfGridStep, pixelCenter.i - halfGridStep),
						focalPoint, leftUpColor, upColor, leftColor, centerColor, level + 1, blackboard);

		Color rightUpSquareColor = compareColors(upColor, rightUpColor, centerColor, rightColor) ? upColor
				: calcColorRec(new Blackboard.SubPixel(pixelCenter.j + halfGridStep, pixelCenter.i - halfGridStep),
						focalPoint, upColor, rightUpColor, centerColor, rightColor, level + 1, blackboard);

		Color leftDownSquareColor = compareColors(leftColor, centerColor, leftDownColor, downColor) ? leftColor
				: calcColorRec(new Blackboard.SubPixel(pixelCenter.j - halfGridStep, pixelCenter.i + halfGridStep),
						focalPoint, leftColor, centerColor, leftDownColor, downColor, level + 1, blackboard);

		Color rightDownSquareColor = compareColors(centerColor, rightColor, downColor, rightDownColor) ? centerColor
				: calcColorRec(new Blackboard.SubPixel(pixelCenter.j + halfGridStep, pixelCenter.i + halfGridStep),
						focalPoint, centerColor, rightColor, downColor, rightDownColor, level + 1, blackboard);

		return leftUpSquareColor.add(rightUpSquareColor, leftDownSquareColor, rightDownSquareColor).reduce(4);
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
		 * <p>
		 * This field holds the configuration of the camera that will be used to
		 * generate rays for rendering the scene. It is initialized in the builder
		 * pattern and configured with various parameters such as location, direction,
		 * view plane size, distance, focus distance, aperture, and image writer.
		 * </p>
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
		 * set method for multithreading
		 * 
		 * @param i number of threads
		 * @return This Builder instance for method chaining.
		 */
		public Builder setMultithreading(int i) {
			camera.threadsCount = i;
			return this;
		}
		/**
		 * Sets the debug print interval for the camera.
		 * 
		 * @param d the interval at which to print debug information
		 * @return This Builder instance for method chaining.
		 */
		public Builder setDebugPrint(double d) {
		    camera.printInterval = d;
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
		 * set method for adaptiveGridDepth
		 * 
		 * @param adaptiveGrid the parameter
		 * @return This Builder instance for method chaining.
		 */
		public Builder setAdaptiveGrid(int adaptiveGrid) {
			Camera.adaptiveGridDepth = adaptiveGrid;
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

			if (camera.imageWriter == null || this.camera.rayTracer == null)
				throw new MissingResourceException("Missing ImageWriter or RayTracer", Camera.class.getName(), "");

			try {
				return (Camera) camera.clone();
			} catch (CloneNotSupportedException e) {
				throw new AssertionError();
			}
		}
	}
}