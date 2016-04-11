package org.montclairrobotics.sprocket.utils;

public class Polar extends Vector{

	private double mag;
	private Angle angle;
	
	public Polar(double mag,Angle angle)
	{
		this.mag=Math.abs(mag);
		this.angle=((mag>0)?angle:angle.opposite());
	}
	
	public double getMag() {
		return mag;
	}
	public Angle getAngle() {
		return angle;
	}
	public double getX() {
		return mag*Math.sin(angle.toRadians());
	}
	public double getY() {
		return mag*Math.cos(angle.toRadians());
	}
	
}
