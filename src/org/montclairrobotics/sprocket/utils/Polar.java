package org.montclairrobotics.sprocket.utils;

public class Polar extends Vector{

	private double mag;
	private Angle angle;
	private double x,y;
	private boolean calcX,calcY;
	
	public Polar(double mag,Angle angle)
	{
		this.mag=Math.abs(mag);
		this.angle=((mag>0)?angle:angle.opposite());
	}
	public Polar(double mag,double degrees)
	{
		this(mag,new Degree(degrees));
	}
	public double getMag() {
		return mag;
	}
	public Angle getAngle() {
		return angle;
	}
	public double getX() {
		if(!calcX)
		{
			calcX=true;
			x=mag*Math.sin(angle.toRadians());
		}
		return x;
	}
	public double getY() {
		if(!calcY)
		{
			calcY=true;
			y=mag*Math.cos(angle.toRadians());
		}
		return y;
	}
	
}
