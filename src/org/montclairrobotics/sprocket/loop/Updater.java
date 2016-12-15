package org.montclairrobotics.sprocket.loop;

import java.util.ArrayList;
import java.util.TreeMap;

public class Updater {

    private static TreeMap<Priority, ArrayList<Updatable>> updatables = new TreeMap<>((o1, o2) -> {
            if(o1.getPriority() > o2.getPriority()) {
                return -1;
            } else if(o1.getPriority() == o2.getPriority()) {
                return 0;
            }
            return 1;
        }
    );



    public static void add(Updatable updatable, Priority priority) {
        if(!updatables.containsKey(priority)) updatables.put(priority, new ArrayList<>());
        ArrayList<Updatable> priorities = updatables.get(priority);
        priorities.add(updatable);
    }

    public static void loop() {
        for(ArrayList<Updatable> us : updatables.values()) {
            for(Updatable u : us) {
                u.update();
            }
        }
    }

}
