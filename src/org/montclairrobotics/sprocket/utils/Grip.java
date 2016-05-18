package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

//TODO: this
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
	public double getX()
	{
		return report.maxCenterX;
	}
	public double getY()
	{
		return report.maxCenterY;
	}
	public GripContourReport getReport()
	{
		return report;
	}
}
