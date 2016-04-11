package org.montclairrobotics.sprocket.utils;

public class Radian extends Angle{

	public static final double RAD_IN_180=Math.toRadians(180);
	private double radians;
	
	public Radian(double r)
	{
		radians=mod(r,-RAD_IN_180,RAD_IN_180);
	}
	
	public double toRadians() {
		return radians;
	}

	public double toDegrees() {
		return Math.toDegrees(radians);
	}

	public Angle add(Angle a)
	{
		return new Radian(this.toRadians()+a.toRadians());
	}
	public Angle subtract(Angle a)
	{
		return new Radian(this.toRadians()-a.toRadians());
	}
	public Angle opposite()
	{
		return new Radian(this.toRadians()+Math.PI);
	}

	public Angle negative() {
		return new Radian(this.toRadians());
	}

}
