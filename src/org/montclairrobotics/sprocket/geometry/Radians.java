package org.montclairrobotics.sprocket.geometry;

public class Radians implements Angle{
	
	/**
	 * The angle measure in readians
	 */
    private double radians;
	
	/**
	 * @param angle the angle measure in radians
	 */
	public Radians(double angle) {
        radians = angle;
    }
	
	/**
	 * @return the angle measure in degrees
	 */
	@Override
    public double toDegrees() {
        return radians*(180/Math.PI);
    }
	
	/**
	 * @return the angle measure in radians
	 */
    @Override
    public double toRadians() {
        return radians;
    }
	
	/**
	 * @param a The angle to be added with this one
	 * @return the sum of the two angles
	 */
	@Override
    public Angle add(Angle a) {
        return new Radians(toRadians() + a.toRadians());
    }
	
	/**
	 * @param a The angle to be subtracted from this one
	 * @return the difference of the two angles
	 */
	@Override
    public Angle subtract(Angle a) {
        return new Radians(toRadians() - a.toRadians());
    }
	
	/**
	 * @return the opposite of the angle
	 */
	public Angle opposite()
    {
    	return new Radians(2*Math.PI+toRadians());
    }
	
	/**
	 * @return the negative of the angle
	 */
	@Override
    public Angle negative() {
        return new Radians(-toRadians());
    }
	
	/**
	 * @param x The value to multiply by
	 * @return the product of the two values
	 */
	@Override
	public Angle times(double x) {
		return new Radians(toRadians()*x);
	}
	
	/**
	 * @return the sin of the angle
	 */
	public double sin()
	{
		return Math.sin(toRadians());
	}
	
	/**
	 * @return the cosin of the angle
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
	@Override
	public Angle divide(double x) {
		return new Radians(radians/x);
	}
	
	/**
	 * @param x the angle to divide by
	 * @return the quotient of the two angles
	 */
	@Override
	public double divide(Angle x) {
		return radians/x.toRadians();
	}
	
	/**
	 * @return
	 */
	public String toString()
	{
		return toDegrees()+"�";
	}
	
	@Override
	public Angle wrap() {
		return new WrappedRadians(radians);
	}
}
