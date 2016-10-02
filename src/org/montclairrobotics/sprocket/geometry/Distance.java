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

    private Distance() {}

	public Distance(double d, Distance unit) {
        super(d, unit);
	}

	public Distance(double d) {
        super(d, METER);
    }
	
	public Distance(Unit u) {
		super(u);
	}
	
    public double getMeters() {
        return super.get();
    }


    public Distance add(Unit a) {
        if(!isSameType(a)) return null;
        return new Distance(raw + a.get());
    }

    public Distance subtract(Distance a) {
        if(!isSameType(a)) return null;
        return new Distance(raw - a.get());
    }

    public Distance multiply(Distance a) {
        if(!isSameType(a)) return null;
        return new Distance(raw * a.get());
    }

    public Distance divide(Distance a) {
        if(!isSameType(a)) return null;
        return new Distance(raw / a.get());
    }

}