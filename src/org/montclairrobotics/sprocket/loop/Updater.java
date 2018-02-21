package org.montclairrobotics.sprocket.loop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

import org.montclairrobotics.sprocket.jrapoport.Updatable;

public class Updater {
	
	private static double lastLoop = getTimeSec();
	private static double loopTime = 1.0 / 50.0;
	
    private static TreeMap<Priority, ArrayList<Updatable>> updatables = new TreeMap<>(new Comparator<Priority>() {
        @Override
        public int compare(Priority p1, Priority p2) {
            if(p1.getLevel() > p2.getLevel()) {
                return -1;
            } else if(p1.getLevel() == p2.getLevel()) {
                return 0;
            }
            return 1;
        }
    });



    public static void add(Updatable updatable, Priority priority) {
        if(!updatables.containsKey(priority)) { updatables.put(priority, new ArrayList<Updatable>()); }
        	ArrayList<Updatable> priorities = updatables.get(priority);
        priorities.add(updatable);
    }
    
    public static void remove(Updatable obj) {
	    	for(ArrayList<Updatable> objects : updatables.values()) {
	    		objects.remove(obj);
	    	}
    }
    
    public static void loop() {
	    	loopTime = getTimeSec() - lastLoop;
	    	lastLoop = getTimeSec();
        for(ArrayList<Updatable> us : updatables.values()) {
            for(Updatable u : us) {
                u.update();
            }
        }
    }

	public static long getTimeMillis() {
		return System.currentTimeMillis();
	}
	
	@Deprecated
	public static double getTime() {
		return getTimeMillis() / 1000.0;
	}
	
	public static double getTimeSec() {
		return getTimeMillis() / 1000.0;
	}
	
	public static double getLoopTime() {
		return loopTime;
	}
	
	public static double getFPS() {
		return 1 / loopTime;
	}

}
