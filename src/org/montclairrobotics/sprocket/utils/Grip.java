package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Grip implements Updatable{
	NetworkTable table;
	GripContourReport report;
	
	public Grip(String key)
	{
		table=NetworkTable.getTable(key);
		Updater.add(this, Priority.INPUT);
	}
	public void update()
	{
		report=new GripContourReport(table);
	}
	public int getX()
	{
		if(report==null)return 0;
		Dashboard.putNumber("grip_x",report.maxCenterX);
		return report.maxCenterX;
	}
	public int getY()
	{
		if(report==null)return 0;
		Dashboard.putNumber("grip_y",report.maxCenterY);
		return report.maxCenterY;
	}
	public GripContourReport getReport()
	{
		return report;
	}
}
