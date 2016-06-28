package org.montclairrobotics.sprocket.updater;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.montclairrobotics.sprocket.resetter.Resettable;

/**
 * @author Jack Hymowitz, Rafi Baum
 * Runs all the modules which need to be updated in each tick
 * 
 */


public class Updater implements Resettable{
	
	/*
	 * TreeMaps are sorted by default, and they are used here in order to execute Updatables
	 * in the correct order without any weird sorting while in runtime
	 */
	private static int loops;
	private static long startTime=System.currentTimeMillis();
	
	private static TreeMap<Priority, ArrayList<Updatable>> objects = 
			new TreeMap<Priority, ArrayList<Updatable>>(new Comparator<Priority>() {
		@Override
		public int compare(Priority o1, Priority o2) {
			return o1.getPriority()-o2.getPriority();
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
		if(!objects.containsKey(priority)) objects.put(priority,new ArrayList<Updatable>());
		objects.get(priority).add(obj);
	}
	
	/**
	 * Updates all the objects that are registered with the Updater
	 */
	public static void update()
	{
		for(ArrayList<Updatable> list : objects.values()) {
			for(Updatable obj:list)
			{
				obj.update();
			}
		}
	}
	
	/**
	 * Removes an updatable from the queue
	 * @param obj The object to be deleted
	 * @return True if deleted, false if the object cannot be found
	 */
	public static boolean remove(Updatable obj) {
		for(Entry<Priority, ArrayList<Updatable>> set : objects.entrySet()) {
			for(int i=0;i<set.getValue().size();i++)
			{
				if(set.getValue().get(i)==obj)//YES, I used ==. I think it is right in this case
				{
					set.getValue().remove(i);
					return true;
				}
			}
		}
		return false;
	}

	public static double loopsPerSec() {
		if(loops<50||System.currentTimeMillis()-startTime<1000)
			return 50.0;
		return loops/System.currentTimeMillis();
	}

	@Override
	public void onStop() {
		loops=0;
	}

	@Override
	public void startAuto() {
		loops=0;
		startTime=System.currentTimeMillis();
	}

	@Override
	public void startTeleop() {
		loops=0;
		startTime=System.currentTimeMillis();
	}
	
}