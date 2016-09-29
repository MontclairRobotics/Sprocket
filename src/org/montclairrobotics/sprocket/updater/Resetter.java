package org.montclairrobotics.sprocket.updater;

import java.util.ArrayList;

public class Resetter {
	
	private static ArrayList<Resettable> objects=new ArrayList<Resettable>();
	
	public static void add(Resettable obj) {
		objects.add(obj);
	}
	

	public static void start()
	{
		for(Resettable obj:objects)
		{
			obj.start();
		}
	}
	public static void stop()
	{
		for(Resettable obj:objects)
		{
			obj.stop();
		}
	}
}
