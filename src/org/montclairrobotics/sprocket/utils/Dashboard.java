package org.montclairrobotics.sprocket.utils;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Dashboard {
	
	public static final boolean DEBUG_OUTPUTS=true;
	
	public static Sendable getData(String key)
	{
		return getData(key,false);
	}
	
	public static Sendable getData(String key,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			return SmartDashboard.getData(key);
		return null;
	}
	
	public static double getNumber(String key)
	{
		return getNumber(key,false);
	}
	public static double getNumber(String key,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			return SmartDashboard.getNumber(key);
		return 0.0;
	}
	
	public static String getString(String key)
	{
		return getString(key,false);
	}
	public static String getString(String key,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			return SmartDashboard.getString(key);
		return "";
	}
	
	public static void putData(String key, Sendable value)
	{
		putData(key,value,false);
	}
	public static void putData(String key, Sendable value,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			SmartDashboard.putData(key, value);
	}
	
	public static void putNumber(String key, double value)
	{
		putNumber(key,value,false);
	}
	public static void putNumber(String key, double value,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			SmartDashboard.putNumber(key, value);
	}
	
	public static void putString(String key, String value)
	{
		putString(key,value,false);
	}
	public static void putString(String key, String s,boolean debug) {
		if(DEBUG_OUTPUTS || !debug)
			SmartDashboard.putString(key, s);
	}
	
}
