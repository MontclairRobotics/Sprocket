package org.montclairrobotics.sprocket.geometry;

public class Distance {
	
	public static final Distance METER = new Distance();
	
	public static final Distance KILOMETER = new Distance(1000, METER);
	public static final Distance CENTIMETER = new Distance(0.01, METER);
	public static final Distance MILLIMETER = new Distance(0.1, CENTIMETER);
	
	public static final Distance INCHES = new Distance(25.4, MILLIMETER);
	public static final Distance FEET = new Distance(12, INCHES);
	public static final Distance YARD = new Distance(3, FEET);
	public static final Distance MILE = new Distance(5280, FEET);
	
	public static final Distance M = METER,
			KM = KILOMETER,
			CM = CENTIMETER,
			MM = MILLIMETER,
			IN = INCHES,
			FT = FEET,
			YD = YARD,
			MI = MILE;
	
	public static final Distance ZERO = new Distance(0,M);
	
	private double distance;
	
	
	public Distance(double dist, Distance unit) {
		distance = dist * unit.getMeters();
	}
	
	private Distance() {
		distance = 1.0;
	}
	
	public double getMeters() {
		return distance;
	}
	
	public double getDistance(Distance unit) {
		return distance * unit.getMeters();
	}

	public Distance add(Distance b)
	{
		return new Distance(distance+b.getMeters(),M);
	}
	public Distance subtract(Distance b)
	{
		return new Distance(distance-b.getMeters(),M);
	}
	
	public Distance times(double x) 
	{
		return new Distance(distance*x,M);
	}
	public Distance times(Distance x)
	{
		return new Distance(distance*x.getMeters(),M);
	}
	
	public Distance divide(double x)
	{
		return new Distance(distance/x,M);
	}
	public double divide(Distance x)
	{
		return distance/x.getMeters();
	}
}