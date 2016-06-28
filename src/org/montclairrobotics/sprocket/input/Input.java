package org.montclairrobotics.sprocket.input;

import org.montclairrobotics.sprocket.SprocketObj;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Updatable;
import org.montclairrobotics.sprocket.updater.Updater;

public abstract class Input extends SprocketObj implements Updatable{
	private double value;
	public Input()
	{
		Updater.add(this, Priority.INPUT);
	}
	public final double get()
	{
		return value;
	}
	public final void update()
	{
		value=getInput();
	}
	public abstract double getInput();
}
