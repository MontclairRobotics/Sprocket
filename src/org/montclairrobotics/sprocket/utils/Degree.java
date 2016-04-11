package org.montclairrobotics.sprocket.utils;

public class Degree extends Angle{

	private double degrees;
	
	public Degree(double d)
	{
		degrees=mod(d,-180,180);
	}
	
	public double toRadians() {
		return Math.toRadians(degrees);
	}

	@Override
	public double toDegrees() {
		return degrees;
	}
}
