package org.montclairrobotics.sprocket.loop;

import java.util.ArrayList;

@Deprecated
public class DisabledUpdater {
	public static ArrayList<Updatable> updatables = new ArrayList<Updatable>();
	
	public static void add(Updatable u) {
		updatables.add(u);
	}
	public static void loop() {
		for(Updatable u : updatables) {
			u.update();
		}
	}
}
