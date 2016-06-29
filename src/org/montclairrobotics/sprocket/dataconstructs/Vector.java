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
		this.x=x;
		this.y=y;
		this.angle=new Angle(Math.atan2(x,y),Angle.Unit.RADIANS);
		this.mag=Math.sqrt(x*x+y*y);
		this.realScale=realScale;
	}
	public Vector(Distance x,Distance y)
	{
		this(x.toMeters(),y.toMeters(),x.isReal()&&y.isReal());
	}
	public Vector(double x,double y)
	{
		this(x,y,false);
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
		return Polar(this.getAngle().add(Angle.quarter),this.getRawMag()*angle.toRadians(false),realScale);
	}
	public Vector rotate(Angle a)
	{
		return Polar(this.getAngle().add(a),this.getMag());
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
		double cos=getAngle().cos(),sin=getAngle().sin();
		if(Math.abs(cos)>Math.abs(sin))
		{
			return Polar(getAngle(),getRawMag()/cos,realScale);
		}
		else
		{
			return Polar(getAngle(),getRawMag()/sin,realScale);
		}
	}
	
	public static Vector Polar(Angle angle,double mag,boolean realScale)
	{
		return new Vector(mag*angle.sin(),mag*angle.cos(),realScale);
	}
	public static Vector Polar(Angle angle,Distance mag)
	{
		return Polar(angle,mag.toMeters(),mag.isReal());
	}
	public static Vector Polar(Angle angle,double mag)
	{
		return Polar(angle,mag,false);
	}
}