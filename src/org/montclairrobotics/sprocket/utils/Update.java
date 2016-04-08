package org.montclairrobotics.sprocket.utils;

import java.util.ArrayList;
public class Update
{
	private static ArrayList<Updatable> objects=new ArrayList<Updatable>();
	
	public static void add(Updatable obj)
	{
		objects.add(obj);
	}
	
	public static void update()
	{
	for(Updatable obj:objects)
		{
			obj.update();
		}
	}
}
