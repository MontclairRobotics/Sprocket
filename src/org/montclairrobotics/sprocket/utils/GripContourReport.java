package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class GripContourReport {
	public final double[] width;
	public final double[] centerX;
	public final double[] centerY;
	public final double[] height;
	public final double[] area;
	public final int max;
	public final int maxCenterX,maxCenterY;
	
	public GripContourReport(NetworkTable table) 
	{
		area = table.getNumberArray("area", new double[0]);
		if (area.length>0) {
			width = table.getNumberArray("width", new double[0]);
			height = table.getNumberArray("height", new double[0]);
			centerX = table.getNumberArray("centerX", new double[0]);
			centerY = table.getNumberArray("centerY", new double[0]);
			Debug.msg("GRIP", "FOUND");
		}
		else
		{
			width =  new double[0];
			height = new double[0];
			centerX = new double[0];
			centerY = new double[0];
			Debug.msg("GRIP", "NOT FOUND");
		}
		double maxArea = -1.0;
		int max = -1;
		for(int i=0; i<area.length;i++) {
			if(area[i]>maxArea) {
				maxArea=area[i];
				max = i;
			}
		}
		this.max=max;
		if(max>-1&&max<centerX.length)
			maxCenterX=(int)(centerX[max]+0.5);
		else
			maxCenterX=-1;
		if(max>-1&&max<centerY.length)
			maxCenterY=(int)(centerY[max]+0.5);
		else
			maxCenterY=-1;
	}
}
