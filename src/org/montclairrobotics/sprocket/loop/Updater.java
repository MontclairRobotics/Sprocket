package org.montclairrobotics.sprocket.loop;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The Updater is in charge of updating all of the updatables in the robot.
 * All updatables are added with a priority and the updatables with the
 * higher priorities are updated first
 * @see Priority
 *
 * The updater also keeps track of information like the time in each loop
 */
public class Updater {
	
	private static double lastLoop=getTime();
	private static double loopTime=1.0/50;
	
	//TODO: rafi, plz help
    private static TreeMap<Priority, ArrayList<Updatable>> updatables = new TreeMap<>((o1, o2) -> {
            if(o1.getPriority() > o2.getPriority()) {
                return -1;
            } else if(o1.getPriority() == o2.getPriority()) {
                return 0;
            }
            return 1;
        }
    );
    
    /**
     * Add an updatable to the updater and
     * @param updatable the updatable to be added
     * @param priority the priority that the updatable has to be updated
     */
    public static void add(Updatable updatable, Priority priority) {
        if(!updatables.containsKey(priority)) updatables.put(priority, new ArrayList<>());
        ArrayList<Updatable> priorities = updatables.get(priority);
        priorities.add(updatable);
    }
    
    /**
     * @param obj updatable to be removed from the updater
     */
    public static void remove(Updatable obj) {
    	for(ArrayList<Updatable> objects : updatables.values()) {
    		objects.remove(obj);
    	}
    }
    
    /**
     * Update the loop time and all of the updatables
     */
    public static void loop() {
    	loopTime=getTime()-lastLoop;
    	lastLoop=getTime();
        for(ArrayList<Updatable> us : updatables.values()) {
            for(Updatable u : us) {
                u.update();
            }
        }
    }
    
    /**
     * @return the current time in miliseconds
     */
	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}
    
    /**
     * @return the current time in seconds
     */
	public static double getTime() {
		return getTimeMillis() / 1000.0;
	}
    
    /**
     * @return the time it takes per loop
     */
	public static double getLoopTime()
	{
		return loopTime;
	}

}
