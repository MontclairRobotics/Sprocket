package org.montclairrobotics.sprocket.utils;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 
 * @author Jack Hymowitz, Rafi Baum
 * Runs all the modules which need to be updated in each tick
 * 
 */
public class Updater {
	
	private static TreeMap<Priority, Updatable> objects = 
			new TreeMap<Priority, Updatable>(new Comparator<Priority>() {
		@Override
		public int compare(Priority o1, Priority o2) {
			/*
			if(o1.getPriority() < o2.getPriority()) {
				return 1;
			} else if(o1.getPriority() == o2.getPriority()) {
				return 0;
			} else {
				return -1;
			}
			*/
			return o2.getPriority()-o1.getPriority();
		}
	});
	
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
		objects.put(priority, obj);
	}
	
	/**
	 * Updates all the objects that are registered with the Updater
	 */
	public static void update()
	{
		for(Updatable obj : objects.values()) {
			obj.update();
		}
	}
}