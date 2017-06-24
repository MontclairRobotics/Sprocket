package org.montclairrobotics.sprocket.loop;

import java.util.ArrayList;

public class DisabledUpdater {
	public static ArrayList<Updatable> updatables;
	public static void add(Updatable u)
	{
		updatables.add(u);
	}
	public static void loop()
	{
		for(Updatable u:updatables)
		{
			u.update();
		}
	}
}
