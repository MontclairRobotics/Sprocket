package org.montclairrobotics.sprocket.utils;

public class Utils {
	public static double constrain(double val,double min,double max)
	{
		if(val<min)return val;
		if(val>max)return max;
		return val;
	}

	public static double deadZone(double val,double deadZone)
	{
		if(val>-deadZone&&val<deadZone)
			return 0;
		return val;
	}
	
	public static double mod(double a,double min,double max)
	{
		double diff=max-min;
		return((a-min)%diff+diff)%diff+min;
	}
}
