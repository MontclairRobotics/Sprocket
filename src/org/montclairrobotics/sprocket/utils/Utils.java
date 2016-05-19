package org.montclairrobotics.sprocket.utils;

public class Utils {
	public static double constrain(double val,double min,double max)
	{
		if(val<min)return val;
		if(val>max)return max;
		return val;
	}
}
