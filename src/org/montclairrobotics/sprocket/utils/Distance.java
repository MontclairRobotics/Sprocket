package org.montclairrobotics.sprocket.utils;

public class Distance {
	public static final Distance
		METERS=new Distance(),
		CENTIMETERS=new Distance(0.01,METERS),
		INCHES=new Distance(2.54,CENTIMETERS),//this is actually an exact measurement
		FEET=new Distance(12,INCHES);
	public static final Distance
		M=METERS,
		CM=CENTIMETERS,
		IN=INCHES,
		FT=FEET;
	
	private double meters;
	
	private Distance()
	{
		meters=1.0;
	}
	public Distance(double distance,Distance scale)
	{
		meters=distance*scale.toMeters();
	}
	
	public double toMeters()
	{
		return meters;
	}
	public double to(Distance scale)
	{
		return meters/scale.toMeters();
	}
}
