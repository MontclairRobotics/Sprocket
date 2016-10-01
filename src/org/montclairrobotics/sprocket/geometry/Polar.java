package org.montclairrobotics.sprocket.geometry;

public class Polar extends Vector{

	private Unit mag;
	private Angle angle;
	
	public Polar(Unit mag,Angle angle)
	{
		this.mag = new Unit(Math.abs(mag.get()), Unit.DEFAULT);
		this.angle = ((mag.get() > 0) ? angle : angle.opposing());
	}
	public Polar(Unit mag,double degrees)
	{
		this(mag,new Degree(degrees));
	}
	public Unit getMag() {
		return mag;
	}
	public Angle getAngle() {
		return angle;
	}
	public Unit getX() {
		return mag.multiply(Math.sin(angle.toRadians()));
	}
	public Unit getY() {
		return mag.multiply(Math.cos(angle.toRadians()));
	}
	
}
