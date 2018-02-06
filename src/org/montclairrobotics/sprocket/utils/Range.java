package org.montclairrobotics.sprocket.utils;

public class Range implements Comparable<Double> {
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
	
	public boolean isNotZero() {
		return min != max;
	}
	
	public double diff() {
		return max - min;
	}
	
	public Range copy() {
		return new Range(min, max);
	}
	
	@Override
	public int compareTo(Double o) {
		if (o < min)
			return -1;
		else if (o > max)
			return +1;
		else
			return 0;
	}
	
	public static Range power() {
		return new Range(-1.0, +1.0);
	}
	
	public static Range angleInDegrees() {
		return new Range(-180.0, +180.0);
	}
	
	public static Range angleInRadians() {
		return new Range(-Math.PI, +Math.PI);
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
		double diff = diff();
		return ((val - min) % diff + diff) % diff + min;
	}
}
