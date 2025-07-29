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
        // This checks avoid having any -0.0 component
        return new Vector2(this.x != 0.0 ? -this.x : 0.0, this.y != 0.0 ? -this.y : 0.0);
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

    /**
     * <p>
     * Normalization scales the vector so that its length (magnitude) is 1,
     * unless the vector is a zero vector (length 0), in which case it returns a
     * copy of the original vector.
     * </p>
     *
     * @return Returns a Vector2 instance representing the normalized (unit length)
     *         vector
     *         in the same direction as this vector.
     */
    public Vector2 normalize() {
        final double norm = Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        if (norm == 0) {
            return new Vector2(this.x, this.y);
        }
        return new Vector2(this.x, this.y).divide(norm);
    }

    /**
     * Computes the magnitude (length) of this vector.
     * <p>
     * The magnitude is calculated using the Euclidean norm:
     * {@code sqrt(x^2 + y^2)}. This represents the distance from the origin
     * (0, 0) to the point defined by this vector.
     *
     * @return the scalar length of the vector.
     */
    public double magnitude() {
        return Math.sqrt(this.x() * this.x() + this.y() * this.y());
    }

    /**
     * Returns a new {@code Vector2} that is the component-wise multiplication
     * of this vector and the given vector.
     * <p>
     * Each component of the resulting vector is the product of the corresponding
     * components of this vector and the other vector:
     *
     * <pre>
     * result.x = this.x * other.x
     * result.y = this.y * other.y
     * </pre>
     *
     * @param other the other vector to multiply with
     * @return a new {@code Vector2} representing the component-wise product
     */
    public Vector2 multiply(final Vector2 other) {
        return new Vector2(this.x * other.x(), this.y * other.y());
    }
}
