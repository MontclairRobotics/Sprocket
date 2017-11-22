package org.montclairrobotics.sprocket.frc;

import org.montclairrobotics.sprocket.loop.Priority;
import org.montclairrobotics.sprocket.loop.Updatable;
import org.montclairrobotics.sprocket.loop.Updater;
import org.montclairrobotics.sprocket.utils.Debug;

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
		if(report==null)return -1;
		Debug.num("grip_x",report.maxCenterX);
		return report.maxCenterX;
	}
	public int getY()
	{
		if(report==null)return -1;
		Debug.num("grip_y",report.maxCenterY);
		return report.maxCenterY;
	}
	public GripContourReport getReport()
	{
		return report;
	}
}
