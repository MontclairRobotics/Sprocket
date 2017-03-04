package org.montclairrobotics.sprocket.geometry;

public interface Angle {

    Angle ZERO=new Degrees(0),QUARTER = new Degrees(90),HALF = new Degrees(180);
	/**
     * Returns the represented angle in degrees
     * @return the angle in degrees
     */
    double toDegrees();

    /**
     * Returns the represented angle in radians
     * @return the angle in radians
     */
    double toRadians();

    /**
     * Adds an angle and itself and returns the sum
     * @param a The angle to be added with this one
     * @return the sum of the two angles
     */
    Angle add(Angle a);

    /**
     * Subtracts an angle from this one and returns the difference (as this - a = result)
     * @param a The angle to be subtracted from this one
     * @return the difference of the two angles
     */
    Angle subtract(Angle a);

    /**
     * Returns the negative of this angle
     * @return the negative of this angle
     */
    Angle negative();
    Angle opposite();

	Angle times(double x);
	Angle divide(double x);
	
	double divide(Angle x);

	public double sin();
	public double cos();
	public double tan();

	Angle wrap();



}
