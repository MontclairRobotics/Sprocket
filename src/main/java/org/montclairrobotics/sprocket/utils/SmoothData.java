package org.montclairrobotics.sprocket.utils;

public class SmoothData {
	private int len;
	private double[]data;
	private int i;
	private double sum;
	
	public SmoothData(int len)
	{
		this.len=len;
		data=new double[len];
		i=0;
		sum=0;
	}
	
	public double smooth(double in)
	{
		sum+=in;
		if(i<len)
		{
			data[i]=in;
			i++;
			return sum/i;
		}
		else
		sum-=data[i%len];
		data[i%len]=in;
		i++;
		return sum/len;
	}
}
