package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.core.Sprocket;


public class Debug {

	public static boolean DEBUG_MODE=true;
	
	public static void num(String key,double value)
	{
		if(DEBUG_MODE)
			Sprocket.debugger.debugNum(key, value);
	}
	
	public static void string(String key,String value)
	{
		if(DEBUG_MODE)
			Sprocket.debugger.debugStr(key, value);
	}
	
	public static void msg(String key,double value)
	{
		num(key,value);
	}
	public static void msg(String key,String value)
	{
		string(key,value);
	}
	public static void msg(String key,boolean value)
	{
		string(key,value?"TRUE":"FALSE");
	}
	public static void msg(String key,Object value)
	{
		string(key,value.toString());
	}
}
