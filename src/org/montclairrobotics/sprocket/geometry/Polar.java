package org.montclairrobotics.sprocket.geometry;

public class Polar extends Vector{

	private Distance mag;
	private Angle angle;
	
	public Polar(Distance mag,Angle angle)
	{
		this.mag=new Distance(Math.abs(mag.getMeters()),Distance.M);
		this.angle=((mag.getMeters()>0)?angle:angle.opposing());
	}
	public Polar(Distance mag,double degrees)
	{
		this(mag,new Degree(degrees));
	}
	public Distance getMag() {
		return mag;
	}
	public Angle getAngle() {
		return angle;
	}
	public Distance getX() {
		return mag.times(Math.sin(angle.toRadians()));
	}
	public Distance getY() {
		return mag.times(Math.cos(angle.toRadians()));
	}
	
}
