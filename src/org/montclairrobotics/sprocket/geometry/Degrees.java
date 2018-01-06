package org.montclairrobotics.sprocket.geometry;


/**
 * Implements the an angle keeping track of the measument in degress
 */
public class Degrees implements Angle {
	
	/**
	 * The degree measure of the angle
	 */
    private double degrees;
	
	/**
	 * @param angle the measure of the angle
	 */
	public Degrees(double angle) {
        degrees = angle;
    }
	
	/**
	 * @return the measure of the angle in degrees
	 */
	@Override
    public double toDegrees() {
        return degrees;
    }
	
	/**
	 * @return the measure of the angle in radians
	 */
	@Override
    public double toRadians() {
        return degrees*(Math.PI/180);
    }
	
	/**
	 * @param a The angle to be added with this one
	 * @return the sum of the two angles
	 */
	@Override
    public Angle add(Angle a) {
        return new Degrees(toDegrees() + a.toDegrees());
    }
	
	/**
	 * @param a The angle to be subtracted from this one
	 * @return the difference of the two angles
	 */
	@Override
    public Angle subtract(Angle a) {
        return new Degrees(toDegrees() - a.toDegrees());
    }
	
	/**
	 * @return the negative of the angle
	 */
	@Override
    public Angle negative() {
        return new Degrees(-toDegrees());
    }
	
	/**
	 * @return the opposite of the angle
	 */
	public Angle opposite()
    {
    	return new Degrees(180+toDegrees());
    }
	
	/**
	 * @param x The value to multiply by
	 * @return the product of the two values
	 */
	@Override
	public Angle times(double x) {
		return new Degrees(toDegrees()*x);
	}
	
	/**
	 * @return the sine of the angle
	 */
	public double sin()
	{
		return Math.sin(toRadians());
	}
	
	/**
	 * @return the cosine of the angle
	 */
	public double cos()
	{
		return Math.cos(toRadians());
	}
	
	/**
	 * @return the tangent of the angle
	 */
	public double tan()
	{
		return Math.tan(toRadians());
	}
	
	/**
	 * @param x The value to divide by
	 * @return the quotient of the two values
	 */
	public Angle divide(double x) {
		return new Degrees(degrees/x);
	}
	
	/**
	 * @param x the angle to divide by
	 * @return the quotient of the two angles
	 */
	@Override
	public double divide(Angle x) {
		return degrees/x.toDegrees();
	}
	
	/**
	 * @return the degrees of the angle
	 */
	public String toString()
	{
		return degrees+"�";
	}
	
	/**
	 * TODO: document
	 * @return
	 */
	@Override
	public Angle wrap() {
		return new WrappedDegrees(degrees);
	}
}
