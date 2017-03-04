package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.geometry.Angle;
import org.montclairrobotics.sprocket.geometry.Vector;

public class Utils {
	public static double constrain(double val,double min,double max)
	{
		if(val<min)return min;
		if(val>max)return max;
		return val;
	}
	
	public static double wrap(double val,double wrapAt)
	{
		double halfWrapAt=wrapAt/2;
		return ((val+halfWrapAt)%wrapAt+wrapAt)%wrapAt-halfWrapAt;
	}
	

	/*
	 * If a dot b = |c| and c is || to b,  given a and c returns |b|
	 */
	public static double inverseDot(Vector force,Vector target)
	{
		Angle diff=target.subtract(force).getAngle().wrap();
		double degTo90=90-Math.abs(diff.toDegrees());
		if(Math.abs(degTo90)<30)
		{
			return target.getMagnitude()*degTo90/15;
		}
		return target.getMagnitude()/diff.cos();
	}
	
}
