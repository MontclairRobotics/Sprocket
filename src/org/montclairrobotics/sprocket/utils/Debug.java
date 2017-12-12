package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.core.Sprocket;

/**
 * Created by Montclair Robotics on 5/5/55.
 *
 * A utility class for printing messages to Sprocket's debugger.
 */
public class Debug {
	/** Indicates whether Sprocket is in Debug Mode. */
	public static boolean DEBUG_MODE = true;

	/**
	 * Prints a numeric value to a category in the debugger.
	 * @param key the category in the debugger.
	 * @param value the numeric value to print.
	 */
	public static void num(String key, double value) {
		if(DEBUG_MODE)
			Sprocket.debugger.debugNum(key, value);
		else
			//TODO: Signal in some way that method failed.
	}

	/**
	 * Prints a debug message to a category in the debugger.
	 * @param key the category in the debugger.
	 * @param value the message to print.
	 */
	public static void string(String key, String value) {
		if(DEBUG_MODE)
			Sprocket.debugger.debugStr(key, value);
		else
			//TODO: Signal in some way that method failed.
	}

	public static void msg(String key, double value)
	{
		num(key, value);
	}
	public static void msg(String key, String value)
	{
		string(key, value);
	}
	public static void msg(String key, boolean value)
	{
		string(key, value? "TRUE" : "FALSE");
	}
	public static void msg(String key, Object value)
	{
		string(key, value);
	}
}
