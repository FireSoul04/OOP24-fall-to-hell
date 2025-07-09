package it.unibo.falltohell.util;

/**
 * Class for representing a vector2 as a point in the 2d plane.
 *
 * @author Davide Mancini
 * @author Martina Malagoli
 * @author Sara Visani
 * @author Lorenzo Casadei

 *
 * @param x
 * @param y
 */
public record Vector2(double x, double y) {

    /**
     * @return a vector with all zeros as coordinates
     */
    public static Vector2 zero() {
        return new Vector2(0.0, 0.0);
    }

    /**
     * @return a vector with all zeros as coordinates
     */
    public static Vector2 one() {
        return new Vector2(1.0, 1.0);
    }

    /**
     * @return a unit vector that indicates left
     */
    public static Vector2 left() {
        return new Vector2(-1.0, 0.0);
    }

    /**
     * @return a unit vector that indicates right
     */
    public static Vector2 right() {
        return new Vector2(1.0, 0.0);
    }

    /**
     * @return a unit vector that indicates up
     */
    public static Vector2 up() {
        return new Vector2(0.0, -1.0);
    }

    /**
     * @return a unit vector that indicates down
     */
    public static Vector2 down() {
        return new Vector2(0.0, 1.0);
    }

    /**
     * @param a
     * @return the sum of this vector and a scalar a
     */
    public Vector2 add(final double a) {
        return new Vector2(this.x + a, this.y + a);
    }

    /**
     * @param a
     * @return the difference of this vector and a scalar a
     */
    public Vector2 subtract(final double a) {
        return this.add(-a);
    }

    /**
     * @param a
     * @return the product of this vector and a scalar a
     */
    public Vector2 multiply(final double a) {
        return new Vector2(this.x * a, this.y * a);
    }

    /**
     * @param a
     * @return the division of this vector and a scalar a
     */
    public Vector2 divide(final double a) {
        return this.multiply(1 / a);
    }

    /**
     * @return the vector with the sign of both coordinates inverted
     */
    public Vector2 invert() {
        return new Vector2(-this.x, -this.y);
    }

    /**
     * @param v
     * @return the sum of this vector and the vector v
     */
    public Vector2 add(final Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    /**
     * @param v
     * @return the difference of this vector and the vector v
     */
    public Vector2 subtract(final Vector2 v) {
        return this.add(v.invert());
    }

    /**
     * @param v
     * @return the distance between two points
     */
    public double distance(final Vector2 v) {
        return Math.sqrt(Math.pow(this.x - v.x, 2) + Math.pow(this.y - v.y, 2));
    }
}
