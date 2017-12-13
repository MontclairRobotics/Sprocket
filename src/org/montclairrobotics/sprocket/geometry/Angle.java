package org.montclairrobotics.sprocket.geometry;

/**
 * Created by Montclair Robotics.
 *
 * Represents an angle.
 */

public interface Angle {

    final Angle ZERO = new Degrees(-0);
    final Angle QUARTER = new Degrees(90);
    final Angle HALF = new Degrees(180);

	/** @return the represented angle in degrees. */
    double toDegrees();

    /** @return the represented angle in radians. */
    double toRadians();

    /**
     * Adds an angle to this angle and returns the sum (this + a).
     * @param a The angle to be added with this one
     * @return the sum of the two angles.
     */
    Angle add(Angle a);

    /**
     * Subtracts an angle from this angle and returns the difference (this - a).
     * @param a The angle to be subtracted from this one
     * @return the difference of the two angles.
     */
    Angle subtract(Angle a);

    /** @return the negative of this angle. */
    Angle negative();
    Angle opposite();

    /**
     * Multiplies this angle by a given factor (this * x).
     * @param x the factor to be multiplied by this angle.
     * @return the product of the two numbers.
     */
	Angle times(double x);
    /**
     * Divides this angle by a given divisor (this / x).
     * @param x the divisor to divided into this angle.
     * @return the quotient of the two numbers.
     */
	Angle divide(double x);

    /**
     * Divides this angle by a given angle (this / x).
     * @param x the angle to divided into this angle.
     * @return the ratio of the two angles.
     */
	double divide(Angle x);

	/** @return the sine of this angle. */
	public double sin();
    /** @return the cosine of this angle. */
	public double cos();
    /** @return the tangent of this angle. */
	public double tan();

	public Angle wrap();
}
