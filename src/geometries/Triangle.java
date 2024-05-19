package geometries;

import primitives.Point;

/**
 * Represents a triangle in three-dimensional space.
 * A triangle is a type of polygon with three vertices.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a triangle with three vertices.
     * The vertices are specified in counter-clockwise order.
     * @param p1 The first vertex of the triangle
     * @param p2 The second vertex of the triangle
     * @param p3 The third vertex of the triangle
     */
    public Triangle(Point p1, Point p2, Point p3) {
        // Call the constructor of the superclass Polygon
        // and pass the vertices to create the triangle
        super(p1, p2, p3);
    }
}
