package org.montclairrobotics.sprocket.utils;

import java.util.ArrayList;

/**
 * 
 * @author Jack Hymowitz
 * Runs all the modules which need to be updated in each tick
 * 
 */
public class Updater {
	
	private static ArrayList<Updatable> objects = new ArrayList<Updatable>();
	
	/**
	 * Adds an object implementing Updatable to the list of objects that will
	 * be updated each tick.
	 * @param obj Object which will be updated each tick
	 */
	public static void add(Updatable obj)
	{
		objects.add(obj);
	}
	
	/**
	 * Updates all the objects that are registered with the Updater
	 */
	public static void update()
	{
	for(Updatable obj:objects)
		{
			obj.update();
		}
	}
}
