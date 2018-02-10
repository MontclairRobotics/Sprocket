package org.montclairrobotics.sprocket.utils;

public class Range implements Comparable<Number>{
	final double min;
	final double max;
	
	public Range(double a, double b) {
		if (a <= b) {
			min = a;
			max = b;
		} else {
			min = b;
			max = a;
		}
	}
	
	public Range() {
		this(0.0, 0.0);
	}
	
	public boolean isZero() {
		return min == max;
	}
	
	public double difference() {
		return max - min;
	}
	
	public Range copy() {
		return new Range(min, max);
	}
	
	public boolean contains(double val) {
		return this.compareTo(val) == 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Range) {
			Range r = (Range) obj;
			return min == r.min && max == r.max;
		}
		
		if (obj instanceof Double) {
			Double d = (Double) obj;
			return this.contains(d);
		}
		
		return false;
	}
	
	@Override
	public int compareTo(Number o) {
		if (o.doubleValue() <= min)
			return -1;
		else if (o.doubleValue() >= max)
			return +1;
		else
			return 0;
	}
	
	public int compareTo(Double d) {
		return this.compareTo((Number) d);
	}
	
	public int compareTo(Integer i) {
		return this.compareTo((Number) i);
	}
	
	public double constrain(double val) {
		if (val < min)
			return min;
		else if (val > max)
			return max;
		else
			return val;
	}
	
	public double wrap(double val) {
		double diff = difference();
		return ((val - min) % diff + diff) % diff + min;
	}
	
	/* Class Methods */
	
	public static Range power() {
		return new Range(-1.0, +1.0);
	}
	
	public static Range angleInDegrees() {
		return new Range(-180.0, +180.0);
	}
	
	public static Range angleInRadians() {
		return new Range(-Math.PI, +Math.PI);
	}
}
