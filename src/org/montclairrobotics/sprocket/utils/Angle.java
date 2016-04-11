package org.montclairrobotics.sprocket.utils;

public abstract class Angle {
	abstract public double toRadians();
	abstract public double toDegrees();
	public Angle add(Angle a)
	{
		return new Degree(this.toDegrees()+a.toDegrees());
	}
	public Angle subtract(Angle a)
	{
		return new Degree(this.toDegrees()-a.toDegrees());
	}
	public double mod(double a,double min,double max)
	{
		double diff=max-min;
		return((a-min)%diff+diff)%diff+min;
	}
	public Angle opposite()
	{
		return new Degree(this.toDegrees()+180);
	}
}
