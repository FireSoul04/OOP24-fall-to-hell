package it.unibo.falltohell.model.api;

public record Position(double x, double y) {

    /**
     * @return a position with all zeros as coordinates
     */
    public static Position zero() {
        return new Position(0.0, 0.0);
    }
    
    /**
     * @return a position with all zeros as coordinates
     */
    public static Position one() {
        return new Position(1.0, 1.0);
    }

    /**
     * @param a
     * @return the sum of this position and a scalar a
     */
    public Position add(final double a) {
        return new Position(this.x + a, this.y + a);
    }

    /**
     * @param a
     * @return the difference of this position and a scalar a
     */
    public Position subtract(final double a) {
        return this.add(-a);
    }

    /**
     * @param a
     * @return the product of this position and a scalar a
     */
    public Position multiply(final double a) {
        return new Position(this.x*a, this.y*a);
    }

    /**
     * @param a
     * @return the division of this position and a scalar a
     */
    public Position divide(final double a) {
        return this.multiply(1/a);
    }

    /**
     * @return the position with the sign of both coordinates inverted
     */
    public Position invert() {
        return new Position(-this.x, -this.y);
    }

    /**
     * @param v
     * @return the sum of this position and the position v
     */
    public Position add(final Position v) {
        return new Position(this.x + v.x, this.y + v.y);
    }

    /**
     * @param v
     * @return the difference of this position and the position v
     */
    public Position subtract(final Position v) {
        return this.add(v.invert());
    }
}


    
