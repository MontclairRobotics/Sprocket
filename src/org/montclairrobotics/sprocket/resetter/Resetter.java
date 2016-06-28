package org.montclairrobotics.sprocket.resetter;

import java.util.ArrayList;

public class Resetter {
	private static ArrayList<Resettable> objs=new ArrayList<Resettable>();

	public enum Mode{DISABLED,TELEOP,AUTO}
	private static Mode mode;
	
	public static void add(Resettable obj)
	{
		objs.add(obj);
	}
	public static void stop()
	{
		mode=Mode.DISABLED;
		for(Resettable obj:objs)
		{
			obj.onStop();
		}
	}
	public static void startAuto()
	{
		mode=Mode.AUTO;
		for(Resettable obj:objs)
		{
			obj.startAuto();
		}
	}
	public static void startTeleop()
	{
		mode=Mode.TELEOP;
		for(Resettable obj:objs)
		{
			obj.startTeleop();
		}
	}
	public static Mode getMode()
	{
		return mode;
	}
}
