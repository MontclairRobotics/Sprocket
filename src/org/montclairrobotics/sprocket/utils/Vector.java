package org.montclairrobotics.sprocket.utils;

public abstract class Vector {
	public abstract double getMag();
	public abstract Angle getAngle();
	public abstract double getX();
	public abstract double getY();
	
	public Vector add(Vector a)
	{
		return new XY(this.getX()+a.getX(),this.getY()+a.getY());
	}
	public Vector opposite()
	{
		return new XY(-this.getX(),-this.getY());
	}
	
	public Vector getRotationVector(double distance)
	{
		return new Polar(distance,this.getAngle().add(new Degree(90)));
	}
	public Vector rotate(Angle a)
	{
		return new Polar(this.getMag(),this.getAngle().add(a));
	}
}
