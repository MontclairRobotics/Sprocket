package org.montclairrobotics.sprocket.utils;

public class Utils {
	public static double constrain(double val,double min,double max)
	{
		if(val<min)return min;
		if(val>max)return max;
		return val;
	}
}
