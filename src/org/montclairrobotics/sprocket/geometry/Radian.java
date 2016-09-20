package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

public class Radian extends Angle{

	public static final double RAD_IN_180=Math.toRadians(180);
	private double radians;
	private double degrees;
	private boolean calcDeg=false;

	/**
	 * Creates an angle in radians
	 * @param r The angle in radians
	 */
	public Radian(double r)
	{
		radians=Utils.mod(r,-RAD_IN_180,RAD_IN_180);
	}

	/**
	 * Returns this angle in radians
	 * @return The angle in radians
	 */
	public double toRadians() {
		return radians;
	}

	/**
	 * Converts this angle to degrees
	 * @return The angle in degrees
	 */
	public double toDegrees() {
		if(!calcDeg)
		{
			degrees=Math.toDegrees(radians);
			calcDeg=true;
		}
		return degrees;
	}

	/**
	 * Adds this angle to another
	 * @param a The angle to add
	 * @return The sum
	 */
	public Angle add(Angle a)
	{
		return new Radian(this.toRadians()+a.toRadians());
	}

	/**
	 * Subtracts another angle from this one
	 * @param a The angle to subtract from this one
	 * @return The difference of the two angles
	 */
	public Angle subtract(Angle a)
	{
		return new Radian(this.toRadians()-a.toRadians());
	}

	/**
	 * Returns an angle that is PI radians away from this one
	 * @return The offset angle
	 */
	public Angle opposing()
	{
		return new Radian(this.toRadians()+Math.PI);
	}

	/**
	 *
	 * @return Returns the negative of this angle
	 */
	public Angle negative() {
		return new Radian(-this.toRadians());
	}

}
