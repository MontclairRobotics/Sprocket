package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard extends SmartDashboard {
	
	private static boolean DEBUG = false;
	
	public void setDebug(boolean debug)
	{
		this.DEBUG=debug;
	}
	
	public static void putDebugNumber(String key, double num) {
		if(DEBUG) putNumber(key, num);
	}
	
	public static void putDebugString(String key,String val)
	{
		if(DEBUG)putString(key,val);
	}
	
}
