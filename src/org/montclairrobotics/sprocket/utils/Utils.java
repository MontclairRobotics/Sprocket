package org.montclairrobotics.sprocket.utils;

public class Utils {
	public static Object[] merge(Object[]a,Object[]b)
	{
		Object[] r=new Object[a.length+b.length];
		int i=0;
		for(int j=0;j<a.length;j++)
		{
			r[i]=a[j];
			i++;
		}
		for(int j=0;j<b.length;j++)
		{
			r[i]=b[j];
			i++;
		}
		return r;
	}
}
