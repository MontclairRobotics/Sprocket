package org.montclairrobotics.sprocket.utils;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 
 * @author Jack Hymowitz, Rafi Baum
 * Runs all the modules which need to be updated in each tick
 * 
 */
public class Updater {
	
	private static HashMap<Updatable, Priority> objects = new HashMap<Updatable, Priority>();
	private static final Priority[] priorities = {Priority.HIGHEST, Priority.HIGH, Priority.NORMAL, Priority.LOW, Priority.LOWEST};
	
	/**
	 * Adds an object implementing Updatable to the list of objects that will
	 * be updated each tick.
	 * @param obj Object which will be updated each tick
	 */
	/*public static void add(Updatable obj)
	{
		objects.add(obj);
	}*/
	
	public static void add(Updatable obj, Priority priority) {
		objects.put(obj, priority);
	}
	
	/**
	 * Updates all the objects that are registered with the Updater
	 */
	public static void update()
	{
		for(Priority p : priorities) {
			for(Entry<Updatable, Priority> obj : objects.entrySet()) {
				if(obj.getValue() == p ) {
					obj.getKey().update();
				}
			}
		}
	}
}