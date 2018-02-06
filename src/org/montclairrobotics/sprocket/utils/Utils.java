package org.montclairrobotics.sprocket.utils;

@Deprecated
public class Utils {
	public static double constrain(double val, double min, double max) {
		if (val < min)
			return min;
		else if (val > max)
			return max;
		else
			return val;
	}
	
	public static double wrap(double val, double wrapAt) {
		return wrap(val,-wrapAt/2,wrapAt/2);
	}
	
	public static double wrap(double val, double min, double max) {
		double diff = max - min;
		return ((val - min) % diff + diff) % diff + min;
	}
	
}
