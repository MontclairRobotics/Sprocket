package org.montclairrobotics.sprocket.dataconstructs;

public class Distance implements Comparable{
	public static final Distance
		METERS=new Distance(),
		CENTIMETERS=new Distance(0.01,METERS),
		INCHES=new Distance(2.54,CENTIMETERS),//this is actually an exact measurement
		FEET=new Distance(12,INCHES);
	public static final Distance
		M=METERS,
		CM=CENTIMETERS,
		IN=INCHES,
		FT=FEET;
	
	public static final Distance zero=new Distance(0,METERS);
	
	private double meters;
	private boolean real;
	
	private Distance()
	{
		meters=1.0;
		real=true;
	}
	public Distance(double distance)
	{
		meters=distance;
		real=false;
	}
	public Distance(double distance,boolean real)
	{
		this(distance);
		this.real=real;
	}
	public Distance(double distance,Distance scale)
	{
		meters=distance*scale.toMeters();
		real=true;
	}
	
	public double toMeters()
	{
		return meters;
	}
	public double to(Distance scale)
	{
		if(real)
			return meters/scale.toMeters();
		return meters;
	}
	public boolean isReal()
	{
		return this.isReal();
	}
	@Override
	public int compareTo(Object other) {
		return (((Distance)other).toMeters()-meters>0)?1:-1;
	}
	public Distance add(Distance x) {
		return new Distance(meters+x.meters,METERS);
	}
	public Distance subtract(Distance x)
	{
		return new Distance(meters-x.meters,METERS);
	}
	public Distance times(double x)
	{
		return new Distance(meters*x);
	}
	public double divide(double x)
	{
		return (meters/x);
	}
	public double divide(Distance x) {
		return meters/x.toMeters();
	}
}
