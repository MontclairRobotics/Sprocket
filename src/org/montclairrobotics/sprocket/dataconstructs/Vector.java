package org.montclairrobotics.sprocket.dataconstructs;


/**
 * Holds a two-dimensional vector in Polar or Cartesian form.
 * @see Polar
 * @see XY
 * @author Hymowitz
 *
 */

public class Vector {
	public static final Vector zero=new Vector(0,0);
	
	private double x,y,mag;
	private Angle angle;
	private boolean realScale;
	
	public Vector(double x,double y,boolean realScale)
	{
		this(x,y);
		this.realScale=realScale;
	}
	public Vector(Distance x,Distance y)
	{
		this(x.toMeters(),y.toMeters());
		realScale=x.isReal()&&y.isReal();
	}
	public Vector(double x,double y)
	{
		this.x=x;
		this.y=y;
		this.angle=new Angle(Math.atan2(x,y),Angle.Unit.RADIANS);
		this.mag=Math.sqrt(x*x+y*y);
		realScale=false;
	}
	public Vector(Angle angle,double mag,boolean realScale)
	{
		this(angle,mag);
		this.realScale=realScale;
	}
	public Vector(Angle angle,Distance mag)
	{
		this(angle,mag.toMeters());
		realScale=mag.isReal();
	}
	public Vector(Angle angle,double mag)
	{
		this.x=mag*Math.sin(angle.toRadians());
		this.y=mag*Math.cos(angle.toRadians());
		this.angle=((mag>0)?angle:angle.opposite());
		this.mag=Math.abs(mag);
		realScale=false;
	}
	
	//Distance getters
	public Distance getX()
	{
		return new Distance(x,realScale);
	}
	public Distance getY()
	{
		return new Distance(y,realScale);
	}
	public Angle getAngle()
	{
		return angle;
	}
	public Distance getMag()
	{
		return new Distance(mag,realScale);
	}
	
	//Raw getters
	public double getRawX()
	{
		return x;
	}
	public double getRawY()
	{
		return y;
	}
	public double getRawMag()
	{
		return mag;
	}
	
	public Vector add(Vector a)
	{
		return new Vector(this.getRawX()+a.getRawX(),this.getRawY()+a.getRawY(),realScale);
	}
	public Vector subtract(Vector a)
	{
		return new Vector(this.getRawX()-a.getRawX(),this.getRawY()-a.getRawY(),realScale);
	}
	public Vector opposite()
	{
		return new Vector(-this.getRawX(),-this.getRawY(),realScale);
	}
	
	public Vector getRotationVector(Angle angle)
	{
		return new Vector(this.getAngle().add(Angle.quarter),this.getRawMag()*angle.toRadians(false),realScale);
	}
	public Vector rotate(Angle a)
	{
		return new Vector(this.getAngle().add(a),this.getRawMag(),realScale);
	}
	public static Vector getAvg(Vector... list)
	{
		if(list.length<1)return zero;
		Distance xSum=Distance.zero;
		Distance ySum=Distance.zero;
		for(Vector v:list)
		{
			xSum=xSum.add(v.getX());
			ySum=ySum.add(v.getY());
		}
		return new Vector(xSum.divide(list.length),ySum.divide(list.length));
	}
	public Vector makeFractionOfSquare()
	{
		double rad=getAngle().toRadians();
		double cos=Math.cos(rad),sin=Math.sin(rad);
		if(Math.abs(cos)>Math.abs(sin))
		{
			return new Vector(getAngle(),getRawMag()/cos,realScale);
		}
		else
		{
			return new Vector(getAngle(),getRawMag()/sin,realScale);
		}
	}
}