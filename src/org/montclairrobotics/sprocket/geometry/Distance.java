package org.montclairrobotics.sprocket.geometry;

public class Distance extends Unit {
	
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
	
	
	public Distance(double dist, Distance unit) {
		super(dist, unit);
	}
	
	private Distance() {
		super();
	}
	
	public double getMeters() {
		return super.get();
	}
	
	public double getDistance(Distance unit) {
		return super.get(unit);
	}

	public Distance add(Distance b)
	{
		return new Distance(raw+b.getMeters(),M);
	}
	public Distance subtract(Distance b)
	{
		return new Distance(raw-b.getMeters(),M);
	}
	
	public Distance multiply(double x)
	{
		return new Distance(raw*x,M);
	}
	public Distance multiply(Distance x)
	{
		return new Distance(raw*x.getMeters(),M);
	}
	
	public Distance divide(double x)
	{
		return new Distance(raw/x,M);
	}
	public double divide(Distance x)
	{
		return raw/x.getMeters();
	}
}