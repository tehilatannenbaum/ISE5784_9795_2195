package primitives;

/**
 * Represents a ray in 3D space, defined by a starting point (head) and a direction vector.
 */
public class Ray {
    /** The starting point (head) of the ray */
    private final Point head;
    /** The direction vector of the ray */
    private final Vector direction;
    
    /**
     * Constructs a new Ray with the given starting point and direction vector.
     * @param p The starting point (head) of the ray
     * @param vec The direction vector of the ray
     */
    public Ray(Point p, Vector vec) {
        // Normalize the direction vector to ensure it represents a unit vector
        head = p;
        direction = vec.normalize();        
    }
    
    /**
     * Checks whether this Ray is equal to another object.
     * Two rays are considered equal if they have the same head and direction.
     * @param obj The object to compare with this Ray
     * @return true if the given object is equal to this Ray, false otherwise
     */
     @Override
     public boolean equals(Object obj) {
         if (this == obj) return true;
         return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
     }
     
     /**
      * Returns a string representation of this Ray.
      * The string contains the head point and the direction vector of the ray.
      * @return A string representation of this Ray
      */
     @Override
     public String toString() {
         return "Ray: " + head.toString() + direction.toString();
     }
}
