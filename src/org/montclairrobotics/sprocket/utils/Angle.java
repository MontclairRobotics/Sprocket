package org.montclairrobotics.sprocket.utils;

/**
 * CLOCKWISE IS POSITIVE
 * 
 * 
 * An abstract class to hold an angle
 * Extended by Degree and Radian
 * @author Hymowitz
 * @see Degree
 * @see Radian
 */

public abstract class Angle {
	abstract public double toRadians();
	abstract public double toDegrees();
	public abstract Angle add(Angle a);
	public abstract Angle subtract(Angle a);
	public abstract Angle opposite();
	public abstract Angle negative();

	public static double mod(double a,double min,double max)
	{
		double diff=max-min;
		return((a-min)%diff+diff)%diff+min;
	}
}
