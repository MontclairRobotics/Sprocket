package org.montclairrobotics.sprocket.geometry;

public interface Angle {

    Angle ZERO=new Degrees(-0),QUARTER = new Degrees(90),HALF = new Degrees(180);
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
    
    /**
     * Returns the opposite of this angle
     * @returnthe opposite of this angle
     */
    Angle opposite();
    
    /**
     * Multiplies the angle by a value and returns the product
     * @param x The value to multiply by
     * @return the product of the two values
     */
	Angle times(double x);
    
    /**
     * Divides the angle by a value and returns the quotient
     * @param x The value to divide by
     * @return the quotient of the two values
     */
	Angle divide(double x);
    
    /**
     * Divides this angle by another one and returns the quotient
     * @param x the angle to divide by
     * @return the quotient of the two angles
     */
	double divide(Angle x);
    
    /**
     * @return the sine of the angle
     */
	public double sin();
    
    /**
     * @return the cosine of the angle
     */
	public double cos();
    
    /**
     * @return the tangent of the angle
     */
	public double tan();
    
    /**
     * TODO: document
     * @return
     */
	public Angle wrap();



}
