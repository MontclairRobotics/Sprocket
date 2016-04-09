package org.montclairrobotics.sprocket.utils;

import java.util.concurrent.ConcurrentHashMap;

import edu.wpi.first.wpilibj.Sendable;

public class Dashboard {
	
	private static edu.wpi.first.wpilibj.smartdashboard.SmartDashboard dashboard;
	
	private static boolean debugOutputs=true;
	
	public static Sendable getData(String key)
	{
		return getData(key,false);
	}
	public static Sendable getData(String key,boolean debug) {
		if(debugOutputs || !debug)
			return dashboard.getData(key);
		return null;
	}
	
	public static double getNumber(String key)
	{
		return getNumber(key,false);
	}
	public static double getNumber(String key,boolean debug) {
		if(debugOutputs || !debug)
			return dashboard.getNumber(key);
		return 0.0;
	}
	
	public static String getString(String key)
	{
		return getString(key,false);
	}
	public static String getString(String key,boolean debug) {
		if(debugOutputs || !debug)
			return dashboard.getString(key);
		return "";
	}
	
	public static void putData(String key, Sendable value)
	{
		putData(key,value,false);
	}
	public static void putData(String key, Sendable value,boolean debug) {
		if(debugOutputs || !debug)
			dashboard.putData(key, value);
	}
	
	public static void putNumber(String key, double value)
	{
		putNumber(key,value,false);
	}
	public static void putNumber(String key, double value,boolean debug) {
		if(debugOutputs || !debug)
			dashboard.putNumber(key, value);
	}
	
	public static void putString(String key, String value)
	{
		putString(key,value,false);
	}
	public static void putString(String key, String s,boolean debug) {
		if(debugOutputs || !debug)
			dashboard.putString(key, s);
	}
	
}
