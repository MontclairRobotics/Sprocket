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
    public static int[] range(int start,int end)
    {
        int[]r=new int[end-start];
        for(int i=0;i<r.length;i++)
        {
            r[i]=i+start;
        }
        return r;
    }
    public static double avg(double[] a)
    {
        if(a.length==0)return 0.0;
        double sum=0;
        for(double x:a)
        {
            sum+=x;
        }
        return sum/a.length;
    }
}
