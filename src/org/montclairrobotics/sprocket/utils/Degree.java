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

	public double toDegrees() {
		return degrees;
	}
	public Angle add(Angle a)
	{
		return new Degree(this.toDegrees()+a.toDegrees());
	}
	public Angle subtract(Angle a)
	{
		return new Degree(this.toDegrees()-a.toDegrees());
	}
	public Angle opposite()
	{
		return new Degree(this.toDegrees()+180);
	}
	
	public Angle negative() {
		return new Degree(-this.toDegrees());
	}
}
