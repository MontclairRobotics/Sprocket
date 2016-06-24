package org.montclairrobotics.sprocket.utils;

/**
 * Holds a two-dimensional vector in Polar or Cartesian form.
 * @see Polar
 * @see XY
 * @author Hymowitz
 *
 */

public abstract class Vector {
	public static final Vector zero=new XY(0,0);
	public abstract double getMag();
	public abstract Angle getAngle();
	public abstract double getX();
	public abstract double getY();
	
	public Vector add(Vector a)
	{
		return new XY(this.getX()+a.getX(),this.getY()+a.getY());
	}
	public Vector subtract(Vector a)
	{
		return new XY(this.getX()-a.getX(),this.getY()-a.getY());
	}
	public Vector opposite()
	{
		return new XY(-this.getX(),-this.getY());
	}
	
	public Vector getRotationVector(double rotation)
	{
		return new Polar(this.getMag()*rotation,this.getAngle().add(new Degree(90)));
	}
	public Vector rotate(Angle a)
	{
		return new Polar(this.getMag(),this.getAngle().add(a));
	}
	public Vector makeFractionOfSquare()
	{
		double rad=getAngle().toRadians();
		double cos=Math.cos(rad),sin=Math.sin(rad);
		if(Math.abs(cos)>Math.abs(sin))
		{
			return new Polar(getMag()/cos,getAngle());
		}
		else
		{
			return new Polar(getMag()/sin,getAngle());
		}
	}
}
