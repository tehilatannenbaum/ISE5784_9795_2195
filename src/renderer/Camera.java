package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;
import java.util.MissingResourceException;

/**
 * Represents a camera in 3D space. The camera is used to generate rays
 * through a view plane for rendering scenes.
 */
public class Camera implements Cloneable {
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height = 0;
    private double width = 0;
    private double distance = 0;

    // Private default constructor
    private Camera() {}

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
    

    public Point getPoint() {
        return p0;
    }

    public Vector getVTo() {
        return vTo;
    }

    public Vector getVUp() {
        return vUp;
    }

    public Vector getVRight() {
        return vRight;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * Builder class for constructing Camera instances.
     */
    public static class Builder {
        private final Camera camera= new Camera();;

        /**
         * Sets the position of the camera.
         * 
         * @param position the position to set
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
         * Builds the Camera instance.
         * 
         * @return the constructed Camera instance
         * @throws MissingResourceException if any required parameter is missing
         */
        public Camera build() {
            if (camera.p0 == null || camera.vTo == null || camera.vUp == null || camera.vRight == null ||
                camera.width == 0 || camera.height == 0 || camera.distance == 0) {
                throw new MissingResourceException("Missing rendering data", Camera.class.getName(), "");
            }
            
            camera.vRight = camera.vTo.crossProduct(camera.vTo).normalize();
            
            return (Camera) camera.clone();
        }
    }
}
