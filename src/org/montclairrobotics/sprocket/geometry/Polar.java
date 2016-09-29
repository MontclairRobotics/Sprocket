package org.montclairrobotics.sprocket.geometry;

public class Polar extends Vector{

	private Speed mag;
	private Angle angle;
	
	public Polar(Speed mag,Angle angle)
	{
		this.mag=new Speed(Math.abs(mag.get()),Speed.MS);
		this.angle=((mag.get() > 0) ? angle : angle.opposing());
	}
	public Polar(Speed mag,double degrees)
	{
		this(mag,new Degree(degrees));
	}
	public Speed getMag() {
		return mag;
	}
	public Angle getAngle() {
		return angle;
	}
	public Speed getX() {
		return mag.times(Math.sin(angle.toRadians()));
	}
	public Speed getY() {
		return mag.times(Math.cos(angle.toRadians()));
	}
	
}
