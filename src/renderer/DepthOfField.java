//package renderer;
//
//import primitives.Point;
//import primitives.Vector;
//import primitives.Ray;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
///**
// * Represents a Depth of Field effect camera that extends a basic Camera. This
// * class simulates the depth of field effect by generating rays from a camera
// * with an aperture and focus distance.
// */
//public class DepthOfField {
//
//	private Camera camera;
//	private double apertureRadius;
//	private double focusDistance;
//	private double sqrtGridSize;
//
//	/**
//	 * Constructs a DepthOfField camera with specified parameters.
//	 * 
//	 * @param position       The position of the camera in 3D space.
//	 * @param direction      The forward direction vector of the camera.
//	 * @param up             The upward direction vector of the camera.
//	 * @param right          The rightward direction vector of the camera.
//	 * @param apertureRadius The radius of the aperture.
//	 * @param focusDistance  The distance from the camera to the focus plane.
//	 * @param width          The width of the view plane.
//	 * @param height         The height of the view plane.
//	 * @param distance       The distance from the camera to the view plane.
//	 * @param sqrtGridSize   The square root of the grid size for anti-aliasing.
//	 */
//	public DepthOfField(Point position, Vector direction, Vector up, Vector right, double apertureRadius,
//			double focusDistance, double width, double height, double distance, double sqrtGridSize) {
//		Camera.Builder builder = Camera.getBuilder();
//		builder.setLocation(position).setDirection(direction, up).setVpSize(width, height).setVpDistance(distance)
//				.setApertureRadius(apertureRadius).setFocusDistance(focusDistance).setSqrtGridSize(sqrtGridSize);
//
//		// Build the camera and initialize DepthOfField parameters
//		this.camera = builder.build();
//		this.apertureRadius = apertureRadius;
//		this.focusDistance = focusDistance;
//		this.sqrtGridSize = sqrtGridSize;
//	}
//
//	private List<Point> constructBeamPoints(Point pointOnSensor) {
//		List<Point> beamPoints = new ArrayList<>();
//		Random random = new Random();
//
//		for (int i = 0; i < sqrtGridSize * sqrtGridSize; i++) {
//			double angle = 2 * Math.PI * random.nextDouble();
//			double radius = apertureRadius * Math.sqrt(random.nextDouble());
//			double x = radius * Math.cos(angle);
//			double y = radius * Math.sin(angle);
//			beamPoints.add(pointOnSensor.add(new Vector(x, y, 0)));
//		}
//
//		return beamPoints;
//	}
//
//	private Point calculateFocalPoint(Ray ray) {
//		return ray.getPoint(focusDistance);
//	}
//
//	public List<Ray> generateRays(Point pointOnSensor, int numRays) {
//		List<Ray> rays = new ArrayList<>();
//		List<Point> beamPoints = constructBeamPoints(pointOnSensor);
//
//		for (Point aperturePoint : beamPoints) {
//			Vector direction = pointOnSensor.subtract(aperturePoint).normalize();
//			Ray ray = new Ray(aperturePoint, direction);
//			rays.add(ray);
//		}
//
//		return rays;
//	}
//
//	public Point findFocusPoint(Ray ray) {
//		return calculateFocalPoint(ray);
//	}
//
//	public List<Ray> generateDepthOfFieldRays(List<Point> sensorPoints, int numRaysPerPoint) {
//		List<Ray> allRays = new ArrayList<>();
//
//		for (Point pointOnSensor : sensorPoints) {
//			List<Ray> rays = generateRays(pointOnSensor, numRaysPerPoint);
//			allRays.addAll(rays);
//		}
//
//		return allRays;
//	}
//}
