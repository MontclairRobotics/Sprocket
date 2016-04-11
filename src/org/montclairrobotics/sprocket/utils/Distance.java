package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.drive.AutoDrive.UNITS;

public class Distance {
	public static final double WHEEL_CIRC=8;//TODO TODO TODO
	public static final double 
			DEGREES=1,
			ROTATIONS=DEGREES*360,
			INCHES=ROTATIONS*WHEEL_CIRC,
			FEET=INCHES*12;
	
	private double distance;
	
	public Distance(double distance, double units)
	{
		this.distance=distance*units;
	}
	public double getDistance()
	{
		return getDistance(DEGREES);
	}
	public double getDistance(double units)
	{
		return distance/units;
	}
}
