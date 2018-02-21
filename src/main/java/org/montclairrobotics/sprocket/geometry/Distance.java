package org.montclairrobotics.sprocket.geometry;

public class Distance {
	public static final Distance ZERO = new Distance(0);
	public static final Distance IN = new Distance(1);
	public static final Distance CM = new Distance(1/2.54);
    public static final Distance M = new Distance(100,CM);
    public static final Distance FT = new Distance(12);
	
    private double inches;
	
	public Distance(double i)
	{
		this.inches=i;
	}
	public Distance(double d,Distance unit)
	{
		this(d*unit.get());
	}
	
	public double get()
	{
		return inches;
	}
	public double get(Distance unit)
	{
		return inches/unit.get();
	}
	
	public String toString()
	{
		return inches+" IN";
	}
}
