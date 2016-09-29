package org.montclairrobotics.sprocket.ftc;

import org.montclairrobotics.sprocket.core.SprocketRobot;
import org.montclairrobotics.sprocket.updater.Priority;
import org.montclairrobotics.sprocket.updater.Resetter;
import org.montclairrobotics.sprocket.updater.Updater;

public abstract class SprocketFTCRobot implements SprocketRobot{
	
	private String mode="";
	
	private boolean init=false;
	
	public abstract void init();
	public abstract void start();
	public abstract void update();
	public abstract void stop();
	
	public void setMode(String mode)
	{
		this.mode=mode;
	}
	public String getMode()
	{
		return mode;
	}
	
	public final void robotInit()
	{
		if(!init)
		{
			init();
			init=true;
			Resetter.add(this);
			Updater.add(this, Priority.CALC);
		}
	}
	public final void robotStart()
	{
		Resetter.start();
	}
	public final void robotLoop()
	{
		Updater.update();
	}
	public final void robotStop()
	{
		Resetter.stop();
	}
}