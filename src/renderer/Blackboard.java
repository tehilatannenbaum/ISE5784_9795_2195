/**
 * The Blackboard class represents a virtual blackboard used for rendering purposes.
 * It facilitates generating jittered points on a grid and adjusting them for circular shape.
 */
package renderer;

import static primitives.Util.isZero;
import static primitives.Util.random;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

/**
 * The Blackboard class represents a virtual blackboard used for rendering
 * purposes. It facilitates generating jittered points on a grid and adjusting
 * them for circular shape.
 */
public class Blackboard {

	/** Number of divisions along the X-axis */
	private int nX;

	/** Number of divisions along the Y-axis */
	private int nY;

	/** Center point of the blackboard */
	private Point centerOfBlackB;

	/** X-axis vector */
	private Vector axisX;

	/** Y-axis vector */
	private Vector axisY;

	/** Normal vector of the blackboard */
	private Vector normal;

	/** List of points on the blackboard */
	public List<Point> points = new LinkedList<>();

	/** Width of the blackboard */
	private double width;

	/** Height of the blackboard */
	private double height;

	/**
	 * Returns the width of the view plane.
	 *
	 * @return The number of pixels along the x-axis (width) of the view plane.
	 */
	public int getNx() {
	    return nX;
	}

	/**
	 * Returns the height of the view plane.
	 *
	 * @return The number of pixels along the y-axis (height) of the view plane.
	 */
	public int getNy() {
	    return nY;
	}

	/**
	 * Constructs a Blackboard object with specified parameters.
	 *
	 * @param numX        Number of divisions along the X-axis
	 * @param numY        Number of divisions along the Y-axis
	 * @param centerPoint Center point of the blackboard
	 * @param axisx       X-axis vector
	 * @param axisy       Y-axis vector
	 * @param width       Width of the blackboard
	 * @param height      Height of the blackboard
	 *
	 */
	public Blackboard(int numX, int numY, Point centerPoint, Vector axisx, Vector axisy, double width, double height) {
		if (centerPoint != null)
			this.centerOfBlackB = centerPoint;
		this.nX = numX;
		this.nY = numY;
		this.axisX = axisx;
		this.axisY = axisy;
		this.width = width;
		this.height = height;
		if (axisx == null || axisy == null)
			this.normal = null;
		else
			this.normal = axisx.crossProduct(axisy).normalize();
	}

	/**
	 * Sets the center point of the blackboard.
	 *
	 * @param pt Center point to set
	 * @return The updated Blackboard object
	 */
	public Blackboard setCenterPoint(Point pt) {
		this.centerOfBlackB = pt;
		return this;
	}

	/**
	 * Generates jittered points on the blackboard.
	 *
	 * @return The Blackboard object with generated jittered points
	 */
	public Blackboard generateJitterdPoint() {
		points = new LinkedList<>();
		for (int i = 0; i < nY; i++) {
			for (int j = 0; j < nX; j++) {
				points.add(jitterdPoint(i, j));
			}
		}
		return this;
	}

	/**
	 * Retrieves the normal vector of the blackboard.
	 *
	 * @return The normal vector of the blackboard
	 */
	public Vector getNormal() {
		return this.normal;
	}

	/**
	 * Retrieves the center point of the blackboard.
	 *
	 * @return The center point of the blackboard
	 */
	public Point getCenterPoint() {
		return centerOfBlackB;
	}

	/**
	 * Calculates a point on the blackboard grid at specified indices.
	 *
	 * @param j Index along the X-axis
	 * @param i Index along the Y-axis
	 * @return The calculated point on the blackboard grid
	 */
	public Point gridMethod( int j, int i) {
		double stepX = (width - 1) / nX;
		double stepY = (height - 1) / nY;

		double xj = ((j * stepX) - ((width - 1) / 2)) + (stepX / 2);
		double yi = ((-i * stepY) + ((height - 1) / 2)) - (stepY / 2);

		Point pIJ = centerOfBlackB;
		if (!isZero(xj))
			pIJ = pIJ.add(axisX.scale(xj));
		if (!isZero(yi))
			pIJ = pIJ.add(axisY.scale(yi));

		return pIJ;
	}

	/**
	 * Generates a jittered point on the blackboard grid at specified indices.
	 *
	 * @param j Index along the X-axis
	 * @param i Index along the Y-axis
	 * @return The generated jittered point
	 */
	private Point jitterdPoint(int j, int i) {
		Point centerOfMiniSqure = gridMethod(j, i);

		double randX = random(-((width - 1) / nX) / 2, ((width - 1) / nX) / 2);
		double randY = random(-((height - 1) / nY) / 2, ((height - 1) / nY) / 2);

		if (!isZero(randX))
			centerOfMiniSqure = centerOfMiniSqure.add(this.axisX.scale(randX));
		if (!isZero(randY))
			centerOfMiniSqure = centerOfMiniSqure.add(this.axisY.scale(randY));

		return centerOfMiniSqure;
	}

	/**
	 * a sub class for representing a single sub-pixel inside the pixel. a PDS
	 */
	public static class SubPixel {
	    /** The column index of the sub-pixel within the pixel. */
	    public int j;
	    
	    /** The row index of the sub-pixel within the pixel. */
	    public int i;

	    /**
	     * Constructs a SubPixel with the specified column and row indices.
	     *
	     * @param j The column index of the sub-pixel.
	     * @param i The row index of the sub-pixel.
	     */
	    public SubPixel(int j, int i) {
	        this.j = j;
	        this.i = i;
	    }
	}
}
