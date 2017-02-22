package org.montclairrobotics.sprocket.geometry;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Debug {

	public static boolean DEBUG_MODE=true;
	
	public static void num(String key,double value)
	{
		if(DEBUG_MODE)
			SmartDashboard.putNumber(key, value);
	}
	
	public static void string(String key,String value)
	{
		if(DEBUG_MODE)
			SmartDashboard.putString(key, value);
	}
}
