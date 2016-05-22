package org.montclairrobotics.sprocket.utils;

public class Degree extends Angle{

	private double degrees;
	private double radians;
	private boolean calcRad=false;
	
	public Degree(double d)
	{
		degrees=Utils.mod(d,-180,180);
	}
	
	public double toRadians() {
		if(!calcRad)
		{
			radians=Math.toRadians(degrees);
			calcRad=true;
		}
		return radians;
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
