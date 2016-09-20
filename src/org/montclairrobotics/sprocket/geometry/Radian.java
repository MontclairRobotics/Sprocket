package org.montclairrobotics.sprocket.geometry;

import org.montclairrobotics.sprocket.utils.Utils;

public class Radian extends Angle{

	public static final double RAD_IN_180=Math.toRadians(180);
	private double radians;
	private double degrees;
	private boolean calcDeg=false;
	
	public Radian(double r)
	{
		radians=Utils.mod(r,-RAD_IN_180,RAD_IN_180);
	}
	
	public double toRadians() {
		return radians;
	}

	public double toDegrees() {
		if(!calcDeg)
		{
			degrees=Math.toDegrees(radians);
			calcDeg=true;
		}
		return degrees;
	}

	public Angle add(Angle a)
	{
		return new Radian(this.toRadians()+a.toRadians());
	}
	public Angle subtract(Angle a)
	{
		return new Radian(this.toRadians()-a.toRadians());
	}
	public Angle opposing()
	{
		return new Radian(this.toRadians()+Math.PI);
	}

	public Angle negative() {
		return new Radian(-this.toRadians());
	}

}
