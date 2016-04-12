package org.montclairrobotics.sprocket.utils;

public class XY extends Vector{

	private double x,y;
	
	public XY(double x,double y)
	{
		this.x=x;
		this.y=y;
	}
	
	public double getMag() {
		return Math.sqrt(x*x+y*y);
	}

	public Angle getAngle() {
		return new Radian(Math.atan2(x,y));
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
