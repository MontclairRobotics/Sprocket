package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

public class Degree extends Angle{

	private double degrees;
	private double radians;
	private boolean calcRad=false;

	/**
	 * Creates an Angle object using degrees
	 * @param d Angle in degrees
	 */
	public Degree(double d)
	{
		degrees=Utils.mod(d,-180,180);
	}

	/**
	 * Converts angle to radians
	 * @return The angle in radians
	 */
	public double toRadians() {
		if(!calcRad)
		{
			radians=Math.toRadians(degrees);
			calcRad=true;
		}
		return radians;
	}

	/**
	 * Returns the angle in degrees
	 * @return The angle in degrees
	 */
	public double toDegrees() {
		return degrees;
	}

	/**
	 * Adds this angle to another
	 * @param a The angle to add
	 * @return The sum
	 */
	public Angle add(Angle a)
	{
		return new Degree(this.toDegrees()+a.toDegrees());
	}

	/**
	 * Subtracts another angle from this one
	 * @param a The angle to subtract from this one
	 * @return The difference of the two angles
	 */
	public Angle subtract(Angle a)
	{
		return new Degree(this.toDegrees()-a.toDegrees());
	}

	/**
	 *
	 * @return The angle 180 degrees away from this one
	 */
	public Angle opposing()
	{
		return new Degree(this.toDegrees()+180);
	}

	/**
	 *
	 * @return The negative of this angle
	 */
	public Angle negative() {
		return new Degree(-this.toDegrees());
	}
}
