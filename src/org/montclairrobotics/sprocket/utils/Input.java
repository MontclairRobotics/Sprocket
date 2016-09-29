package org.montclairrobotics.sprocket.utils;

import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

public abstract class Input <T> implements Updatable{
	
	private T val;
	
	public Input()
	{
		Updater.add(this,Priority.INPUT);
	}
	public abstract T getRaw();
	public T get()
	{
		return val;
	}
	public void update()
	{
		val=getRaw();
	}
}
