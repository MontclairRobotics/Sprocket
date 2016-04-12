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
	
	/*
	 * TreeMaps are sorted by default, and they are used here in order to execute Updatables
	 * in the correct order without any weird sorting while in runtime
	 */
	private static TreeMap<Priority, Updatable> objects = new TreeMap<Priority, Updatable>(new Comparator<Priority>() {
		@Override
		public int compare(Priority o1, Priority o2) {
			if(o1.getPriority() < o2.getPriority()) {
				return 1;
			} else if(o1.getPriority() == o2.getPriority()) {
				return 0;
			} else {
				return -1;
			}
		}
	});
	
	/**
	 * Adds an object implementing Updatable to the list of objects that will
	 * be updated each tick.
	 * @param obj Object which will be updated each tick
	 * @param priority The priority (order) of the object and how it will be updated
	 * @see Priority
	 * @see UpdateClass
	 */
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