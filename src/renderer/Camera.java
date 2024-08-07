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
	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double height = 0;
	private double width = 0;
	private double distance = 0;

	// New fields for Depth of Field and Anti-aliasing
	private double focusDistance = 0;
	private double aperture = 0;
	private double sqrtGridSize = 1;
	private int numOfRays;

	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;

	private Camera() {
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public Ray constructRay(int nX, int nY, int j, int i) {
		Point pij = getPij(nX, nY, j, i);
		return new Ray(getP0(), pij.subtract(getP0()));
	}

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

	public static class DepthOfField {

		private Camera camera;
//	    private double apertureRadius;
//	    private double focusDistance;
//	    private double sqrtGridSize;

		/**
		 * Constructs a DepthOfField camera with specified parameters.
		 * 
		 * @param position       The position of the camera in 3D space.
		 * @param direction      The forward direction vector of the camera.
		 * @param up             The upward direction vector of the camera.
		 * @param right          The rightward direction vector of the camera.
		 * @param apertureRadius The radius of the aperture.
		 * @param focusDistance  The distance from the camera to the focus plane.
		 * @param width          The width of the view plane.
		 * @param height         The height of the view plane.
		 * @param distance       The distance from the camera to the view plane.
		 * @param sqrtGridSize   The square root of the grid size for anti-aliasing.
		 */
		public DepthOfField(Camera camera) {
			this.camera = camera;
//	        Camera.Builder builder = Camera.getBuilder();
//	        builder.setLocation(position)
//	               .setDirection(direction, up)
//	               .setVpSize(width, height)
//	               .setVpDistance(distance)

			// .setFocusDistance(focusDistance)
//	               .setSqrtGridSize(sqrtGridSize);
			//
//	        // Build the camera and initialize DepthOfField parameters
//	        this.camera = builder.build();
//	        this.apertureRadius = apertureRadius;
//	        this.focusDistance = focusDistance;
//	        this.sqrtGridSize = sqrtGridSize;
		}

		private List<Point> constructBeamPoints(Point pointOnSensor) {
			List<Point> beamPoints = new ArrayList<>();
			Random random = new Random();
			//double apertureRadius = camera.getAperture();
		    double apertureRadius = camera.getAperture();
			double sqrtGridSize = Math.sqrt(apertureRadius);

			for (int i = 0; i < sqrtGridSize * sqrtGridSize; i++) {
				double angle = 2 * Math.PI * random.nextDouble();
				double radius = apertureRadius * Math.sqrt(random.nextDouble());
				double x = radius * Math.cos(angle);
				double y = radius * Math.sin(angle);
				beamPoints.add(pointOnSensor.add(new Vector(x, y, 0)));
			}

			return beamPoints;
		}
		
		

//	    private Point calculateFocalPoint(Ray ray) {
//	        return ray.getPoint(camera.getFocusDistance());
//	    }

		public List<Ray> generateRays(Point pIJ, int numRays) {
			List<Ray> rays = new ArrayList<>();
			List<Point> beamPoints = constructBeamPoints(pIJ);

			Vector pIJdirection = camera.getP0().subtract(pIJ).normalize();

			Point focalPoint = camera.getP0().add(pIJdirection.scale(camera.getFocusDistance()));

			for (Point aperturePoint : beamPoints) {
				Vector direction = focalPoint.subtract(aperturePoint).normalize();
				Ray ray = new Ray(aperturePoint, direction);
				rays.add(ray);
			}

			return rays;
		}

	}
	
	

	public static class Builder {
		private final Camera camera = new Camera();

		public Builder setLocation(Point location) {
			if (location == null)
				throw new IllegalArgumentException("location cannot be null");
			camera.p0 = location;
			return this;
		}

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

		public Builder setVpSize(double width, double height) {
			if (alignZero(width) <= 0 || alignZero(height) <= 0)
				throw new IllegalArgumentException("View plane dimensions must be positive");
			camera.width = width;
			camera.height = height;
			return this;
		}

		public Builder setVpDistance(double distance) {
			if (distance <= 0)
				throw new IllegalArgumentException("View plane distance must be positive");
			camera.distance = distance;
			return this;
		}

		// New Builder methods for Depth of Field
		public Builder setFocusDistance(double focusDistance) {
			if (focusDistance < 0)
				throw new IllegalArgumentException("Focus distance cannot be negative");
			camera.focusDistance = focusDistance;
			return this;
		}

		public Builder setAperture(double aperture) {
			if (aperture < 0)
				throw new IllegalArgumentException("Focus aperture cannot be negative");
			this.camera.aperture = aperture;
			return this;
		}

		public Builder setNumOfRays(int numOfRays) {
			this.camera.numOfRays = numOfRays;
			return this;
		}

//		public Builder setSqrtGridSize(double sqrtGridSize) {
//			if (sqrtGridSize < 1)
//				throw new IllegalArgumentException("sqrtGridSize must be greater than 0");
//			camera.sqrtGridSize = sqrtGridSize;
//			return this;
//		}

		public Builder setImageWriter(ImageWriter imageWriter) {
			this.camera.imageWriter = imageWriter;
			return this;
		}

		public Builder setRayTracer(RayTracerBase rayTracer) {
			this.camera.rayTracer = rayTracer;
			return this;
		}

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

	public double getAperture() {
		return aperture;
	}

	public double getFocusDistance() {
		return focusDistance;
	}

	public Point getP0() {
		return p0;
	}

	public int getNumOfRays() {
		return numOfRays;
	}

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

	public void writeToImage() {
		imageWriter.writeToImage();
	}

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

	private void castRay(int nX, int nY, int pixelX, int pixelY) {
		Color color = Color.BLACK;

		if (getAperture() > 0 && getFocusDistance() > 0) {
			DepthOfField dof = new DepthOfField(this);
			Point pIJ = getPij(nX, nY, pixelX, pixelY);
			List<Ray> rays = dof.generateRays(pIJ, getNumOfRays());
			color = rayTracer.traceRays(rays);
		} else {
			Ray ray = constructRay(nX, nY, pixelX, pixelY);
			color = rayTracer.traceRay(ray);
		}
		imageWriter.writePixel(pixelX, pixelY, color);
	}

}
